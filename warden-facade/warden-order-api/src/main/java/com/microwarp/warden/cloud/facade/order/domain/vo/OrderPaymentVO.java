package com.microwarp.warden.cloud.facade.order.domain.vo;

import com.microwarp.warden.cloud.common.core.domain.BaseVO;
import com.microwarp.warden.cloud.facade.order.enums.PaymentChannelEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentMethodEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;

import java.math.BigDecimal;

/**
 * vo - 订单支付
 */
public class OrderPaymentVO extends BaseVO {
    /** 支付单号 */
    private String paymentSn;
    /** 订单号 */
    private String orderSn;
    /** 订单ID */
    private Long orderId;
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

    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
}
