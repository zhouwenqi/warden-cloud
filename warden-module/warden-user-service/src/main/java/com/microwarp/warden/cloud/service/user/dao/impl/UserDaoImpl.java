package com.microwarp.warden.cloud.service.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDetailsDTO;
import com.microwarp.warden.cloud.service.user.dao.UserDao;
import com.microwarp.warden.cloud.service.user.domain.convert.UserMapstruct;
import com.microwarp.warden.cloud.service.user.domain.entity.User;
import com.microwarp.warden.cloud.service.user.mapper.UserMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * dao - 用户 - impl
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserMapper,User> implements UserDao {

    /**
     * 查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserDTO findById(Long id){
        User user = baseMapper.selectById(id);
        if(null == user){
            return null;
        }
        UserDTO userDTO = UserMapstruct.Instance.userToUserDto(user);
        userDTO.setPermissionValues(baseMapper.findByUserId(user.getId()));
        return  userDTO;
    }

    /**
     * 查询用户信息
     * @param uid 用户名(帐号)
     * @return 用户信息
     */
    @Override
    public UserDTO findByUid(String uid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        User user = baseMapper.selectOne(queryWrapper);
        if(null == user){
            return null;
        }
        UserDTO userDTO = UserMapstruct.Instance.userToUserDto(user);
        userDTO.setPermissionValues(baseMapper.findByUserId(user.getId()));
        return  userDTO;
    }

    /**
     * 删除用户信息
     * @param userId 用户id
     */
    public void delete(Long userId){
        baseMapper.deleteByUserId(userId);
        baseMapper.deleteById(userId);
    }


    /**
     * 更新用户权限
     * @param permissionValues 权限列表
     * @param userId 用户id
     */
    @Override
    public void updateUserPermissions(List<String> permissionValues, Long userId){
        if(null != permissionValues && permissionValues.size() > 0){
            baseMapper.deleteByUserId(userId);
            List<Map> list = new ArrayList<>();
            permissionValues.forEach(value->{
                Map<String,Object> map = new HashMap<>();
                map.put("userId",userId);
                map.put("permissionValue",value);
                list.add(map);
            });
            baseMapper.insertUserPermission(list);
        }
    }

}
