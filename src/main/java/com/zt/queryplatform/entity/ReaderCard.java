package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 读者证实体类
 * created by linzj on 2018/12/21
 **/

@Entity
@Table(name ="t_readercard")
@Data
public class ReaderCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String value;
    private  String label;
    @Column(name = "library_id")
    private Long libraryId;
    @Column(name = "museum_rule")
    private  Long museumRule;
    @Column(name = "interlibrary_rule")
    private  Long interlibraryRule;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "create_by")
    private String createBy;
    private String remarks;
    private String type;
}
