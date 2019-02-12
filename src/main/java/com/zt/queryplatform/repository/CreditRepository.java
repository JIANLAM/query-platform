package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Credit;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/24
 **/
public interface CreditRepository extends CrudRepository<Credit,Long> {

    Credit findCreditByUserId(Long userId);
}
