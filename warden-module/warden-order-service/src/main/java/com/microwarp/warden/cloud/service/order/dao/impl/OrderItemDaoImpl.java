package com.microwarp.warden.cloud.service.order.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderItemDTO;
import com.microwarp.warden.cloud.service.order.dao.OrderItemDao;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderItemMapstruct;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderItem;
import com.microwarp.warden.cloud.service.order.mapper.OrderItemMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * dao - 订单项 - impl
 */
@Repository
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItemMapper,OrderItem> implements OrderItemDao {
    /**
     * 查询订单项
     * @param id 订单项id
     * @return 订单项
     */
    @Override
    public OrderItemDTO findById(Long id){
        OrderItem orderItem = baseMapper.selectById(id);
        return null == orderItem ? null : OrderItemMapstruct.Instance.orderItemToOrderItemDTO(orderItem);
    }

    /**
     * 查询订单项列表
     * @param orderId 订单id
     * @return 订单项列表
     */
    @Override
    public List<OrderItemDTO> findByOrderId(Long orderId){
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        List<OrderItem> list = baseMapper.selectList(queryWrapper);
        return null == list || list.size() < 1 ? new ArrayList<>() : OrderItemMapstruct.Instance.orderItemsToOrderItemDTOs(list);
    }

    /**
     * 删除订单项
     * @param orderId 订单ID
     */
    @Override
    public void deleteByOrderId(Long orderId){
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        baseMapper.delete(queryWrapper);
    }
}
