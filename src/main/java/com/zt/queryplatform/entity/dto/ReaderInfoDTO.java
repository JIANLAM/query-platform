package com.zt.queryplatform.entity.dto;

import com.zt.queryplatform.entity.LendRule;
import lombok.Data;

import java.util.List;

/**
 * 读者信息传输类
 */
@Data
public class ReaderInfoDTO{

    private Long readerId;//读者ID
    private String libraryName;//图书馆名称
    private String readerCardNumber;//读者证号
    private String readerName;//读者姓名
    private String endDate;//证件有效期限
    private Integer status;//状态0禁用1正常2注销3挂失
    private String startusName; //证件状态名
    private String readerTypeName;//读者证类别
    private String gradeName;
    private Integer lendNum;// 可借
    private Integer borrow;// 已借
    private Double arrear;//欠款
    private String icon;//读者头像
    private String IDCard;//身份证号
    private Integer ownValue;//信用分
    private Long userId;//用户id
    private String logionName;//登录名
    private String startDate;//证件启用日期
    private Double prePayment;//预付款
    private Long libraryId;//图书馆ID
    private Integer lendDays;//可借阅天数
    private Long readerType;//读者证类型 外键
    private List<LendDTO> lendBookList;//在借 图书信息
    private String ruleTypeName;//借阅规则类型名称
    private LendRule lendRule; //借阅规则
    private String lendStatusName;

}
