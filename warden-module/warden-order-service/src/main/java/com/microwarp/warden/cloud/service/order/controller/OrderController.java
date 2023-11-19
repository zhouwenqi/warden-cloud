package com.microwarp.warden.cloud.service.order.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.forestage.BaseController;
import com.microwarp.warden.cloud.common.forestage.util.SecurityUtil;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderDTO;
import com.microwarp.warden.cloud.facade.order.enums.OrderStatusEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderMapstruct;
import com.microwarp.warden.cloud.service.order.domain.vo.CreateOrderRequest;
import com.microwarp.warden.cloud.service.order.domain.vo.UpdateOrderRequest;
import com.microwarp.warden.cloud.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 订单
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单信息
     * @param id 订单id
     * @return
     */
    @GetMapping("order/{id}")
    public ResultModel get(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenParamterErrorException("订单id不能为空");
        }
        OrderDTO orderDTO = orderService.findById(id);
        if(null != orderDTO || !SecurityUtil.isCurrentUser(orderDTO.getUserId())){
            throw new WardenParamterErrorException("订单信息不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("order", OrderMapstruct.Instance.orderDtoToOrderVO(orderDTO));
        return resultModel;
    }

    /**
     * 创建订单信息
     * @param createOrderRequest 订单信息
     * @return
     */
    @PostMapping("order")
    public ResultModel create(@RequestBody @Validated CreateOrderRequest createOrderRequest){
        CreateOrderDTO createOrderDTO = OrderMapstruct.Instance.createOrderRequestToCreateOrderDTO(createOrderRequest);
        createOrderDTO.setPaymentStatus(PaymentStatusEnum.NOT_PAID);
        createOrderDTO.setOrderStatus(OrderStatusEnum.AUDITED);
        TokenUser tokenUser = getTokenUser();
        createOrderDTO.setUid(tokenUser.getUsername());
        createOrderDTO.setUserId(Long.parseLong(tokenUser.getUserId()));
        OrderDTO orderDTO = orderService.create(createOrderDTO);
        ResultModel resultModel= ResultModel.success();
        resultModel.addData("order",OrderMapstruct.Instance.orderDtoToOrderVO(orderDTO));
        return resultModel;
    }

    /**
     * 修改订单信息
     * @param updateOrderRequest 修改参数
     * @return
     */
    @PatchMapping("order")
    public ResultModel update(@RequestBody @Validated UpdateOrderRequest updateOrderRequest){
        UpdateOrderDTO updateOrderDTO = OrderMapstruct.Instance.updateOrderRequestToUpdateOrderDTO(updateOrderRequest);
        OrderDTO orderDTO = orderService.findById(updateOrderDTO.getId());
        if(null != orderDTO || !SecurityUtil.isCurrentUser(orderDTO.getUserId())){
            throw new WardenParamterErrorException("订单信息不存在");
        }
        orderService.modifyStatusCheck(orderDTO);
        OrderDTO newOrder = orderService.update(updateOrderDTO);
        ResultModel resultModel= ResultModel.success();
        resultModel.addData("order",OrderMapstruct.Instance.orderDtoToOrderVO(newOrder));
        return resultModel;
    }

    /**
     * 取消订单
     * @param id 订单ID
     * @return
     */
    @DeleteMapping("order/{id}")
    public ResultModel delete(@PathVariable("id")Long id){
        if(null == id){
            throw new WardenParamterErrorException("订单id不能为空");
        }
        OrderDTO orderDTO = orderService.findById(id);
        if(null != orderDTO || !SecurityUtil.isCurrentUser(orderDTO.getUserId())){
            throw new WardenParamterErrorException("订单信息不存在");
        }
        if(orderDTO.getOrderStatus().equals(OrderStatusEnum.COMPLETE) || orderDTO.getOrderStatus().equals(OrderStatusEnum.COMPLETE) || orderDTO.getPaymentStatus().equals(PaymentStatusEnum.PAID)){
            throw new WardenParamterErrorException("订单已完成或取消");
        }
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setId(orderDTO.getId());
        updateOrderDTO.setOrderStatus(OrderStatusEnum.CANCEL);
        orderService.update(updateOrderDTO);
        return ResultModel.success();
    }

    /**
     * 翻页查询订单信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @PostMapping("order/search")
    public ResultModel search(@RequestBody ISearchPageable<OrderSearchDTO> iSearchPageable){
        // 设置当前登录用户 id 条件
        iSearchPageable.getFilters().setUserId(SecurityUtil.getCurrentUser().getId());
        ResultModel resultModel = ResultModel.success();
        ResultPage<OrderDTO> resultPage = orderService.findPage(iSearchPageable);
        resultModel.addData("list",OrderMapstruct.Instance.orderDTOsToOrderVOs(resultPage.getList()));
        resultModel.addData("pageInfo",iSearchPageable.getPageInfo());
        return resultModel;
    }
}
