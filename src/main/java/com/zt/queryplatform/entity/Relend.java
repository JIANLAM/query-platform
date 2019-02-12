package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   续借
 */
@Data
@Entity
@Table(name = "t_relend")
public class Relend  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//续借标识
    @Column(name = "lend_id")
    private Long lendId;//借阅标识
    @Column(name = "relend_time")
    private String relendTime;//续借日期
    @Column(name = "relend_back_time")
    private String relendBackTime;//续借归还日期
    @Column(name = "primary_back_time")
    private String primaryBackTime;//原应还书日期

}
