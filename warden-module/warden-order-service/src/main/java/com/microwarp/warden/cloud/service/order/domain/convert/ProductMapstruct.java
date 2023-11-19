package com.microwarp.warden.cloud.service.order.domain.convert;

import com.microwarp.warden.cloud.facade.order.domain.dto.CreateProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.vo.ProductVO;
import com.microwarp.warden.cloud.service.order.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 订单
 * @author zhouwenqi
 */
@Mapper
public interface ProductMapstruct {
    ProductMapstruct Instance = Mappers.getMapper(ProductMapstruct.class);

    /** entity - dto */
    ProductDTO productToProductDTO(Product product);
    List<ProductDTO> productsToProductDTOs(List<Product>  products);
    Product createProductDtoToProduct(CreateProductDTO createProductDTO);
    Product updateProductDtoToProduct(UpdateProductDTO updateProductDTO);

    /** vo - dto */
    ProductVO productDtoToProductVO(ProductDTO productDTO);
    List<ProductVO> productDTOsToProductVOs(List<ProductDTO> productDTOs);

}
