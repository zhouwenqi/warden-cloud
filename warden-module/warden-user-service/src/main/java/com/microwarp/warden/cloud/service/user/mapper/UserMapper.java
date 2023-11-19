package com.microwarp.warden.cloud.service.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.microwarp.warden.cloud.service.user.domain.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * mapper - 用户
 */
public interface UserMapper extends BaseMapper<User> {
    List<String> findByUserId(@Param("userId") Long userId);
    void deleteByUserId(@Param("userId") Long userId);
    void deleteByPermissionValue(@Param("permissionValue") String permissionValue);
    void insertUserPermission(List<Map> list);
}
