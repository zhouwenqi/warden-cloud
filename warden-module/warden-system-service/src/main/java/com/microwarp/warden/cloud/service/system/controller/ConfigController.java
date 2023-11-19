package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.security.util.WebUtil;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysConfigDTO;
import com.microwarp.warden.cloud.facade.user.service.IUserService;
import com.microwarp.warden.cloud.service.system.config.WardenAdminConfig;
import com.microwarp.warden.cloud.service.system.domain.convert.SysConfigMapstruct;
import com.microwarp.warden.cloud.service.system.domain.vo.GlobalConfigVO;
import com.microwarp.warden.cloud.service.system.domain.vo.SysConfigVO;
import com.microwarp.warden.cloud.service.system.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 配置信息
 * @author zhouwenqi
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private WardenAdminConfig wardenAdminConfig;

    /**
     * 获取系统配置
     * @return
     */
    @GetMapping("system")
    @PreAuthorize("hasAuthority('config:admin')")
    public ResultModel system(){
        ResultModel resultModel = ResultModel.success();
        SysConfigDTO sysConfigDTO = sysConfigService.findCurrent();
        resultModel.addData("config", SysConfigMapstruct.Instance.sysConfigDTOtoSysConfigVO(sysConfigDTO));
        return resultModel;
    }
    /**
     * 更新系统配置
     * @return
     */
    @PatchMapping("system")
    @PreAuthorize("hasAuthority('config:admin')")
    public ResultModel update(@RequestBody SysConfigVO sysConfigVO){
        SysConfigDTO sysConfigDTO = SysConfigMapstruct.Instance.sysConfigVOtoSysConfigDTO(sysConfigVO);
        sysConfigService.update(sysConfigDTO);
        return ResultModel.success();
    }

    /**
     * 获取全局开放配置
     * @return
     */
    @GetMapping("global")
    public ResultModel global(){
        SysConfigDTO sysConfigDTO = sysConfigService.findCurrent();
        ResultModel resultModel = ResultModel.success();
        GlobalConfigVO globalConfigVO = new GlobalConfigVO();
        globalConfigVO.setEnableRegister(sysConfigDTO.getEnabledRegister());
        globalConfigVO.setTokenEffectiveHour(wardenAdminConfig.getEffectiveHour());
        globalConfigVO.setDefaultPageSize(HttpConstant.DEFAULT_PAGE_SIZE);
        globalConfigVO.setCaptchaType(wardenAdminConfig.getCaptchaType());
        globalConfigVO.setEnableCaptcha(wardenAdminConfig.getEnableCaptcha());
        String guestId = WebUtil.getGuestId();
        globalConfigVO.setGuestId(guestId);
        resultModel.addData("config",globalConfigVO);
        return resultModel;
    }
}
