package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.RecommendedBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * created by linzj on 2018/12/21
 **/


public interface RecommRepository extends PagingAndSortingRepository<RecommendedBook,Long> {

    @Query(value = "select distinct o.peopleId as peopleId ,o.bookId as bookId from RecommendedBook o where o.bookFrom =:bookFrom order by createTime desc")
    List<Map<String,Long>> findRecommendedBooks(@Param("bookFrom") int bookFrom);

    int countByBookIdAndPeopleId(Long peopleId,Long bookId);
}
