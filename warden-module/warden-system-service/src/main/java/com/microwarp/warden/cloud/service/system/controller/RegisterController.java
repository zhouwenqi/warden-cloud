package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.enums.ActionTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.AppTerminalEnum;
import com.microwarp.warden.cloud.common.core.enums.ModuleTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.PlatformTypeEnum;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.security.service.CaptchaService;
import com.microwarp.warden.cloud.common.security.util.WebUtil;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysConfigDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.service.system.config.WardenAdminConfig;
import com.microwarp.warden.cloud.service.system.domain.convert.SysUserMapstruct;
import com.microwarp.warden.cloud.service.system.domain.vo.SysUserRegisterRequest;
import com.microwarp.warden.cloud.service.system.service.LogService;
import com.microwarp.warden.cloud.service.system.service.SysConfigService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
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
    private WardenAdminConfig wardenAdminConfig;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private LogService logService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 注册系统用户
     * @param registerRequest 注册信息
     * @return
     */
    @PostMapping("/register")
    public ResultModel register(@RequestBody @Validated SysUserRegisterRequest registerRequest){
        SysConfigDTO sysConfigDTO = sysConfigService.findCurrent();
        if(!sysConfigDTO.getEnabledRegister()){
            throw new WardenRequireParamterException("注册功能已关闭");
        }

        // 图形验证码校验
        if(wardenAdminConfig.getEnableCaptcha()){
            if(StringUtils.isBlank(registerRequest.getCaptchaCode())){
                throw new WardenParamterErrorException("请输入验证码");
            }
            // 验证码类型检查
            switch (wardenAdminConfig.getCaptchaType()){
                case KAPTCHA_IMAGE:
                    if(!captchaService.verify(registerRequest.getCaptchaCode())){
                        throw new WardenParamterErrorException("验证码错误");
                    }
                    break;
            }
        }

        SysUserDTO sysUserDTO = SysUserMapstruct.Instance.sysUserRegisterRequestToSysUserDTO(registerRequest);
        sysUserDTO.setPwd(bCryptPasswordEncoder.encode(registerRequest.getPwd()));
        Long userId = sysUserService.insert(sysUserDTO);
        SysUserDetailsDTO sysUserDetailsDTO = sysUserService.findDetailsById(userId);

        ResultModel resultModel = ResultModel.success();
        resultModel.addData("user",SysUserMapstruct.Instance.sysUserDetailsDtoToSysUserDetailsVo(sysUserDetailsDTO));

        // 写入日志
        String ip = WebUtil.getIpAddr();
        logService.syncWrite(sysUserDetailsDTO,"注册个人信息:"+sysUserDetailsDTO.getUid()+"["+sysUserDetailsDTO.getId()+"]",ip, ActionTypeEnum.CREATE, AppTerminalEnum.PC_WEB, PlatformTypeEnum.BACKSTAGE, ModuleTypeEnum.SYS_USER,sysUserDetailsDTO.getId());
        return resultModel;
    }
}
