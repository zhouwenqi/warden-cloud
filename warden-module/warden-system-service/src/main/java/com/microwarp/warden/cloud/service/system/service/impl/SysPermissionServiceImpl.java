package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.pageing.*;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.common.redis.constant.CacheKeys;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPermissionDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPermissionTreeDTO;
import com.microwarp.warden.cloud.service.system.dao.SysPermissionDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysPermissionMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysPermission;
import com.microwarp.warden.cloud.service.system.service.SysPermissionService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * service - 系统权限 - impl
 * @author zhouwenqi
 */
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission,SysPermissionDao> implements SysPermissionService {
    @Resource
    private SysUserService sysUserService;

    /**
     * 查询所有权限
     * @return
     */
    @Override
    public List<SysPermissionDTO> findAll(){
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("orders");
        List<SysPermission> list = this.dao.list(queryWrapper);
        if(null == list || list.size() < 1)
        {
            return new ArrayList<>();
        }
        return SysPermissionMapstruct.Instance.sysPermissionsToSysPermissionDTOs(list);
    }

    /**
     * 查询权限信息
     * @param id 权限id
     * @return 权限信息
     */
    @Override
    public SysPermissionDTO findById(Long id){
        SysPermissionDTO sysPermissionDTO = this.dao.findById(id);
        recursionParent(sysPermissionDTO);
        return sysPermissionDTO;
    }

    /**
     * 查询权限信息(含子级权限列表)
     * @param id 权限ID
     * @return 权限信息
     */
    public SysPermissionTreeDTO findChildrenById(Long id){
        SysPermission sysPermission = this.dao.getById(id);
        if(null == sysPermission){
            return null;
        }
        SysPermissionTreeDTO sysPermissionTreeDTO = SysPermissionMapstruct.Instance.sysPermissionToSysPermissionTreeDTO(sysPermission);
        sysPermissionTreeDTO.setChildren(this.dao.findByParentId(sysPermission.getParentId()));
        return  sysPermissionTreeDTO;
    }
    /**
     * 创建权限
     * @param sysPermissionDTO 权限内容
     * @return 权限信息
     */
    @Override
    @Transactional
    public SysPermissionDTO create(SysPermissionDTO sysPermissionDTO){
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("value",sysPermissionDTO.getValue());
        if(this.dao.count(queryWrapper) > 0){
            throw new WardenParamterErrorException("权限值不能重复");
        }
        SysPermission sysPermission = SysPermissionMapstruct.Instance.sysPermissionDtoToSysPermission(sysPermissionDTO);
        this.dao.save(sysPermission);
        return findById(sysPermission.getId());
    }

    /**
     * 更新权限信息
     * @param sysPermissionDTO 权限信息
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheKeys.DATA_SYS_PERMISSION_TREE, allEntries = true)
    public void update(SysPermissionDTO sysPermissionDTO){
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("id",sysPermissionDTO.getId());
        queryWrapper.eq("value",sysPermissionDTO.getValue());
        if(this.dao.count(queryWrapper) > 0){
            throw new WardenParamterErrorException("权限值不能重复");
        }
        SysPermission sysPermission = SysPermissionMapstruct.Instance.sysPermissionDtoToSysPermission(sysPermissionDTO);
        this.dao.updateById(sysPermission);

        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 删除权限信息
     * @param id 权限id
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheKeys.DATA_SYS_PERMISSION_TREE, allEntries = true)
    public void delete(Long... id){
        if(null == id || id.length < 1){
            return;
        }
        List<Long> ids = new ArrayList<>();
        recursionIds(Arrays.asList(id),ids);
        this.dao.delete(ids.toArray(new Long[ids.size()]));
        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 保存角色权限关联信息
     * @param roleId 角色id
     * @param permissionIds 权限id
     */
    @Override
    @Transactional
    public void saveRolePermission(Long roleId,Long... permissionIds){
        this.dao.saveRolePermission(roleId, permissionIds);
        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 权限拖拽排序
     * @param baseSortDTO 排序参数
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheKeys.DATA_SYS_PERMISSION_TREE, allEntries = true)
    public void dragAndSort(BaseSortDTO baseSortDTO){
        if(null != baseSortDTO.getParentId() && null != baseSortDTO.getDragId()){
            SysPermission sysPermission = new SysPermission();
            sysPermission.setParentId(baseSortDTO.getParentId());
            sysPermission.setId(baseSortDTO.getDragId());
            this.dao.updateById(sysPermission);
            // 清除用户缓存
            sysUserService.clearAll();
        }
        if(null != baseSortDTO.getIds() && baseSortDTO.getIds().length > 0){
            int i = 0;
            for(Long id:baseSortDTO.getIds()){
                UpdateWrapper<SysPermission> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("orders",i);
                updateWrapper.eq("id",id);
                this.dao.update(updateWrapper);
                i ++;
            }
        }
    }

    /**
     * 递归查询父级权限信息
     * @param sysPermissionDTO 权限信息
     */
    private void recursionParent(SysPermissionDTO sysPermissionDTO){
        if(null != sysPermissionDTO.getParentId() && sysPermissionDTO.getParentId() > 0){
            sysPermissionDTO.setParentPermission(findById(sysPermissionDTO.getParentId()));
            if(null != sysPermissionDTO.getParentPermission()){
                recursionParent(sysPermissionDTO.getParentPermission());
            }
        }
    }

    /**
     * 递归获取下级权限列表
     * @param list 权限列表
     */
    private void recursionChildren(List<SysPermissionTreeDTO> list){
        if(null == list || list.size() < 1){
            return;
        }
        for(SysPermissionTreeDTO sysPermissionTreeDTO:list){
            List<SysPermissionTreeDTO> sysPermissionTreeDTOS = this.dao.findByParentId(sysPermissionTreeDTO.getId());
            sysPermissionTreeDTO.setChildren(sysPermissionTreeDTOS.size() < 1 ? null : sysPermissionTreeDTOS);
            recursionChildren(sysPermissionTreeDTO.getChildren());
        }
    }

    /**
     * 递归获取所有权限ID
     * @param ids 下级权限ID列表
     * @param permissionIds 权限ID平铺列表
     * @return
     */
    @Override
    public void recursionIds(List<Long> ids,List<Long> permissionIds){
        if(ids != null && ids.size() > 0){
            permissionIds.addAll(ids);
            for (Long id:ids){
                QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("id");
                queryWrapper.eq("parent_id",id);
                List<SysPermission> sysPermissions = this.dao.list(queryWrapper);
                if(null != sysPermissions && sysPermissions.size()>0){
                    recursionIds(sysPermissions.stream().map(SysPermission::getId).collect(Collectors.toList()),permissionIds);
                }
            }
        }
    }

    /**
     * 根据权限id查询权限列表
     * @param ids 权限值
     * @return
     */
    @Override
    public List<SysPermissionDTO> findByIds(Long... ids){
        return this.dao.findByIds(ids);
    }

    /**
     * 获取所有权限树
     * @return 权限树
     */
    @Override
    @Cacheable(value = CacheKeys.DATA_SYS_PERMISSION_TREE, key="'tree'", unless = "#result.size() < 1")
    public List<SysPermissionTreeDTO> findTrees(){
        List<SysPermissionTreeDTO> root = this.dao.findByParentId(0L);
        recursionChildren(root);
        return root;
    }

    /**
     * 分页查询权限信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @Override
    public ResultPage<SysPermissionDTO> findPage(ISearchPageable<BasicSearchDTO> iSearchPageable){
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("name", iSearchPageable.getSearchValue())
                    .or()
                    .like("value", iSearchPageable.getSearchValue())
            );
        }

        if(null != iSearchPageable.getFilters()){
            this.dao.useBaseFilter(queryWrapper,iSearchPageable.getFilters());

        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysPermission> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        this.dao.page(page,queryWrapper);
        ResultPage<SysPermissionDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysPermissionMapstruct.Instance.sysPermissionsToSysPermissionDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
