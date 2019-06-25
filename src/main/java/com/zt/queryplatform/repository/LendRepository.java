package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Lend;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * created by linzj on 2018/12/24
 **/
public interface LendRepository extends PagingAndSortingRepository<Lend,Long>, JpaSpecificationExecutor<Lend> {

    List<Lend> getAllByLendLibIdAndReaderIdAndLendStatus(Long libraryId, Long readerId, Integer lendStatus);

    List<Lend> getAllByLendLibIdAndReaderIdOrderByLendTimeDesc(Long libraryId, Long readerId);


    @Query(value = "select distinct o.readerId as readerId from Lend o where o.lendLibId =:libraryId")
    List<Map<String,Long>> findLendListForReader(@Param("libraryId") Long libraryId);

    @Query(value = "SELECT b.title,b.ISBN,b.call_no as callNo,b.author,b.publisher,b.price,count(1) lendCount FROM t_lend l " +
                    "INNER JOIN t_holding h on l.holding_id = h.id INNER JOIN t_book b on h.book_id = b.id " +
                    "INNER JOIN t_reader r on l.reader_id = r.id  where l.lend_lib_id = ?1 GROUP BY b.id" +
                    " ORDER BY lendCount desc",nativeQuery = true)
    List<Object[]>  findLendListForHolding(Long id);

    @Override
    Lend findOne(Long aLong);

    @Modifying
    @Query(value = "update Lend l set l.renew = :renew,l.lendStatus =:lendStatus,l.dueBackTime =:date where l.id =:id")
    void updateLend(@Param("id") Long id, @Param("renew") int renew, @Param("lendStatus") int lendStatus, @Param("date") Date date);


    //根据馆藏id 和 图书馆id查询数目
    int countByHoldingIdAndLendLibId(Long holdingId, Long libraryId);

    //根据读者id查询书目
    int countByReaderIdAndLendLibId(Long readerid, Long libraryId);

    Lend findLendByHoldingIdAndLendStatus(Long holdingId, Integer lendStatus);

}
