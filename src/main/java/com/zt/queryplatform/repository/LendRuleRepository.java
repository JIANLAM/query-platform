package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.LendRule;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/24
 **/
public interface LendRuleRepository extends CrudRepository<LendRule,Long> {

    @Override
    LendRule findOne(Long aLong);
}
