package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionary;

/**
 * dao - 字典
 * @author zhouwenqi
 */
public interface SysDictionaryDao extends BaseDao<SysDictionary> {
    /**
     * 查询字典信息
     * @param id 字典id
     * @return
     */
    SysDictionaryDTO findById(Long id);

    /**
     * 使用字典id反向查询字典code
     * @param ids 字典数据id列表
     * @return
     */
    String[] findDCodeByIds(Long... ids);
    /**
     * 使用字典数据id反向查询字典code
     * @param ids 字典数据id列表
     * @return
     */
    String[] findCodeByDataIds(Long... ids);
    /**
     * 查询字典信息
     * @param code 字典编码
     * @return
     */
    SysDictionaryDTO findByCode(String code);
}
