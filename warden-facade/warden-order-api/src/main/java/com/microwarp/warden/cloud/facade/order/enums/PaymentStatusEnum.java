package com.microwarp.warden.cloud.facade.order.enums;

import com.microwarp.warden.cloud.common.core.enums.BaseEnum;

/**
 * enum - 支付状态
 */
public enum PaymentStatusEnum implements BaseEnum {
    NOT_PAID(0,"未付款"),
    PAID(1,"已付款");
    int code;
    String tag;
    PaymentStatusEnum(int code, String tag){
        this.code = code;
        this.tag = tag;
    }
}
