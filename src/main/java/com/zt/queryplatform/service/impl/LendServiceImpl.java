package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.*;
import com.zt.queryplatform.entity.dto.BookDTO;
import com.zt.queryplatform.entity.dto.LendDTO;
import com.zt.queryplatform.entity.dto.ReaderInfoDTO;
import com.zt.queryplatform.repository.*;
import com.zt.queryplatform.service.LendService;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * created by linzj on 2018/12/21
 **/

@Service
public class LendServiceImpl implements LendService {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReadercardRepository readercardRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private  GradeRepository gradeRepository;


    @Override
    public Rule getLendRuleByReaderId(Long readerId) {

        Reader reader = readerRepository.findOne(readerId);

        if(reader == null || StringUtils.isEmpty(reader.getReaderType().toString())){
            return null;
        }
        ReaderCard readerCard = readercardRepository.findReaderCardByMuseumRule(reader.getReaderType());

        if(readerCard == null || StringUtils.isEmpty(readerCard.getMuseumRule().toString())){
            return null;
        }

        Rule rule = ruleRepository.findOne(readerCard.getMuseumRule());

        if(rule == null){
            return null;
        }
        return rule;
    }

    @Override
    public ServiceMultiResult<BookDTO> getRankingListOfBook(Long libraryId) {
        List<Map<String, Long>> lendList = lendRepository.findLendListForHolding(libraryId);
        List<LendDTO> lendDTOList = new ArrayList<>();

        lendList.forEach(map -> {
            LendDTO lendDTO = new LendDTO();
            lendDTO.setHoldingId(map.get("holdingId"));
            //获取馆藏
            Holding holding = holdingRepository.findHoldingById(lendDTO.getHoldingId());
            //填充图书信息
            if(holding!=null){
                Book  book = bookRepository.findOne(holding.getBookId());
                lendDTO.setBook(book);
                //填充借阅次数
                int lendCount = lendRepository.countByHoldingIdAndLendLibId(lendDTO.getHoldingId(), libraryId);

                lendDTO.setLendCount(lendCount);
                lendDTOList.add(lendDTO);
            }
        });

        Map<Long, Integer> map = asynTheSameBookCount(lendDTOList);
        List<BookDTO> bookDTOList = new ArrayList<>();
        //fixme 待完善 过滤掉多余的书本
        map.forEach((aLong, integer) ->{
            Book book = bookRepository.findOne(aLong);
            BookDTO bookDTO = modelMapper.map(book,BookDTO.class);
            bookDTO.setLendCount(map.get(aLong));
            bookDTOList.add(bookDTO);
        });
        List<BookDTO> bdl = fixLendListOrderByLendCountforReader(bookDTOList);
        return new ServiceMultiResult<>(bdl.size(),bdl);
    }

    private Map<Long,Integer> asynTheSameBookCount(List<LendDTO> lendDTOList ){
        Map<Long,Integer> map = new HashMap<>();
        lendDTOList.forEach(lendDTO -> {
            Long id = lendDTO.getBook().getId();
            Integer lendCount = lendDTO.getLendCount();
           if(map.containsKey(id)){
              map.put(id,map.get(id)+lendCount);
            }else{
               map.put(id,lendCount);
           }
        });
        return map;
    }

    @Override
    public ServiceMultiResult<LendDTO> getRankingListOfReader(Long libraryId) {

        //查询借阅最多的读者
        List<Map<String, Long>> lendList = lendRepository.findLendListForReader(libraryId);
        List<LendDTO> lendDTOList = new ArrayList<>();

        lendList.forEach(map -> {
            Long readerId = map.get("readerId");
            int count = lendRepository.countByReaderIdAndLendLibId(readerId, libraryId);
            LendDTO lendDTO = new LendDTO();
            lendDTO.setLendCount(count);
            lendDTO.setReaderId(readerId);
            lendDTOList.add(lendDTO);
        });
        List<LendDTO> ldl = fixLendListOrderByLendCount(lendDTOList);

        //填充数据
        ldl.forEach(lendDTO -> {
            Reader reader = readerRepository.findOne(lendDTO.getReaderId());
            if(reader != null){
                ReaderInfoDTO readerInfoDTO = modelMapper.map(reader,ReaderInfoDTO.class);
                People people = peopleRepository.findOne(reader.getPeopleId());
                ReaderCard readerCard = readercardRepository.findOne(Long.valueOf(reader.getReaderType()));
                if(reader.getGrade()!=null){
                    Grade grade = gradeRepository.findOne(reader.getGrade());
                    readerInfoDTO.setGradeName(grade.getName());
                }
                //个人数据
                if(people!=null){
                    readerInfoDTO.setIcon(people.getIcon());
                    readerInfoDTO.setReaderName(people.getUsername());
                }
                if(readerCard!=null){
                    readerInfoDTO.setReaderTypeName(readerCard.getLabel());
                }
                lendDTO.setReaderInfoDTO(readerInfoDTO);
            }
        });
        return new ServiceMultiResult<>(ldl.size(),ldl);
    }


    //校正顺序 - 根据图书借阅次数
    private  List<BookDTO>  fixLendListOrderByLendCountforReader(List<BookDTO> list){

        List<BookDTO> bookDTOList = new ArrayList<>();
        if(list.size()==0 || list.isEmpty()){
            return null;
        }
        //fixme 对于借阅的内容进行排序
        Collections.sort(list, new Comparator<BookDTO>() {
            @Override
            public int compare(BookDTO o1, BookDTO o2) {
                return o2.getLendCount() - o1.getLendCount();
            }
        });

        int count = 0;
        for (BookDTO bookDTO : list) {
            bookDTOList.add(bookDTO);
            count ++;
            if(count>=20){
                break;
            }
        }
        return bookDTOList;
    }
    //校正顺序 - 根据读者借阅次数
    private  List<LendDTO>  fixLendListOrderByLendCount(List<LendDTO> list){

        List<LendDTO> lendDTOList = new ArrayList<>();

        if(list.size()==0 || list.isEmpty()){
            return null;
        }
        //fixme 对于借阅的内容进行排序
        Collections.sort(list, new Comparator<LendDTO>() {
            @Override
            public int compare(LendDTO o1, LendDTO o2) {
                return o2.getLendCount() - o1.getLendCount();
            }
        });

        int count = 0;
        for (LendDTO lendDTO : list) {
            lendDTOList.add(lendDTO);
            count ++;
            if(count>=20){
                break;
            }
        }
        return lendDTOList;
    }
}
