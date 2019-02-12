package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_prebook")
public class Prebook {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    private String pic;//图片
    private String title;//书名
    private String author; //作者
    private String summary;//概要
    private String publisher;//出版社
    private String pubdate;//出版日期
    @Column(name = "book_type")
    private String bookType;//书的分类
    private String isbn;//isbn
    private Float price;//单价
    private String pages;//书页数
    @Column(name = "book_size")
    private String bookSize;//书的开本
    @Column(name = "in_type")
    private Integer inType;//采访类别
    private Integer status;//0未购买，状态1购买中，2已入藏
    @Column(name = "create_by")
    private Long createBy;//创建者
    @Column(name = "create_time")
    private String createTime;//create_time
    private Integer source;//1新华集团2本馆图书3豆瓣图书4成员馆图书
    @Column(name = "goods_code")
    private String goodsCode;//货物代码
    @Column(name = "storage_time")
    private String storageTime;//入库时间
    @Column(name = "series_title")
    private String seriesTitle;//从题名
    @Column(name = "second_title")
    private String secondTitle;//副题名
    @Column(name = "subject_word")
    private String subjectWord;//主题词
    @Column(name = "first_duty")
    private String firstDuty;//第一责任人
    private String revision;//版次
    private String language;//语种
    @Column(name = "carrier_type")
    private String carrierType;//载体类型:图书、报刊、录相带、光碟
    private String binding;//装帧类型：平装、精装
    @Column(name = "parallel_title")
    private String parallelTitle;//并列题名
    @Column(name = "pub_area")
    private String pubArea;//出版地
    private long ownlib;//图书馆id

  }