package com.microwarp.warden.cloud.common.core.model;

import java.util.Map;

/**
 * interface - warden统一返回数据模型
 * @author zhouwenqi
 */
public interface IResultModel {
    int getCode();
    String getMsg();
    Map<String,Object> getData();
}
