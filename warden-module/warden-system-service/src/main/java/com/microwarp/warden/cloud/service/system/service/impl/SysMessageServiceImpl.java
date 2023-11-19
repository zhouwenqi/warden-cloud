package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.PageInfo;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.core.util.ResultUtil;
import com.microwarp.warden.cloud.common.core.util.StringUtil;
import com.microwarp.warden.cloud.common.database.convert.PageMapstruct;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.common.security.token.TokenUser;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysMessageSearchDTO;
import com.microwarp.warden.cloud.service.system.dao.SysMessageDao;
import com.microwarp.warden.cloud.service.system.dao.SysUserDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysMessageMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysMessage;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUser;
import com.microwarp.warden.cloud.service.system.service.SysMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * service - 系统消息 - impl
 * @author zhouwenqi
 */
@Service
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage,SysMessageDao> implements SysMessageService {
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 查询一条系统消息
     * @param id 消息ID
     * @return 消息内容
     */
    @Override
    public SysMessageDTO findById(Long id){
        SysMessage sysMessage = this.dao.getById(id);
        return null == sysMessage ? null : SysMessageMapstruct.Instance.sysMessageToSysMessageDTO(sysMessage);
    }

    /**
     * 查询一条系统消息
     * @param id 消息ID
     * @param tokenUser 接收用户
     * @return 消息内容
     */
    public SysMessageDTO findById(Long id, TokenUser tokenUser){
        // 防止贯穿访问
        if(null == id || null == tokenUser || null == tokenUser.getUserId() || null == tokenUser.getUserType()){
            return null;
        }
        return this.dao.findById(id,tokenUser);
    }
    /**
     * 创建一条系统消息
     * @param sysMessageDTO 消息内容
     * @return
     */
    @Override
    public SysMessageDTO create(SysMessageDTO sysMessageDTO){
        SysMessage sysMessage = SysMessageMapstruct.Instance.sysMessageDtoToSysMessage(sysMessageDTO);
        this.dao.save(sysMessage);
        return findById(sysMessage.getId());
    }

    /**
     * 设计消息已读
     * @param id 消息ID
     * @param tokenUser 接收人
     */
    @Override
    public void read(Long[] id, TokenUser tokenUser){
        // 暂时不让这个方法阅读所有，强制用readAll方法；
        if(null == id || id.length < 1 ){
            return;
        }
        this.dao.setReadStatus(id,tokenUser,true);
    }

    /**
     * 批量写入消息(所有用户写一条)
     * @param sysMessageDTO 消息内容
     */
    @Transactional
    public void writeByAllSysUser(SysMessageDTO sysMessageDTO){
        List<SysUser> list = sysUserDao.findAll();
        for(SysUser sysUser:list) {
            sysMessageDTO.setToId(sysUser.getId());
            SysMessage sysMessage = SysMessageMapstruct.Instance.sysMessageDtoToSysMessage(sysMessageDTO);
            this.dao.save(sysMessage);
        }
    }

    /**
     * 设置所有消息已读
     * @param tokenUser 接收人
     */
    @Override
    public void readAll(TokenUser tokenUser){
        this.dao.setReadStatus(null, tokenUser, true);
    }

    /**
     * 删除系统消息
     * @param tokenUser 接收人
     */
    @Override
    public void delete(Long[]id, TokenUser tokenUser){
        this.dao.delete(id,tokenUser);
    }

    /**
     * 获取未读消息数量
     * @param tokenUser 接收人
     * @return
     */
    @Override
    public long totalUnread(TokenUser tokenUser){
        QueryWrapper<SysMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("to_id",tokenUser.getUserId());
        queryWrapper.eq("to_platform", ResultUtil.convertToPlatform(tokenUser.getUserType()));
        queryWrapper.eq("reading",false);
        return this.dao.count(queryWrapper);
    }

    /**
     * 分页系统消息
     * @param iSearchPageable 查询条件
     * @return
     */
    @Override
    public ResultPage<SysMessageDTO> findPage(ISearchPageable<SysMessageSearchDTO> iSearchPageable){
        QueryWrapper<SysMessage> queryWrapper = new QueryWrapper<>();
        String searchKey = iSearchPageable.getSearchKey();
        if(StringUtils.isNotBlank(iSearchPageable.getSearchValue())) {
            switch (searchKey) {
                case "title":
                    queryWrapper.and(wrapper -> wrapper.like("title", iSearchPageable.getSearchValue()));
                    break;
                case "content":
                    queryWrapper.and(wrapper -> wrapper.like("content", iSearchPageable.getSearchValue()));
                    break;
                case "msgId":
                    queryWrapper.and(wrapper -> wrapper.like("msgId", iSearchPageable.getSearchValue()));
                    break;
            }
        }
        if(null != iSearchPageable.getFilters()){
            SysMessageSearchDTO searchDTO = iSearchPageable.getFilters();
            if(null != searchDTO.getToId()) {
                queryWrapper.and(true, wrapper -> wrapper.eq("to_id", searchDTO.getToId()));
            }
            if(null != searchDTO.getToPlatform()){
                queryWrapper.and(true, wrapper -> wrapper.eq("to_platform", searchDTO.getToPlatform()));
            }

            if(null != searchDTO.getMsgType() && searchDTO.getMsgType().length > 0) {
                queryWrapper.and(true, wrapper -> wrapper.in("msg_type", Arrays.asList(searchDTO.getMsgType())));
            }

            if(null != searchDTO.getReading()){
                queryWrapper.and(true,wrapper -> wrapper.eq("reading",searchDTO.getReading()));
            }
            this.dao.useBaseFilter(queryWrapper,searchDTO);
        }
        PageInfo pageInfo = iSearchPageable.getPageInfo();
        Page<SysMessage> page = new Page<>(pageInfo.getCurrent(),pageInfo.getPageSize());
        page.setOrders(PageMapstruct.Instance.sortFieldsToOrderItems(iSearchPageable.getSorts()));
        this.dao.page(page,queryWrapper);
        ResultPage<SysMessageDTO> resultPage = new ResultPage<>();
        resultPage.setList(SysMessageMapstruct.Instance.sysMessagesToSysMessageDTOs(page.getRecords()));
        pageInfo = PageMapstruct.Instance.pageToPageInfo(page);
        resultPage.setPageInfo(pageInfo);
        return resultPage;
    }
}
