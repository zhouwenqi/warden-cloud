package com.microwarp.warden.cloud.facade.order.enums;

import com.microwarp.warden.cloud.common.core.enums.BaseEnum;

/**
 * enum - 订单状态
 */
public enum OrderStatusEnum implements BaseEnum {
    AUDIT(0,"待审核"),
    AUDITED(1,"已审核"),
    CANCEL(2,"已取消"),
    COMPLETE(3,"已完成");
    int code;
    String tag;
    OrderStatusEnum(int code, String tag){
        this.code = code;
        this.tag = tag;
    }
}
