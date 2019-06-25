package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.Book;
import com.zt.queryplatform.entity.RecommendedBook;
import com.zt.queryplatform.entity.dto.BookDTO;
import com.zt.queryplatform.entity.dto.RecommendedBookDTO;
import com.zt.queryplatform.repository.BookRepository;
import com.zt.queryplatform.repository.ReaderRepository;
import com.zt.queryplatform.repository.RecommRepository;
import com.zt.queryplatform.service.RecommService;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
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

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public ServiceMultiResult<BookDTO> findRecommendedBookList(Long libraryId, Pageable pageable) {

        List<Long> peopleIdList = readerRepository.findAllByLibraryId(libraryId);

        //fixme update on 2019.4.3 设置 in 查询
        RecommendedBook recommendedBook = new RecommendedBook();
        recommendedBook.setBookFrom(0);
        Page<RecommendedBook> recommendedBookPage = recommRepository.findAll(new Specification<RecommendedBook>() {
            @Override
            public Predicate toPredicate(Root<RecommendedBook> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("bookFrom").as(Integer.class), recommendedBook.getBookFrom()));
                Expression<Long> exp = root.get("peopleId");
                predicates.add(exp.in(peopleIdList)); // 往in中添加所有id 实现in 查询
                if (peopleIdList.size() > 0){
                    Predicate[] pre = new Predicate[predicates.size()];
                    criteriaQuery.where(predicates.toArray(pre));
                    return cb.and(predicates.toArray(pre));
                }else {
                    return null;
                }
            }
        }, pageable);

        if(recommendedBookPage.getSize() <= 0){
            return new ServiceMultiResult<>(0,new ArrayList<>());
        }

        List<RecommendedBookDTO> list = new ArrayList<>();
        recommendedBookPage.forEach(rb ->{
            RecommendedBookDTO recommendedBookDTO=new RecommendedBookDTO();
            recommendedBookDTO.setPeopleId(rb.getPeopleId());
            recommendedBookDTO.setBookId(rb.getBookId());
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
        List<BookDTO> bookDTOList = new ArrayList<>();
        sortByRecommCount(asynTheSameBookCount(list, bookDTOList));
        return new ServiceMultiResult<>(bookDTOList.size(),bookDTOList);
    }

    //fixme 对重复数据进行过滤
    private List<BookDTO> asynTheSameBookCount(List<RecommendedBookDTO> RecommendedBookList , List<BookDTO> bookDTOList){
        Map<Long,Integer> map = new HashMap<>();
        RecommendedBookList.forEach(recommendedBookDTO -> {
            Long id = recommendedBookDTO.getBookId();
            Integer recommNumber = recommendedBookDTO.getRecommNumber();
            Book book = bookRepository.findOne(id);
            BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
            bookDTO.setRecommendedNum(recommNumber);
            bookDTOList.add(bookDTO);

            if(map.containsKey(id)){
                map.put(id,map.get(id)+recommNumber);
            }else{
                map.put(id,recommNumber);
            }
        });
        return bookDTOList;
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
