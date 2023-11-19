package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateProductDTO;
import com.microwarp.warden.cloud.facade.order.service.IProductService;
import com.microwarp.warden.cloud.service.system.domain.convert.ProductMapstruct;
import com.microwarp.warden.cloud.service.system.domain.vo.CreateProductRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 商品
 * 配合前台(用户端)项目demo演示
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/")
public class ProductController extends BaseController{
    @Autowired(required = false)
    private IProductService iProductService;

    /**
     * 获取商品信息
     * @param id 商品id
     * @return
     */
    @GetMapping("product/{id}")
    public ResultModel get(@PathVariable("id")Long id){
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("product",iProductService.findById(id));
        return resultModel;
    }

    /**
     * 创建商品信息
     * @param createProductRequest 商品信息
     * @return
     */
    @PreAuthorize("hasAuthority('product:create')")
    @PostMapping("product")
    public ResultModel create(@RequestBody @Validated CreateProductRequest createProductRequest){
        CreateProductDTO createProductDTO = ProductMapstruct.Instance.createProductRequestToCreateProductDTO(createProductRequest);
        ResultModel resultModel = ResultModel.success();
        ProductDTO productDTO = iProductService.create(createProductDTO);
        resultModel.addData("product", ProductMapstruct.Instance.productDtoToProductVO(productDTO));
        return resultModel;
    }


    /**
     * 更新商品信息
     * @param updateProductRequest 商品信息
     * @return
     */
    @PreAuthorize("hasAuthority('product:modify')")
    @PatchMapping("product")
    public ResultModel update(@RequestBody @Validated UpdateProductRequest updateProductRequest){
        UpdateProductDTO updateProductDTO = ProductMapstruct.Instance.updateProductRequestToUpdateProductDTO(updateProductRequest);
        ProductDTO productDTO = iProductService.update(updateProductDTO);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("product", ProductMapstruct.Instance.productDtoToProductVO(productDTO));
        return resultModel;
    }

    /**
     * 翻页查询商品信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @PreAuthorize("hasAuthority('product:delete')")
    @PostMapping("product/search")
    public ResultModel search(@RequestBody ISearchPageable<ProductSearchDTO> iSearchPageable){
        ResultModel resultModel = ResultModel.success();
        ResultPage<ProductDTO> resultPage = iProductService.findPage(iSearchPageable);
        resultModel.addData("list",ProductMapstruct.Instance.productDTOsToProductVOs(resultPage.getList()));
        resultModel.addData("pageInfo",iSearchPageable.getPageInfo());
        return resultModel;
    }

    /**
     * 删除商品信息
     * @param id 商品id
     * @return
     */
    @DeleteMapping("product/{id}")
    public ResultModel delete(@PathVariable("id") Long id){
        if(null != id) {
            iProductService.delete(id);
        }
        return ResultModel.success();
    }

}
