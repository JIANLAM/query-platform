package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.UserRole;

/**
 * created by linzj on 2018/12/19
 **/
public interface UserRoleService {

    UserRole findUserRoleByUserId(Long userid);
}
