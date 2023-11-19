package com.microwarp.warden.cloud.service.system.service;
import com.microwarp.warden.cloud.common.core.pageing.BaseSortDTO;
import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDataDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionaryData;

import java.util.List;

/**
 * service - 字典数据
 * @author zhouwenqi
 */
public interface SysDictionaryDataService extends BaseService<SysDictionaryData> {
    /**
     * 查询字典数据
     * @param id 字典id
     * @return
     */
    SysDictionaryDataDTO findById(Long id);
    /**
     * 创建字典数据
     * @param sysDictionaryDataDTO 字典数据
     * @return
     */
    SysDictionaryDataDTO create(SysDictionaryDataDTO sysDictionaryDataDTO);
    /**
     * 更新字典数据
     * @param sysDictionaryDTO 字典数据
     */
    void update(SysDictionaryDataDTO sysDictionaryDTO);

    /**
     * 字典项数据拖拽排序
     * @param baseSortDTO 排序参数
     */
    void dragAndSort(BaseSortDTO baseSortDTO);
    /**
     * 删除字典数据
     * @param id 字典id
     */
    void delete(Long... id);
    /**
     * 获取字典数据列表
     * @param dictId 字典id
     * @return
     */
    List<SysDictionaryDataDTO> findByDictId(Long dictId);
    /**
     * 查询字典数据(过滤禁用数据)
     * @param code 字典编码
     * @return
     */
    List<SysDictionaryDataDTO> findByDictCode(String code);
}
