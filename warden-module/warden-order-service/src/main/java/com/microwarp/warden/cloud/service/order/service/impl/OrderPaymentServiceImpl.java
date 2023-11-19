package com.microwarp.warden.cloud.service.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.enums.SnEnum;
import com.microwarp.warden.cloud.service.order.dao.OrderPaymentDao;
import com.microwarp.warden.cloud.service.order.domain.convert.OrderPaymentMapstruct;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderPayment;
import com.microwarp.warden.cloud.service.order.service.OrderPaymentService;
import com.microwarp.warden.cloud.service.order.util.SnUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * service - 订单支付 - impl
 */
@Service
public class OrderPaymentServiceImpl extends BaseServiceImpl<OrderPayment,OrderPaymentDao> implements OrderPaymentService {

    /**
     * 获取订单支付信息
     * @param id 支付单ID
     * @return 支付单信息
     */
    public OrderPaymentDTO findById(Long id){
        return this.dao.findById(id);
    }

    /**
     * 获取订单支付信息
     * @param paymentSn 支付单SN
     * @return 支付单信息
     */
    public OrderPaymentDTO findBySn(String paymentSn){
        return this.dao.findBySn(paymentSn);
    }

    /**
     * 获取订单所有支付单信息
     * @param orderId 订单ID
     * @return 支付单列表
     */
    public List<OrderPaymentDTO> findByOrderId(Long orderId){
        return this.dao.findByOrderId(orderId);
    }

    /**
     * 创建支付订单
     * @param createOrderPaymentDTO 支付订单参数
     * @return 支付订单信息
     */
    @Override
    public OrderPaymentDTO create(CreateOrderPaymentDTO createOrderPaymentDTO){
        OrderPayment orderPayment = OrderPaymentMapstruct.Instance.createOrderPaymentDtoToOrderPayment(createOrderPaymentDTO);
        orderPayment.setPaymentSn(SnUtil.generateSN(SnEnum.PA));
        this.dao.save(orderPayment);
        return this.dao.findById(orderPayment.getId());
    }

    /**
     * 修改支付订单
     * @param updateOrderPaymentDTO 支付订单参数
     * @return
     */
    public OrderPaymentDTO update(UpdateOrderPaymentDTO updateOrderPaymentDTO){
        OrderPayment orderPayment = OrderPaymentMapstruct.Instance.updateOrderPaymentDtoToOrderPayment(updateOrderPaymentDTO);
        this.dao.updateById(orderPayment);
        return this.dao.findById(orderPayment.getId());
    }

    /**
     * 删除一条支付单
     * @param id 支付单ID
     */
    public void delete(Long id){
        this.dao.delete(id);
    }

    /**
     * 删除订单相关的所有支付单
     * @param orderId 订单ID
     */
    public void deleteByOrderId(Long orderId){
        this.dao.deleteByOrderId(orderId);
    }

    /**
     * 分页查询支付单列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<OrderPaymentDTO> findPage(ISearchPageable<OrderPaymentSearchDTO> iSearchPageable){
        QueryWrapper<OrderPayment> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .likeLeft("payment_sn", iSearchPageable.getSearchValue())
                    .or()
                    .likeLeft("prd_sn", iSearchPageable.getSearchValue())
            );
        }
        if(null != iSearchPageable.getFilters()){
            OrderPaymentSearchDTO searchDTO = iSearchPageable.getFilters();
            if(null != searchDTO.getAmounts() && searchDTO.getAmounts().length > 0){
                if(searchDTO.getAmounts().length < 2){
                    queryWrapper.and(true, wrapper -> wrapper.ge("payment_amount",searchDTO.getAmounts()[0]));
                }else{
                    queryWrapper.and(true, wrapper -> wrapper.between("payment_amount",searchDTO.getAmounts()[0],searchDTO.getAmounts()[1]));
                }
            }
            if(null != searchDTO.getOrderId()) {
                queryWrapper.and(true, wrapper -> wrapper.eq("order_id", searchDTO.getOrderId()));
            }
            if(null != searchDTO.getPaymentChannel() && searchDTO.getPaymentChannel().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("payment_channel", Arrays.asList(searchDTO.getPaymentChannel())));
            }
            if(null != searchDTO.getPaymentMethod() && searchDTO.getPaymentMethod().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("payment_method", Arrays.asList(searchDTO.getPaymentMethod())));
            }
            if(null != searchDTO.getPaymentStatus() && searchDTO.getPaymentStatus().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("payment_status", Arrays.asList(searchDTO.getPaymentStatus())));
            }
            if(null != searchDTO.getPaymentDate() && searchDTO.getPaymentDate().length > 0){
                if(searchDTO.getCreateDate().length < 2){
                    queryWrapper.and(true, wrapper -> wrapper.ge("payment_date",searchDTO.getPaymentDate()[0]));
                }else{
                    queryWrapper.and(true, wrapper -> wrapper.between("payment_date",searchDTO.getPaymentDate()[0],searchDTO.getPaymentDate()[1]));
                }
            }
            dao.useBaseFilter(queryWrapper,searchDTO);
        }

        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<OrderPayment> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        dao.page(page,queryWrapper);
        ResultPage<OrderPaymentDTO> resultPage = new ResultPage<>();
        resultPage.setList(OrderPaymentMapstruct.Instance.orderPaymentsToOrderPaymentDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }

}
