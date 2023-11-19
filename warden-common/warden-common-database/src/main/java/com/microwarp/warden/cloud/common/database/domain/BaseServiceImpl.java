package com.microwarp.warden.cloud.common.database.domain;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * service - 基类服务 - impl
 * @author zhouwenqi
 */
public class BaseServiceImpl<T,D extends BaseDao<T>> implements BaseService<T> {
    @Autowired
    protected D dao;
}
