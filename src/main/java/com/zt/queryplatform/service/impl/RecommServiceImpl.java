package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.Book;
import com.zt.queryplatform.entity.dto.BookDTO;
import com.zt.queryplatform.entity.dto.LendDTO;
import com.zt.queryplatform.entity.dto.RecommendedBookDTO;
import com.zt.queryplatform.repository.BookRepository;
import com.zt.queryplatform.repository.RecommRepository;
import com.zt.queryplatform.service.RecommService;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * created by linzj on 2018/12/21
 **/

@Service
public class RecommServiceImpl implements RecommService {

    @Autowired
    private RecommRepository recommRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ServiceMultiResult<BookDTO> findRecommendedBookList(Long libraryId) {

        //分页参数设置
        List<RecommendedBookDTO> list = new ArrayList<>();
        List<Map<String,Long>> recommendedBooks = recommRepository.findRecommendedBooks(0);
        if(recommendedBooks.size() <= 0){
            return new ServiceMultiResult<>(0,new ArrayList<>());
        }
        recommendedBooks.forEach(rb ->{
            RecommendedBookDTO recommendedBookDTO=new RecommendedBookDTO();
            recommendedBookDTO.setPeopleId(rb.get("peopleId"));
            recommendedBookDTO.setBookId(rb.get("bookId"));
            int count = recommRepository.countByBookIdAndPeopleId( recommendedBookDTO.getBookId(),recommendedBookDTO.getPeopleId());
            //查询本馆图书
             Book book = bookRepository.findBookByOwnlibAndId(libraryId,recommendedBookDTO.getBookId());
            //设置推荐类实体
            if(count >= 0){
                recommendedBookDTO.setRecommNumber(count);
            }
            if(book != null){
                recommendedBookDTO.setBook(book);
            }
            list.add(recommendedBookDTO);
        });
        Map<Long, Integer> map = asynTheSameBookCount(list);
        List<BookDTO> bookDTOList = new ArrayList<>();

        //fixme 待完善 过滤掉多余的书本
        map.forEach((aLong, integer) ->{
            Book book = bookRepository.findOne(aLong);
            BookDTO bookDTO = modelMapper.map(book,BookDTO.class);
            bookDTO.setRecommendedNum(map.get(aLong));
            bookDTOList.add(bookDTO);
        });
        sortByRecommCount(bookDTOList);
        return new ServiceMultiResult<>(bookDTOList.size(),bookDTOList);
    }

    //fixme 对重复数据进行过滤
    private Map<Long,Integer> asynTheSameBookCount(List<RecommendedBookDTO> RecommendedBookList ){
        Map<Long,Integer> map = new HashMap<>();
        RecommendedBookList.forEach(recommendedBookDTO -> {
            Long id = recommendedBookDTO.getBookId();
            Integer recommNumber = recommendedBookDTO.getRecommNumber();
            if(map.containsKey(id)){
                map.put(id,map.get(id)+recommNumber);
            }else{
                map.put(id,recommNumber);
            }
        });
        return map;
    }

    //对数据进行校正
    private void  sortByRecommCount(List<BookDTO> list){

        if(list.size()==0 || list.isEmpty()){
            return;
        }
        //fixme 对于推荐的内容进行排序
        Collections.sort(list, new Comparator<BookDTO>() {
            @Override
            public int compare(BookDTO o1, BookDTO o2) {
                return o2.getRecommendedNum()-o1.getRecommendedNum();
            }
        });
    }
}
