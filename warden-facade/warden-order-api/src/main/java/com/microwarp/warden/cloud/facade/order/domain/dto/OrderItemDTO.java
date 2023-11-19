package com.microwarp.warden.cloud.facade.order.domain.dto;

import com.microwarp.warden.cloud.common.core.domain.BaseDTO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * dto - 订单项
 */
public class OrderItemDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -8812129237837576344L;
    /** 订单ID */
    private Long orderId;
    /** 商品ID */
    private Long prdId;
    /** 商品名称 */
    private String prdName;
    /** 商品数量 */
    private Integer quantity;
    /** 商品单价 */
    private BigDecimal price;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPrdId() {
        return prdId;
    }

    public void setPrdId(Long prdId) {
        this.prdId = prdId;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
