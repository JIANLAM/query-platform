package com.zt.queryplatform.service;


import com.zt.queryplatform.entity.dto.ReaderInfoDTO;
import com.zt.queryplatform.service.common.ServiceResult;

/**
 * created by linzj on 2018/12/20
 **/
public interface ReaderService {

    //获取读者个人信息
    ServiceResult<ReaderInfoDTO> getReaderInfo(Long userId, Long library);

    //续借
    ServiceResult renewBookOperation(Long userId,Long library,Long holdingId,Long lendId);
}
