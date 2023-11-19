package com.microwarp.warden.cloud.common.core.pageing;

/**
 * 基本排序参数
 * @author zhouwenqi
 */
public interface BaseSortDTO {
    Long getDragId();
    Long getParentId();
    Long[] getIds();
}
