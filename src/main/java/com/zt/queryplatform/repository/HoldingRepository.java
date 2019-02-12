package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Holding;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * created by linzj on 2018/12/23
 **/
public interface HoldingRepository extends CrudRepository<Holding,Long> {


    //馆藏列表
    List<Holding> findAllByOwnlibAndBookId(Long libraryId,Long bookId);

    Holding findHoldingById(Long holdingId);

    Holding findHoldingByIdAndOwnlibAndCurlib(Long holdingId,Long ownLib,Long curLib);
}
