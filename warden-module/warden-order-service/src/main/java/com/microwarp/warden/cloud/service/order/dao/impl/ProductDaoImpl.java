package com.microwarp.warden.cloud.service.order.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductDTO;
import com.microwarp.warden.cloud.service.order.dao.ProductDao;
import com.microwarp.warden.cloud.service.order.domain.convert.ProductMapstruct;
import com.microwarp.warden.cloud.service.order.domain.entity.Product;
import com.microwarp.warden.cloud.service.order.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * dao - 商品 - impl
 */
@Repository
public class ProductDaoImpl extends BaseDaoImpl<ProductMapper,Product> implements ProductDao {
    /**
     * 查询商品信息
     * @param id 商品id
     * @return 商品信息
     */
    public ProductDTO findById(Long id){
        Product product = baseMapper.selectById(id);
        return null == product ? null : ProductMapstruct.Instance.productToProductDTO(product);
    }

    /**
     * 查询商品信息
     * @param productSn 商品编号
     * @return 商品信息
     */
    public ProductDTO findBySn(String productSn){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("prd_sn",productSn);
        Product product = baseMapper.selectOne(queryWrapper);
        return null == product ? null : ProductMapstruct.Instance.productToProductDTO(product);
    }

    /**
     * 检查有效商品id
     * @param prdIds 商品id列表
     * @return 是否全部有效
     */
    @Override
    public boolean match(List<Long> prdIds){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",prdIds);
        long count = baseMapper.selectCount(queryWrapper);
        return count == prdIds.size();
    }
}
