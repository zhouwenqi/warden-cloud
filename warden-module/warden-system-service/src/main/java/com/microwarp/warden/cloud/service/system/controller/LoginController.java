package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.enums.ActionStatusEnum;
import com.microwarp.warden.cloud.common.core.enums.AgainVerifyTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.AppTerminalEnum;
import com.microwarp.warden.cloud.common.core.enums.PlatformTypeEnum;
import com.microwarp.warden.cloud.common.core.exception.WardenAccountFailedException;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.security.service.CaptchaService;
import com.microwarp.warden.cloud.common.security.service.RedisTokenService;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.common.security.util.WebUtil;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysConfigDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.service.system.config.WardenAdminConfig;
import com.microwarp.warden.cloud.service.system.constant.SecurityConstant;
import com.microwarp.warden.cloud.service.system.domain.vo.SysUserLoginRequest;
import com.microwarp.warden.cloud.service.system.service.LoginService;
import com.microwarp.warden.cloud.service.system.service.SysConfigService;
import com.microwarp.warden.cloud.service.system.service.SysUserLockService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoginController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private WardenAdminConfig wardenAdminConfig;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysUserLockService sysUserLockService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private RedisTokenService tokenService;

    /**
     * 登录
     * @param loginRequest 登录参数
     * @return
     */
    @PostMapping("/login")
    public ResultModel login(@RequestBody @Validated SysUserLoginRequest loginRequest){
        ResultModel resultModel = ResultModel.success();
        String ip = WebUtil.getIpAddr();
        // IP锁定次数检查
        if(sysUserLockService.totalByIp(ip) > SecurityConstant.IP_LOCK_LIMIT){
            throw new WardenParamterErrorException("当前IP暂时禁止登录");
        }

        // 图形验证码校验
        if(wardenAdminConfig.getEnableCaptcha()){
            if(StringUtils.isBlank(loginRequest.getCaptchaCode())){
                throw new WardenParamterErrorException("请输入验证码");
            }
            // 验证码类型检查
            switch (wardenAdminConfig.getCaptchaType()){
                case KAPTCHA_IMAGE:
                    if(!captchaService.verify(loginRequest.getCaptchaCode())){
                        throw new WardenParamterErrorException("验证码错误");
                    }
                    break;
            }
        }

        // 查询用户信息
        SysUserDetailsDTO sysUserDetailsDTO = sysUserService.findDetailsByUid(loginRequest.getUid());
        if(null == sysUserDetailsDTO){
            // 实际只是帐号不存在
            throw new WardenParamterErrorException("帐号或密码错误");
        }

        // 判断用户是否锁住
        if(sysUserLockService.isLocked(sysUserDetailsDTO.getId(),ip)){
            // 写入日志
            loginService.asyncWriteLog(sysUserDetailsDTO,"登录失败:用户已被锁住", ActionStatusEnum.FAILED, ip, AppTerminalEnum.PC_WEB, PlatformTypeEnum.BACKSTAGE);
            throw new WardenParamterErrorException("用户已被锁住");
        }

        // 校验登录密码
        if(!bCryptPasswordEncoder.matches(loginRequest.getPwd(),sysUserDetailsDTO.getPwd())){
            long failedCount = loginService.getLoginFailedCount(sysUserDetailsDTO.getId(),ip);
            // 失败 5 次锁住
            if(failedCount >= SecurityConstant.LOGIN_COUNT_LIMIT){
                loginService.lock(sysUserDetailsDTO.getId(), ip);
                // 写入日志
                loginService.asyncWriteLog(sysUserDetailsDTO,"登录失败:失败次败超限被锁住", ActionStatusEnum.FAILED, ip, AppTerminalEnum.PC_WEB, PlatformTypeEnum.BACKSTAGE);
                throw new WardenParamterErrorException("用户已被锁住");
            }else{
                loginService.failed(sysUserDetailsDTO.getId(),ip);
            }
            // 写入日志
            loginService.asyncWriteLog(sysUserDetailsDTO,"登录失败:密码错误", ActionStatusEnum.FAILED, ip, AppTerminalEnum.PC_WEB, PlatformTypeEnum.BACKSTAGE);
            throw new WardenParamterErrorException("帐号或密码错误");
        }

        // 检查系统用户状态
        if(sysUserDetailsDTO.getDisabled()){
            // 写入日志
            loginService.asyncWriteLog(sysUserDetailsDTO,"登录失败:此用户已被禁用", ActionStatusEnum.FAILED, ip, AppTerminalEnum.PC_WEB, PlatformTypeEnum.BACKSTAGE);
            throw new WardenAccountFailedException("此用户已被禁用");
        }

        // 生成token
        Long effectiveHour = wardenAdminConfig.getEffectiveHour();

        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserType(UserType.SYSTEM);
        tokenUser.setUserId(sysUserDetailsDTO.getId().toString());
        tokenUser.setUsername(sysUserDetailsDTO.getUid());

        String token = tokenService.create(tokenUser,effectiveHour);
        resultModel.addData("token",token);

        // token写入缓存
        SysConfigDTO sysConfigDTO = sysConfigService.findCurrent();
        if(!sysConfigDTO.getAllowManyToken()){
            tokenService.clearLoggedToken(tokenUser);
        }
        tokenService.addLoggedToken(tokenUser,token);

        // 清除登录失败计数
        loginService.success(sysUserDetailsDTO.getId(),ip);
        // 是否开启二次验证
        AgainVerifyTypeEnum againVerifyType = sysConfigDTO.getAgainVerify();
        if(null != againVerifyType && !againVerifyType.equals(AgainVerifyTypeEnum.NONE)) {
            loginService.checkBlip(sysUserDetailsDTO.getId(), ip, againVerifyType.equals(AgainVerifyTypeEnum.ALWAYS));
        }
        // 写入日志
        loginService.asyncWriteLog(sysUserDetailsDTO,"登录成功", ActionStatusEnum.SUCCESS,ip, AppTerminalEnum.PC_WEB, PlatformTypeEnum.BACKSTAGE);
        return resultModel;
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/logout")
    public ResultModel logout(HttpServletRequest request){
        String ip = WebUtil.getIpAddr();
        TokenUser tokenUser = getTokenUser();
        String token = request.getHeader(HttpConstant.HEADER_TOKEN_KEY);
        if(null != tokenUser && StringUtils.isNotBlank(token)) {
            // 从当前已登录列表中移除token
            tokenService.removeLoggedToken(tokenUser,token);
            // 清除缓存中的token,让token失效
            tokenService.remove(token);
            // 清除登录失败计数
            loginService.success(Long.parseLong(tokenUser.getUserId()), ip);
        }
        return ResultModel.success();

    }

}
