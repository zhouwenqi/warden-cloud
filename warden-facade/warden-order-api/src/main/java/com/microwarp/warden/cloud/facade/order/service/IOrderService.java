package com.microwarp.warden.cloud.facade.order.service;

import com.microwarp.warden.cloud.common.core.constant.AppConstant;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * feign - 订单服务
 * @author zhouwenqi
 */
@FeignClient(value = AppConstant.ORDER_SERVICE_NAME, contextId = "iOrderService")
public interface IOrderService {
    /**
     * 查询订单信息
     */
    @GetMapping(HttpConstant.FEIGN_URI_PREFIX +"/order/{id}")
    OrderDTO findById(@PathVariable("id")Long id);
    /**
     * 分页查询订单列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @PostMapping(HttpConstant.FEIGN_URI_PREFIX +"/order/search")
    ResultPage<OrderDTO> findPage(@RequestBody ISearchPageable<OrderSearchDTO> iSearchPageable);

    /**
     * 删除订单
     * @param id 订单id
     */
    @DeleteMapping(HttpConstant.FEIGN_URI_PREFIX +"/order/{id}")
    void delete(@PathVariable("id")Long id);
}
