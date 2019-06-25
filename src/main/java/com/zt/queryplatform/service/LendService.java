package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.Rule;
import com.zt.queryplatform.entity.dto.BookDTO;
import com.zt.queryplatform.entity.dto.LendDTO;
import com.zt.queryplatform.entity.dto.LendOrderDTO;
import com.zt.queryplatform.service.common.ServiceMultiResult;

import java.util.Map;


/**
 * created by linzj on 2018/12/21
 **/


public interface LendService {

    Rule getLendRuleByReaderId(Long readerId);

    /**
     * 图书排行
     * @param libraryId
     * @return
     */
    ServiceMultiResult<LendOrderDTO> getRankingListOfBook(Long libraryId) throws Exception;

    /**
     * 读者排行
     * @param libraryId
     * @return
     */
    ServiceMultiResult<LendDTO> getRankingListOfReader(Long libraryId);

}
