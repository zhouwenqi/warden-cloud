package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.core.util.StringUtil;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.common.redis.DataCacheableService;
import com.microwarp.warden.cloud.common.redis.constant.CacheKeys;
import com.microwarp.warden.cloud.facade.system.domain.dto.*;
import com.microwarp.warden.cloud.service.system.dao.*;
import com.microwarp.warden.cloud.service.system.domain.convert.SysUserMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUser;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * service - 系统用户 - impl
 * @author zhouwenqi
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser,SysUserDao> implements SysUserService {
    @Resource
    private SysRoleDao sysRoleDao;
    @Resource
    private SysDeptDao sysDeptDao;
    @Resource
    private SysPostDao sysPostDao;
    @Resource
    private SysPermissionDao sysPermissionDao;
    @Resource
    private DataCacheableService iCacheService;

    /**
     * 查询用户基本信息
     * @param id  用户id
     * @return
     */
    @Override
    public SysUserDTO findById(Long id){
        return dao.findById(id);
    }

    /**
     * 查询用户基本信息
     * @param uid  用户名(帐号)
     * @return
     */
    @Override
    public SysUserDTO findByUid(String uid){
        return dao.findByUid(uid);
    }

    /**
     * 查询用户详情
     * @param id 用户ID
     * @return
     */
    @Override
    @Cacheable(value = CacheKeys.DATA_SYS_USER_ID, key="#id", unless = "#result eq null")
    public SysUserDetailsDTO findDetailsById(Long id){
        SysUserDTO sysUserDTO = findById(id);
        return findDetailsByUserDTO(sysUserDTO);
    }

    /**
     * 查询用户详情
     * @param uid 用户名(帐号)
     * @return
     */
    @Override
    @Cacheable(value = CacheKeys.DATA_SYS_USER_UID, key="#uid", unless = "#result eq null")
    public SysUserDetailsDTO findDetailsByUid(String uid){
        SysUserDTO sysUserDTO = findByUid(uid);
        return findDetailsByUserDTO(sysUserDTO);
    }

    /**
     * 插入系统用户
     * @param sysUserDTO 用户信息
     * @return
     */
    @Override
    @Transactional
    public Long insert(SysUserDTO sysUserDTO){
        if(null != findByUid(sysUserDTO.getUid())){
            throw new WardenParamterErrorException("用户已存在");
        }
        // 真实姓名拼音处理
        if(StringUtils.isNotBlank(sysUserDTO.getRealName())){
            String[] pinyins = StringUtil.getPinyins(sysUserDTO.getRealName());
            sysUserDTO.setPinyin(pinyins[0]);
            sysUserDTO.setPy(pinyins[1]);
        }
        SysUser sysUser = SysUserMapstruct.Instance.sysUserDtoToSysUser(sysUserDTO);
        dao.save(sysUser);

        return sysUser.getId();
    }

    /**
     * 创建系统用户
     * @param sysUserRequestDTO 用户信息
     * @return
     */
    @Override
    @Transactional
    public Long create(SysUserRequestDTO sysUserRequestDTO){
        Long userId = insert(sysUserRequestDTO);
        if(null != sysUserRequestDTO.getRoleIds()){
            sysRoleDao.saveUserRoles(userId,sysUserRequestDTO.getRoleIds());
        }
        return userId;
    }

    /**
     * 更新用户信息
     * @param sysUserDTO 用户信息
     */
    @Override
    @Caching(
            put = {
                    @CachePut(value = CacheKeys.DATA_SYS_USER_ID,key="#sysUserDTO.id", unless = "#sysUserDTO.id eq null"),
                    @CachePut(value = CacheKeys.DATA_SYS_USER_UID,key="#result.uid", unless = "#result eq null")
            }
    )
    @Transactional
    public SysUserDetailsDTO update(SysUserDTO sysUserDTO){
        // 真实姓名拼音处理
        if(StringUtils.isNotBlank(sysUserDTO.getRealName())){
            String[] pinyins = StringUtil.getPinyins(sysUserDTO.getRealName());
            sysUserDTO.setPinyin(pinyins[0]);
            sysUserDTO.setPy(pinyins[1]);
        }
        SysUser sysUser = SysUserMapstruct.Instance.sysUserDtoToSysUser(sysUserDTO);
        dao.updateById(sysUser);
        // 因为还有以uid为key的缓存，所在要手动清理一下
        return findDetailsById(sysUser.getId());

    }

    /**
     * 更新用户密码
     * @param sysUserPasswordDTO 密码参数
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheKeys.DATA_SYS_USER_ID, key = "#sysUserPasswordDTO.userId")
    public void updatePassowrd(SysUserPasswordDTO sysUserPasswordDTO){
        if(null == sysUserPasswordDTO.getUserId()){
            throw new WardenRequireParamterException();
        }

        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pwd",sysUserPasswordDTO.getNewPassword());
        updateWrapper.eq("id",sysUserPasswordDTO.getUserId());
        dao.update(updateWrapper);

        // 因为还有以uid为key的缓存，所在要手动清理一下
        SysUser user = dao.getById(sysUserPasswordDTO.getUserId());
        if(null != user){
            iCacheService.batchRemove(CacheKeys.DATA_SYS_USER_UID, user.getUid());
        }
    }

    /**
     * 根据DTO用户信息查询用户详情
     * @param sysUserDTO 用户信息
     * @return
     */
    private SysUserDetailsDTO findDetailsByUserDTO(SysUserDTO sysUserDTO){
        if(null == sysUserDTO){
            return null;
        }

        SysUserDetailsDTO sysUserDetailsDTO = SysUserMapstruct.Instance.sysUserDtoToSysUserDetailsDTO(sysUserDTO);
        Set<SysRoleDTO> roleDTOS = new HashSet<>(sysRoleDao.findByUserId(sysUserDTO.getId()));
        Long[] roleIds = roleDTOS.stream().map(SysRoleDTO::getId).toArray(Long[]::new);
        Set<SysPermissionDTO> permissionDTOS = new HashSet<>(sysPermissionDao.findByRoleIds(roleIds));
        sysUserDetailsDTO.setDept(sysDeptDao.findById(sysUserDTO.getDeptId()));
        sysUserDetailsDTO.setPost(sysPostDao.findById(sysUserDTO.getPostId()));
        sysUserDetailsDTO.setRoles(roleDTOS);
        sysUserDetailsDTO.setPermissions(permissionDTOS);
        return sysUserDetailsDTO;
    }

    /**
     * 分页查询用户列表信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<SysUserDTO> findPage(ISearchPageable<SysUserSearchDTO> iSearchPageable){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("uid", iSearchPageable.getSearchValue())
                    .or()
                    .like("real_name", iSearchPageable.getSearchValue())
                    .or()
                    .likeLeft("py", iSearchPageable.getSearchValue())
                    .or()
                    .likeLeft("pinyin", iSearchPageable.getSearchValue())
            );
        }
        if(null != iSearchPageable.getFilters()){
            dao.useBaseFilter(queryWrapper,iSearchPageable.getFilters());
        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysUser> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        dao.page(page,queryWrapper);
        ResultPage<SysUserDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysUserMapstruct.Instance.sysUsersToSysUserDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }

    /**
     * 刷新用户密钥
     * @param userId 用户id
     * @return 新的密钥
     */
    @Override
    public String refreshSecretKey(Long userId){
        SysUser sysUser = this.dao.getById(userId);
        if(null == sysUser){
            return null;
        }
        // 先清掉缓存
        iCacheService.batchRemove(CacheKeys.DATA_SYS_USER_UID, sysUser.getUid());
        iCacheService.batchRemove(CacheKeys.DATA_SYS_USER_ID, userId.toString());
        // 刷新密钥
        return this.dao.refreshSecretKey(userId);
    }

    /**
     * 删除系统用户
     * @param userId 用户id
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheKeys.DATA_SYS_USER_ID, key = "#userId")
    public void delete(Long userId){
        // 因为还有以uid为key的缓存，所在要手动清理一下
        SysUser sysUser = dao.getById(userId);
        if(null != sysUser){
            iCacheService.batchRemove(CacheKeys.DATA_SYS_USER_UID, sysUser.getUid());
        }
        dao.removeById(userId);
        sysRoleDao.deleteRoleByUserId(userId);

    }

    /**
     * 清除用户详情缓存
     * @param userId 用户ID
     */
    @Override
    public void clearCache(Long userId){
        if(null == userId){
            iCacheService.batchRemove(CacheKeys.DATA_SYS_USER_UID, null);
            iCacheService.batchRemove(CacheKeys.DATA_SYS_USER_ID, null);
        }else{
            SysUser sysUser = dao.getById(userId);
            iCacheService.batchRemove(CacheKeys.DATA_SYS_USER_UID, sysUser.getUid());
            iCacheService.batchRemove(CacheKeys.DATA_SYS_USER_ID, userId.toString());
        }
    }

    /**
     * 清除所有用户详情缓存
     */
    @Override
    public void clearAll(){
        clearCache(null);
    }
}
