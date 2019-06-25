package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.RecommendedBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * created by linzj on 2018/12/21
 **/


public interface RecommRepository extends PagingAndSortingRepository<RecommendedBook,Long>, JpaSpecificationExecutor<RecommendedBook> {

    int countByBookIdAndPeopleId(Long peopleId, Long bookId);

    @Override
    Page<RecommendedBook> findAll(Specification<RecommendedBook> specification, Pageable pageable);
}
