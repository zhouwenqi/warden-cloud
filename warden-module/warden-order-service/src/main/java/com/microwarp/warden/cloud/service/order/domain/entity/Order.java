package com.microwarp.warden.cloud.service.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.microwarp.warden.cloud.common.core.enums.AppTerminalEnum;
import com.microwarp.warden.cloud.common.core.enums.TerminalEnum;
import com.microwarp.warden.cloud.common.database.domain.LogicEntity;
import com.microwarp.warden.cloud.facade.order.enums.OrderStatusEnum;
import com.microwarp.warden.cloud.facade.order.enums.OrderTypeEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;

import java.math.BigDecimal;

/**
 * entity - 订单
 */
@TableName("wd_order")
public class Order extends LogicEntity {
    private static final long serialVersionUID = -8823466373801060839L;
    /** 订单sn */
    private String orderSn;
    /** 订单类型 */
    private OrderTypeEnum orderType;
    /** 应用类型 */
    private AppTerminalEnum appTerminalType;
    /** 终端类型 */
    private TerminalEnum terminalType;
    /** 订单总金额 */
    private BigDecimal amount;
    /** 订单状态 */
    private OrderStatusEnum orderStatus;
    /** 支付状态 */
    private PaymentStatusEnum paymentStatus;
    /** 备注 */
    private String memo;
    /** 用户id */
    private Long userId;
    /** 用户名 */
    private String uid;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
