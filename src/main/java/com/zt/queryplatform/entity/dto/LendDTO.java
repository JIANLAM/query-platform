package com.zt.queryplatform.entity.dto;

import com.zt.queryplatform.entity.Book;
import lombok.Data;

/**
 * 借阅表数据传输类
 */
@Data
public class LendDTO {

    private Long id;//借阅标识
    private Long holdingId;//馆藏标识
    private String lendTime;//借阅日期
    private String backTime;//归还日期
    private String dueBackTime;//应还书日期
    private Integer lendStatus;//借阅状态0在借，1已还，2续借，3逾期
    private Integer renew;//是否续借过，0 否；1 是
    private Long readerId;//读者id
    private Long userId;//用户id，操作员
    private Long lendLibId;//借书图书管id
    private Long backLibId;//还书图书管id

    private Integer lendStatusArr[];//状态数组
    private Long bookTableId;//图书id
    private String ownlibName;  //所属图书馆名称
    private String barcode;//图书馆条码
    private String title;//书名
    private String callNo;//索书号
    private String isbn;//isbn
    private Float price;//单价
    private Integer relendNum;//续借次数
    private Book book; //书
    private ReaderInfoDTO readerInfoDTO;
    private  Integer lendCount;// 借阅次数
    private String lendStatusName;//借阅状态名

}
