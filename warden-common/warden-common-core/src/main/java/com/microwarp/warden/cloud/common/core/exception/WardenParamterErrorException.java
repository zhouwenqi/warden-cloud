package com.microwarp.warden.cloud.common.core.exception;


import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;

/**
 * exception - 参数错误异常
 * @author zhouwenqi
 */
public class WardenParamterErrorException extends WardenException {
    public WardenParamterErrorException(){
        super(new ResultModel(ResultCode.ERROR_PARAMETER));
    }
    public WardenParamterErrorException(String message){
        super(new ResultModel(ResultCode.ERROR_PARAMETER.getCode(),message));
    }
}
