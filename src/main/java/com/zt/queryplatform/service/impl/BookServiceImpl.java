package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.base.BorrowBookStatus;
import com.zt.queryplatform.base.HoldingStatus;
import com.zt.queryplatform.entity.*;
import com.zt.queryplatform.entity.dto.BookDTO;
import com.zt.queryplatform.entity.dto.HoldingDTO;
import com.zt.queryplatform.repository.*;
import com.zt.queryplatform.service.BookService;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import com.zt.queryplatform.service.common.ServiceResult;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * created by linzj on 2018/12/20
 **/

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private SysBasicParamRespository sysBasicParamRespository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private LendRepository lendRepository;

    @Override
    public ServiceMultiResult<BookDTO> queryBookList(Book book,Pageable pageable) {
        Page<Book> list = bookRepository.findAll(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (book.getTitle() != null && !book.getTitle().equals("")) {
                    predicates.add(cb.like(root.get("title").as(String.class), "%" + book.getTitle()+ "%"));
                }
                if (book.getIsbn() != null && !book.getIsbn().equals("")) {
                    predicates.add(cb.equal(root.get("isbn").as(String.class), book.getIsbn().replace("-","") ));
                }
                if (book.getAuthor() != null && !book.getAuthor().equals("")) {
                    predicates.add(cb.like(root.get("author").as(String.class), "%" + book.getAuthor() + "%"));
                }
                if (book.getCallNo() != null && !book.getCallNo().equals("")) {
                    predicates.add(cb.equal(root.get("callNo").as(String.class), book.getCallNo()));
                }
                if (book.getOwnlib() != null && !book.getOwnlib().equals("")) {
                    predicates.add(cb.equal(root.get("ownlib").as(String.class), book.getOwnlib()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return cb.and(predicates.toArray(pre));
            }
        },pageable);
        List<BookDTO> bookList = new ArrayList<>();

        list.forEach(b -> bookList.add(modelMapper.map(b,BookDTO.class)));

        return new ServiceMultiResult<>(list.getTotalElements(),bookList);
    }



    @Override
    public ServiceMultiResult<BookDTO> getBookList(Long libraryId, int start , int size) {

        //分页参数设置
        Pageable pageable = new PageRequest(start,size,new Sort(Sort.Direction.DESC,"pubdate"));

        Page<Book> bookPage = bookRepository.findAllByOwnlib(libraryId, pageable);
        int totalPages = bookPage.getTotalPages();

        List<BookDTO> bookList = new ArrayList<>();

        bookPage.forEach(book -> {
            BookDTO bookDTO = modelMapper.map(book,BookDTO.class);
            List<Holding> holdingList = holdingRepository.findAllByOwnlibAndBookId(libraryId, bookDTO.getId());
            //全部可借馆藏列表
            List<HoldingDTO> canBorrowHoldingList = new ArrayList<>();
            holdingList.forEach(holding -> {
                HoldingDTO holdingDTO  = modelMapper.map(holding,HoldingDTO.class);
                if(holding.getStatus() == 0){
                    canBorrowHoldingList.add(holdingDTO);
                }
            });
            bookDTO.setDupNum(holdingList.size());
            bookDTO.setOnShelfNum(canBorrowHoldingList.size());
            bookList.add(bookDTO);

        });

        return new ServiceMultiResult<>(bookPage.getTotalElements(),totalPages,start,bookList);
    }

    @Override
    public ServiceResult<BookDTO> getBookInfoDetail(Long libraryId, Long bookId) {

        Book book = bookRepository.findOne(bookId);
        BookDTO bookDTO = modelMapper.map(book,BookDTO.class);
        if(book == null){
            return ServiceResult.notFound();
        }
        List<Holding> holdingList = holdingRepository.findAllByOwnlibAndBookId(libraryId, bookId);
        //所属馆
        Organization organization = organizationRepository.findOne(bookDTO.getOwnlib());
        bookDTO.setBelongLibrary(organization.getName());
        //全部馆藏列表
        List<HoldingDTO> holdingDTOList = new ArrayList<>();
        //全部可借馆藏列表
        List<HoldingDTO> canBorrowHoldingList = new ArrayList<>();
        //全部已借馆藏列表
        List<HoldingDTO> isAlreadyBorrowHoldingList = new ArrayList<>();
        //装饰一下馆藏
        holdingList.forEach(holding -> {
            HoldingDTO hd = modelMapper.map(holding,HoldingDTO.class);
            if(hd.getColladdressId()>0){
                SysBasicParam   s = sysBasicParamRespository.getSysBasicParamById(Long.valueOf(hd.getActType()));
                SysBasicParam    s1 = sysBasicParamRespository.getSysBasicParamById(hd.getColladdressId());
                if(s1 != null){
                    hd.setHoldingLocation(s1.getLabel());
                }
               if(s != null){
                   hd.setActTypeName(s.getLabel());//流通类型名称
               }
            }
            switch (hd.getStatus().intValue()){
                case 0:
                    canBorrowHoldingList.add(hd);
                    hd.setStatusName("在架");
                    break;
                case 1:
                    Lend lend = lendRepository.findLendByHoldingIdAndLendStatus(hd.getId(),0);
                    if(lend!=null){
                        hd.setDueBackTime(DateFormatUtils.format(lend.getDueBackTime(),"yyyy-MM-dd HH:mm:ss"));
                    }
                    isAlreadyBorrowHoldingList.add(hd);
                    hd.setStatusName("借出");
                    break;
                case 2:
                    hd.setStatusName("阅览");
                    break;
                case 3:
                    hd.setStatusName("污损");
                    break;
                case 4:
                    hd.setStatusName("丢失");
                    break;
                case 5:
                    hd.setStatusName("剔除");
                    break;
                case 6:
                    hd.setStatusName("预借");
                    break;
                case 7:
                    hd.setStatusName("赠送");
                    break;
                default:
                        break;
            }
            holdingDTOList.add(hd);
        });
        bookDTO.setHoldingList(holdingDTOList);
        bookDTO.setCanBorrowHoldingList(canBorrowHoldingList);
        bookDTO.setIsAlreadyBorrowHoldingList(isAlreadyBorrowHoldingList);
        bookDTO.setDupNum(holdingDTOList.size());
        return ServiceResult.of(bookDTO);
    }


}
