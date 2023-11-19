package com.microwarp.warden.cloud.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.user.domain.dto.*;
import com.microwarp.warden.cloud.service.user.dao.UserDao;
import com.microwarp.warden.cloud.service.user.domain.convert.UserMapstruct;
import com.microwarp.warden.cloud.service.user.domain.entity.User;
import com.microwarp.warden.cloud.service.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * service - 用户 - impl
 * @author zhouwenqi
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,UserDao> implements UserService {
    /**
     * 查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public UserDTO findById(Long id){
        return this.dao.findById(id);
    }

    /**
     * 查询用户信息
     * @param uid 用户名(帐号)
     * @return 用户信息
     */
    @Override
    public UserDTO findByUid(String uid){
        return this.dao.findByUid(uid);
    }

    /**
     * 创建新用户
     * @param createUserDTO 用户信息
     * @return
     */
    @Override
    public UserDTO create(CreateUserDTO createUserDTO){
        User user = UserMapstruct.Instance.createUserDtoToUser(createUserDTO);
        this.dao.save(user);
        this.dao.updateUserPermissions(createUserDTO.getPermissionValues(),user.getId());
        return this.dao.findById(user.getId());
    }

    /**
     * 更新用户权限
     * @param permissionValues 权限列表
     * @param userId 用户id
     */
    @Override
    public void updateUserPermissions(List<String> permissionValues, Long userId){
        this.dao.updateUserPermissions(permissionValues,userId);
    }

    /**
     * 删除用户
     * @param id 用户id
     */
    @Override
    public void delete(Long id){
        this.dao.delete(id);
    }

    /**
     * 更新用户信息
     * @param updateUserDTO 用户信息
     * @return
     */
    public UserDTO update(UpdateUserDTO updateUserDTO){
        User user = UserMapstruct.Instance.updateUserDtoToUser(updateUserDTO);
        this.dao.updateById(user);
        return this.dao.findById(user.getId());
    }

    /**
     * 更新用户密码
     * @param userPasswordDTO 密码信息
     */
    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO){
        User user = new User();
        user.setId(userPasswordDTO.getUserId());
        user.setPwd(userPasswordDTO.getNewPassowrd());
        this.dao.updateById(user);
    }



    /**
     * 分页查询用户信息
     * @param iSearchPageable 查询条件
     * @return
     */
    @Override
    public ResultPage<UserDTO> findPage(ISearchPageable<UserSearchDTO> iSearchPageable){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("uid", iSearchPageable.getSearchValue())
                    .or()
                    .like("nick_name", iSearchPageable.getSearchValue())
                    .or()
                    .like("mobile", iSearchPageable.getSearchValue())
                    .or()
                    .like("real_name", iSearchPageable.getSearchValue())
            );
        }
        if(null != iSearchPageable.getFilters()){
            UserSearchDTO searchDTO = iSearchPageable.getFilters();
            if(null != searchDTO.getGenderEnum() && searchDTO.getGenderEnum().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("gender", Arrays.asList(searchDTO.getGenderEnum())));
            }
            if(null != searchDTO.getDisabled() && searchDTO.getDisabled().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("disabled", Arrays.asList(searchDTO.getDisabled())));
            }
            dao.useBaseFilter(queryWrapper,searchDTO);
        }

        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<User> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        dao.page(page,queryWrapper);
        ResultPage<UserDTO> resultPage = new ResultPage<>();
        resultPage.setList(UserMapstruct.Instance.usersToUserDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }

}
