package com.microwarp.warden.cloud.service.user.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultFlux;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.facade.user.domain.dto.UpdateUserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserPasswordDTO;
import com.microwarp.warden.cloud.service.user.domain.convert.UserMapstruct;
import com.microwarp.warden.cloud.service.user.domain.vo.UpdateUserRequest;
import com.microwarp.warden.cloud.service.user.domain.vo.UserPasswordRequest;
import com.microwarp.warden.cloud.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 用户
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     * @param id 用户id
     * @return
     */
    @GetMapping("user/{id}")
    public ResultModel info(@PathVariable("id") Long id){
        UserDTO userDTO = userService.findById(id);
        if(null == userDTO){
            throw new WardenParamterErrorException("用户不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("user", UserMapstruct.Instance.userDtoToUserVo(userDTO));
        return resultModel;
    }

    /**
     * 更新用户信息
     * @param updateUserRequest 用户信息
     * @return
     */
    @PatchMapping("user")
    public ResultModel update(@RequestBody @Validated UpdateUserRequest updateUserRequest) {
        UserDTO userDTO = userService.findById(updateUserRequest.getId());
        if(null == userDTO){
            throw new WardenParamterErrorException("用户不存在");
        }
        UpdateUserDTO updateUserDTO = UserMapstruct.Instance.updateUserRequestToUpdateUserDTO(updateUserRequest);
        userService.update(updateUserDTO);
        return  ResultModel.success();

    }

    /**
     * 更新用户密码
     * @param userPasswordRequest 密码信息
     * @return
     */
    @PatchMapping("user/password")
    public ResultModel update(@RequestBody @Validated UserPasswordRequest userPasswordRequest){
        UserPasswordDTO userPasswordDTO = UserMapstruct.Instance.userPasswordRequestToUserPasswordDTO(userPasswordRequest);
        userService.updatePassword(userPasswordDTO);
        return  ResultModel.success();
    }

}
