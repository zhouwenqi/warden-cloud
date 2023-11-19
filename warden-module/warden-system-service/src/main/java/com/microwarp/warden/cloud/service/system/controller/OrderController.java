package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderSearchDTO;
import com.microwarp.warden.cloud.facade.order.service.IOrderService;
import com.microwarp.warden.cloud.service.system.domain.convert.OrderMapstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 订单
 * 配合前台(用户端)项目demo演示
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired(required = false)
    private IOrderService iOrderService;

    /**
     * 查询订单信息
     * @param id 订单id
     * @return
     */
    @PreAuthorize("hasAuthority('order:view')")
    @GetMapping("order/{id}")
    public ResultModel get(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenParamterErrorException("订单ID不能为空");
        }
        OrderDTO orderDTO = iOrderService.findById(id);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("order", OrderMapstruct.Instance.orderDtoToOrderVO(orderDTO));
        return resultModel;
    }

    /**
     * 翻页查询订单信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @PostMapping("order/search")
    public ResultModel search(@RequestBody ISearchPageable<OrderSearchDTO> iSearchPageable){
        ResultModel resultModel = ResultModel.success();
        ResultPage<OrderDTO> resultPage = iOrderService.findPage(iSearchPageable);
        resultModel.addData("list",OrderMapstruct.Instance.orderDTOsToOrderVOs(resultPage.getList()));
        resultModel.addData("pageInfo",iSearchPageable.getPageInfo());
        return resultModel;
    }


    /**
     * 删除订单信息
     * @param id 订单ID
     * @return
     */
    @PreAuthorize("hasAuthority('order:admin')")
    @DeleteMapping("order/{id}")
    public ResultModel delete(@PathVariable("id")Long id){
        if(null != id){
            iOrderService.delete(id);
        }
        return ResultModel.success();
    }

}
