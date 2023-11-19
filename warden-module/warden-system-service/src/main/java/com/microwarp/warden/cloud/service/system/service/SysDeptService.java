package com.microwarp.warden.cloud.service.system.service;

import com.microwarp.warden.cloud.common.core.pageing.BaseSortDTO;
import com.microwarp.warden.cloud.common.core.pageing.ISearchPageable;
import com.microwarp.warden.cloud.common.core.pageing.ResultPage;
import com.microwarp.warden.cloud.common.database.domain.BaseService;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptSearchDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptTreeDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDept;

import java.util.List;

/**
 * service - 部门
 * @author zhouwenqi
 */
public interface SysDeptService extends BaseService<SysDept> {
    /**
     * 查询部门信息
     * @param id 部门id
     * @return
     */
    SysDeptDTO findById(Long id);

    /**
     * 获取部门信息(含子部门列表)
     * @param id
     * @return
     */
    SysDeptTreeDTO findChildrenById(Long id);

    /**
     * 获取部门树型结构
     * @return 树型数据
     */
    List<SysDeptTreeDTO> findTrees();
    /**
     * 创建部门
     * @param sysDeptDTO 部门信息
     * @return
     */
    SysDeptDTO create(SysDeptDTO sysDeptDTO);
    /**
     * 更新部门信息
     * @param sysDeptDTO 部门id
     */
    void update(SysDeptDTO sysDeptDTO);

    /**
     * 部门拖曳和排序
     * @param baseSortDTO 排序数据
     */
    void dragAndSort(BaseSortDTO baseSortDTO);
    /**
     * 删除部门信息
     * @param id 部门id
     */
    void delete(Long...id);

    /**
     * 递归获取所有部门ID
     * @param ids 下级部门ID列表
     * @param deptIds 部门ID平铺列表
     * @return
     */
    void recursionIds(List<Long> ids,List<Long> deptIds);
    /**
     * 分页查询部门信息
     * @param iSearchPageable 查询参数
     * @return
     */
    ResultPage<SysDeptDTO> findPage(ISearchPageable<SysDeptSearchDTO> iSearchPageable);
}
