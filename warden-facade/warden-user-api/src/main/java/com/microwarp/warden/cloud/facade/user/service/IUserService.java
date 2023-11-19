package com.microwarp.warden.cloud.facade.user.service;

import com.microwarp.warden.cloud.common.core.constant.AppConstant;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.user.domain.dto.UpdatePermissionsDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDetailsDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(HttpConstant.FEIGN_URI_PREFIX +"/user")
    UserDTO findByUid(@RequestParam("username") String username);

    /**
     * 查询用户信息
     * @param id 用户ID
     * @return
     */
    @GetMapping(HttpConstant.FEIGN_URI_PREFIX +"/user/{id}")
    UserDTO findById(@PathVariable("id") Long id);

    /**
     * 删除用户信息
     * @param id 用户id
     */
    @DeleteMapping(HttpConstant.FEIGN_URI_PREFIX +"/user/{id}")
    void delete(@PathVariable("id")Long id);

    /**
     * 更新用户权限
     * @param updatePermissionsDTO 用户权限
     */
    @PatchMapping(HttpConstant.FEIGN_URI_PREFIX +"/user/premissions")
    void updatePermissions(@RequestBody UpdatePermissionsDTO updatePermissionsDTO);

    /**
     * 分页查询用户信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @RequestMapping(HttpConstant.FEIGN_URI_PREFIX +"/user/search")
    ResultPage<UserDTO> findPage(@RequestBody ISearchPageable<UserSearchDTO> iSearchPageable);
}
