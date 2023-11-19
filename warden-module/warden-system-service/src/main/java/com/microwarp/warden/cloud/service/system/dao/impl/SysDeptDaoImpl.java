package com.microwarp.warden.cloud.service.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptTreeDTO;
import com.microwarp.warden.cloud.service.system.dao.SysDeptDao;
import com.microwarp.warden.cloud.service.system.domain.convert.SysDeptMapstruct;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDept;
import com.microwarp.warden.cloud.service.system.mapper.SysDeptMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * dao - 部门 - impl
 * @author zhouwenqi
 */
@Repository
public class SysDeptDaoImpl extends BaseDaoImpl<SysDeptMapper,SysDept> implements SysDeptDao {

    /**
     * 查询部门信息
     * @param id 部门ID
     * @return
     */
    public SysDeptDTO findById(Long id){
        SysDept sysDept = this.baseMapper.selectById(id);
        return null != sysDept ? SysDeptMapstruct.Instance.sysDeptToSysDeptDTO(sysDept) : null;
    }

    /**
     * 查询子部门列表
     * @param parentId 上级部门ID
     * @return
     */
    public List<SysDeptTreeDTO> findByParentId(Long parentId){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        queryWrapper.orderByAsc("orders");
        List<SysDept> list = baseMapper.selectList(queryWrapper);
        return null == list ? new ArrayList<>() : SysDeptMapstruct.Instance.sysDeptsToSysDeptTreeDTOs(list);
    }
}
