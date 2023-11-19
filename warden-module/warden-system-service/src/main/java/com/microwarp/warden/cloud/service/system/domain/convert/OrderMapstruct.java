package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.order.domain.dto.OrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateOrderDTO;
import com.microwarp.warden.cloud.facade.order.domain.vo.OrderVO;
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

    /** vo - dto */
    OrderVO orderDtoToOrderVO(OrderDTO orderDTO);
    List<OrderVO> orderDTOsToOrderVOs(List<OrderDTO> orderDTOs);

}
