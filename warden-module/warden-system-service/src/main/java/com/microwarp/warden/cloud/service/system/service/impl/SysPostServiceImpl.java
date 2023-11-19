package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.pageing.*;
import com.microwarp.warden.cloud.common.core.util.StringUtil;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPostDTO;
import com.microwarp.warden.cloud.service.system.dao.SysPostDao;
import com.microwarp.warden.cloud.service.system.dao.SysUserDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysPostMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysPost;
import com.microwarp.warden.cloud.service.system.service.SysPostService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * service - 岗位
 * @author zhouwenqi
 */
@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPost,SysPostDao> implements SysPostService {
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysUserService sysUserService;

    /**
     * 查询岗位信息
     * @param id 岗位id
     * @return
     */
    @Override
    public SysPostDTO findById(Long id){
        return  this.dao.findById(id);
    }

    /**
     * 创建岗位信息
     * @param sysPostDTO 岗位信息
     * @return
     */
    @Override
    @Transactional
    public SysPostDTO create(SysPostDTO sysPostDTO){
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",sysPostDTO.getName());
        if(this.dao.count(queryWrapper)>0){
            throw new WardenParamterErrorException("部门名称不能重复");
        }
        // 岗位拼音处理
        if(StringUtils.isNotBlank(sysPostDTO.getName())){
            String[] pinyins = StringUtil.getPinyins(sysPostDTO.getName());
            sysPostDTO.setPinyin(pinyins[0]);
            sysPostDTO.setPy(pinyins[1]);
        }
        SysPost sysPost = SysPostMapstruct.Instance.sysPostDtoToSysPost(sysPostDTO);
        this.dao.save(sysPost);
        return findById(sysPost.getId());
    }

    /**
     * 更新岗位信息
     * @param sysPostDTO 岗位信息
     */
    @Override
    @Transactional
    public void update(SysPostDTO sysPostDTO){
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",sysPostDTO.getName());
        queryWrapper.ne("id",sysPostDTO.getId());
        if(this.dao.count(queryWrapper)>0){
            throw new WardenParamterErrorException("部门名称不能重复");
        }
        // 岗位拼音处理
        if(StringUtils.isNotBlank(sysPostDTO.getName())){
            String[] pinyins = StringUtil.getPinyins(sysPostDTO.getName());
            sysPostDTO.setPinyin(pinyins[0]);
            sysPostDTO.setPy(pinyins[1]);
        }
        SysPost sysPost = SysPostMapstruct.Instance.sysPostDtoToSysPost(sysPostDTO);
        this.dao.updateById(sysPost);
        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 岗位拖拽排序
     * @param baseSortDTO 排序参数
     */
    @Override
    @Transactional
    public void dragAndSort(BaseSortDTO baseSortDTO){
        if(null != baseSortDTO.getIds() && baseSortDTO.getIds().length > 0){
            int i = 0;
            for(Long id:baseSortDTO.getIds()){
                UpdateWrapper<SysPost> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("orders",i);
                updateWrapper.eq("id",id);
                this.dao.update(updateWrapper);
                i ++;
            }
        }
    }

    /**
     * 删除岗位信息
     * @param id 岗位id
     */
    @Override
    @Transactional
    public void delete(Long... id){
        if(null == id || id.length < 1){
            return;
        }
        this.dao.removeBatchByIds(Arrays.asList(id));
        sysUserDao.clearPostId(id);
        // 清除用户缓存
        sysUserService.clearAll();
    }

    /**
     * 获取所有岗位信息
     * @return 所有岗位列表
     */
    @Override
    public List<SysPostDTO> findAll(){
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("orders");
        List<SysPost> list = this.dao.list(queryWrapper);
        return null == list || list.size() < 1 ? new ArrayList<>() : SysPostMapstruct.Instance.sysPostsToSysPostsDTOs(list);
    }



    /**
     * 分页查询岗位信息
     * @param iSearchPageable 查询参数
     * @return
     */
    @Override
    public ResultPage<SysPostDTO> findPage(ISearchPageable<BasicSearchDTO> iSearchPageable){
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
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
            this.dao.useBaseFilter(queryWrapper,iSearchPageable.getFilters());
        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysPost> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        this.dao.page(page,queryWrapper);
        ResultPage<SysPostDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysPostMapstruct.Instance.sysPostsToSysPostsDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }

}
