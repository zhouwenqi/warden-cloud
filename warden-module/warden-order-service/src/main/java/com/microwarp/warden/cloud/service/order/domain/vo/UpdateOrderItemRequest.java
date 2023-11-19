package com.microwarp.warden.cloud.service.order.domain.vo;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * vo - 订单项 - request
 */
public class UpdateOrderItemRequest {
    @NotNull(message = "订单项ID不能为空")
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
