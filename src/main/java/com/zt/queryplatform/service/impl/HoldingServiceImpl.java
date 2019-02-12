package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.entity.Holding;
import com.zt.queryplatform.repository.HoldingRepository;
import com.zt.queryplatform.service.HoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by linzj on 2018/12/23
 **/
@Service
public class HoldingServiceImpl implements HoldingService {

    @Autowired
    private HoldingRepository holdingRepository;
    @Override
    public List<Holding> getHoldingList(Long libraryId, Long bookId) {
        return holdingRepository.findAllByOwnlibAndBookId(libraryId,bookId);
    }
}
