package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 级实体类
 */
@Data
@Entity
@Table(name = "t_grade")
public class Grade  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lib_id")
    private Long libId;
    @Column(name = "readercard_id")
    private Long readerCardId;
    private String type;
    private String code;
    private String name;
    private Integer level;
    @Column(name = "lend_rule")
    private Long lendRule;
    private Integer graduate;
    @Column(name = "graduate_time")
    private String graduateTime;
    private String remark;

}
