package com.microwarp.warden.cloud.service.order.domain.convert;

import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.vo.OrderVO;
import com.microwarp.warden.cloud.service.order.domain.entity.Order;
import com.microwarp.warden.cloud.service.order.domain.vo.CreateOrderRequest;
import com.microwarp.warden.cloud.service.order.domain.vo.UpdateOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 订单
 * @author zhouwenqi
 */
@Mapper
public interface OrderMapstruct {
    OrderMapstruct Instance = Mappers.getMapper(OrderMapstruct.class);

    /** entity - dto */
    OrderDTO orderToOrderDTO(Order order);
    List<OrderDTO> ordersToOrderDTOs(List<Order> orders);
    Order createOrderDtoToOrder(CreateOrderDTO createOrderDTO);
    Order updateOrderDtoToOrder(UpdateOrderDTO updateOrderDTO);

    /** vo - dto */
    OrderVO orderDtoToOrderVO(OrderDTO orderDTO);
    List<OrderVO> orderDTOsToOrderVOs(List<OrderDTO> orderDTOs);
    CreateOrderDTO createOrderRequestToCreateOrderDTO(CreateOrderRequest createOrderRequest);
    UpdateOrderDTO updateOrderRequestToUpdateOrderDTO(UpdateOrderRequest updateOrderRequest);

}
