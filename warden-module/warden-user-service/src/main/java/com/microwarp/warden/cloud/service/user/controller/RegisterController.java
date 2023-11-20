package com.microwarp.warden.cloud.service.user.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.security.service.CaptchaService;
import com.microwarp.warden.cloud.facade.user.domain.dto.CreateUserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.service.user.config.WardenUserConfig;
import com.microwarp.warden.cloud.service.user.domain.convert.UserMapstruct;
import com.microwarp.warden.cloud.service.user.domain.vo.RegisterUserRequest;
import com.microwarp.warden.cloud.service.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller - 注册
 * @author zhouwenqi
 */
@RestController
public class RegisterController {
    @Autowired
    private WardenUserConfig wardenUserConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CaptchaService captchaService;

    /**
     * 注册系统用户
     * @param registerRequest 注册信息
     * @return
     */
    @PostMapping("/register")
    public ResultModel register(@RequestBody @Validated RegisterUserRequest registerRequest){
        // 图形验证码校验
        if(wardenUserConfig.getEnableCaptcha()){
            if(StringUtils.isBlank(registerRequest.getCaptchaCode())){
                throw new WardenParamterErrorException("请输入验证码");
            }
            // 验证码类型检查
            switch (wardenUserConfig.getCaptchaType()){
                case KAPTCHA_IMAGE:
                    if(!captchaService.verify(registerRequest.getCaptchaCode())){
                        throw new WardenParamterErrorException("验证码错误");
                    }
                    break;
            }
        }

        CreateUserDTO createUserDTO = UserMapstruct.Instance.registerUserRequestToCreateUserDTO(registerRequest);
        createUserDTO.setPwd(bCryptPasswordEncoder.encode(registerRequest.getPwd()));
        createUserDTO.getPermissionValues().add("create:order");
        UserDTO newUser = userService.create(createUserDTO);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("user",UserMapstruct.Instance.userDtoToUserVo(newUser));
        return resultModel;
    }
}
