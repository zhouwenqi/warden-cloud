package com.microwarp.warden.cloud.service.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDTO;
import com.microwarp.warden.cloud.service.system.dao.SysDictionaryDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysDictionaryMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionary;
import com.microwarp.warden.cloud.service.system.mapper.SysDictionaryMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * dao - 字典 - impl
 * @author zhouwenqi
 */
@Repository
public class SysDictionaryDaoImpl extends BaseDaoImpl<SysDictionaryMapper,SysDictionary> implements SysDictionaryDao {
    /**
     * 查询字典信息
     * @param id 字典id
     * @return
     */
    public SysDictionaryDTO findById(Long id){
        if(null == id){
            return null;
        }
        SysDictionary sysDictionary = baseMapper.selectById(id);
        if(null == sysDictionary){
            return null;
        }
        return SysDictionaryMapstruct.Instance.sysDictionaryToSysDictionaryDTO(sysDictionary);
    }

    /**
     * 使用字典id反向查询字典code
     * @param ids 字典数据id列表
     * @return
     */
    public String[] findDCodeByIds(Long...ids){
        List<String> list = baseMapper.findCodeByIds(ids);
        if(null != list && list.size()>0){
            return list.toArray(new String[list.size()]);
        }
        return new String[0];
    }

    /**
     * 使用字典数据id反向查询字典code
     * @param ids 字典数据id列表
     * @return
     */
    public String[] findCodeByDataIds(Long...ids){
        List<String> list = baseMapper.findCodeByDataIds(ids);
        if(null != list && list.size()>0){
            return list.toArray(new String[list.size()]);
        }
        return new String[0];
    }

    /**
     * 查询字典信息
     * @param code 字典编码
     * @return
     */
    public SysDictionaryDTO findByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",code);
        queryWrapper.last("limit 1");
        SysDictionary sysDictionary = baseMapper.selectOne(queryWrapper);
        if(null == sysDictionary){
            return null;
        }
        return SysDictionaryMapstruct.Instance.sysDictionaryToSysDictionaryDTO(sysDictionary);
    }

}
