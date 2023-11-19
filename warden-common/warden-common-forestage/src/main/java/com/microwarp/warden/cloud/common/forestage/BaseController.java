package com.microwarp.warden.cloud.common.forestage;
import com.microwarp.warden.cloud.common.core.constant.AttrConstant;
import com.microwarp.warden.cloud.common.forestage.security.SecurityUser;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.common.security.util.WebUtil;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * controller - base
 * @author zhouwenqi
 */
public class BaseController {

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
}
