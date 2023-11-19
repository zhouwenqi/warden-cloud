package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPermissionDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPermissionTreeDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysPermission;

import java.util.List;

/**
 * dao - 系统权限
 */
public interface SysPermissionDao extends BaseDao<SysPermission> {
    /**
     * 查询权限信息
     * @param id 权限ID
     * @return 权限信息
     */
    SysPermissionDTO findById(Long id);
    /**
     * 获取子权限列表
     * @param parentId 父级权限ID
     * @return 权限列表
     */
    List<SysPermissionTreeDTO> findByParentId(Long parentId);
    /**
     * 查询所有权限
     * @return
     */
    List<SysPermissionDTO> findAll();
    /**
     * 根据角色id数组查询权限列表
     * @param roleIds 角色id数组
     * @return
     */
    List<SysPermissionDTO> findByRoleIds(Long... roleIds);

    /**
     * 根据权限id查询权限列表
     * @param ids 权限值
     * @return
     */
    List<SysPermissionDTO> findByIds(Long... ids);

    /**
     * 保存角色权限关联信息
     * @param roleId 角色id
     * @param permissionIds 权限id
     */
    void saveRolePermission(Long roleId, Long... permissionIds);
    /**
     * 根据角色id删除权限与角色关联
     * @param roleIds 角色id
     */
    void deletePermissionByRoleIds(Long... roleIds);

    /**
     * 删除权限
     * @param ids 权限id 列表
     */
    void delete(Long... ids);
}
