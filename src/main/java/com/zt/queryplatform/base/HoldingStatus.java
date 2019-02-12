package com.zt.queryplatform.base;

/**
 * created by linzj on 2018/12/26
 * 状态: 0在架1借出2阅览3污损4丢失5剔除6预借7赠送
 **/
public enum HoldingStatus {

    ON_SHELF(0),
    LEND_OUT(1),
    IS_READERING(2),
    IS_STAIND(3),
    IS_MISSING(4),
    BE_GET_RID_OF(5),
    IS_PRE_LEND(6),
    IS_GIVING(7);

    int value ;

    HoldingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public  static  HoldingStatus of(int value){
        for (HoldingStatus status : HoldingStatus.values()) {
            if(value == status.getValue()){
                return status;
            }
        }
        return HoldingStatus.BE_GET_RID_OF;

    }
}

