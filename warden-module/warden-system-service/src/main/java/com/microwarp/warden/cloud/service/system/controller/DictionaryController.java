package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.enums.ActionTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.ModuleTypeEnum;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.core.pageing.SearchPageable;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDTO;
import com.microwarp.warden.cloud.service.system.domain.convert.SysDictionaryMapstruct;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDictionaryCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysDictionaryUpdateRequest;
import com.microwarp.warden.cloud.service.system.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * controller - 字典
 * @author zhouwenqi
 */
@RestController
public class DictionaryController extends BaseController {
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 获取字典信息
     * @param id 字典id
     * @return
     */
    @GetMapping("/dictionary/{id}")
    @PreAuthorize("hasAuthority('dictionary:view')")
    public ResultModel getDictionary(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenRequireParamterException("字典id不能为空");
        }
        SysDictionaryDTO sysDictionaryDTO = sysDictionaryService.findById(id);
        if(null == sysDictionaryDTO){
            throw new WardenParamterErrorException("字典不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("dictionary", SysDictionaryMapstruct.Instance.sysDictionaryDtoToSysDictionaryVO(sysDictionaryDTO));
        return resultModel;

    }

    /**
     * 创建字典
     * @param sysDictionaryRequest 字典信息
     * @return
     */
    @PostMapping("/dictionary")
    @PreAuthorize("hasAuthority('dictionary:create')")
    public ResultModel postDictionary(@Validated @RequestBody SysDictionaryCreateRequest sysDictionaryRequest){
        SysDictionaryDTO sysDictionaryDTO = SysDictionaryMapstruct.Instance.sysDictionaryCreateRequestToSysDictionaryDTO(sysDictionaryRequest);
        SysDictionaryDTO newDictionaryDTO = sysDictionaryService.create(sysDictionaryDTO);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("dictionary", SysDictionaryMapstruct.Instance.sysDictionaryDtoToSysDictionaryVO(newDictionaryDTO));

        // 写入日志
        writeLog("创建字典信息:"+newDictionaryDTO.getName()+"["+newDictionaryDTO.getCode()+"]", ActionTypeEnum.CREATE, ModuleTypeEnum.SYS_DICTIONARY,newDictionaryDTO.getId());
        return resultModel;
    }

    /**
     * 更新字典
     * @param sysDictionaryRequest 字典信息
     * @return
     */
    @PatchMapping("/dictionary")
    @PreAuthorize("hasAuthority('dictionary:modify')")
    public ResultModel putDictionary(@Validated @RequestBody SysDictionaryUpdateRequest sysDictionaryRequest){
        SysDictionaryDTO sysDictionaryDTO = SysDictionaryMapstruct.Instance.sysDictionaryUpdateRequestToSysDictionaryDTO(sysDictionaryRequest);
        sysDictionaryService.update(sysDictionaryDTO);
        // 写入日志
        writeLog("修改字典信息:"+sysDictionaryDTO.getName()+"["+sysDictionaryDTO.getCode()+"]", ActionTypeEnum.MODIFY, ModuleTypeEnum.SYS_DICTIONARY,sysDictionaryDTO.getId());
        return ResultModel.success();
    }

    /**
     * 删除字典
     * @param id 字典id
     * @return
     */
    @DeleteMapping("/dictionary/{id}")
    @PreAuthorize("hasAuthority('dictionary:delete')")
    public ResultModel deleteDictionary(@PathVariable Long... id){
        if(null != id && id.length > 0){
            sysDictionaryService.delete(id);
            // 写入日志
            writeLog("删除字典信息:"+"["+ Arrays.toString(id)+"]", ActionTypeEnum.DELETE, ModuleTypeEnum.SYS_DICTIONARY,id);
        }
        return ResultModel.success();
    }

    /**
     * 分页查询字典信息
     * @param searchPageable 查询条件
     * @return
     */
    @PostMapping("dictionarys/search")
    @PreAuthorize("hasAuthority('dictionary:view')")
    public ResultModel search(@RequestBody SearchPageable<BasicSearchDTO> searchPageable){
        ResultPage<SysDictionaryDTO> resultPage = sysDictionaryService.findPage(searchPageable);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("list",SysDictionaryMapstruct.Instance.sysDictionaryDtosToSysDictionaryVOs(resultPage.getList()));
        resultModel.addData("pageInfo",resultPage.getPageInfo());
        return resultModel;
    }

}
