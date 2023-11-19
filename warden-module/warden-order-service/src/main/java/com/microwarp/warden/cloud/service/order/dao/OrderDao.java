package com.microwarp.warden.cloud.service.order.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.service.order.domain.entity.Order;

/**
 * dao - 订单
 */
public interface OrderDao extends BaseDao<Order> {
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
}
