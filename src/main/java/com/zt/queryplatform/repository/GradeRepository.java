package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Grade;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/25
 **/

public interface GradeRepository extends CrudRepository<Grade,Long> {

    @Override
    Grade findOne(Long aLong);
}
