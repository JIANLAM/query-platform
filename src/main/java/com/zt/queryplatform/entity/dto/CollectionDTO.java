package com.zt.queryplatform.entity.dto;


import lombok.Data;

/**
 * created by linzj on 2018/12/25
 **/
@Data
public class CollectionDTO {
    private Long id;
    private Long preBookId;
    private Long readerId;
    private String createTime;
    private Integer collectFrom;
    private Long userId;
    private Long libraryId;
}
