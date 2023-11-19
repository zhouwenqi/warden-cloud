package com.microwarp.warden.cloud.service.order.domain.vo;

import com.microwarp.warden.cloud.facade.order.enums.OrderStatusEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * vo - 订单
 */
public class UpdateOrderRequest{
    /** 商品id */
    @NotNull(message = "商品id不能为空")
    private Long id;
    /** 订单总金额 */
    private BigDecimal amount;
    /** 订单状态 */
    private OrderStatusEnum orderStatus;
    /** 支付状态 */
    private PaymentStatusEnum paymentStatus;
    /** 备注 */
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
