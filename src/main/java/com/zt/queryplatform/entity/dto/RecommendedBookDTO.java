package com.zt.queryplatform.entity.dto;

import com.zt.queryplatform.entity.Book;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 推荐图书实体类
 */
@Data
public class RecommendedBookDTO {
    private Long id;
    private Long peopleId;
    private Integer recommType;//0导师推荐，1读者推荐，2猜你喜欢
    private Integer bookFrom;//0本馆，1成员馆，2新华
    private String recommReason;
    private Long bookId;
    private String createTime;
    private  int recommNumber; //推荐次数
    private Book book;
}
