package com.microwarp.warden.cloud.service.order.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderItemDTO;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderItem;

import java.util.List;

/**
 * dao - 订单项
 */
public interface OrderItemDao extends BaseDao<OrderItem> {
    /**
     * 查询订单项
     * @param id 订单项id
     * @return 订单项
     */
    OrderItemDTO findById(Long id);

    /**
     * 查询订单项列表
     * @param orderId 订单id
     * @return 订单项列表
     */
    List<OrderItemDTO> findByOrderId(Long orderId);

    /**
     * 删除订单项
     * @param orderId 订单ID
     */
    void deleteByOrderId(Long orderId);
}
