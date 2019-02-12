package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "t_organization")
public class Organization{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "parent_ids")
    private String parentIds;
    private String name;
    private String code;
    @Column(name = "linkman_phone")
    private String linkmanPhone;
    @Column(name = "linkman_name")
    private String linkmanName;
    private String city;
    private Integer type;
    private String remark;

}
