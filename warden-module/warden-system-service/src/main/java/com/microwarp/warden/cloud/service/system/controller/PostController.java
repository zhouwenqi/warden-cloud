package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.enums.ActionTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.ModuleTypeEnum;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.core.pageing.SearchPageable;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPostDTO;
import com.microwarp.warden.cloud.service.system.domain.SortRequest;
import com.microwarp.warden.cloud.service.system.domain.convert.SysPostMapstruct;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPostCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPostUpdateRequest;
import com.microwarp.warden.cloud.service.system.service.SysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * controller - 岗位
 * @author zhouwenqi
 */
@RestController
public class PostController extends BaseController {
    @Autowired
    private SysPostService sysPostService;

    /**
     * 获取岗位信息
     * @param id 岗位id
     * @return
     */
    @GetMapping("/post/{id}")
    @PreAuthorize("hasAuthority('post:view')")
    public ResultModel getInfo(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenRequireParamterException("岗位id不能为空");
        }
        SysPostDTO sysPostDTO = sysPostService.findById(id);
        if(null == sysPostDTO){
            throw new WardenParamterErrorException("岗位信息不存不在");
        }
        
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("post", SysPostMapstruct.Instance.sysPostDtoToSysPostVO(sysPostDTO));
        return resultModel;
    }

    /**
     * 创建岗位
     * @param sysPostRequest 岗位信息
     * @return
     */
    @PostMapping("/post")
    @PreAuthorize("hasAuthority('post:create')")
    public ResultModel postInfo(@Validated @RequestBody SysPostCreateRequest sysPostRequest){
        SysPostDTO sysPostDTO = SysPostMapstruct.Instance.sysPostCreateRequestToSysPostDTO(sysPostRequest);
        ResultModel resultModel = ResultModel.success();
        SysPostDTO newPostDTO = sysPostService.create(sysPostDTO);
        resultModel.addData("post",newPostDTO);

        // 写入日志
        writeLog("创建岗位信息:"+newPostDTO.getName()+"["+newPostDTO.getCode()+"]", ActionTypeEnum.CREATE, ModuleTypeEnum.SYS_POST,newPostDTO.getId());
        return resultModel;
    }

    /**
     * 更新岗位
     * @param sysPostRequest 岗位信息
     * @return
     */
    @PatchMapping("/post")
    @PreAuthorize("hasAuthority('post:modify')")
    public ResultModel putInfo(@Validated @RequestBody SysPostUpdateRequest sysPostRequest){
        SysPostDTO sysPostDTO = SysPostMapstruct.Instance.sysPostUpdateRequestToSysPostDTO(sysPostRequest);
        sysPostService.update(sysPostDTO);

        // 写入日志
        writeLog("修改岗位信息:"+sysPostDTO.getName()+"["+sysPostDTO.getCode()+"]", ActionTypeEnum.MODIFY, ModuleTypeEnum.SYS_POST,sysPostDTO.getId());
        return ResultModel.success();
    }

    /**
     * 岗位拖拽排序
     * @param sortRequest 排序参数
     * @return
     */
    @PutMapping("/posts/sort")
    @PreAuthorize("hasAuthority('post:modify')")
    public ResultModel sort(@Validated @RequestBody SortRequest sortRequest){
        sysPostService.dragAndSort(sortRequest);
        // 写入日志
        writeLog("修改岗位排序:"+sortRequest.getDragId()+"["+sortRequest.getDragId()+"]", ActionTypeEnum.MODIFY, ModuleTypeEnum.SYS_POST,sortRequest.getIds());
        return ResultModel.success();
    }

    /**
     * 删除岗位信息
     * @param id 岗位id
     * @return
     */
    @DeleteMapping("/post/{id}")
    @PreAuthorize("hasAuthority('post:delete')")
    public ResultModel deleteInfo(@PathVariable Long[] id){
        if(null != id && id.length>0){
            sysPostService.delete(id);
            writeLog("删除岗位信息:["+ Arrays.toString(id)+"]", ActionTypeEnum.DELETE, ModuleTypeEnum.SYS_POST,id);

        }
        return ResultModel.success();
    }

    /**
     * 查询所有岗位信息
     * @return
     */
    @GetMapping("/posts")
    @PreAuthorize("hasAuthority('post:view')")
    public ResultModel all(){
        ResultModel resultModel = ResultModel.success();
        List<SysPostDTO> list = sysPostService.findAll();
        resultModel.addData("list",SysPostMapstruct.Instance.sysPostDtosToSysPostVOs(list));
        return resultModel;
    }

    /**
     * 分页查询岗位信息
     * @param searchPageable 查询条件
     * @return
     */
    @PostMapping("/posts/search")
    @PreAuthorize("hasAuthority('post:view')")
    public ResultModel search(@RequestBody SearchPageable<BasicSearchDTO> searchPageable){
        ResultModel resultModel = ResultModel.success();
        ResultPage<SysPostDTO> resultPage = sysPostService.findPage(searchPageable);
        resultModel.addData("list",SysPostMapstruct.Instance.sysPostDtosToSysPostVOs(resultPage.getList()));
        resultModel.addData("pageInfo",resultPage.getPageInfo());
        return resultModel;
    }

}
