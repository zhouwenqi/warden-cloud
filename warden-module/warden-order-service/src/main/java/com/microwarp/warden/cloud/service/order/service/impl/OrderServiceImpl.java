package com.microwarp.warden.cloud.service.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.common.forestage.util.SecurityUtil;
import com.microwarp.warden.cloud.facade.order.domain.dto.*;
import com.microwarp.warden.cloud.facade.order.enums.OrderStatusEnum;
import com.microwarp.warden.cloud.facade.order.enums.PaymentStatusEnum;
import com.microwarp.warden.cloud.facade.order.enums.SnEnum;
import com.microwarp.warden.cloud.service.order.dao.OrderDao;
import com.microwarp.warden.cloud.service.order.dao.OrderItemDao;
import com.microwarp.warden.cloud.service.order.dao.OrderPaymentDao;
import com.microwarp.warden.cloud.service.order.dao.ProductDao;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderItemMapstruct;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderMapstruct;
import com.microwarp.warden.cloud.service.order.domain.convert.ProductMapstruct;
import com.microwarp.warden.cloud.service.order.domain.entity.Order;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderItem;
import com.microwarp.warden.cloud.service.order.domain.entity.Product;
import com.microwarp.warden.cloud.service.order.service.OrderService;
import com.microwarp.warden.cloud.service.order.util.SnUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * service - 订单 - impl
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order,OrderDao> implements OrderService {
    @Resource
    private OrderItemDao orderItemDao;
    @Resource
    private OrderPaymentDao orderPaymentDao;
    @Resource
    private ProductDao productDao;

    /**
     * 查询订单信息
     * @param id 订单ID
     * @return 订单信息
     */
    @Override
    public OrderDTO findById(Long id){
        OrderDTO orderDTO = this.dao.findById(id);
        if(null == orderDTO){
            return null;
        }
        orderDTO.setOrderItems(orderItemDao.findByOrderId(orderDTO.getId()));
        return orderDTO;
    }

    /**
     * 查询订单信息
     * @param orderSn 订单sn
     * @return 订单信息
     */
    @Override
    public OrderDTO findBySn(String orderSn){
        OrderDTO orderDTO = this.dao.findBySn(orderSn);
        if(null == orderDTO){
            return null;
        }
        orderDTO.setOrderItems(orderItemDao.findByOrderId(orderDTO.getId()));
        return orderDTO;
    }

    /**
     * 创建订单
     * @param createOrderDTO 订单参数
     * @return 创建成功的订单信息
     */
    @Override
    public OrderDTO create(CreateOrderDTO createOrderDTO){
        Order order = OrderMapstruct.Instance.createOrderDtoToOrder(createOrderDTO);

        BigDecimal orderAmount = new BigDecimal(0);
        List<OrderItem> orderItems = new ArrayList<>();

        // 计算订单金额和检查
        if(null != createOrderDTO.getOrderItems() && createOrderDTO.getOrderItems().size()>0){
            for (CreateOrderItemDTO createOrderItemDTO : createOrderDTO.getOrderItems()) {
                BigDecimal amount = createOrderItemDTO.getPrice().multiply(new BigDecimal(createOrderItemDTO.getQuantity()));
                orderAmount = orderAmount.add(amount);
                OrderItem orderItem = OrderItemMapstruct.Instance.createOrderItemDtoToOrderItem(createOrderItemDTO);
                orderItems.add(orderItem);
            }
            List<Long> prdIds = createOrderDTO.getOrderItems().stream().map(CreateOrderItemDTO::getPrdId).collect(Collectors.toList());
            if(!productDao.match(prdIds)){
                throw new WardenParamterErrorException("订单项商品信息异常");
            }
        }
        // 生成订单号
        order.setOrderSn(SnUtil.generateSN(SnEnum.OD));
        // 设置订单总金额
        order.setAmount(orderAmount);
        this.dao.save(order);

        // 处理订单项
        if(orderItems.size() > 0){
            orderItems.forEach(item->{
                item.setOrderId(order.getId());
            });
            orderItemDao.saveBatch(orderItems);
        }
        return this.findById(order.getId());
    }

    /**
     * 更新订单
     * @param updateOrderDTO 订单参数
     * @return
     */
     public  OrderDTO update(UpdateOrderDTO updateOrderDTO){
         Order order = OrderMapstruct.Instance.updateOrderDtoToOrder(updateOrderDTO);
         this.dao.updateById(order);
         return findById(order.getId());
     }

    /**
     * 删除订单
     * @param orderId 订单id
     */
    @Override
    public void delete(Long orderId){
        // 删除订单项
        orderItemDao.deleteByOrderId(orderId);
        // 删除支付单
        orderPaymentDao.deleteByOrderId(orderId);
        // 删除订单
        this.dao.removeById(orderId);
    }

    /**
     * 修改订单状态检查
     * @param orderDTO 订单信息
     * @return
     */
    @Override
    public boolean modifyStatusCheck(OrderDTO orderDTO){
        if(orderDTO.getOrderStatus().equals(OrderStatusEnum.CANCEL)){
            throw new WardenParamterErrorException("订单已取消，无法修改");
        }
        if(orderDTO.getOrderStatus().equals(OrderStatusEnum.COMPLETE)){
            throw new WardenParamterErrorException("订单已完成，无法修改");
        }
        if(orderDTO.getPaymentStatus().equals(PaymentStatusEnum.PAID)){
            throw new WardenParamterErrorException("订单已支付，无法修改");
        }
        return true;
    }

    /**
     * 分页查询订单列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<OrderDTO> findPage(ISearchPageable<OrderSearchDTO> iSearchPageable){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("memo", iSearchPageable.getSearchValue())
                    .or()
                    .like("uid", iSearchPageable.getSearchValue())
                    .or()
                    .likeLeft("order_sn", iSearchPageable.getSearchValue())
            );
        }
        if(null != iSearchPageable.getFilters()){
            OrderSearchDTO searchDTO = iSearchPageable.getFilters();
            if(null != searchDTO.getAmounts() && searchDTO.getAmounts().length > 0){
                if(searchDTO.getAmounts().length < 2){
                    queryWrapper.and(true, wrapper -> wrapper.ge("amount",searchDTO.getAmounts()[0]));
                }else{
                    queryWrapper.and(true, wrapper -> wrapper.between("amount",searchDTO.getAmounts()[0],searchDTO.getAmounts()[1]));
                }
            }
            if(null != searchDTO.getUserId()) {
                queryWrapper.and(true, wrapper -> wrapper.eq("user_id", searchDTO.getUserId()));
            }
            if(null != searchDTO.getOrderType() && searchDTO.getOrderType().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("order_type", Arrays.asList(searchDTO.getOrderType())));
            }
            if(null != searchDTO.getOrderStatus() && searchDTO.getOrderStatus().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("order_status", Arrays.asList(searchDTO.getOrderStatus())));
            }
            if(null != searchDTO.getPaymentStatus() && searchDTO.getPaymentStatus().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("payment_status", Arrays.asList(searchDTO.getPaymentStatus())));
            }
            dao.useBaseFilter(queryWrapper,searchDTO);
        }

        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<Order> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        dao.page(page,queryWrapper);
        ResultPage<OrderDTO> resultPage = new ResultPage<>();
        resultPage.setList(OrderMapstruct.Instance.ordersToOrderDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
