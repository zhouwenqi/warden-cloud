package com.microwarp.warden.cloud.service.order.feign;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderSearchDTO;
import com.microwarp.warden.cloud.facade.order.service.IOrderPaymentService;
import com.microwarp.warden.cloud.facade.order.service.IOrderService;
import com.microwarp.warden.cloud.service.order.service.OrderPaymentService;
import com.microwarp.warden.cloud.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller - 支付单 - feign impl
 * @author zhouwenqi
 */
@RestController
public class FeignOrderPaymentController implements IOrderPaymentService{
    @Autowired
    private OrderPaymentService orderPaymentService;

    /**
     * 查询订单信息
     */
    @Override
    public OrderPaymentDTO findById(@PathVariable("id") Long id) {
        return orderPaymentService.findById(id);
    }

    /**
     * 分页查询订单列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<OrderPaymentDTO> findPage(@RequestBody ISearchPageable<OrderPaymentSearchDTO> iSearchPageable){
        return orderPaymentService.findPage(iSearchPageable);
    }

    /**
     * 删除订单
     * @param id 订单id
     */
    @Override
    public void delete(@PathVariable("id")Long id){
        orderPaymentService.delete(id);
    }

}
