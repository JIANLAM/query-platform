package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/19
 **/
public interface UserRoleRepository extends CrudRepository<UserRole,Long> {

    UserRole findUserRoleByUserId(Long userId);
}
