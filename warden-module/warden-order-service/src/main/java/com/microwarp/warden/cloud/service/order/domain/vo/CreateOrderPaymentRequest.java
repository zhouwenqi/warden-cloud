package com.microwarp.warden.cloud.service.order.domain.vo;

import com.microwarp.warden.cloud.facade.order.enums.PaymentChannelEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentMethodEnum;

import javax.validation.constraints.NotNull;

/**
 * dto - 订单支付 - create
 */
public class CreateOrderPaymentRequest {
    /** 订单编号 */
    @NotNull(message = "订单编号不能空")
    private String orderSn;
    /** 第三方支付流水号 */
    private String paymentBiz;
    /** 支付方式 */
    private PaymentMethodEnum paymentMethod;
    /** 支付渠道 */
    private PaymentChannelEnum paymentChannel;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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
}
