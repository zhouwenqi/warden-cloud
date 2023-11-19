package com.microwarp.warden.cloud.service.order.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductDTO;
import com.microwarp.warden.cloud.service.order.domain.entity.Product;

import java.util.List;

/**
 * dao - 商品
 */
public interface ProductDao extends BaseDao<Product> {
    /**
     * 查询商品信息
     * @param id 商品id
     * @return 商品信息
     */
    ProductDTO findById(Long id);

    /**
     * 查询商品信息
     * @param productSn 商品编号
     * @return 商品信息
     */
    ProductDTO findBySn(String productSn);

    /**
     * 检查有效商品id
     * @param prdIds 商品id列表
     * @return 是否全部有效
     */
    boolean match(List<Long> prdIds);
}
