package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysNoticeDTO;
import com.microwarp.warden.cloud.service.system.dao.SysNoticeDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysNoticeMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysNotice;
import com.microwarp.warden.cloud.service.system.service.SysNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * service - 系统公告 - impl
 * @author zhouwenqi
 */
@Service
public class SysNoticeServiceImpl extends BaseServiceImpl<SysNotice,SysNoticeDao> implements SysNoticeService {

    /**
     * 查询公告信息
     * @param id 公告ID
     * @return
     */
    @Override
    public SysNoticeDTO findById(Long id){
        SysNotice sysNotice = this.dao.getById(id);
        return null == sysNotice ? null : SysNoticeMapstruct.Instance.sysNoticeToSysNoticeDTO(sysNotice);
    }

    /**
     * 创建系统公告
     * @param sysNoticeDTO 公告内容
     * @return
     */
    @Override
    public SysNoticeDTO create(SysNoticeDTO sysNoticeDTO) {
        SysNotice sysNotice = SysNoticeMapstruct.Instance.sysNoticeDtoToSysNotice(sysNoticeDTO);
        this.dao.save(sysNotice);
        return findById(sysNotice.getId());
    }

    /**
     * 更新系统公告
     * @param sysNoticeDTO 公告内容
     */
    @Override
    public void update(SysNoticeDTO sysNoticeDTO){
        SysNotice sysNotice = SysNoticeMapstruct.Instance.sysNoticeDtoToSysNotice(sysNoticeDTO);
        this.dao.updateById(sysNotice);
    }

    /**
     * 删除系统公告
     * @param id 公告ID
     */
    @Override
    public void delete(Long ... id){
        if(null != id && id.length > 0){
            this.dao.removeByIds(Arrays.asList(id));
        }
    }

    /**
     * 分页查询系统公告
     * @param iSearchPageable 查询条件
     * @return
     */
    @Override
    public ResultPage<SysNoticeDTO> findPage(ISearchPageable<BasicSearchDTO> iSearchPageable){
        QueryWrapper<SysNotice> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            queryWrapper.and(wrapper -> wrapper
                    .like("title", iSearchPageable.getSearchValue())
                    .or()
                    .like("content", iSearchPageable.getSearchValue())
            );
        }
        if(null != iSearchPageable.getFilters()){
            this.dao.useBaseFilter(queryWrapper,iSearchPageable.getFilters());
        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysNotice> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        this.dao.page(page,queryWrapper);
        ResultPage<SysNoticeDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysNoticeMapstruct.Instance.sysNoticesToSysNoticeDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
