package com.microwarp.warden.cloud.service.user.service;

import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.service.user.domain.entity.User;

/**
 * service - 用户
 */
public interface UserService extends BaseService<User> {
    /**
     * 查询用户信息
     * @param id 用户id
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
     * 创建新用户
     * @param userDTO
     * @return
     */
    UserDTO create(UserDTO userDTO);
}
