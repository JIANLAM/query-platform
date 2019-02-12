package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.LibNews;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * created by linzj on 2018/12/24
 **/
public interface LibNewsRepository extends CrudRepository<LibNews,Long> {


    //根据userId列表查询管理资讯
    List<LibNews> findAllByCreateByInOrderByCreateTimeDesc(List<Long> userIdList);

    @Override
    LibNews findOne(Long aLong);
}
