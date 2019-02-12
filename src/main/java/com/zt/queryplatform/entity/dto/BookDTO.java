package com.zt.queryplatform.entity.dto;

import lombok.Data;
import java.util.List;

/**
 * 书的数据传输对象
 */
@Data
public class BookDTO {
    private Long id;
    private String pic;//书封面
    private String title;//书名
    private String author;//作者
    private String summary;//概要
    private String pubArea;//出版地区
    private String publisher;//出版社
    private String pubdate;//出版日期
    private String bookType;//书的类目
    private String isbn;//isbn
    private Float price;//单价
    private Long createBy;//创建者
    private String createTime;//创建时间
    private String pages;//页数
    private Integer bookrecno;//图创id
    private String rowid;//图创id
    private String seriesTitle;//从题名
    private String secondTitle;//副题名
    private String subjectWord;//主题词
    private String firstDuty;//第一责任人
    private String revision;//版次
    private String language;//语种
    private String carrierType;//载体类型:图书、报刊、录相带、光碟
    private String binding;//装帧类型：平装、精装
    private String bookSize;//书的开本:32开
    private String parallelTitle;//并列题名
    private String callNo;//索书号
    private Long ownlib;//图书馆id
    private String divisionNumber;//分册号
    private String otherDuty;//其他负责人
    private String currency;//币种:0 RMB人民币   1USD美元  2JPY日元  3EUR欧元   4HKD港币   5KRW韩国元
    private String divisionName;//分辑名
    private String priceTest;
    private int dupNum; //复本数
    private List<HoldingDTO> holdingList;//全部馆藏列表
    private List<HoldingDTO> canBorrowHoldingList;//可外借的馆藏列表
    private List<HoldingDTO> isAlreadyBorrowHoldingList;//已经外借的馆藏类表
    private Integer lendCount;//借阅次数
    private String belongLibrary;//所属馆
    private int onShelfNum;
    private int recommendedNum;

}
