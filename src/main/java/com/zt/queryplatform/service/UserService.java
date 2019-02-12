package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.User;

/**
 * created by linzj on 2018/12/18
 **/


public interface UserService {

    User getUserByUserName(String loginName);

    User getUserById(Long userId,String readerCardNum,Long libraryId);
}
