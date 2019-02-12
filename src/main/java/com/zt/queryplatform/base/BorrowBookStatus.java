package com.zt.queryplatform.base;

/**
 * 借书状态枚举类
 * created by linzj on 2018/12/21
 * 借阅状态 - 0在借，1已还，2续借，3逾期
 **/


public enum  BorrowBookStatus {

    IS_BORROWING(0),
    IS_RETURNING(1),
    IS_BORROW_AGAIN(2),
    IS_OVERDUE(3);

    int value;

    BorrowBookStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static  BorrowBookStatus of(int value){

        for (BorrowBookStatus status : BorrowBookStatus.values()) {
            if(value == status.getValue()){
                return status;
            }
        }

        return BorrowBookStatus.IS_OVERDUE;
    }
}
