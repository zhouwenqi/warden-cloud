package com.microwarp.warden.cloud.common.core.exception;

import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;

/**
 * exception - Token凭据异常
 * 校验失败或已过期
 * @author zhouwenqi
 */
public class WardenTokenErrorException extends WardenException {
    public WardenTokenErrorException(){
        super(new ResultModel(ResultCode.ERROR_TOKEN));
    }
    public WardenTokenErrorException(String message){
        super(new ResultModel(ResultCode.ERROR_TOKEN.getCode(),message));
    }
}
