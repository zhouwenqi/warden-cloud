package com.microwarp.warden.cloud.common.core.exception;

import com.microwarp.warden.cloud.common.core.model.IResultModel;
import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;

/**
 * exception - 需要登录异常
 * @author zhouwenqi
 */
public class WardenRequireLoginException extends WardenException {
    public WardenRequireLoginException(){
        super(new ResultModel(ResultCode.REQUIRE_LOGIN));
    }
    public WardenRequireLoginException(IResultModel iResultModel){
        super(iResultModel);
    }
    public WardenRequireLoginException(String message){
        super(new ResultModel(ResultCode.REQUIRE_LOGIN));
        this.getResultModel().setMsg(message);
    }
}
