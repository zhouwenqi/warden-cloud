package com.microwarp.warden.cloud.facade.order.domain.dto;

import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;

import java.math.BigDecimal;

/**
 * dto - 商品查询
 */
public class ProductSearchDTO extends BasicSearchDTO {
    /** 价格区间 */
    private BigDecimal[] prices;

    public BigDecimal[] getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal[] prices) {
        this.prices = prices;
    }
}
