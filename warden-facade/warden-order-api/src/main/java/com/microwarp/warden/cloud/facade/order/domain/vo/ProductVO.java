package com.microwarp.warden.cloud.facade.order.domain.vo;

import com.microwarp.warden.cloud.common.core.domain.BaseVO;

import java.math.BigDecimal;

/**
 * vo - 用户
 */
public class ProductVO extends BaseVO {
    /** 商品名称 */
    private String prdName;
    /** 商品编号 */
    private String prdSn;
    /** 商品单价(原价) */
    private BigDecimal price;
    /** 商品售价 */
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

    public String getPrdSn() {
        return prdSn;
    }

    public void setPrdSn(String prdSn) {
        this.prdSn = prdSn;
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
