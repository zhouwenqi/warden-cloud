package com.microwarp.warden.cloud.service.order.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.forestage.BaseController;
import com.microwarp.warden.cloud.common.forestage.util.SecurityUtil;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.enums.PaymentMethodEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderPaymentMapstruct;
import com.microwarp.warden.cloud.service.order.domain.vo.CreateOrderPaymentRequest;
import com.microwarp.warden.cloud.service.order.service.OrderPaymentService;
import com.microwarp.warden.cloud.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * controller - 支付
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/")
public class OrderPaymentController extends BaseController {
    @Autowired
    private OrderPaymentService orderPaymentService;
    @Autowired
    private OrderService orderService;

    /**
     * 获取支付单信息
     * @param id 支付单id
     * @return
     */
    @GetMapping("orderPayment/{id}")
    public ResultModel get(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenParamterErrorException("支付单id不能为空");
        }
        OrderPaymentDTO orderPaymentDTO = orderPaymentService.findById(id);
        if(null == orderPaymentDTO){
            throw new WardenParamterErrorException("支付单不存在");
        }
        OrderDTO orderDTO = orderService.findById(orderPaymentDTO.getOrderId());
        if(null != orderDTO || !SecurityUtil.isCurrentUser(orderDTO.getUserId())){
            throw new WardenParamterErrorException("订单信息不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("orderPayment", OrderPaymentMapstruct.Instance.orderPaymentDtoToOrderPaymentVO(orderPaymentDTO));
        return resultModel;
    }

    /**
     * 创建支付单
     * @param createOrderPaymentRequest 支付信息
     * @return
     */
    @PostMapping("orderPayment")
    public ResultModel create(@RequestBody @Validated CreateOrderPaymentRequest createOrderPaymentRequest){
        ResultModel resultModel = ResultModel.success();
        OrderDTO orderDTO = orderService.findBySn(createOrderPaymentRequest.getOrderSn());

        if(null != orderDTO || !SecurityUtil.isCurrentUser(orderDTO.getUserId())){
            throw new WardenParamterErrorException("订单信息不存在");
        }

        CreateOrderPaymentDTO createOrderPaymentDTO = OrderPaymentMapstruct.Instance.createOrderPaymentRequestToCreateOrderPaymentDTO(createOrderPaymentRequest);
        createOrderPaymentDTO.setOrderId(orderDTO.getId());
        createOrderPaymentDTO.setOrderAmount(orderDTO.getAmount());
        // 订单金额小于等于0，或者是余额支付，设置支付单已支付
        if(orderDTO.getAmount().compareTo(new BigDecimal(0)) <= 0 || createOrderPaymentDTO.getPaymentMethod().equals(PaymentMethodEnum.BALANCE)){
            createOrderPaymentDTO.setPaymentStatus(PaymentStatusEnum.PAID);
            createOrderPaymentDTO.setPaymentDate(new Date());
        }
        OrderPaymentDTO orderPaymentDTO = orderPaymentService.create(createOrderPaymentDTO);

        resultModel.addData("orderPayment", OrderPaymentMapstruct.Instance.orderPaymentDtoToOrderPaymentVO(orderPaymentDTO));
        return resultModel;
    }
}
