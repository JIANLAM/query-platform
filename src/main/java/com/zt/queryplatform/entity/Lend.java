package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 借阅表数据传输类
 */
@Entity
@Data
@Table(name = "t_lend")
public class Lend {
    /**
     * 借阅标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 馆藏标识
     */
    @Column(name = "holding_id")
    private Long holdingId;
    /**
     * 借阅日期
     */
    @Column(name = "lend_time")
    private Date lendTime;
    /**
     * 归还日期
     */
    @Column(name = "back_time")
    private Date backTime;
    /**
     * 应还书日期
     */
    @Column(name = "due_back_time")
    private Date dueBackTime;
    /**
     * 借阅状态0在借，1已还，2续借，3逾期
     */
    @Column(name = "lend_status")
    private Integer lendStatus;
    /**
     * 是否续借过，0 否；1 是
     */
    private Integer renew;
    /**
     * 读者id
     */
    @Column(name = "reader_id")
    private Long readerId;
    /**
     * 用户id，操作员
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 借书图书管id
     */
    @Column(name = "lend_lib_id")
    private Long lendLibId;
    /**
     * 还书图书管id
     */
    @Column(name = "back_lib_id")
    private Long backLibId;

}