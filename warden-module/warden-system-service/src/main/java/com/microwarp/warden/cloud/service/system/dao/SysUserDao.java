package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUser;

import java.util.List;

/**
 * dao - 系统用户
 * @author zhouwenqi
 */
public interface SysUserDao extends BaseDao<SysUser> {
    /**
     * 查询系统用户基本信息
     * @param userId 用户id
     * @return
     */
    SysUserDTO findById(Long userId);
    /**
     * 查询系统用户基本信息
     * @param uid 用户名(帐号)
     * @return
     */
    SysUserDTO findByUid(String uid);

    /**
     * 查询所有有效系统用户
     * @return 系统用户列表
     */
    List<SysUser> findAll();

    /**
     * 刷新用户密钥
     * @param userId 用户id
     * @return 新的密钥
     */
    String refreshSecretKey(Long userId);
    /**
     * 清除用户岗位id
     * @param id 岗位id
     */
    void clearPostId(Long... id);

    /**
     * 清除用户部门id
     * @param id 部门id
     */
    public void clearDeptId(Long... id);
}
