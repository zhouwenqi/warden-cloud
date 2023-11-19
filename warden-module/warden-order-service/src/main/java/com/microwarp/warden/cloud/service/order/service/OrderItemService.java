package com.microwarp.warden.cloud.service.order.service;

import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderItemDTO;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderItem;

/**
 * service - 订单项
 */
public interface OrderItemService extends BaseService<OrderItem> {
    /**
     * 查询订单项
     * @param id 订单项id
     * @return
     */
    OrderItemDTO findById(Long id);
    /**
     * 添加订单项
     * @param createOrderItemDTO 订单项信息
     * @return 订单项
     */
    OrderItemDTO add(CreateOrderItemDTO createOrderItemDTO);

    /**
     * 更新订单项
     * @param updateOrderItemDTO 订单项信息
     * @return 订单项
     */
    OrderItemDTO update(UpdateOrderItemDTO updateOrderItemDTO);

    /**
     * 删除订单项
     * @param id 订单项id
     */
    void delete(Long id);
}
