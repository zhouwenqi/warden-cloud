package com.microwarp.warden.cloud.service.order.domain.vo;

import com.microwarp.warden.cloud.common.core.enums.AppTerminalEnum;
import com.microwarp.warden.cloud.common.core.enums.TerminalEnum;
import com.microwarp.warden.cloud.facade.order.enums.OrderTypeEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * vo - 订单 - request
 */
public class CreateOrderRequest {
    /** 订单类型 */
    @NotNull(message = "订单类型不能为空")
    private OrderTypeEnum orderType;
    /** 应用类型 */
    private AppTerminalEnum appTerminalType;
    /** 终端类型 */
    private TerminalEnum terminalType;
    /** 备注 */
    private String memo;
    /** 订单项 */
    @NotNull(message = "订单项不能为空")
    @Size(message = "订单项不能为空")
    private List<CreateOrderItemRequest> orderItems;

    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    public AppTerminalEnum getAppTerminalType() {
        return appTerminalType;
    }

    public void setAppTerminalType(AppTerminalEnum appTerminalType) {
        this.appTerminalType = appTerminalType;
    }

    public TerminalEnum getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(TerminalEnum terminalType) {
        this.terminalType = terminalType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<CreateOrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CreateOrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
