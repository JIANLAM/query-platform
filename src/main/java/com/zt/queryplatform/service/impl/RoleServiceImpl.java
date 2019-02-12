package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.Role;
import com.zt.queryplatform.repository.RoleRepository;
import com.zt.queryplatform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by linzj on 2018/12/19
 **/

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleById(Long roleId) {
        return roleRepository.findRoleById(roleId);
    }
}
