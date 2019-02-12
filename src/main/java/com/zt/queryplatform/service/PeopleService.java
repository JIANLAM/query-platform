package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.People;

/**
 * created by linzj on 2018/12/21
 **/
public interface PeopleService {

    People findPeopleByUserId(Long userId);
}
