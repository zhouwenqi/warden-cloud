package com.microwarp.warden.cloud.service.order.feign;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderSearchDTO;
import com.microwarp.warden.cloud.facade.order.service.IOrderService;
import com.microwarp.warden.cloud.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller - 订单 - feign impl
 * @author zhouwenqi
 */
@RestController
public class FeignOrderController implements IOrderService{
    @Autowired
    private OrderService orderService;

    /**
     * 查询订单信息
     */
    @Override
    public OrderDTO findById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    /**
     * 分页查询订单列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<OrderDTO> findPage(@RequestBody ISearchPageable<OrderSearchDTO> iSearchPageable){
        return orderService.findPage(iSearchPageable);
    }

    /**
     * 删除订单
     * @param id 订单id
     */
    @Override
    public void delete(@PathVariable("id")Long id){
        orderService.delete(id);
    }

}
