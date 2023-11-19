package com.microwarp.warden.cloud.service.system.feign;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.facade.system.service.ISysUserService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * controller - 系统用户 - feign impl
 * @author zhouwenqi
 */
@RestController
public class FeignSysUserController implements ISysUserService {
    @Resource
    private SysUserService userService;
    public SysUserDetailsDTO findByUid(@PathVariable("uid") String uid){
        return userService.findDetailsByUid(uid);
    }
}
