package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.UserRole;
import com.zt.queryplatform.repository.UserRoleRepository;
import com.zt.queryplatform.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by linzj on 2018/12/19
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole findUserRoleByUserId(Long userid) {
        return userRoleRepository.findUserRoleByUserId(userid);
    }
}
