package com.microwarp.warden.cloud.service.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDataDTO;
import com.microwarp.warden.cloud.service.system.dao.SysDictionaryDataDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysDictionaryDataMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionaryData;
import com.microwarp.warden.cloud.service.system.mapper.SysDictionaryDataMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

/**
 * dao - 字典数据 - impl
 */
@Repository
public class SysDictionaryDataDaoImpl extends BaseDaoImpl<SysDictionaryDataMapper,SysDictionaryData> implements SysDictionaryDataDao {
    /**
     * 查询字典数据信息
     * @param id 字典数据id
     * @return
     */
    public SysDictionaryDataDTO findById(Long id){
        if(null == id){
            return null;
        }
        SysDictionaryData sysDictionaryData = baseMapper.selectById(id);
        if(null == sysDictionaryData){
            return null;
        }
        return SysDictionaryDataMapstruct.Instance.sysDictionaryDataToSysDictionaryDataDTO(sysDictionaryData);
    }


    /**
     * 删除字典数据
     * @param dictId 字典id
     */
    public void deleteByDictId(Long... dictId){
        QueryWrapper<SysDictionaryData> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("dict_id", Arrays.asList(dictId));
        baseMapper.delete(queryWrapper);
    }
}
