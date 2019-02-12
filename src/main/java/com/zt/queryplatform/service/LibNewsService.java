package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.LibNews;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import com.zt.queryplatform.service.common.ServiceResult;

/**
 * 馆内资讯
 * created by linzj on 2018/12/22
 **/
public interface LibNewsService {


    //馆内资讯列表
    ServiceMultiResult<LibNews> getLibNewsList(Long libraryId);

    //馆内资讯详情
    ServiceResult<LibNews> getLibNewsDetail(Long id);
}
