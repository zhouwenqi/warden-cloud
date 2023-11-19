package com.microwarp.warden.cloud.service.order.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.forestage.util.SecurityUtil;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderItemDTO;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderItemMapstruct;
import com.microwarp.warden.cloud.service.order.domain.vo.CreateOrderItemRequest;
import com.microwarp.warden.cloud.service.order.domain.vo.UpdateOrderItemRequest;
import com.microwarp.warden.cloud.service.order.service.OrderItemService;
import com.microwarp.warden.cloud.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 订单项
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;

    /**
     * 添加订单项
     * @param createOrderItemRequest 订单项信息
     * @return
     */
    @PostMapping("orderItem")
    public ResultModel add(@RequestBody @Validated CreateOrderItemRequest createOrderItemRequest){
        CreateOrderItemDTO createOrderItemDTO = OrderItemMapstruct.Instance.createOrderItemRequestToCreateOrderItemDTO(createOrderItemRequest);
        OrderDTO orderDTO = orderService.findById(createOrderItemDTO.getOrderId());
        if(null != orderDTO || !SecurityUtil.isCurrentUser(orderDTO.getUserId())){
            throw new WardenParamterErrorException("订单信息不存在");
        }
        orderService.modifyStatusCheck(orderDTO);
        OrderItemDTO orderItemDTO = orderItemService.add(createOrderItemDTO);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("orderItem", OrderItemMapstruct.Instance.orderItemDtoToOrderItemVO(orderItemDTO));
        return resultModel;
    }

    /**
     * 更新订单项
     * @param updateOrderItemRequest 订单项信息
     * @return
     */
    @PatchMapping("orderItem")
    public ResultModel update(@RequestBody @Validated UpdateOrderItemRequest updateOrderItemRequest){
        UpdateOrderItemDTO updateOrderItemDTO = OrderItemMapstruct.Instance.updateOrderItemRequestToUpdateOrderItemDTO(updateOrderItemRequest);
        OrderItemDTO orderItemDTO = orderItemService.findById(updateOrderItemRequest.getId());
        if(null == orderItemDTO){
            throw new WardenParamterErrorException("订单项不存在");
        }
        OrderDTO orderDTO = orderService.findById(orderItemDTO.getOrderId());
        if(null != orderDTO || !SecurityUtil.isCurrentUser(orderDTO.getUserId())){
            throw new WardenParamterErrorException("订单信息不存在");
        }
        orderService.modifyStatusCheck(orderDTO);
        orderItemDTO = orderItemService.update(updateOrderItemDTO);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("orderItem", OrderItemMapstruct.Instance.orderItemDtoToOrderItemVO(orderItemDTO));
        return resultModel;
    }

    /**
     * 删除订单项
     * @param id 订单项id
     * @return
     */
    @DeleteMapping("orderItem/{id}")
    public ResultModel delete(@PathVariable("id")Long id){
        if(null != id){
            OrderItemDTO orderItemDTO = orderItemService.findById(id);
            if(null == orderItemDTO){
                throw new WardenParamterErrorException("订单项不存在");
            }
            OrderDTO orderDTO = orderService.findById(orderItemDTO.getOrderId());
            if(null != orderDTO || !SecurityUtil.isCurrentUser(orderDTO.getUserId())){
                throw new WardenParamterErrorException("订单信息不存在");
            }
            orderItemService.delete(id);
        }
        return ResultModel.success();
    }

}
