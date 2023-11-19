package com.microwarp.warden.cloud.facade.order.domain.dto;

import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;
import com.microwarp.warden.cloud.facade.order.enums.OrderStatusEnum;
import com.microwarp.warden.cloud.facade.order.enums.OrderTypeEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;

import java.math.BigDecimal;

/**
 * dto - 订单查询
 */
public class OrderSearchDTO extends BasicSearchDTO {
    /** 订单金额区间 */
    private BigDecimal[] amounts;
    /** 用户id */
    private Long userId;
    /** 订单状态 */
    private OrderStatusEnum[] orderStatus;
    /** 支付状态 */
    private PaymentStatusEnum[] paymentStatus;
    /** 订单类型 */
    private OrderTypeEnum[] orderType;


    public BigDecimal[] getAmounts() {
        return amounts;
    }

    public void setAmounts(BigDecimal[] amounts) {
        this.amounts = amounts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderStatusEnum[] getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum[] orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatusEnum[] getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum[] paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderTypeEnum[] getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum[] orderType) {
        this.orderType = orderType;
    }
}
