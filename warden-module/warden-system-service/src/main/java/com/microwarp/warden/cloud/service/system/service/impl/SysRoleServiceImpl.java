package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.pageing.*;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysRoleDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysRoleDetailsDTO;
import com.microwarp.warden.cloud.service.system.dao.SysPermissionDao;
import com.microwarp.warden.cloud.service.system.dao.SysRoleDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysRoleMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysRole;
import com.microwarp.warden.cloud.service.system.service.SysRoleService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * service - 系统角色 - impl
 * @author zhouwenqi
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole,SysRoleDao> implements SysRoleService {
    @Resource
    private SysPermissionDao sysPermissionDao;
    @Resource
    private SysUserService sysUserService;

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<SysRoleDTO> findAll(){
        return this.dao.findAll();
    }
    /**
     * 查询系统角色信息
     * @param id 角色id
     * @return
     */
    @Override
    public SysRoleDTO findById(Long id){
        SysRole sysRole = this.dao.getById(id);
        return null != sysRole ? SysRoleMapstruct.Instance.sysRoleToSysRoleDTO(sysRole) : null;
    }

    /**
     * 查询角色列表
     * @param ids 角色id
     * @return
     */
    @Override
    public List<SysRoleDTO> findByIds(Long...ids){
        return this.dao.findByIds(ids);
    }

    /**
     * 查询系统角色详情
     * @param id 角色id
     * @return 角色详情
     */
    @Override
    public SysRoleDetailsDTO findDetailsById(Long id){
        SysRoleDTO sysRoleDTO = findById(id);
        if(null == sysRoleDTO){
            return null;
        }
        SysRoleDetailsDTO sysRoleDetailsDTO = SysRoleMapstruct.Instance.sysRoleDtoToSysRoleDetailsDTO(sysRoleDTO);
        sysRoleDetailsDTO.setPermissions(new HashSet<>(sysPermissionDao.findByRoleIds(sysRoleDetailsDTO.getId())));
        return sysRoleDetailsDTO;
    }

    /**
     * 删除角色信息
     * @param id
     */
    @Transactional
    @Override
    public void delete(Long... id){
         // 先删除角色与权限关系信息
         sysPermissionDao.deletePermissionByRoleIds(id);
         // 先删除角色与用户关系信息，和角色信息
         this.dao.deleteByIds(id);
        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 创建系统角色
     * @param sysRoleDTO 角色信息
     * @return 角色信息
     */
    @Transactional
    @Override
    public SysRoleDTO create(SysRoleDTO sysRoleDTO) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("value",sysRoleDTO.getValue());
        if(this.dao.count(queryWrapper) > 0){
            throw new WardenParamterErrorException("角色值不能重复");
        }
        SysRole sysRole = SysRoleMapstruct.Instance.sysRoleDtoToSysSysRole(sysRoleDTO);
        this.dao.save(sysRole);
        return findById(sysRole.getId());
    }

    /**
     * 更新角色信息
     * @param sysRoleDTO 角色信息
     */
    @Transactional
    @Override
    public void update(SysRoleDTO sysRoleDTO){
        if(null == sysRoleDTO.getId()){
            return;
        }
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("id",sysRoleDTO.getId());
        queryWrapper.eq("value",sysRoleDTO.getValue());
        if(this.dao.count(queryWrapper) > 0){
            throw new WardenParamterErrorException("角色值不能重复");
        }
        SysRole sysRole = SysRoleMapstruct.Instance.sysRoleDtoToSysSysRole(sysRoleDTO);
        this.dao.updateById(sysRole);
        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 角色拖拽排序
     * @param baseSortDTO 排序参数
     */
    @Transactional
    @Override
    public void dragAndSort(BaseSortDTO baseSortDTO){
        if(null != baseSortDTO.getIds() && baseSortDTO.getIds().length > 0){
            int i = 0;
            for(Long id:baseSortDTO.getIds()){
                UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("orders",i);
                updateWrapper.eq("id",id);
                this.dao.update(updateWrapper);
                i ++;
            }
        }
    }


    /**
     * 设置系统用户角色信息
     * @param userId 用户id
     * @param roleIds 角色id
     */
    @Override
    @Transactional
    public void saveUserRoles(Long userId, Long... roleIds) {
        this.dao.saveUserRoles(userId, roleIds);
        // 清除用户缓存
        sysUserService.clearCache(userId);
    }

    /**
     * 查询角色列表
     * @param values 角色值
     * @return
     */
    @Override
    public List<SysRoleDTO> findByValues(String...values){
        return this.dao.findByValues(values);
    }

    /**
     * 分页查询角色列表
     * @param iSearchPageable 查询条件
     * @return
     */
    @Override
    public ResultPage<SysRoleDTO> findPage(ISearchPageable<BasicSearchDTO> iSearchPageable){
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("name", iSearchPageable.getSearchValue())
                    .or()
                    .like("value", iSearchPageable.getSearchValue())
                    .or()
                    .like("description", iSearchPageable.getSearchValue())
            );
        }
        if(null != iSearchPageable.getFilters()){
            this.dao.useBaseFilter(queryWrapper,iSearchPageable.getFilters());
        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysRole> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        this.dao.page(page,queryWrapper);
        ResultPage<SysRoleDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysRoleMapstruct.Instance.sysRolesToSysRoleDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
