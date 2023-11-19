package com.microwarp.warden.cloud.service.user.controller;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.exception.WardenAccountFailedException;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.forestage.BaseController;
import com.microwarp.warden.cloud.common.security.service.CaptchaService;
import com.microwarp.warden.cloud.common.security.service.RedisTokenService;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.service.user.config.WardenUserConfig;
import com.microwarp.warden.cloud.service.user.domain.vo.UserLoginRequest;
import com.microwarp.warden.cloud.service.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * controller - 登录
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/")
public class LoginController extends BaseController {
    @Autowired
    private WardenUserConfig wardenUserConfig;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private RedisTokenService tokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResultModel login(@RequestBody @Validated UserLoginRequest loginRequest){
        ResultModel resultModel = ResultModel.success();
        // 图形验证码校验
        if(wardenUserConfig.getEnableCaptcha()){
            if(StringUtils.isBlank(loginRequest.getCaptchaCode())){
                throw new WardenParamterErrorException("请输入验证码");
            }
            // 验证码类型检查
            switch (wardenUserConfig.getCaptchaType()){
                case KAPTCHA_IMAGE:
                    if(!captchaService.verify(loginRequest.getCaptchaCode())){
                        throw new WardenParamterErrorException("验证码错误");
                    }
                    break;
            }
        }

        // 查询用户信息
        UserDTO userDTO = userService.findByUid(loginRequest.getUid());
        if(null == userDTO){
            // 实际只是帐号不存在
            throw new WardenParamterErrorException("帐号或密码错误");
        }

        // 校验登录密码
        if(!bCryptPasswordEncoder.matches(loginRequest.getPwd(),userDTO.getPwd())){
            throw new WardenParamterErrorException("帐号或密码错误");
        }

        // 检查系统用户状态
        if(userDTO.getDisabled()){
            throw new WardenAccountFailedException("此用户已被禁用");
        }

        // 生成token
        Long effectiveHour = wardenUserConfig.getEffectiveHour();

        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserType(UserType.NORMAL);
        tokenUser.setUserId(userDTO.getId().toString());
        tokenUser.setUsername(userDTO.getUid());

        String token = tokenService.create(tokenUser,effectiveHour);
        resultModel.addData("token",token);
        tokenService.addLoggedToken(tokenUser,token);
        return resultModel;
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public ResultModel logout(HttpServletRequest request){
        TokenUser tokenUser = getTokenUser();
        String token = request.getHeader(HttpConstant.HEADER_TOKEN_KEY);
        if(null != tokenUser && StringUtils.isNotBlank(token)) {
            // 从当前已登录列表中移除token
            tokenService.removeLoggedToken(tokenUser,token);
            // 清除缓存中的token,让token失效
            tokenService.remove(token);
        }
        return ResultModel.success();

    }
}
