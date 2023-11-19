package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.core.pageing.SearchPageable;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserSearchDTO;
import com.microwarp.warden.cloud.facade.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * controller - 普通(前台)用户
 * 配合前台(用户端)项目demo演示
 * @author zhouwenqi
 */
@RestController
public class UserController extends BaseController {
    @Autowired(required = false)
    private IUserService iUserService;

    /**
     * 查看用户信息
     * @param id 用户id
     * @return
     */
    @GetMapping("/user/{id}")
    public ResultModel info(@PathVariable("id") Long id){
        if(null == id){
            throw new WardenParamterErrorException("参数错误");
        }
        UserDTO userDTO = iUserService.findById(id);
        if(null == userDTO){
            throw new WardenRequireParamterException("用户不存在");
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData("user", userDTO);
        return resultModel;
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return
     */
    @DeleteMapping("/user/{id}")
    public ResultModel delete(@PathVariable("id") Long id){
        if(null != id){
            iUserService.delete(id);
        }
        return ResultModel.success();
    }

    /**
     * 查询用户列表 - 分页
     * @param searchPageable 查询条件
     * @return
     */
    @PostMapping("/users/search")
    public ResultModel postSearch(@RequestBody SearchPageable<UserSearchDTO> searchPageable){
        ResultModel resultModel = ResultModel.success();
        ResultPage<UserDTO> resultPage = iUserService.findPage(searchPageable);
        resultModel.addData("list", resultPage.getList());
        resultModel.addData("pageInfo",resultPage.getPageInfo());
        return  resultModel;
    }
}
