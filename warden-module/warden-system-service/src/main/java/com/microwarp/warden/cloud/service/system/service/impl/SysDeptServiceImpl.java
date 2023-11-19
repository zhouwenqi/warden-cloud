package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.pageing.BaseSortDTO;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.common.redis.constant.CacheKeys;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptSearchDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptTreeDTO;
import com.microwarp.warden.cloud.service.system.dao.SysDeptDao;
import com.microwarp.warden.cloud.service.system.dao.SysUserDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysDeptMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDept;
import com.microwarp.warden.cloud.service.system.mapper.SysDeptMapper;
import com.microwarp.warden.cloud.service.system.service.SysDeptService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * service - 部门 - impl
 * @author zhouwenqi
 */
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDept,SysDeptDao> implements SysDeptService {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 查询部门信息(含上级部门信息)
     * @param id 部门id
     * @return
     */
    @Override
    public SysDeptDTO findById(Long id){
        SysDept sysDept = this.dao.getById(id);
        SysDeptDTO sysDeptDTO = null != sysDept ? SysDeptMapstruct.Instance.sysDeptToSysDeptDTO(sysDept) : null;
        recursionParent(sysDeptDTO);
        return sysDeptDTO;
    }

    /**
     * 查询子部门列表
     * @param parentId 上级部门ID
     * @return
     */
    public List<SysDeptTreeDTO> findByParentId(Long parentId){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        queryWrapper.orderByAsc("orders");
        List<SysDept> list = this.dao.list(queryWrapper);
        return null == list ? new ArrayList<>() : SysDeptMapstruct.Instance.sysDeptsToSysDeptTreeDTOs(list);
    }

    /**
     * 获取部门信息(含子部门列表)
     * @param id
     * @return
     */
    @Override
    public SysDeptTreeDTO findChildrenById(Long id){
        SysDept sysDept = this.dao.getById(id);
        if(null == sysDept){
            return null;
        }
        SysDeptTreeDTO sysDeptTreeDTO = SysDeptMapstruct.Instance.sysDeptToSysDeptTreeDTO(sysDept);
        sysDeptTreeDTO.setChildren(findByParentId(sysDept.getParentId()));
        return sysDeptTreeDTO;
    }

    /**
     * 创建部门
     * @param sysDeptDTO 部门信息
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheKeys.DATA_DEPT_TREE, allEntries = true)
    public SysDeptDTO create(SysDeptDTO sysDeptDTO){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",sysDeptDTO.getName());
        if(this.dao.count(queryWrapper) > 0){
            throw new WardenParamterErrorException("部门名称不能重复");
        }
        SysDept sysDept = SysDeptMapstruct.Instance.sysDeptDtoToSysDept(sysDeptDTO);
        this.dao.save(sysDept);
        return findById(sysDept.getId());
    }

    /**
     * 更新部门信息
     * @param sysDeptDTO 部门id
     */
    @Transactional
    @Override
    @CacheEvict(value = CacheKeys.DATA_DEPT_TREE, allEntries = true)
    public void update(SysDeptDTO sysDeptDTO){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",sysDeptDTO.getName());
        queryWrapper.ne("id",sysDeptDTO.getId());
        if(this.dao.count(queryWrapper) > 0){
            throw new WardenParamterErrorException("部门名称不能重复");
        }
        SysDept sysDept = SysDeptMapstruct.Instance.sysDeptDtoToSysDept(sysDeptDTO);
        this.dao.updateById(sysDept);
        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 部门拖曳和排序
     * @param baseSortDTO 排序数据
     */
    @Transactional
    @Override
    @CacheEvict(value = CacheKeys.DATA_DEPT_TREE, allEntries = true)
    public void dragAndSort(BaseSortDTO baseSortDTO){
        if(null != baseSortDTO.getParentId() && null != baseSortDTO.getDragId()){
            SysDept sysDept = new SysDept();
            sysDept.setParentId(baseSortDTO.getParentId());
            sysDept.setId(baseSortDTO.getDragId());
            this.dao.updateById(sysDept);
            // 清除用户缓存
            sysUserService.clearAll();
        }
        if(null != baseSortDTO.getIds() && baseSortDTO.getIds().length > 0){
            int i = 0;
            for(Long id:baseSortDTO.getIds()){
                UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("orders",i);
                updateWrapper.eq("id",id);
                this.dao.update(updateWrapper);
                i ++;
            }
        }
    }

    /**
     * 删除部门信息
     * @param id 部门id
     */
    @Transactional
    @Override
    @CacheEvict(value =  CacheKeys.DATA_DEPT_TREE, allEntries = true)
    public void delete(Long...id){
        if(null == id || id.length < 1){
            return;
        }
        List<Long> ids = new ArrayList<>();
        recursionIds(Arrays.asList(id),ids);
        this.dao.removeBatchByIds(ids);
        sysUserDao.clearDeptId(ids.toArray(new Long[ids.size()]));
        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 递归获取父级部门
     * @param sysDeptDTO 当门部门
     */
    private void recursionParent(SysDeptDTO sysDeptDTO){
        if(sysDeptDTO.getParentId() != null && sysDeptDTO.getParentId() > 0){
            sysDeptDTO.setParentDept(findById(sysDeptDTO.getParentId()));
            if(null != sysDeptDTO.getParentDept()) {
                recursionParent(sysDeptDTO.getParentDept());
            }
        }
    }

    /**
     * 递归获取下级部门列表
     * @param list 部门列表
     */
    private void recursionChildren(List<SysDeptTreeDTO> list){
        if(null == list || list.size() < 1){
            return;
        }
        for(SysDeptTreeDTO sysDeptTreeDTO:list){
            List<SysDeptTreeDTO> children = findByParentId(sysDeptTreeDTO.getId());
            sysDeptTreeDTO.setChildren(children.size() < 1 ? null : children);
            recursionChildren(sysDeptTreeDTO.getChildren());
        }
    }

    /**
     * 递归获取所有部门ID
     * @param ids 下级部门ID列表
     * @param deptIds 部门ID平铺列表
     * @return
     */
    @Override
    public void recursionIds(List<Long> ids,List<Long> deptIds){
        if(ids != null && ids.size() > 0){
            deptIds.addAll(ids);
            for (Long id:ids){
                QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("id");
                queryWrapper.eq("parent_id",id);
                List<SysDept> sysDepts = this.dao.list(queryWrapper);
                if(null != sysDepts && sysDepts.size()>0){
                    recursionIds(sysDepts.stream().map(SysDept::getId).collect(Collectors.toList()),deptIds);
                }
            }
        }
    }

    /**
     * 获取部门树型结构
     * @return 树型数据
     */
    @Override
    @Cacheable(value = CacheKeys.DATA_DEPT_TREE, key="'tree'", unless = "#result.size() < 1")
    public List<SysDeptTreeDTO> findTrees(){
        List<SysDeptTreeDTO> root = this.dao.findByParentId(0L);
        recursionChildren(root);
        return root;
    }

    /**
     * 分页查询部门信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<SysDeptDTO> findPage(ISearchPageable<SysDeptSearchDTO> iSearchPageable){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("name", iSearchPageable.getSearchValue())
                    .or()
                    .like("code", iSearchPageable.getSearchValue())
                    .or()
                    .like("description", iSearchPageable.getSearchValue())
                    .or()
                    .likeLeft("py", iSearchPageable.getSearchValue())
                    .or()
                    .likeLeft("pinyin", iSearchPageable.getSearchValue())
            );
        }
        if(null != iSearchPageable.getFilters()){
            SysDeptSearchDTO searchDTO = iSearchPageable.getFilters();
            if(null != searchDTO.getLeaderId()){
                queryWrapper.and(true, wrapper -> wrapper.eq("leader_id",searchDTO.getLeaderId()));
            }
            if(null != searchDTO.getDisabled()){
                queryWrapper.and(true, wrapper -> wrapper.eq("disabled",searchDTO.getDisabled()));
            }
            if(null != searchDTO.getCreateDate() && searchDTO.getCreateDate().length > 0){
                if(searchDTO.getCreateDate().length < 2){
                    queryWrapper.and(true, wrapper -> wrapper.ge("create_date",searchDTO.getCreateDate()[0]));
                }else{
                    queryWrapper.and(true, wrapper -> wrapper.between("create_date",searchDTO.getCreateDate()[0],searchDTO.getCreateDate()[1]));
                }
            }
        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysDept> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        this.dao.page(page,queryWrapper);
        ResultPage<SysDeptDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysDeptMapstruct.Instance.sysDeptsToSysDeptDTOs(page.getRecords()));
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
