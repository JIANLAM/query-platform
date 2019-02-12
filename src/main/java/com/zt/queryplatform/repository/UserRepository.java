package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * created by linzj on 2018/12/18
 **/
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    User findOne(Long aLong);

    //通过用户名查询用户
    User findUserByUserName(String username);

    //获取图书馆下的所有用户
    List<User> findAllByOrgId(Long libraryId);
}
