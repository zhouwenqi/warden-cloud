package com.microwarp.warden.cloud.facade.order.enums;

import com.microwarp.warden.cloud.common.core.enums.BaseEnum;

/**
 * enum - 支付渠道
 */
public enum PaymentChannelEnum implements BaseEnum {
    WEIXIN_PAY(0,"微信支付"),
    ALIPAY_PAY(1,"支付宝支付"),
    APPLE_PAY(2,"Applepay支付");
    int code;
    String tag;
    PaymentChannelEnum(int code, String tag){
        this.code = code;
        this.tag = tag;
    }
}
