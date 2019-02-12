package com.zt.queryplatform.repository;

import com.zt.queryplatform.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * created by linzj on 2018/12/19
 **/
public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findRoleById(Long roleId);
}
