package com.microwarp.warden.cloud.facade.order.domain.dto;

import com.microwarp.warden.cloud.facade.order.enums.PaymentChannelEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentMethodEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * dto - 订单支付 - update
 */
public class UpdateOrderPaymentDTO {
    private Long id;
    /** 订单(应付)金额 */
    private BigDecimal orderAmount;
    /** 支付(已付)金额 */
    private BigDecimal paymentAmount;
    /** 第三方支付流水号 */
    private String paymentBiz;
    /** 支付方式 */
    private PaymentMethodEnum paymentMethod;
    /** 支付渠道 */
    private PaymentChannelEnum paymentChannel;
    /** 支付状态 */
    private PaymentStatusEnum paymentStatus;
    /** 支付时间 */
    private Date paymentDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentBiz() {
        return paymentBiz;
    }

    public void setPaymentBiz(String paymentBiz) {
        this.paymentBiz = paymentBiz;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentChannelEnum getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(PaymentChannelEnum paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
