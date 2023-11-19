package com.microwarp.warden.cloud.service.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.microwarp.warden.cloud.common.core.exception.WardenParamterErrorException;
import com.microwarp.warden.cloud.common.core.pageing.BaseSortDTO;
import com.microwarp.warden.cloud.common.database.domain.BaseServiceImpl;
import com.microwarp.warden.cloud.common.redis.DataCacheableService;
import com.microwarp.warden.cloud.common.redis.constant.CacheKeys;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDictionaryDataDTO;
import com.microwarp.warden.cloud.service.system.dao.SysDictionaryDao;
import com.microwarp.warden.cloud.service.system.dao.SysDictionaryDataDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysDictionaryDataMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionary;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDictionaryData;
import com.microwarp.warden.cloud.service.system.service.SysDictionaryDataService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * service - 字典数据 - impl
 * @author zhouwenqi
 */
@Service
public class SysDictionaryDataServiceImpl extends BaseServiceImpl<SysDictionaryData,SysDictionaryDataDao> implements SysDictionaryDataService {
    @Resource
    private SysDictionaryDao sysDictionaryDao;
    @Resource
    private DataCacheableService iCacheService;

    /**
     * 查询字典数据
     * @param id 字典数据id
     * @return
     */
    public SysDictionaryDataDTO findById(Long id){
        return this.dao.findById(id);
    }

    /**
     * 创建字典数据
     * @param sysDictionaryDataDTO 字典数据
     * @return
     */
    public SysDictionaryDataDTO create(SysDictionaryDataDTO sysDictionaryDataDTO){
        QueryWrapper<SysDictionaryData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id",sysDictionaryDataDTO.getDictId());
        queryWrapper.eq("data_key",sysDictionaryDataDTO.getDataKey());
        if(this.dao.count(queryWrapper) > 0){
            throw new WardenParamterErrorException("字典数据key不能重复");
        }
        SysDictionaryData sysDictionaryData = SysDictionaryDataMapstruct.Instance.sysDictionaryDataDtoToSysDictionaryData(sysDictionaryDataDTO);
        this.dao.save(sysDictionaryData);

        // 手动删除缓存
        String[] dictCodes = sysDictionaryDao.findCodeByDataIds(sysDictionaryData.getId());
        if(dictCodes.length> 0){
            iCacheService.batchRemove(CacheKeys.DATA_DICT_DATAS, dictCodes);
        }

        return findById(sysDictionaryData.getId());
    }

    /**
     * 更新字典数据
     * @param sysDictionaryDataDTO 字典数据
     */
    public void update(SysDictionaryDataDTO sysDictionaryDataDTO){
        if(null == sysDictionaryDataDTO.getId()){
            return;
        }
        SysDictionaryData dictionaryData = this.dao.getById(sysDictionaryDataDTO.getId());
        if(null == dictionaryData){
            throw new WardenParamterErrorException("字典数据不存在");
        }
        QueryWrapper<SysDictionaryData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id",sysDictionaryDataDTO.getDictId());
        queryWrapper.eq("data_key",sysDictionaryDataDTO.getDataKey());
        queryWrapper.ne("id",sysDictionaryDataDTO.getId());
        if(this.dao.count(queryWrapper) > 0){
            throw new WardenParamterErrorException("字典数据key不能重复");
        }
        SysDictionaryData sysDictionaryData = SysDictionaryDataMapstruct.Instance.sysDictionaryDataDtoToSysDictionaryData(sysDictionaryDataDTO);
        this.dao.updateById(sysDictionaryData);
        // 手动删除缓存
        String[] dictCodes = sysDictionaryDao.findCodeByDataIds(sysDictionaryData.getId());
        if(dictCodes.length> 0){
            iCacheService.batchRemove(CacheKeys.DATA_DICT_DATAS, dictCodes);
        }
    }

    /**
     * 字典项数据拖拽排序
     * @param baseSortDTO 排序参数
     */
    @Override
    @Transactional
    public void dragAndSort(BaseSortDTO baseSortDTO){
        if(null != baseSortDTO.getIds() && baseSortDTO.getIds().length > 0){
            int i = 0;
            for(Long id:baseSortDTO.getIds()){
                UpdateWrapper<SysDictionaryData> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("orders",i);
                updateWrapper.eq("id",id);
                this.dao.update(updateWrapper);
                i ++;
            }
        }
    }

    /**
     * 删除字典数据
     * @param id 字典数据id
     */
    public void delete(Long...id){
        if(null == id || id.length < 1){
            return;
        }
        // 手动删除缓存
        String[] dictCodes = sysDictionaryDao.findCodeByDataIds(id);
        if(dictCodes.length> 0){
            iCacheService.batchRemove(CacheKeys.DATA_DICT_DATAS, dictCodes);
        }
        this.dao.removeBatchByIds(Arrays.asList(id));
    }

    /**
     * 获取字典数据列表
     * @param dictId 字典id
     * @return
     */
    public List<SysDictionaryDataDTO> findByDictId(Long dictId){
        QueryWrapper<SysDictionaryData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id",dictId);
        queryWrapper.orderByAsc("orders");
        List<SysDictionaryData> list = this.dao.list(queryWrapper);
        return null == list || list.size() < 1 ? new ArrayList<>() : SysDictionaryDataMapstruct.Instance.sysDictionaryDatasToSysDictionaryDatasDTO(list);
    }

    /**
     * 查询字典数据(过滤禁用数据)
     * @param code 字典编码
     * @return
     */
    @Cacheable(value = CacheKeys.DATA_DICT_DATAS, key="#code", unless = "#result.size() < 1")
    public List<SysDictionaryDataDTO> findByDictCode(String code){
        SysDictionaryDTO sysDictionaryDTO = sysDictionaryDao.findByCode(code);
        if(null == sysDictionaryDTO || sysDictionaryDTO.getDisabled()){
            return new ArrayList<>();
        }
        QueryWrapper<SysDictionaryData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id",sysDictionaryDTO.getId());
        queryWrapper.eq("disabled",false);
        queryWrapper.orderByAsc("orders");
        List<SysDictionaryData> list = this.dao.list(queryWrapper);
        return null == list || list.size() < 1 ? new ArrayList<>() : SysDictionaryDataMapstruct.Instance.sysDictionaryDatasToSysDictionaryDatasDTO(list);
    }
}
