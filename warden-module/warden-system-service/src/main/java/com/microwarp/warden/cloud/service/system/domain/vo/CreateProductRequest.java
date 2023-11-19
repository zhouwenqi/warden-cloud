package com.microwarp.warden.cloud.service.system.domain.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * vo - 用户 - create
 */
public class CreateProductRequest {
    /** 商品名称 */
    @NotNull(message = "商品名称不能为空")
    @Size(max = 40, message = "商品名称不能超过40个字符")
    private String prdName;
    /** 商品单价(原价) */
    @Min(value = 0, message = "商品单价不能低于0")
    @Max(value = 10000, message = "商品单价不能高于10000")
    private BigDecimal price;
    /** 商品售价 */
    @Min(value = 0, message = "商品单价不能低于0")
    @Max(value = 10000, message = "商品单价不能高于10000")
    private BigDecimal salesPrice;
    /** 商品标题 */
    private String title;
    /** 商品描述 */
    private String description;
    /** 禁用 */
    private Boolean disabled;

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
