package com.microwarp.warden.cloud.service.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderItemDTO;
import com.microwarp.warden.cloud.service.order.dao.OrderItemDao;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderItemMapstruct;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderItem;
import com.microwarp.warden.cloud.service.order.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service - 订单项 - impl
 */
@Service
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem,OrderItemDao> implements OrderItemService {
    /**
     * 查询订单项
     * @param id 订单项id
     * @return
     */
    public OrderItemDTO findById(Long id){
        return this.dao.findById(id);
    }
    /**
     * 添加订单项
     * @param createOrderItemDTO 订单项信息
     * @return 订单项
     */
    @Override
    public OrderItemDTO add(CreateOrderItemDTO createOrderItemDTO){
        OrderItem newItem = OrderItemMapstruct.Instance.createOrderItemDtoToOrderItem(createOrderItemDTO);
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",createOrderItemDTO.getOrderId());
        queryWrapper.eq("prd_id",createOrderItemDTO.getPrdId());
        OrderItem orderItem = this.dao.getOne(queryWrapper);
        if(null != orderItem){
            orderItem.setQuantity(orderItem.getQuantity()+newItem.getQuantity());
            this.dao.updateById(orderItem);
            return this.dao.findById(orderItem.getId());

        }else {
            this.dao.save(newItem);
            return this.dao.findById(newItem.getId());
        }
    }

    /**
     * 更新订单项
     * @param updateOrderItemDTO 订单项信息
     * @return 订单项
     */
    @Override
    public OrderItemDTO update(UpdateOrderItemDTO updateOrderItemDTO){
        OrderItem orderItem = OrderItemMapstruct.Instance.updateOrderItemDtoToOrderItem(updateOrderItemDTO);
        this.dao.updateById(orderItem);
        return this.dao.findById(orderItem.getId());
    }

    /**
     * 删除订单项
     * @param id 订单项id
     */
    @Override
    public void delete(Long id){
        this.dao.removeById(id);
    }
}
