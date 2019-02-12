package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name ="t_credit")
@Data
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户id
     */
    @Column(name ="user_id")
    private Long userId;
    /**
     * 默认值
     */
    @Column(name ="default_value")
    private Integer defaultValue;
    /**
     * 剩余值
     */
    @Column(name ="own_value")
    private Integer ownValue;
    /**
     * 状态0禁用1启用
     */
    private Integer status;
    /**
     * 阅读卡状态0禁用1启用
     */
    @Column(name ="card_status")
    private Integer cardStatus;
    /**
     * 借购启用0禁用1启用
     */
    @Column(name ="islend_buy")
    private Integer islendBuy;
    /**
     * 备注
     */
    private String summary;

}
