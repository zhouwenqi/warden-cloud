package com.microwarp.warden.cloud.facade.order.domain.dto;

import java.math.BigDecimal;

/**
 * dto - 更新订单项 - update
 */
public class UpdateOrderItemDTO {
    private Long id;
    /** 商品数量 */
    private Integer quantity;
    /** 商品单价 */
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
