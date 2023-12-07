package com.microwarp.warden.cloud.service.order.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.service.order.dao.OrderDao;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderMapstruct;
import com.microwarp.warden.cloud.service.order.domain.entity.Order;
import com.microwarp.warden.cloud.service.order.mapper.OrderMapper;
import org.springframework.stereotype.Repository;

/**
 * dao - 订单 - impl
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<OrderMapper,Order> implements OrderDao {
    /**
     * 查询订单信息
     * @param id 订单ID
     * @return 订单信息
     */
    @Override
    public OrderDTO findById(Long id){
        Order order = baseMapper.selectById(id);
        return null == order ? null : OrderMapstruct.Instance.orderToOrderDTO(order);
    }

    /**
     * 查询订单信息
     * @param orderSn 订单sn
     * @return 订单信息
     */
    @Override
    public OrderDTO findBySn(String orderSn){
         QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("order_sn",orderSn);
         queryWrapper.last("limit 1");
         Order order = baseMapper.selectOne(queryWrapper);
         return null == order ? null : OrderMapstruct.Instance.orderToOrderDTO(order);
    }
}
