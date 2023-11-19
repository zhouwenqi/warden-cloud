package com.microwarp.warden.cloud.service.user.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.service.user.domain.convert.UserMapstruct;
import com.microwarp.warden.cloud.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller - 用户
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     * @param id 用户id
     * @return
     */
    @GetMapping("{id}")
    public ResultModel info(@PathVariable("id") Long id){
        UserDTO userDTO = userService.findById(id);
        if(null == userDTO){
            throw new WardenParamterErrorException("用户不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("user", UserMapstruct.Instance.userDtoToUserVo(userDTO));
        return resultModel;
    }

}
