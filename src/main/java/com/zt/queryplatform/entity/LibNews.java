package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 最新资讯实体
 */
@Data
@Table(name = "t_libnews")
@Entity
public class LibNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String title;
    private String content;
    private Long type;      //资讯类型 sys_basicparam外键
    @Column(name = "libnews_view")
    private String libnewsView;
    @Column(name = "libnews_img")
    private String libNewsImg;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "create_by")
    private Long createBy;
}
