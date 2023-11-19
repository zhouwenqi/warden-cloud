package com.microwarp.warden.cloud.service.order.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.forestage.BaseController;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductSearchDTO;
import com.microwarp.warden.cloud.service.order.domain.convert.ProductMapstruct;
import com.microwarp.warden.cloud.service.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 商品
 */
@RestController
@RequestMapping("/")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    /**
     * 获取商品信息
     * @param id 商品id
     * @return
     */
    @GetMapping("product/{id}")
    public ResultModel get(@PathVariable("id") Long id) {
        if(null == id){
            throw new WardenParamterErrorException("商品id不能为空");
        }
        ProductDTO productDTO = productService.findById(id);
        if(null == productDTO){
            throw new WardenParamterErrorException("商品信息不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("product", ProductMapstruct.Instance.productDtoToProductVO(productDTO));
        return resultModel;
    }

    /**
     * 翻页查询商品信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @PostMapping("product/search")
    public ResultModel search(@RequestBody ISearchPageable<ProductSearchDTO> iSearchPageable){
        ResultModel resultModel = ResultModel.success();
        ResultPage<ProductDTO> resultPage = productService.findPage(iSearchPageable);
        resultModel.addData("list",ProductMapstruct.Instance.productDTOsToProductVOs(resultPage.getList()));
        resultModel.addData("pageInfo",iSearchPageable.getPageInfo());
        return resultModel;
    }

}
