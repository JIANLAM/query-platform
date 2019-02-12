package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 馆藏地参数
 * created by linzj on 2018/12/23
 **/
@Data
@Entity
@Table(name = "sys_basicparam")
public class SysBasicParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private String label;
    private String type;
    private String description;
    @Column(name = "library_id")
    private Long libraryId;
    @Column(name = "create_date")
    private Date createTime;
    @Column(name = "create_by")
    private Long createBy;
    private String remarks;

}
