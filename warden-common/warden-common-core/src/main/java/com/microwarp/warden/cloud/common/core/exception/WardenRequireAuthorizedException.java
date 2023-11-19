package com.microwarp.warden.cloud.common.core.exception;


import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;

/**
 * exception - 权限不足异常
 * @author zhouwenqi
 */
public class WardenRequireAuthorizedException extends WardenException {
    public WardenRequireAuthorizedException(){
        super(new ResultModel(ResultCode.REQUIRE_AUTHORIZED));
    }
    public WardenRequireAuthorizedException(String message){
        super(new ResultModel(ResultCode.REQUIRE_AUTHORIZED.getCode(),message));
    }
}
