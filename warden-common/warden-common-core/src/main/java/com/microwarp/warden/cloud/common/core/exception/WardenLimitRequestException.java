package com.microwarp.warden.cloud.common.core.exception;

import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;

/**
 * exception - 限流或熔断异常
 * @author zhouwenqi
 */
public class WardenLimitRequestException extends WardenException {
    public WardenLimitRequestException(){
        super(new ResultModel(ResultCode.LIMIT_REQUEST));
    }
    public WardenLimitRequestException(String message){
        super(new ResultModel(ResultCode.ERROR_TOKEN.getCode(),message));
    }
}
