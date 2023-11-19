package com.microwarp.warden.cloud.service.system.dao;

import com.microwarp.warden.cloud.common.database.domain.BaseDao;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptTreeDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDept;

import java.util.List;

/**
 * dao - 部门
 * @author zhouwenqi
 */
public interface SysDeptDao extends BaseDao<SysDept> {
    /**
     * 查询部门信息
     * @param id 部门ID
     * @return
     */
    SysDeptDTO findById(Long id);
    /**
     * 查询子部门列表
     * @param parentId 上级部门ID
     * @return
     */
    List<SysDeptTreeDTO> findByParentId(Long parentId);
}
