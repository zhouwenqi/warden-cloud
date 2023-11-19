package com.microwarp.warden.cloud.service.user.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.service.user.domain.entity.User;

import java.util.List;

/**
 * dao - 用户
 * @author zhouwenqi
 */
public interface UserDao extends BaseDao<User> {
    /**
     * 查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserDTO findById(Long id);

    /**
     * 查询用户信息
     * @param uid 用户名(帐号)
     * @return 用户信息
     */
    UserDTO findByUid(String uid);

    /**
     * 删除用户信息
     * @param userId 用户id
     */
    void delete(Long userId);

    /**
     * 更新用户权限
     * @param permissionValues 权限列表
     * @param userId 用户id
     */
    void updateUserPermissions(List<String> permissionValues, Long userId);

}
