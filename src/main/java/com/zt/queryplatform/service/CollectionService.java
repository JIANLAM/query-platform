package com.zt.queryplatform.service;

import com.zt.queryplatform.service.common.ServiceResult;

/**
 * 收藏
 * created by linzj on 2018/12/25
 * Merry Christmas
 **/
public interface CollectionService {

    ServiceResult addToMyCollection(Long libraryId,Long bookId);
}
