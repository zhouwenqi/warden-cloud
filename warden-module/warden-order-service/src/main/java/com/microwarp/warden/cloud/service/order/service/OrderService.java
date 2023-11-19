package com.microwarp.warden.cloud.service.order.service;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderDTO;
import com.microwarp.warden.cloud.service.order.domain.entity.Order;

/**
 * service - 订单
 */
public interface OrderService extends BaseService<Order> {
    /**
     * 查询订单信息
     * @param id 订单ID
     * @return 订单信息
     */
    OrderDTO findById(Long id);
    /**
     * 查询订单信息
     * @param orderSn 订单sn
     * @return 订单信息
     */
    OrderDTO findBySn(String orderSn);

    /**
     * 创建订单
     * @param createOrderDTO 订单参数
     * @return 创建成功的订单信息
     */
    OrderDTO create(CreateOrderDTO createOrderDTO);

    /**
     * 更新订单
     * @param updateOrderDTO 订单参数
     * @return
     */
    OrderDTO update(UpdateOrderDTO updateOrderDTO);

    /**
     * 删除订单
     * @param orderId 订单id
     */
    void delete(Long orderId);

    /**
     * 修改订单状态检查
     * @param orderDTO 订单信息
     * @return
     */
    boolean modifyStatusCheck(OrderDTO orderDTO);

    /**
     * 分页查询订单列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    ResultPage<OrderDTO> findPage(ISearchPageable<OrderSearchDTO> iSearchPageable);
}
