package com.microwarp.warden.cloud.service.order.feign;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.*;
import com.microwarp.warden.cloud.facade.order.service.IOrderService;
import com.microwarp.warden.cloud.facade.order.service.IProductService;
import com.microwarp.warden.cloud.service.order.service.OrderService;
import com.microwarp.warden.cloud.service.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller - 商品 - feign impl
 * @author zhouwenqi
 */
@RestController
public class FeignProductController implements IProductService{
    @Autowired
    private ProductService productService;

    /**
     * 查询商品信息
     */
    @Override
    public ProductDTO findById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    /**
     * 创建商品
     * @param createProductDTO 商品参数
     * @return 商品信息
     */
    @Override
    public ProductDTO create(@RequestBody CreateProductDTO createProductDTO){
        return productService.create(createProductDTO);
    }

    /**
     * 更新商品信息
     * @param updateProductDTO 商品信息
     * @return 商品信息
     */
    @Override
    public ProductDTO update(UpdateProductDTO updateProductDTO){
        return productService.update(updateProductDTO);
    }

    /**
     * 分页查询商品列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<ProductDTO> findPage(@RequestBody ISearchPageable<ProductSearchDTO> iSearchPageable){
        return productService.findPage(iSearchPageable);
    }

    /**
     * 删除订单
     * @param id 订单id
     */
    @Override
    public void delete(@PathVariable("id")Long id){
        productService.delete(id);
    }

}
