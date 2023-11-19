package com.microwarp.warden.cloud.facade.system.domain.dto;

import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;

/**
 * dto - 系统用户过滤
 * @author zhouwenqi
 */
public class SysUserSearchDTO extends BasicSearchDTO {
    private Long deptId;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
