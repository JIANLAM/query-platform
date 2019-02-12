package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.Holding;

import java.util.List;

/**
 * 馆藏业务层
 * created by linzj on 2018/12/23
 **/
public interface HoldingService {

     List<Holding> getHoldingList(Long libraryId,Long bookId);
}
