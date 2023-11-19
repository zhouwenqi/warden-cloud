package com.microwarp.warden.cloud.service.order.domain.convert;

import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.vo.OrderPaymentVO;
import com.microwarp.warden.cloud.service.order.domain.entity.OrderPayment;
import com.microwarp.warden.cloud.service.order.domain.vo.CreateOrderPaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 订单
 * @author zhouwenqi
 */
@Mapper
public interface OrderPaymentMapstruct {
    OrderPaymentMapstruct Instance = Mappers.getMapper(OrderPaymentMapstruct.class);

    /** entity - dto */
    OrderPaymentDTO orderPaymentToOrderPaymentDTO(OrderPayment orderPayment);
    List<OrderPaymentDTO> orderPaymentsToOrderPaymentDTOs(List<OrderPayment> orderPayments);
    OrderPayment createOrderPaymentDtoToOrderPayment(CreateOrderPaymentDTO createOrderPaymentDTO);
    OrderPayment updateOrderPaymentDtoToOrderPayment(UpdateOrderPaymentDTO updateOrderPaymentDTO);

    /** vo - dto */
    OrderPaymentVO orderPaymentDtoToOrderPaymentVO(OrderPaymentDTO orderPaymentDTO);
    List<OrderPaymentVO> orderPaymentDTOsToOrderPaymentVOs(List<OrderPaymentDTO> orderPaymentDTOs);
    CreateOrderPaymentDTO createOrderPaymentRequestToCreateOrderPaymentDTO(CreateOrderPaymentRequest createOrderPaymentRequest);

}
