package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.*;
import com.zt.queryplatform.entity.dto.LendDTO;
import com.zt.queryplatform.entity.dto.LendOrderDTO;
import com.zt.queryplatform.entity.dto.ReaderInfoDTO;
import com.zt.queryplatform.repository.*;
import com.zt.queryplatform.service.LendService;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * created by linzj on 2018/12/21
 **/

@Service
public class LendServiceImpl implements LendService {

    private final ReaderRepository readerRepository;

    private final ReadercardRepository readercardRepository;

    private final RuleRepository ruleRepository;

    private final LendRepository lendRepository;

    private final ModelMapper modelMapper;

    private final HoldingRepository holdingRepository;

    private final BookRepository bookRepository;

    private final PeopleRepository peopleRepository;

    private final GradeRepository gradeRepository;

    @Autowired
    public LendServiceImpl(ReaderRepository readerRepository, ReadercardRepository readercardRepository, RuleRepository ruleRepository, LendRepository lendRepository, ModelMapper modelMapper, HoldingRepository holdingRepository, BookRepository bookRepository, PeopleRepository peopleRepository, GradeRepository gradeRepository) {
        this.readerRepository = readerRepository;
        this.readercardRepository = readercardRepository;
        this.ruleRepository = ruleRepository;
        this.lendRepository = lendRepository;
        this.modelMapper = modelMapper;
        this.holdingRepository = holdingRepository;
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
        this.gradeRepository = gradeRepository;
    }


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
    public ServiceMultiResult<LendOrderDTO> getRankingListOfBook(Long libraryId){
        List<Object[]> objects = lendRepository.findLendListForHolding(libraryId);

        List<LendOrderDTO> list = new ArrayList<>(16);
        try {
            list = transformEntity(objects, LendOrderDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list == null){
            return new ServiceMultiResult<>(0,new ArrayList<>());
        }
        return new ServiceMultiResult<>(list.size(),list);
    }

    //转换实体类
    private  <T>  List<T> transformEntity(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<T>();
        Object[] co = list.get(0);
        Class[] c2 = new Class[co.length];
        //确定构造方法
        for (int i = 0; i < co.length; i++) {
            c2[i] = co[i].getClass();
        }
        for (Object[] o : list) {
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }
        return returnList;
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


        if (ldl == null){
            return new ServiceMultiResult<>(0,new ArrayList<>());
        }
        //填充数据
        ldl.forEach(lendDTO -> {
            Reader reader = readerRepository.findOne(lendDTO.getReaderId());
            if(reader != null){
                ReaderInfoDTO readerInfoDTO = modelMapper.map(reader, ReaderInfoDTO.class);
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


    /**
     *  校正顺序 - 根据读者借阅次数
     */
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
