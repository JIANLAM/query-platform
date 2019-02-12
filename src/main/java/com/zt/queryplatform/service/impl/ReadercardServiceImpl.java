package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.ReaderCard;
import com.zt.queryplatform.repository.ReadercardRepository;
import com.zt.queryplatform.service.ReadercardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by linzj on 2018/12/21
 **/
@Service
public class ReadercardServiceImpl implements ReadercardService {

    @Autowired
    private ReadercardRepository readercardRepository;

    @Override
    public ReaderCard findReadercardByRedaerType(Long readerType) {
        return readercardRepository.findReaderCardByMuseumRule(readerType);
    }
}
