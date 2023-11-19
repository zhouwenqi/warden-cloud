package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.enums.ActionTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.ModuleTypeEnum;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserPasswordDTO;
import com.microwarp.warden.cloud.service.system.config.WardenAdminConfig;
import com.microwarp.warden.cloud.service.system.domain.convert.SysUserMapstruct;
import com.microwarp.warden.cloud.service.system.domain.vo.ProfilePasswordRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysUserDetailsVO;
import com.microwarp.warden.cloud.service.system.domain.vo.SysUserProfileRequest;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import com.microwarp.warden.cloud.service.system.util.SysSecurityUtil;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * controller - 个人信息
 * @author zhouwenqi
 */
@RestController
public class ProfileController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private WardenAdminConfig wardenAdminConfig;
    /**
     * 获取当前用户信息
     * @return
     */
    @GetMapping("/profile")
    public ResultModel getProfile(){
        ResultModel resultModel = ResultModel.success();
        SysUserDetailsVO sysUserResponseVO = SysUserMapstruct.Instance.sysUserDetailsDtoToSysUserDetailsVo(getSecruityUser().getSysUser());
        sysUserResponseVO.setAuthorities(SysSecurityUtil.getAuthorityArray());
        resultModel.addData("user",sysUserResponseVO);
        return resultModel;
    }

    /**
     * 更新当前用户信息
     * @return
     */
    @PatchMapping("/profile")
    public ResultModel putProfile(@RequestBody @Validated SysUserProfileRequest profileRequest){
        ResultModel resultModel = ResultModel.success();
        SysUserDTO sysUserDTO = SysUserMapstruct.Instance.sysUserProfileRequestToSysUserDTO(profileRequest);
        sysUserDTO.setId(getSecruityUser().getSysUser().getId());
        sysUserService.update(sysUserDTO);
        // 写入日志
        writeLog("修改个人资料:"+sysUserDTO.getUid()+"["+sysUserDTO.getId()+"]", ActionTypeEnum.MODIFY, ModuleTypeEnum.SYS_USER,sysUserDTO.getId());
        return resultModel;
    }

    /**
     * 更新当前用户密码
     * @return
     */
    @PutMapping("/profile/password")
    public ResultModel putProfilePassword(@RequestBody @Validated ProfilePasswordRequest passwordRequest){
        String oldPassword = passwordRequest.getOldPassword();
        SysUserDetailsDTO sysUserDetailsDTO = getSecruityUser().getSysUser();
        if(!bCryptPasswordEncoder.matches(oldPassword,sysUserDetailsDTO.getPwd())){
            throw new WardenRequireParamterException("原始密码错误");
        }
        SysUserPasswordDTO sysUserPasswordDTO = new SysUserPasswordDTO();
        sysUserPasswordDTO.setUserId(sysUserDetailsDTO.getId());
        sysUserPasswordDTO.setNewPassword(bCryptPasswordEncoder.encode(passwordRequest.getNewPassword()));
        sysUserService.updatePassowrd(sysUserPasswordDTO);

        // 写入日志
        writeLog("修改登录密码:"+sysUserDetailsDTO.getUid()+"["+sysUserPasswordDTO.getUserId()+"]", ActionTypeEnum.MODIFY, ModuleTypeEnum.SYS_USER,sysUserPasswordDTO.getUserId());
        return ResultModel.success();
    }
}
