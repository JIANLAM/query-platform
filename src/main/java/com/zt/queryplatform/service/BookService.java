package com.zt.queryplatform.service;

import com.zt.queryplatform.entity.Book;
import com.zt.queryplatform.entity.dto.BookDTO;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import com.zt.queryplatform.service.common.ServiceResult;
import org.springframework.data.domain.Pageable;

/**
 * created by linzj on 2018/12/20
 * 书本的服务
 **/
public interface BookService {

    //查询图书
    ServiceMultiResult<BookDTO> queryBookList(Book book, Pageable pageable);


    //获取图书列表
    ServiceMultiResult<BookDTO> getBookList( Long libraryId, int start,int size);

    //获取图书详情

    ServiceResult<BookDTO>  getBookInfoDetail(Long libraryId,Long BookId);
}
