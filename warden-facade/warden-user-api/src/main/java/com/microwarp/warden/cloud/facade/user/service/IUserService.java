package com.microwarp.warden.cloud.facade.user.service;

import com.microwarp.warden.cloud.common.core.constant.AppConstant;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feign - 用户服务
 * @author zhouwenqi
 */
@FeignClient(value = AppConstant.USER_SERVICE_NAME, contextId = "iUserService")
public interface IUserService {
    /**
     * 查询用户信息
     * @param username 用户名
     * @return
     */
    @RequestMapping(HttpConstant.FEIGN_URI_PREFIX +"/user")
    UserDTO findByUid(@RequestParam("username") String username);

    /**
     * 查询用户信息
     * @param id 用户ID
     * @return
     */
    @RequestMapping(HttpConstant.FEIGN_URI_PREFIX +"/user/{id}")
    UserDTO findById(@PathVariable("id") Long id);
}
