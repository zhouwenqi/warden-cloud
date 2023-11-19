package com.microwarp.warden.cloud.service.user.service.impl;

import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.service.user.dao.UserDao;
import com.microwarp.warden.cloud.service.user.domain.convert.UserMapstruct;
import com.microwarp.warden.cloud.service.user.domain.entity.User;
import com.microwarp.warden.cloud.service.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * service - 用户 - impl
 * @author zhouwenqi
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,UserDao> implements UserService {
    /**
     * 查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public UserDTO findById(Long id){
        return this.dao.findById(id);
    }

    /**
     * 查询用户信息
     * @param uid 用户名(帐号)
     * @return 用户信息
     */
    @Override
    public UserDTO findByUid(String uid){
        return this.dao.findByUid(uid);
    }

    /**
     * 创建新用户
     * @param userDTO 用户信息
     * @return
     */
    @Override
    public UserDTO create(UserDTO userDTO){
        return this.dao.create(userDTO);
    }

}
