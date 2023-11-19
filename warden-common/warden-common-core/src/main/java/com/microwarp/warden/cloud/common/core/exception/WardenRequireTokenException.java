package com.microwarp.warden.cloud.common.core.exception;


import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;

/**
 * exception - 缺少Token凭据异常
 * @author zhouwenqi
 */
public class WardenRequireTokenException extends WardenException {
    public WardenRequireTokenException(){
        super(new ResultModel(ResultCode.REQUIRE_TOKEN));
    }
    public WardenRequireTokenException(String message){
        super(new ResultModel(ResultCode.REQUIRE_TOKEN.getCode(),message));
    }
}
