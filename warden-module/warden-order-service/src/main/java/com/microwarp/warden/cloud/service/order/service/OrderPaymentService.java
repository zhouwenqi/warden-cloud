package com.microwarp.warden.cloud.service.order.service;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderPaymentDTO;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderPayment;

import java.util.List;

/**
 * service - 订单支付
 */
public interface OrderPaymentService extends BaseService<OrderPayment>{
    /**
     * 获取订单支付信息
     * @param id 支付单ID
     * @return 支付单信息
     */
    OrderPaymentDTO findById(Long id);
    /**
     * 获取订单支付信息
     * @param paymentSn 支付单SN
     * @return 支付单信息
     */
    OrderPaymentDTO findBySn(String paymentSn);

    /**
     * 获取订单所有支付单信息
     * @param orderId 订单ID
     * @return 支付单列表
     */
    List<OrderPaymentDTO> findByOrderId(Long orderId);
    /**
     * 创建支付订单
     * @param createOrderPaymentDTO 支付订单参数
     * @return 支付订单信息
     */
    OrderPaymentDTO create(CreateOrderPaymentDTO createOrderPaymentDTO);

    /**
     * 修改支付订单
     * @param updateOrderPaymentDTO 支付订单参数
     * @return
     */
    OrderPaymentDTO update(UpdateOrderPaymentDTO updateOrderPaymentDTO);

    /**
     * 删除一条支付单
     * @param id 支付单ID
     */
    void delete(Long id);

    /**
     * 删除订单相关的所有支付单
     * @param orderId 订单ID
     */
    void deleteByOrderId(Long orderId);

    /**
     * 分页查询支付单列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    ResultPage<OrderPaymentDTO> findPage(ISearchPageable<OrderPaymentSearchDTO> iSearchPageable);

}
