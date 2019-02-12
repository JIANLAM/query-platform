package com.zt.queryplatform.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 推荐图书实体类
 */
@Data
@Entity
@Table(name ="t_recomm_book")
public class RecommendedBook{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "people_id")
    private Long peopleId;
    @Column(name = "recomm_type")
    private Integer recommType;//0导师推荐，1读者推荐，2猜你喜欢
    @Column(name = "book_from")
    private Integer bookFrom;//0本馆，1成员馆，2新华
    @Column(name = "recomm_reason")
    private String recommReason;
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "create_time")
    private String createTime;
}
