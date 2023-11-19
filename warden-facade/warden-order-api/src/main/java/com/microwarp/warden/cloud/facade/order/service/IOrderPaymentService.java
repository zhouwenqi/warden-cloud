package com.microwarp.warden.cloud.facade.order.service;

import com.microwarp.warden.cloud.common.core.constant.AppConstant;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * feign - 支付服务
 * @author zhouwenqi
 */
@FeignClient(value = AppConstant.ORDER_SERVICE_NAME, contextId = "iOrderPaymentService")
public interface IOrderPaymentService {
    /**
     * 查询支付单信息
     */
    @GetMapping(HttpConstant.FEIGN_URI_PREFIX +"/orderPayment/{id}")
    OrderPaymentDTO findById(@PathVariable("id") Long id);
    /**
     * 分页查询支付单列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @PostMapping(HttpConstant.FEIGN_URI_PREFIX +"/orderPayment/search")
    ResultPage<OrderPaymentDTO> findPage(@RequestBody ISearchPageable<OrderPaymentSearchDTO> iSearchPageable);

    /**
     * 删除支付单
     * @param id 支付单id
     */
    @DeleteMapping(HttpConstant.FEIGN_URI_PREFIX +"/orderPayment/{id}")
    void delete(@PathVariable("id") Long id);
}
