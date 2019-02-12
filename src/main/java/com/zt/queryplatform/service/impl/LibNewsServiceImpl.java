package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.LibNews;
import com.zt.queryplatform.entity.User;
import com.zt.queryplatform.repository.LibNewsRepository;
import com.zt.queryplatform.repository.UserRepository;
import com.zt.queryplatform.service.LibNewsService;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import com.zt.queryplatform.service.common.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by linzj on 2018/12/24
 **/
@Service
public class LibNewsServiceImpl implements LibNewsService {

    @Autowired
    private LibNewsRepository libNewsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ServiceMultiResult<LibNews> getLibNewsList(Long libraryId) {

        //先获取所有该机构下用户的列表
        List<User> userList = userRepository.findAllByOrgId(libraryId);
        List<Long> userIdList = new ArrayList<>();
        if(userList == null){
            return new ServiceMultiResult<>(0,new ArrayList<>());
        }

        userList.forEach(user -> {
            userIdList.add(user.getId());
        });
        if(userIdList == null){
            return new ServiceMultiResult<>(0,new ArrayList<>());
        }
        List<LibNews> libNewsList = libNewsRepository.findAllByCreateByInOrderByCreateTimeDesc(userIdList);
        if(libNewsList == null){
            return new ServiceMultiResult<>(0,new ArrayList<>());
        }

        return new ServiceMultiResult<>(libNewsList.size(),libNewsList);
    }

    @Override
    public ServiceResult<LibNews> getLibNewsDetail(Long id) {

        LibNews libNews = libNewsRepository.findOne(id);

        if(libNews == null){
            return new ServiceResult<>(false,"cant not find this libNews detail!");
        }

        return  ServiceResult.of(libNews);
    }
}
