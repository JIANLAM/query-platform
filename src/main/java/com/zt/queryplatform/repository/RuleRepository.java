package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Rule;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/21
 **/
public interface RuleRepository extends CrudRepository<Rule,Long> {

    @Override
    Rule findOne(Long aLong);
}
