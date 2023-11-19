package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysLoginLogDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysLoginLogSearchDTO;
import com.microwarp.warden.cloud.service.system.dao.SysLoginLogDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysLoginLogMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysLoginLog;
import com.microwarp.warden.cloud.service.system.service.SysLoginLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * service - 登录日志 - impl
 * @author zhouwenqi
 */
@Service
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLog,SysLoginLogDao> implements SysLoginLogService {
    /**
     * 查询登录日志信息
     * @param id 日志id
     * @return
     */
    public SysLoginLogDTO findById(Long id){
        return this.dao.findById(id);
    }

    /**
     * 添加登录日志信息
     * @param sysLoginLogDTO 日志信息
     */
    public void add(SysLoginLogDTO sysLoginLogDTO){
        SysLoginLog sysLoginLog = SysLoginLogMapstruct.Instance.sysLoginLogDtoToSysLoginLog(sysLoginLogDTO);
        this.dao.save(sysLoginLog);
    }

    /**
     * 删除登录日志
     * @param id 日志id
     */
    public void delete(Long... id){
        this.dao.delete(id);
    }

    /**
     * 清空登录日志
     */
    public void clearAll(){
        this.dao.clearAll();
    }

    /**
     * 分页查询登录日志
     * @param iSearchPageable 查询条件
     * @return
     */
    public ResultPage<SysLoginLogDTO> findPage(ISearchPageable<SysLoginLogSearchDTO> iSearchPageable){
        QueryWrapper<SysLoginLog> queryWrapper = new QueryWrapper<>();
        String searchKey = iSearchPageable.getSearchKey();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            switch (searchKey) {
                case "ip":
                    queryWrapper.and(wrapper -> wrapper.like("ip", iSearchPageable.getSearchValue()));
                    break;
                case "location":
                    queryWrapper.and(wrapper -> wrapper.like("location", iSearchPageable.getSearchValue()));
                    break;
                case "content":
                    queryWrapper.and(wrapper -> wrapper.like("content", iSearchPageable.getSearchValue()));
                    break;
            }
        }
        if(null != iSearchPageable.getFilters()){
            SysLoginLogSearchDTO searchDTO = iSearchPageable.getFilters();
            if(null != searchDTO.getUserId()) {
                queryWrapper.and(true, wrapper -> wrapper.eq("user_id", searchDTO.getUserId()));
            }

            if(null != searchDTO.getStatus() && searchDTO.getStatus().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("status", Arrays.asList(searchDTO.getStatus())));
            }
            this.dao.useBaseFilter(queryWrapper,searchDTO);
        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysLoginLog> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        this.dao.page(page,queryWrapper);
        ResultPage<SysLoginLogDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysLoginLogMapstruct.Instance.sysLoginLogsToSysLoginLogDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
