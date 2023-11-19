package com.microwarp.warden.cloud.facade.order.service;

import com.microwarp.warden.cloud.common.core.constant.AppConstant;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * feign - 商品服务
 * @author zhouwenqi
 */
@FeignClient(value = AppConstant.ORDER_SERVICE_NAME, contextId = "iProductService")
public interface IProductService {
    /**
     * 查询商品信息
     */
    @GetMapping(HttpConstant.FEIGN_URI_PREFIX +"/product/{id}")
    ProductDTO findById(@PathVariable("id") Long id);

    /**
     * 创建商品
     * @param createProductDTO 商品参数
     * @return 商品信息
     */
    @PostMapping(HttpConstant.FEIGN_URI_PREFIX +"/product")
    ProductDTO create(@RequestBody CreateProductDTO createProductDTO);

    /**
     * 更新商品信息
     * @param productDTO 商品信息
     * @return 商品信息
     */
    @PatchMapping(HttpConstant.FEIGN_URI_PREFIX +"/product")
    ProductDTO update(@RequestBody UpdateProductDTO productDTO);

    /**
     * 分页查询商品列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @PostMapping(HttpConstant.FEIGN_URI_PREFIX +"/product/search")
    ResultPage<ProductDTO> findPage(@RequestBody ISearchPageable<ProductSearchDTO> iSearchPageable);

    /**
     * 删除商品
     * @param id 商品id
     */
    @DeleteMapping(HttpConstant.FEIGN_URI_PREFIX +"/product/{id}")
    void delete(@PathVariable("id") Long id);

}
