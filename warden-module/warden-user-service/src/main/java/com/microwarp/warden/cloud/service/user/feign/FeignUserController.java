package com.microwarp.warden.cloud.service.user.feign;

import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.facade.user.domain.dto.UpdatePermissionsDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserSearchDTO;
import com.microwarp.warden.cloud.facade.user.service.IUserService;
import com.microwarp.warden.cloud.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 删除用户
     * @param id 用户id
     */
    @Override
    public void delete(@PathVariable("id")Long id){
        userService.delete(id);
    }
    /**
     * 更新用户权限
     * @param updatePermissionsDTO 用户权限
     */
    @Override
    public void updatePermissions(@RequestBody UpdatePermissionsDTO updatePermissionsDTO){
        userService.updateUserPermissions(updatePermissionsDTO.getPermissionValues(),updatePermissionsDTO.getUserId());
    }

    /**
     * 分页查询用户信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @Override
    public ResultPage<UserDTO> findPage(@RequestBody ISearchPageable<UserSearchDTO> iSearchPageable){
        return userService.findPage(iSearchPageable);
    }
}
