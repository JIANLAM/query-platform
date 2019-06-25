package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.dto.BookDTO;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import org.springframework.data.domain.Pageable;

/**
 * created by linzj on 2018/12/21
 **/
public interface RecommService {

    ServiceMultiResult<BookDTO> findRecommendedBookList(Long libraryId, Pageable pageable);

}


