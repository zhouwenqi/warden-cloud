package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.enums.ActionTypeEnum;
import com.microwarp.warden.cloud.common.core.enums.ModuleTypeEnum;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireAuthorizedException;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.core.pageing.SearchPageable;
import com.microwarp.warden.cloud.facade.system.domain.dto.*;
import com.microwarp.warden.cloud.service.system.constant.SecurityConstant;
import com.microwarp.warden.cloud.service.system.domain.convert.SysUserMapstruct;
import com.microwarp.warden.cloud.service.system.domain.vo.SysUserCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysUserPasswordRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysUserUpdateRequest;
import com.microwarp.warden.cloud.service.system.service.ExcelExportService;
import com.microwarp.warden.cloud.service.system.service.SysRoleService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import com.microwarp.warden.cloud.service.system.util.SysSecurityUtil;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.security.util.SecurityConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * controller - 系统用户
 * @author zhouwenqi
 */
@RestController
public class UserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private ExcelExportService excelExportService;

    /**
     * 查看用户信息
     * @param id 用户id
     * @return
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('system:user:view')")
    public ResultModel info(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenParamterErrorException("参数错误");
        }
        SysUserDetailsDTO sysUserDTO = sysUserService.findDetailsById(id);
        if(null == sysUserDTO){
            throw new WardenRequireParamterException("用户不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("user", SysUserMapstruct.Instance.sysUserDetailsDtoToSysUserDetailsVo(sysUserDTO));
        return resultModel;
    }

    /**
     * 创建用户信息
     * @param createRequest 用户信息
     * @return
     */
    @PostMapping("/user")
    @PreAuthorize("hasAuthority('system:user:create')")
    public ResultModel postInfo(@RequestBody @Validated SysUserCreateRequest createRequest){
        SysUserRequestDTO requestDTO = SysUserMapstruct.Instance.sysUserCreateRequestToSysUserRequestDTO(createRequest);
        requestDTO.setPwd(bCryptPasswordEncoder.encode(createRequest.getPwd()));
        if(null != createRequest.getRoleIds()){
            List<SysRoleDTO> roleDTOS = sysRoleService.findByIds(createRequest.getRoleIds());
            Set<String> roleValues = roleDTOS.stream().map(SysRoleDTO::getValue).collect(Collectors.toSet());
            if(SysSecurityUtil.hasAnyAuthority(roleValues, SecurityConstant.ROLE_ROOT_VALUE) && !SysSecurityUtil.inRoot()){
                throw new WardenRequireAuthorizedException("创建超级管理员权限不够");
            }
        }
        Long userId = sysUserService.create(requestDTO);
        SysUserDTO userDTO = sysUserService.findById(userId);
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("user",SysUserMapstruct.Instance.sysUserDtoToSysUserVo(userDTO));

        // 写入日志
        writeLog("创建系统用户:"+userDTO.getUid()+"["+userDTO.getId()+"]", ActionTypeEnum.CREATE, ModuleTypeEnum.SYS_USER,userDTO.getId());
        return resultModel;
    }

    /**
     * 更新用户信息
     * @param updateRequest 更新用户信息
     * @return
     */
    @PatchMapping("/user")
    @PreAuthorize("hasAuthority('system:user:modify')")
    public ResultModel putInfo(@RequestBody @Validated SysUserUpdateRequest updateRequest){
        SysUserDTO sysUserDTO = SysUserMapstruct.Instance.sysUserUpdateRequestToSysUserDTO(updateRequest);
        SysUserDetailsDTO sysUserDetailsDTO = sysUserService.findDetailsById(sysUserDTO.getId());

        if(null == sysUserDetailsDTO){
            throw new WardenParamterErrorException("用户不存在");
        }

        Set<String> roleValues = sysUserDetailsDTO.getRoles().stream().map(SysRoleDTO::getValue).collect(Collectors.toSet());
        if(SysSecurityUtil.hasAnyAuthority(roleValues, SecurityConstant.ROLE_ROOT_VALUE) && !SysSecurityUtil.inRoot()){
            throw new WardenRequireAuthorizedException("修改超级管理员权限不够");
        }

        ResultModel resultModel = ResultModel.success();
        sysUserService.update(sysUserDTO);
        // 角色更新
        if(null != updateRequest.getRoleIds()){
            List<SysRoleDTO> roleDTOS = sysRoleService.findByIds(updateRequest.getRoleIds());
            Set<String> values = roleDTOS.stream().map(SysRoleDTO::getValue).collect(Collectors.toSet());
            if(SysSecurityUtil.hasAnyAuthority(values, SecurityConstant.ROLE_ROOT_VALUE) && !SysSecurityUtil.inRoot()){
                throw new WardenRequireAuthorizedException("提权超级管理员权限不够");
            }
            sysRoleService.saveUserRoles(sysUserDetailsDTO.getId(),updateRequest.getRoleIds());
        }

        // 写入日志
        writeLog("修改系统用户:"+sysUserDTO.getUid()+"["+sysUserDTO.getId()+"]", ActionTypeEnum.MODIFY, ModuleTypeEnum.SYS_USER,sysUserDTO.getId());
        return resultModel;
    }

    /**
     * 更新用户密码
     * @param passwordRequest 密码参数
     * @return
     */
    @PutMapping("/user/password")
    @PreAuthorize("hasAuthority('system:user:modify')")
    public ResultModel putPassword(@RequestBody @Validated SysUserPasswordRequest passwordRequest){
        SysUserDetailsDTO sysUserDetailsDTO = sysUserService.findDetailsById(passwordRequest.getUserId());
        if(null == sysUserDetailsDTO){
            throw new WardenParamterErrorException("用户不存在");
        }
        Set<String> roleValues = sysUserDetailsDTO.getRoles().stream().map(SysRoleDTO::getValue).collect(Collectors.toSet());
        if(SysSecurityUtil.hasAnyAuthority(roleValues, SecurityConstant.ROLE_ROOT_VALUE) && !SysSecurityUtil.inRoot()){
            throw new WardenRequireAuthorizedException("修改超级管理员权限不够");
        }
        SysUserPasswordDTO sysUserPasswordDTO = new SysUserPasswordDTO();
        sysUserPasswordDTO.setUserId(passwordRequest.getUserId());
        sysUserPasswordDTO.setNewPassword(bCryptPasswordEncoder.encode(passwordRequest.getNewPassword()));
        sysUserService.updatePassowrd(sysUserPasswordDTO);

        // 写入日志
        writeLog("修改系统用户密码:"+sysUserDetailsDTO.getUid()+"["+sysUserDetailsDTO.getId()+"]", ActionTypeEnum.MODIFY, ModuleTypeEnum.SYS_USER,sysUserDetailsDTO.getId());
        return ResultModel.success();
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return
     */
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('system:user:delete')")
    public ResultModel deleteProfile(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenParamterErrorException("参数错误");
        }
        SysUserDetailsDTO sysUserDetailsDTO = sysUserService.findDetailsById(id);
        if(null == sysUserDetailsDTO){
            throw new WardenParamterErrorException("用户不存在");
        }
        if(sysUserDetailsDTO.getId().equals(SysSecurityUtil.getCurrentSysUser().getId())){
            throw new WardenParamterErrorException("不能删除自己");
        }
        if(sysUserDetailsDTO.getUid().equals(SecurityConstant.RESERVE_ROOT_USER_NAME)){
            throw new WardenParamterErrorException("保留帐号不可删除");
        }
        Set<String> roleValues = sysUserDetailsDTO.getRoles().stream().map(SysRoleDTO::getValue).collect(Collectors.toSet());
        if(SysSecurityUtil.hasAnyAuthority(roleValues, SecurityConstant.ROLE_ROOT_VALUE) && !SysSecurityUtil.inRoot()){
            throw new WardenRequireAuthorizedException("删除超级管理员权限不够");
        }
        sysUserService.delete(sysUserDetailsDTO.getId());
        // 写入日志
        writeLog("删除系统用户:"+sysUserDetailsDTO.getUid()+"["+sysUserDetailsDTO.getId()+"]", ActionTypeEnum.MODIFY, ModuleTypeEnum.SYS_USER,sysUserDetailsDTO.getId());
        return  ResultModel.success();
    }

    /**
     * 查询用户列表 - 分页
     * @param searchPageable 查询条件
     * @return
     */
    @PostMapping("/users/search")
    @PreAuthorize("hasAuthority('system:user:view')")
    public ResultModel postSearch(@RequestBody SearchPageable<SysUserSearchDTO> searchPageable){
        ResultModel resultModel = ResultModel.success();
        ResultPage<SysUserDTO> resultPage = sysUserService.findPage(searchPageable);
        resultModel.addData("list",  SysUserMapstruct.Instance.sysUsersDtoToSysUsersVo(resultPage.getList()));
        resultModel.addData("pageInfo",resultPage.getPageInfo());
        return  resultModel;
    }

    /**
     * 导出Excel数据
     * @param searchPageable 查询条件
     * @param response
     * @throws IOException
     */
    @PostMapping("/users/export")
    @PreAuthorize("hasAuthority('data:export')")
    public void export(@RequestBody SearchPageable<SysUserSearchDTO> searchPageable, HttpServletResponse response) throws IOException{
        String fileName = "系统用户"+System.currentTimeMillis();
        excelExportService.sysUserPageData(fileName,"用户列表", response, searchPageable);

        // 写入日志
        writeLog("导出系统用户信息:"+fileName, ActionTypeEnum.EXPORT, ModuleTypeEnum.SYS_USER,null);
    }
}
