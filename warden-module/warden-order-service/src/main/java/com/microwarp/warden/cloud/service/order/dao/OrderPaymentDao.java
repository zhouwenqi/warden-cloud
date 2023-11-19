package com.microwarp.warden.cloud.service.order.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderPayment;

import java.util.List;

/**
 * dao - 订单支付
 */
public interface OrderPaymentDao extends BaseDao<OrderPayment> {
    /**
     * 查询单条支付信息
     * @param id 支付记录id
     * @return 支付单
     */
    OrderPaymentDTO findById(Long id);

    /**
     * 查询单条支付信息
     * @param paymentSn 支付单号
     * @return 支付单
     */
    OrderPaymentDTO findBySn(String paymentSn);

    /**
     * 查询支付单列表
     * @param orderId 订单id
     * @return 支付单列表
     */
    List<OrderPaymentDTO> findByOrderId(Long orderId);

    /**
     * 查询支付单列表
     * @param orderSn 订单sn
     * @return 支付单列表
     */
    List<OrderPaymentDTO> findByOrderSn(String orderSn);

    /**
     * 查询一条已支付成功的支付单
     * @param orderId 订单id
     * @return
     */
    OrderPaymentDTO findPaidByOrderId(Long orderId);

    /**
     * 删除支付订单
     * @param id 支付订单id
     */
    void delete(Long id);

    /**
     * 删除支付订单
     * @param orderId 订单id
     */
    void deleteByOrderId(Long orderId);
}
