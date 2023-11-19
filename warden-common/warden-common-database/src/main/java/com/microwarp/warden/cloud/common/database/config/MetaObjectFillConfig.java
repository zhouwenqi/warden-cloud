package com.microwarp.warden.cloud.common.database.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * config - mybatis plus 数据库写操作自动填充
 * @author zhouwenqi
 */
@Component
public class MetaObjectFillConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject){
        this.strictInsertFill(metaObject,"createDate",Date.class,new Date());
        this.strictInsertFill(metaObject,"updateDate",Date.class,new Date());
    }
    @Override
    public void updateFill(MetaObject metaObject){
        this.strictUpdateFill(metaObject,"updateDate",Date.class,new Date());
    }
}
