package com.zt.queryplatform.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * 馆藏数据传输类
 */
@Data
public class HoldingDTO {
    private Long id;
    private String callNo;//索书号
    private Long bookId; //书id
    private String barcode;//图书馆条码
    private Long ownlib;//所属图书馆id
    private Long curlib;//当前所在的图书馆id
    private Long shelf;//书架号
    private String indate;//入馆日期
    private Float singleprice;//单价
    private Float totalprice;//总价
    private String source;//获得方式1购买2捐赠3交换4自编
    private Integer status;//状态0可借阅,1在借中,2可阅览,3污损,4遗失,5剔除
    private String actType;//文献流通类型:
    private String rfid;//RFID号码
    private Integer recno;//图创id
    private Integer bookrecno;//图创id
    private String rowid;//图创id
    private Long colladdressId;//馆藏地Id
    private String catalogBatch;//编目批次号
    private String partition;//条码分区号
    private String volume;//分册号
    private String remark;//备注
    private String currBarcode;
    private String barcodeId;
    private String HoldingLocation;//馆藏地点
    private String statusName;//馆藏状态名
    private String actTypeName;//流通类型
    private String  dueBackTime;//应还日期
}
