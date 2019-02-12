package com.zt.queryplatform.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 收藏记录
 */
@Data
@Entity
@Table(name = "t_collection")
public class Collection{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "prebook_id")
    private Long preBookId;
    @Column(name = "reader_id")
    private Long readerId;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "collect_from")
    private Integer collectFrom;

}
