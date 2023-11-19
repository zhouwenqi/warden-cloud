package com.microwarp.warden.cloud.service.order.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;
import com.microwarp.warden.cloud.service.order.dao.OrderPaymentDao;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderPaymentMapstruct;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderPayment;
import com.microwarp.warden.cloud.service.order.mapper.OrderPaymentMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * dao - 订单支付 - impl
 */
@Repository
public class OrderPaymentDaoImpl extends BaseDaoImpl<OrderPaymentMapper,OrderPayment> implements OrderPaymentDao {
    /**
     * 查询单条支付信息
     * @param id 支付记录id
     * @return 支付单
     */
    @Override
    public OrderPaymentDTO findById(Long id){
        OrderPayment orderPayment = baseMapper.selectById(id);
        return null == orderPayment ? null : OrderPaymentMapstruct.Instance.orderPaymentToOrderPaymentDTO(orderPayment);
    }

    /**
     * 查询单条支付信息
     * @param paymentSn 支付单号
     * @return 支付单
     */
    @Override
    public OrderPaymentDTO findBySn(String paymentSn){
        QueryWrapper<OrderPayment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("payment_sn", paymentSn);
        OrderPayment orderPayment = baseMapper.selectOne(queryWrapper);
        return null == orderPayment ? null : OrderPaymentMapstruct.Instance.orderPaymentToOrderPaymentDTO(orderPayment);
    }

    /**
     * 查询支付单列表
     * @param orderId 订单id
     * @return 支付单列表
     */
    @Override
    public List<OrderPaymentDTO> findByOrderId(Long orderId){
        QueryWrapper<OrderPayment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        List<OrderPayment> list = baseMapper.selectList(queryWrapper);
        return null == list || list.size() < 1 ? new ArrayList<>() : OrderPaymentMapstruct.Instance.orderPaymentsToOrderPaymentDTOs(list);
    }

    /**
     * 查询一条已支付成功的支付单
     * @param orderId 订单id
     * @return 支付单
     */
    @Override
    public OrderPaymentDTO findPaidByOrderId(Long orderId){
        List<OrderPaymentDTO> list = findByOrderId(orderId);
        return list.stream().filter(item->item.getPaymentStatus().equals(PaymentStatusEnum.PAID)).findFirst().orElse(null);
    }

    /**
     * 查询支付单列表
     * @param orderSn 订单sn
     * @return 支付单列表
     */
    @Override
    public List<OrderPaymentDTO> findByOrderSn(String orderSn){
        QueryWrapper<OrderPayment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_sn", orderSn);
        List<OrderPayment> list = baseMapper.selectList(queryWrapper);
        return null == list || list.size() < 1 ? new ArrayList<>() : OrderPaymentMapstruct.Instance.orderPaymentsToOrderPaymentDTOs(list);

    }

    /**
     * 删除支付订单
     * @param id 支付订单id
     */
    @Override
    public void delete(Long id){
        baseMapper.deleteById(id);
    }

    /**
     * 删除支付订单
     * @param orderId 订单id
     */
    @Override
    public void deleteByOrderId(Long orderId){
        QueryWrapper<OrderPayment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orderId);
        baseMapper.delete(queryWrapper);
    }
}
