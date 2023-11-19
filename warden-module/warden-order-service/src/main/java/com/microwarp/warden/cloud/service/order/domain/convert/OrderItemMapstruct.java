package com.microwarp.warden.cloud.service.order.domain.convert;

import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderItemDTO;
import com.microwarp.warden.cloud.facade.order.domain.vo.OrderItemVO;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderItem;
import com.microwarp.warden.cloud.service.order.domain.vo.CreateOrderItemRequest;
import com.microwarp.warden.cloud.service.order.domain.vo.UpdateOrderItemRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 订单
 * @author zhouwenqi
 */
@Mapper
public interface OrderItemMapstruct {
    OrderItemMapstruct Instance = Mappers.getMapper(OrderItemMapstruct.class);

    /** entity - dto */
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
    List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems);
    OrderItem createOrderItemDtoToOrderItem(CreateOrderItemDTO createOrderItemDTO);
    List<OrderItem> createOrderItemsToOrderItems(List<CreateOrderItemDTO> createOrderItemDTOs);
    OrderItem updateOrderItemDtoToOrderItem(UpdateOrderItemDTO updateOrderItemDTO);

    /** vo - dto */
    OrderItemVO orderItemDtoToOrderItemVO(OrderItemDTO orderItemDTO);
    List<OrderItemVO> orderItemDTOsToOrderItemVOs(List<OrderItemDTO> orderItemDTOs);
    CreateOrderItemDTO createOrderItemRequestToCreateOrderItemDTO(CreateOrderItemRequest createOrderItemRequest);
    List<CreateOrderItemRequest> createOrderItemRequestsToCreateOrderItemDTOs(List<CreateOrderItemRequest> createOrderItemRequests);
    UpdateOrderItemDTO updateOrderItemRequestToUpdateOrderItemDTO(UpdateOrderItemRequest updateOrderItemRequest);

}
