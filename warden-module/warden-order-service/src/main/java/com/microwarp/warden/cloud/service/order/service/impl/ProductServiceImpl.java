package com.microwarp.warden.cloud.service.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.order.domain.dto.CreateProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.ProductSearchDTO;
import com.microwarp.warden.cloud.facade.order.domain.dto.UpdateProductDTO;
import com.microwarp.warden.cloud.facade.order.enums.SnEnum;
import com.microwarp.warden.cloud.service.order.dao.ProductDao;
import com.microwarp.warden.cloud.service.order.domain.convert.ProductMapstruct;
import com.microwarp.warden.cloud.service.order.domain.entity.Product;
import com.microwarp.warden.cloud.service.order.service.ProductService;
import com.microwarp.warden.cloud.service.order.util.SnUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
/**
 * service - 商品 - impl
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product,ProductDao> implements ProductService {

    /**
     * 查询商品信息
     * @param id 商品ID
     * @return 商品信息
     */
    @Override
    public ProductDTO findById(Long id){
        return this.dao.findById(id);
    }

    /**
     * 查询商品信息
     * @param productSn 商品编号
     * @return 商品信息
     */
    @Override
    public ProductDTO findBySn(String productSn){
        return this.dao.findBySn(productSn);
    }

    /**
     * 创建商品
     * @param createProductDTO 商品参数
     * @return 创建成功的商品信息
     */
    @Override
    public ProductDTO create(CreateProductDTO createProductDTO){
        Product product = ProductMapstruct.Instance.createProductDtoToProduct(createProductDTO);
        product.setPrdSn(SnUtil.generateSN(SnEnum.PD));
        this.dao.save(product);
        return this.dao.findById(product.getId());
    }

    /**
     * 更新商品信息
     * @param updateProductDTO 商品信息
     * @return 商品信息
     */
    @Override
    public ProductDTO update(UpdateProductDTO updateProductDTO){
        if(null == this.dao.getById(updateProductDTO.getId())){
            throw new WardenParamterErrorException("商品信息不存在");
        }
        Product product = ProductMapstruct.Instance.updateProductDtoToProduct(updateProductDTO);
        this.dao.updateById(product);
        return this.dao.findById(product.getId());
    }

    /**
     * 删除商品
     * @param productId 商品id
     */
    @Override
    public void delete(Long productId){
        this.dao.removeById(productId);
    }

    /**
     * 分页查询商品列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<ProductDTO> findPage(ISearchPageable<ProductSearchDTO> iSearchPageable){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("prd_name", iSearchPageable.getSearchValue())
                    .or()
                    .like("title", iSearchPageable.getSearchValue())
                    .or()
                    .like("description", iSearchPageable.getSearchValue())
                    .or()
                    .likeLeft("prd_sn", iSearchPageable.getSearchValue())
            );
        }
        if(null != iSearchPageable.getFilters()){
            ProductSearchDTO searchDTO = iSearchPageable.getFilters();
            if(null != searchDTO.getPrices() && searchDTO.getPrices().length > 0){
                if(searchDTO.getPrices().length < 2){
                    queryWrapper.and(true, wrapper -> wrapper.ge("sales_price",searchDTO.getPrices()[0]));
                }else{
                    queryWrapper.and(true, wrapper -> wrapper.between("sales_price",searchDTO.getPrices()[0],searchDTO.getPrices()[1]));
                }
            }
            dao.useBaseFilter(queryWrapper,searchDTO);
        }

        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<Product> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        dao.page(page,queryWrapper);
        ResultPage<ProductDTO> resultPage = new ResultPage<>();
        resultPage.setList(ProductMapstruct.Instance.productsToProductDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
