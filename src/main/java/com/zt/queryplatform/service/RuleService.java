package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.dto.LendDTO;

/**
 * created by linzj on 2018/12/21
 **/
public interface RuleService {
    LendDTO getLendRuleByReaderId(Long ruleId);
}
