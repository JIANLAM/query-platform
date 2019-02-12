package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.repository.OrganizationRepository;
import com.zt.queryplatform.service.OrginazationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by linzj on 2019/1/24
 **/
@Service
public class OrginazationServiceImpl implements OrginazationService {


    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Long findOrgIdByCode(String code) {
        return organizationRepository.findOrganizationByCode(code);
    }
}
