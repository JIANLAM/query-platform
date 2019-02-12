package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.Organization;
import com.zt.queryplatform.entity.Role;
import com.zt.queryplatform.entity.User;
import com.zt.queryplatform.entity.UserRole;
import com.zt.queryplatform.repository.OrganizationRepository;
import com.zt.queryplatform.repository.RoleRepository;
import com.zt.queryplatform.repository.UserRepository;
import com.zt.queryplatform.repository.UserRoleRepository;
import com.zt.queryplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by linzj on 2018/12/18
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public User getUserByUserName(String loginName) {
        return userRepository.findUserByUserName(loginName);
    }

    @Override
    public User getUserById(Long userId,String readerCardNum,Long libraryId) {

        User user = userRepository.findOne(userId);
        if(user == null){
            return null;
        }

        UserRole userRole = userRoleRepository.findUserRoleByUserId(user.getId());

        if(userRole == null){
            throw  new DisabledException("not permission！");
        }

        Role role = roleRepository.findRoleById(userRole.getRoleId());

        if(role == null){
            throw   new DisabledException("not permission！");
        }

        //用户授权
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));

        //添加到对应的用户对象里
        user.setAuthorityList(authorities);

        user.setRemark(readerCardNum);

        Organization organization = organizationRepository.findOne(libraryId);

        user.setCreateName(organization.getName());

        return user;
    }
}
