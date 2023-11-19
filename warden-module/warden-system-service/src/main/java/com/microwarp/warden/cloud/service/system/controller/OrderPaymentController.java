package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentSearchDTO;
import com.microwarp.warden.cloud.facade.order.service.IOrderPaymentService;
import com.microwarp.warden.cloud.service.system.domain.convert.OrderPaymentMapstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 支付单
 * 配合前台(用户端)项目demo演示
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/")
public class OrderPaymentController extends BaseController {
    @Autowired(required = false)
    private IOrderPaymentService iOrderPaymentService;

    /**
     * 获取支付订信息
     * @param id 支付单id
     * @return
     */
    @GetMapping("orderPayment/{id}")
    public ResultModel get(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenParamterErrorException("支付单id不能为空");
        }
        OrderPaymentDTO orderPaymentDTO = iOrderPaymentService.findById(id);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("orderPayment", OrderPaymentMapstruct.Instance.orderPaymentDtoToOrderPaymentVO(orderPaymentDTO));
        return resultModel;
    }

    /**
     * 翻页查询支付单信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @PostMapping("orderPayment/search")
    public ResultModel search(@RequestBody ISearchPageable<OrderPaymentSearchDTO> iSearchPageable){
        ResultModel resultModel = ResultModel.success();
        ResultPage<OrderPaymentDTO> resultPage = iOrderPaymentService.findPage(iSearchPageable);
        resultModel.addData("list", OrderPaymentMapstruct.Instance.orderPaymentDTOsToOrderPaymentVOs(resultPage.getList()));
        resultModel.addData("pageInfo",iSearchPageable.getPageInfo());
        return resultModel;
    }

    /**
     * 删除支付单
     * @param id 支付单id
     * @return
     */
    /**
     * 删除订单信息
     * @param id 订单ID
     * @return
     */
    @PreAuthorize("hasAuthority('order:admin')")
    @DeleteMapping("orderPayment/{id}")
    public ResultModel delete(@PathVariable("id") Long id){
        if(null != id){
            iOrderPaymentService.delete(id);
        }
        return ResultModel.success();
    }
}
