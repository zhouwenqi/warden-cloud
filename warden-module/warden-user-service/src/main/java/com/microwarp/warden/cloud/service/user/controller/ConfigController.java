package com.microwarp.warden.cloud.service.user.controller;

import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.security.util.WebUtil;
import com.microwarp.warden.cloud.service.user.config.WardenUserConfig;
import com.microwarp.warden.cloud.service.user.domain.vo.GlobalConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 配置信息
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private WardenUserConfig wardenUserConfig;


    /**
     * 获取全局开放配置
     * @return
     */
    @GetMapping("global")
    public ResultModel global(){
        ResultModel resultModel = ResultModel.success();
        GlobalConfigVO globalConfigVO = new GlobalConfigVO();
        globalConfigVO.setCaptchaType(wardenUserConfig.getCaptchaType());
        globalConfigVO.setEnableCaptcha(wardenUserConfig.getEnableCaptcha());
        String guestId = WebUtil.getGuestId();
        globalConfigVO.setGuestId(guestId);
        resultModel.addData("config",globalConfigVO);
        return resultModel;
    }
}
