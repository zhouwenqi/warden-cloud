package com.microwarp.warden.cloud.service.order.service;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateProductDTO;
import com.microwarp.warden.cloud.service.order.domain.entity.Product;

/**
 * service - 商品
 */
public interface ProductService extends BaseService<Product> {
    /**
     * 查询商品信息
     * @param id 商品ID
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
     * 创建商品
     * @param createProductDTO 商品参数
     * @return 商品信息
     */
    ProductDTO create(CreateProductDTO createProductDTO);

    /**
     * 更新商品信息
     * @param updateProductDTO 商品信息
     * @return 商品信息
     */
    ProductDTO update(UpdateProductDTO updateProductDTO);

    /**
     * 删除删除
     * @param productId 商品id
     */
    void delete(Long productId);

    /**
     * 分页查询商品列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    ResultPage<ProductDTO> findPage(ISearchPageable<ProductSearchDTO> iSearchPageable);
}
