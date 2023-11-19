package com.microwarp.warden.cloud.common.core.exception;


import com.microwarp.warden.cloud.common.core.model.IResultModel;
import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;

/**
 * exception - 基本异常
 * @author zhouwenqi
 */
public class WardenException extends RuntimeException implements IWardenException {
    private ResultModel resultModel = new ResultModel(ResultCode.ERROR);
    public WardenException(){
        super();
    }
    public WardenException(IResultModel iResultModel){
        super();
        resultModel = new ResultModel(iResultModel);
    }
    public WardenException(ResultCode resultCode){
        super();
        resultModel = new ResultModel(resultCode);
    }
    public WardenException(String message){
        super(message);
        resultModel.setMsg(message);
    }
    public WardenException(int code,String message){
        super(message);
        resultModel = new ResultModel(code,message);
    }
    public ResultModel getResultModel(){
        return resultModel;
    }
}
