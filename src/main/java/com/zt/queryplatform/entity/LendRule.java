package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 借阅规则表
 */
@Entity
@Data
@Table(name = "t_lend_rule")
public class LendRule {
    /**
     * 借阅规则标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 规则代码 
     */
    @Column(name ="rule_code")
    private String ruleCode;
    /**
     * 规则名称
     */
    @Column(name ="rule_name")
    private String ruleName;
    /**
     * 0 管内借阅；1 馆际借阅
     */
    @Column(name ="rule_type")
    private Integer ruleType;
    /**
     * 借阅册数
     */
    @Column(name ="lend_qty")
    private Integer lendQty;
    /**
     * 允许借阅天数
     */
    @Column(name ="lend_days")
    private Integer lendDays;
    /**
     * 续借次数
     */
    @Column(name ="renew_qty")
    private Integer renewQty;
    /**
     * 续借天数
     */
    @Column(name ="renew_days")
    private Integer renewDays;
    /**
     * 逾期罚款率
     */
    @Column(name ="overdue_payrate")
    private Float overduePayrate;
    /**
     * 丢失罚款率
     */
    @Column(name ="lost_payrate")
    private Float lostPayrate;
    /**
     * 污损罚款率
     */
    @Column(name ="broken_payrate")
    private Float brokenPayrate;
    /**
     * 要求信用分
     */
    private Integer credit;
    /**
     * 可借文献类型
     */
    @Column(name ="literature_type")
    private String literatureType;
    /**
     * 是否启用 1启用   0不启用
     */
    private Integer status;
    /**
     * 图书馆标识 外键 
     */
    @Column(name ="library_id")
    private Long libraryId;
    /**
     * 创建者 外键，t_user的主键
     */
    @Column(name ="create_by")
    private Long createBy;
    /**
     * 创建时间
     */
    @Column(name ="create_time")
    private Date createTime;
    /**
     * 修改者 外键，t_user的主键
     */
    @Column(name ="update_by")
    private Long updateBy;
    /**
     * 修改时间
     */
    @Column(name ="update_time")
    private Date updateTime;
    /**
     * 备注
     */
    private String remarks;

}
