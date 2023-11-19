package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDataDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionaryData;

/**
 * dao - 字典数据
 * @author zhouwenqi
 */
public interface SysDictionaryDataDao extends BaseDao<SysDictionaryData> {
    /**
     * 查询字典数据信息
     * @param id 字典数据id
     * @return
     */
    SysDictionaryDataDTO findById(Long id);

    /**
     * 删除字典数据
     * @param dictId 字典id
     */
    void deleteByDictId(Long... dictId);
}
