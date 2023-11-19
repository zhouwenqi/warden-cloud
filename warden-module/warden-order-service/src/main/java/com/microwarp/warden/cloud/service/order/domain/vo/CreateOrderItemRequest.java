package com.microwarp.warden.cloud.service.order.domain.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * vo - 订单项 - request
 */
public class CreateOrderItemRequest {
    /** 订单ID */
    @NotNull(message = "订单类型不能为空")
    private Long orderId;
    /** 商品ID */
    @NotNull(message = "订单类型不能为空")
    private Long prdId;
    /** 商品名称 */
    private String prdName;
    /** 商品数量 */
    @NotNull(message = "订单类型不能为空")
    @Min(value = 1, message = "数量不能小于1")
    @Max(value = 100, message = "数量不能大于100")
    private Integer quantity;
    /** 商品单价 */
    @NotNull(message = "单价不能为空")
    @Min(value = 0, message = "单价不能小于0")
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
