package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.common.redis.constant.CacheKeys;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysConfigDTO;
import com.microwarp.warden.cloud.service.system.dao.SysConfigDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysConfigMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysConfig;
import com.microwarp.warden.cloud.service.system.service.SysConfigService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * service - 配置 - impl
 * @author zhouwenqi
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig,SysConfigDao> implements SysConfigService {
    @Resource
    private SysConfigDao sysConfigDao;
    /**
     * 获取当前系统配置
     * @return 系统配置
     */
    @Override
    @Cacheable(value = CacheKeys.DATA_SYS_CONFIG, key="'system'", unless = "#result eq null")
    public SysConfigDTO findCurrent(){
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        SysConfig sysConfig = sysConfigDao.getOne(queryWrapper);
        if(null == sysConfig){
            sysConfig = new SysConfig();
            sysConfig.setAllowManyToken(false);
            sysConfig.setEnabledRegister(false);
        }
        return SysConfigMapstruct.Instance.sysConfigToSysConfigDTO(sysConfig);
    }

    /**
     * 更新系统配置
     * @param sysConfigDTO 系统配置
     */
    @Override
    @Transactional
    @CacheEvict(value = CacheKeys.DATA_SYS_CONFIG, key="'system'")
    public void update(SysConfigDTO sysConfigDTO){
        SysConfig sysConfig = SysConfigMapstruct.Instance.sysConfigDTOtoSysConfig(sysConfigDTO);
        sysConfigDao.update(sysConfig,null);
    }
}
