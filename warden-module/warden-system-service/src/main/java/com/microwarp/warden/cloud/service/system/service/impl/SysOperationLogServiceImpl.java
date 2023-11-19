package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysOperationLogDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysOperationLogSearchDTO;
import com.microwarp.warden.cloud.service.system.dao.SysOperationLogDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysOperationLogMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysOperationLog;
import com.microwarp.warden.cloud.service.system.service.SysOperationLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * service - 操作日志 - impl
 * @author zhouwenqi
 */
@Service
public class SysOperationLogServiceImpl extends BaseServiceImpl<SysOperationLog,SysOperationLogDao> implements SysOperationLogService {


    /**
     * 查询操作日志信息
     * @param id 日志id
     * @return
     */
    public SysOperationLogDTO findById(Long id){
        return this.dao.findById(id);
    }

    /**
     * 添加操作日志信息
     * @param sysOperationLogDTO 日志信息
     */
    public void add(SysOperationLogDTO sysOperationLogDTO){
        SysOperationLog sysOperationLog = SysOperationLogMapstruct.Instance.sysOperationLogDtoToSysOperationLog(sysOperationLogDTO);
        this.dao.save(sysOperationLog);
    }

    /**
     * 删除操作日志
     * @param id 日志id
     */
    public void delete(Long... id){
        this.dao.delete(id);
    }

    /**
     * 清空操作日志
     */
    public void clearAll(){
        this.dao.clearAll();
    }

    /**
     * 分页查询操作日志
     * @param iSearchPageable 查询条件
     * @return
     */
    public ResultPage<SysOperationLogDTO> findPage(ISearchPageable<SysOperationLogSearchDTO> iSearchPageable){
        QueryWrapper<SysOperationLog> queryWrapper = new QueryWrapper<>();
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
            SysOperationLogSearchDTO searchDTO = iSearchPageable.getFilters();
            if(null != searchDTO.getUserId()) {
                queryWrapper.and(true, wrapper -> wrapper.eq("user_id", searchDTO.getUserId()));
            }
            if(null != searchDTO.getMateId()) {
                queryWrapper.and(true, wrapper -> wrapper.eq("mate_id", searchDTO.getMateId()));
            }
            if(null != searchDTO.getActionType() && searchDTO.getActionType().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("action_type", Arrays.asList(searchDTO.getActionType())));
            }
            if(null != searchDTO.getModuleType() && searchDTO.getModuleType().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.eq("module_type", Arrays.asList(searchDTO.getModuleType())));
            }
            this.dao.useBaseFilter(queryWrapper,searchDTO);
        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysOperationLog> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        this.dao.page(page,queryWrapper);
        ResultPage<SysOperationLogDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysOperationLogMapstruct.Instance.sysOperationLogsToSysOperationLogDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
