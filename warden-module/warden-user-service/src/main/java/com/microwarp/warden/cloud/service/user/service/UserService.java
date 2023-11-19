package com.microwarp.warden.cloud.service.user.service;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.user.domain.dto.*;
import com.microwarp.warden.cloud.service.user.domain.entity.User;

import java.util.List;

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
     * @param createUserDTO 用户信息
     * @return
     */
    UserDTO create(CreateUserDTO createUserDTO);

    /**
     * 更新用户信息
     * @param updateUserDTO 用户信息
     * @return
     */
    UserDTO update(UpdateUserDTO updateUserDTO);

    /**
     * 更新用户密码
     * @param userPasswordDTO 密码信息
     */
    void updatePassword(UserPasswordDTO userPasswordDTO);

    /**
     * 更新用户权限
     * @param permissionValues 权限列表
     * @param userId 用户id
     */
    void updateUserPermissions(List<String> permissionValues, Long userId);

    /**
     * 删除用户
     * @param id 用户id
     */
    void delete(Long id);

    /**
     * 分页查询用户信息
     * @param iSearchPageable 查询条件
     * @return
     */
    ResultPage<UserDTO> findPage(ISearchPageable<UserSearchDTO> iSearchPageable);
}
