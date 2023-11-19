package com.microwarp.warden.cloud.common.core.pageing;

/**
 * interface - 分页信息
 */
public interface IPageInfo {
    long getCurrent();
    long getPageCount();
    long getTotal();
    long getPageSize();
}
