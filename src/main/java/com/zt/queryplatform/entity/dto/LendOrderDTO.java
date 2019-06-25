package com.zt.queryplatform.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Author: linzj
 * @Date: 2019/6/11 11:40
 * @Desciption
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendOrderDTO {

    private String title;
    private String ISBN;
    private String callNo;
    private String author;
    private String publisher;
    private BigDecimal price;
    private BigInteger lendCount;
}
