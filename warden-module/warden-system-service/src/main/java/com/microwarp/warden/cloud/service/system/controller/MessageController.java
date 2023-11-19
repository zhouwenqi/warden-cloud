package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.enums.ActionTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.ModuleTypeEnum;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.core.pageing.SearchPageable;
import com.microwarp.warden.cloud.common.core.util.ResultUtil;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageSearchDTO;
import com.microwarp.warden.cloud.service.system.domain.convert.SysMessageMapstruct;
import com.microwarp.warden.cloud.service.system.service.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * controller - 系统消息
 * @author zhouwenqi
 */
@RestController
public class MessageController extends BaseController {
    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 查询系统消息
     * @param id 消息ID
     * @return
     */
    @GetMapping("/message/{id}")
    public ResultModel getMessage(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenRequireParamterException("消息ID不能为空");
        }
        TokenUser tokenUser = getTokenUser();
        SysMessageDTO sysMessageDTO = sysMessageService.findById(id,tokenUser);
        if(null == sysMessageDTO){
            throw new WardenParamterErrorException("系统消息不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("message", SysMessageMapstruct.Instance.sysMessageDtoToSysMessageVO(sysMessageDTO));
        return resultModel;
    }

    /**
     * 阅读消息
     * @param id 消息ID
     * @return
     */
    @PatchMapping("/message/reading/{id}")
    public ResultModel reading(@PathVariable Long[] id){
        if(null != id && id.length>0) {
            TokenUser tokenUser = getTokenUser();
            sysMessageService.read(id,tokenUser);
        }
        return ResultModel.success();
    }

    /**
     * 阅读所有消息
     * @return
     */
    @PatchMapping("/messages/reading")
    public ResultModel readingAll(){
        TokenUser tokenUser = getTokenUser();
        sysMessageService.readAll(tokenUser);
        return ResultModel.success();
    }

    /**
     * 获取消息未读数
     * @return
     */
    @GetMapping("/messages/unread")
    public ResultModel unread(){
        ResultModel resultModel = ResultModel.success();
        TokenUser tokenUser = getTokenUser();
        resultModel.addData("total",sysMessageService.totalUnread(tokenUser));
        return resultModel;
    }

    /**
     * 删除系统消息
     * @param id 消息ID
     * @return
     */
    @DeleteMapping("/message/{id}")
    public ResultModel deleteMessage(@PathVariable Long[] id){
        TokenUser tokenUser = getTokenUser();
        if(null != id && id.length>0){
            sysMessageService.delete(id,tokenUser);
            writeLog("删除系统消息:["+ Arrays.toString(id)+"]", ActionTypeEnum.DELETE, ModuleTypeEnum.SYS_MESSAGE,id);

        }
        return ResultModel.success();
    }

    /**
     * 分页查询系统消息
     * @param searchPageable 分页信息
     * @return
     */
    @PostMapping("/messages/search")
    public ResultModel search(@RequestBody SearchPageable<SysMessageSearchDTO> searchPageable){
        TokenUser tokenUser = getTokenUser();
        if(null == searchPageable.getFilters()){
            searchPageable.setFilters(new SysMessageSearchDTO());
        }
        searchPageable.getFilters().setToId(Long.parseLong(tokenUser.getUserId()));
        searchPageable.getFilters().setToPlatform(ResultUtil.convertToPlatform(tokenUser.getUserType()));
        ResultPage<SysMessageDTO> resultPage = sysMessageService.findPage(searchPageable);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("list", SysMessageMapstruct.Instance.sysMessageDTOsToSysMessageVOs(resultPage.getList()));
        resultModel.addData("pageInfo",resultPage.getPageInfo());
        return resultModel;
    }

}
