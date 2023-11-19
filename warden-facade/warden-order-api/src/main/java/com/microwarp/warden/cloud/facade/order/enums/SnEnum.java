package com.microwarp.warden.cloud.facade.order.enums;

import com.microwarp.warden.cloud.common.core.enums.BaseEnum;

/**
 * enum - 单号前缀
 */
public enum SnEnum implements BaseEnum {
    OD(0,"订单编号"),
    PA(1,"支付单号"),
    PD(2,"商品编码");
    int code;
    String tag;
    SnEnum(int code, String tag){
    this.code = code;
    this.tag = tag;
    }
}