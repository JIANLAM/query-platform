package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * created by linzj on 2018/12/20
 **/
public interface BookRepository extends PagingAndSortingRepository<Book,Long>, JpaSpecificationExecutor<Book>{

    Page<Book> findAllByOwnlib(Long libraryId,Pageable pageable);

    Book findBookByOwnlibAndId(Long libraryId,Long bookId);

    @Override
    Book findOne(Long aLong);

    @Override
    Page<Book> findAll(Specification<Book> specification, Pageable pageable);

}
