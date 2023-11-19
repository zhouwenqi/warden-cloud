package com.microwarp.warden.cloud.facade.order.enums;

import com.microwarp.warden.cloud.common.core.enums.BaseEnum;

/**
 * enum - 支付方式
 */
public enum PaymentMethodEnum implements BaseEnum {
    ONLINE(0,"在线支付"),
    BALANCE(1,"余额支付"),
    REMIT(2,"转帐汇款");
    int code;
    String tag;
    PaymentMethodEnum(int code, String tag){
        this.code = code;
        this.tag = tag;
    }
}
