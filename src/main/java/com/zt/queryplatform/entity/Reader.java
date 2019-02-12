package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="t_reader")
@Data
public class Reader{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 个人信息id
     */
    @Column(name ="people_id")
    private Long peopleId;
    /**
     * 证件类型 0读书证
     */
    @Column(name ="reader_card_type")
    private Integer readerCardType;
    /**
     * 证件号码
     */
    @Column(name ="reader_card_number")
    private String readerCardNumber;
    /**
     * 状态0禁用1正常2注销3挂失
     */
    private Integer status;
    /**
     * 读者类型
     */
    @Column(name ="reader_type")
    private Long readerType;
    /**
     * 年级
     */
    private Long grade;
    /**
     * 班级
     */
    private Long classes;
    /**
     * 备注
     */
    private String remark;
    /**
     * 学号工号
     */
    @Column(name ="stuwork_number")
    private String stuworkNumber;
    /**
     * 所属图书馆id
     */
    @Column(name ="library_id")
    private Long libraryId;
    /**
     * 预付款
     */
    @Column(name ="pre_payment")
    private Double prePayment;
    /**
     * 欠款
     */
    private Double arrear;
    /**
     * 读者图片
     */
    private String pic;
    /**
     * 启用日期
     */
    @Column(name ="start_date")
    private Date startDate;
    /**
     * 终止日期
     */
    @Column(name ="end_date")
    private Date endDate;
    /**
     * 办证时间
     */
    @Column(name ="create_date")
    private Date createDate;
}
