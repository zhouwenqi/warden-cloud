package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.order.domain.dto.CreateOrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.OrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderPaymentDTO;
import com.microwarp.warden.cloud.facade.order.domain.vo.OrderPaymentVO;
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

    /** vo - dto */
    OrderPaymentVO orderPaymentDtoToOrderPaymentVO(OrderPaymentDTO orderPaymentDTO);
    List<OrderPaymentVO> orderPaymentDTOsToOrderPaymentVOs(List<OrderPaymentDTO> orderPaymentDTOs);

}
