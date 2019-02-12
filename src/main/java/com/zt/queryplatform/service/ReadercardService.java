package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.ReaderCard;

/**
 * created by linzj on 2018/12/21
 **/
public interface ReadercardService {

    ReaderCard findReadercardByRedaerType(Long readerType);
}
