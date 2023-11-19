package com.microwarp.warden.cloud.facade.order.enums;

import com.microwarp.warden.cloud.common.core.enums.BaseEnum;

/**
 * enum - 订单类态
 */
public enum OrderTypeEnum implements BaseEnum {
    NORMAL(0,"普通订单"),
    VIRTUAL(1,"虚拟商品订单"),
    EXCHANGE(2,"兑换订单");
    int code;
    String tag;
    OrderTypeEnum(int code, String tag){
        this.code = code;
        this.tag = tag;
    }
}
