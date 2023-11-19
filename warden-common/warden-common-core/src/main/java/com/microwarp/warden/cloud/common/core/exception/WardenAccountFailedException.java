package com.microwarp.warden.cloud.common.core.exception;

import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;

/**
 * exception - 帐号异常
 * @author zhouwenqi
 */
public class WardenAccountFailedException extends WardenException {
    public WardenAccountFailedException(){
        super(new ResultModel(ResultCode.ACCOUNT_FAILED));
    }
    public WardenAccountFailedException(String message){
        super(new ResultModel(ResultCode.ACCOUNT_FAILED.getCode(),message));
    }
}
