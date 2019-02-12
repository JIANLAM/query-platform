package com.zt.queryplatform.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;

/**
 * created by linzj on 2018.12.21
 */
@Entity
@Table(name ="t_lend_rule")
@Data
public class Rule {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;     //借阅规则表
        @Column(name = "rule_code")
        private String ruleCode;        //规则代码
        @Column(name = "rule_name")
        private String ruleName;   //规则名称
        @Column(name = "rule_type")
        private Integer ruleType;   //规则类别 0 管内借阅；1 馆际借阅
        @Builder.Default
        @Column(name = "lend_qty")
        private Integer lendQty = 0;   //借阅册数
        @Builder.Default
        @Column(name = "lend_days")
        private Integer lendDays = 0;   //允许借阅天数
        @Builder.Default
        @Column(name = "renew_qty")
        private Integer renewQty = 0;   //续借次数
        @Builder.Default
        @Column(name = "renew_days")
        private Integer renewDays = 0;   //续接天数
        @Column(name = "overdue_payrate")
        private float overduePayrate;    //逾期罚款率 改为罚款金额
        @Column(name = "lost_payrate")
        private float lostPayrate;    //丢失罚款率
        @Column(name = "broken_payrate")
        private float brokenPayrate;    //污损罚款率
        @Builder.Default
        private Integer credit = 0;      //要求信用分
        @Column(name = "literature_type")
        private String literatureType;    //可借文献类型
        private Integer status;     //是否启用 1是  0不启用
        @Column(name = "library_id")
        private Long libraryId;       //图书馆标识
        @Column(name = "create_by")
        private Long createBy;       //创建者
        @Column(name = "create_time")
        private String createTime;       //创建时间
        @Column(name = "update_by")
        private Long updateBy;       //修改者
        @Column(name = "update_time")
        private String updateTime;       //修改时间
        private String remarks;         //备注
}