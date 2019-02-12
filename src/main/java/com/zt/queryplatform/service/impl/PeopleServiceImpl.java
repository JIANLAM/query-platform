package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.People;
import com.zt.queryplatform.repository.PeopleRepository;
import com.zt.queryplatform.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by linzj on 2018/12/21
 **/
@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public People findPeopleByUserId(Long userId) {
        return peopleRepository.findPeopleByUserId(userId);
    }
}
