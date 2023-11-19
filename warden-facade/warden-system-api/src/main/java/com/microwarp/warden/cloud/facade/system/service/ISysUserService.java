package com.microwarp.warden.cloud.facade.system.service;

import com.microwarp.warden.cloud.common.core.constant.AppConstant;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * feign - 系统用户服务
 * @author zhouwenqi
 */
@FeignClient(value = AppConstant.USER_SERVICE_NAME, contextId = "iSysUserService")
public interface ISysUserService {
    @RequestMapping(HttpConstant.FEIGN_URI_PREFIX +"/sysuser/{uid}")
    SysUserDetailsDTO findByUid(@PathVariable("uid") String uid);
}
