package com.microwarp.warden.cloud.facade.order.domain.dto;

import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;
import com.microwarp.warden.cloud.facade.order.enums.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * dto - 支付单查询
 */
public class OrderPaymentSearchDTO extends BasicSearchDTO {
    /** 支付金额区间 */
    private BigDecimal[] amounts;
    /** 订单ID */
    private Long orderId;
    /** 支付渠道 */
    private PaymentChannelEnum[] paymentChannel;
    /** 支付方式 */
    private PaymentMethodEnum[] paymentMethod;
    /** 支付状态 */
    private PaymentStatusEnum[] paymentStatus;
    /** 支付时间 */
    private Date[] paymentDate;


    public BigDecimal[] getAmounts() {
        return amounts;
    }

    public void setAmounts(BigDecimal[] amounts) {
        this.amounts = amounts;
    }

    public PaymentStatusEnum[] getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum[] paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public PaymentChannelEnum[] getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(PaymentChannelEnum[] paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public PaymentMethodEnum[] getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum[] paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date[] getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date[] paymentDate) {
        this.paymentDate = paymentDate;
    }
}
