package com.microwarp.warden.cloud.service.system.controller;
import com.microwarp.warden.cloud.common.core.constant.AttrConstant;
import com.microwarp.warden.cloud.common.core.enums.ActionTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.ModuleTypeEnum;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.common.security.util.WebUtil;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.service.system.security.SecurityUser;
import com.microwarp.warden.cloud.service.system.service.LogService;
import com.microwarp.warden.cloud.service.system.util.SysSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * controller - base
 * @author zhouwenqi
 */
public class BaseController {
    @Autowired
    private LogService logService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    public SecurityUser getSecruityUser(){
        HttpServletRequest request = WebUtil.getRequest();
        return (SecurityUser)request.getAttribute(AttrConstant.SECURITY_USER_KEY);
    }
    public TokenUser getTokenUser(){
        HttpServletRequest request = WebUtil.getRequest();
        return (TokenUser) request.getAttribute(AttrConstant.TOKEN_USER_KEY);
    }
    public void writeLog(String logContent, ActionTypeEnum actionType, ModuleTypeEnum moduleType, Long... mateId){
        String ip = WebUtil.getIpAddr();
        SysUserDetailsDTO sysUserDetailsDTO = SysSecurityUtil.getCurrentSysUser();
        logService.syncBackstageWrite(sysUserDetailsDTO,logContent,ip,actionType,moduleType,mateId);
    }
}
