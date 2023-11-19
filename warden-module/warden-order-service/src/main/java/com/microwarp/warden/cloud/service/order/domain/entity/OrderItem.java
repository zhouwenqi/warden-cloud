package com.microwarp.warden.cloud.service.order.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.microwarp.warden.cloud.common.database.domain.LogicEntity;

import java.math.BigDecimal;

/**
 * entity - 订单项
 */
@TableName("wd_order_item")
public class OrderItem  extends LogicEntity {
    private static final long serialVersionUID = -1534983026357202086L;
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
