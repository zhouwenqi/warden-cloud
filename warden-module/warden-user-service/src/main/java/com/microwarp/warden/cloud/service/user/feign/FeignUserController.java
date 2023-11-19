package com.microwarp.warden.cloud.service.user.feign;

import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.service.IUserService;
import com.microwarp.warden.cloud.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller - 用户 - feign impl
 * @author zhouwenqi
 */
@RestController
public class FeignUserController implements IUserService{
    @Autowired
    private UserService userService;
    /** 用户ID查询用户信息 */
    @Override
    public UserDTO findByUid(@RequestParam String username) {
        return userService.findByUid(username);
    }
    /** 用户ID查询用户信息 */
    @Override
    public UserDTO findById(@PathVariable("id") Long  id) {
        return userService.findById(id);
    }
}
