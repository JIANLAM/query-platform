package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.People;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/21
 **/
public interface PeopleRepository extends CrudRepository<People,Long> {

    People findPeopleByUserId(Long userId);

    @Override
    People findOne(Long aLong);
}
