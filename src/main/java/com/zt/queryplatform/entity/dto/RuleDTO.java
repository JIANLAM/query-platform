package com.zt.queryplatform.entity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 借阅规则表
 * created by linzj on 2018.12.21
 * 对应表 t_lend_rule
 */
@Data
public class RuleDTO{

        private Long id;     //借阅规则表

        private String ruleCode;        //规则代码

        private String ruleName;   //规则名称
        @Builder.Default
        private Integer lendQty = 0;   //借阅册数
        @Builder.Default
        private Integer lendDays = 0;   //允许借阅天数
        @Builder.Default
        private Integer renewQty = 0;   //续借次数
        @Builder.Default
        private Integer renewDays = 0;   //续接天数

        private float overduePayrate;    //逾期罚款率 改为罚款金额

        private float lostPayrate;    //丢失罚款率

        private float brokenPayrate;    //污损罚款率

        private Integer ruleType;   //规则类别

        @Builder.Default
        private Integer credit = 0;      //要求信用分

        private String literatureType;    //可借文献类型

        private String literatureTypeTest;    //可借文献类型          文本

        private Integer status;     //是否启用 1是  0不启用

        private Long libraryId;       //图书馆标识

        private Long createBy;       //创建者

        private String createTime;       //创建时间

        private Long updateBy;       //修改者

        private String updateTime;       //修改时间

        private String createName;         //创建者名字

        private String updaterName;    //修改者姓名

        private String readerTypeCode;          //读者类型代码

        private String readerTypeName;          //读者类型名称

        private String remarks;         //备注

        private Long ownlib;            //所属馆

        private String ruleTypeTest;   //规则类别

        private Map errorMap;//装返回错误信息

}