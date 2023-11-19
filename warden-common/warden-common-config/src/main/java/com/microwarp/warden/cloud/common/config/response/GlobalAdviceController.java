package com.microwarp.warden.cloud.common.config.response;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.exception.WardenException;
import com.microwarp.warden.cloud.common.core.model.ResponseResult;
import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultError;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import feign.FeignException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器异常处理
 * @author zhouwenqi
 * @version 1.0.0
 */
@ControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler(WardenException.class)
    public void wardenExceptionHandler(WardenException exception, HttpServletRequest request, HttpServletResponse response){
        ResultModel resultModel = exception.getResultModel();
        response.setStatus(resultModel.getCode());
        ResponseResult.output(resultModel,response);
    }

    /**
     * body 数据为空异常重新包装
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception, HttpServletRequest request, HttpServletResponse response){
        ResultModel resultModel = new ResultModel(ResultCode.ERROR_PARAMETER);
        response.setStatus(resultModel.getCode());
        ResponseResult.output(resultModel,response);
    }

    /**
     *
     * @param exception
     * @param request
     * @param response
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void missingServletRequestParameterException(MissingServletRequestParameterException exception, HttpServletRequest request, HttpServletResponse response){
        ResultModel resultModel = new ResultModel(ResultCode.REQUIRE_PARAMETER,exception.getMessage());
        response.setStatus(resultModel.getCode());
        ResponseResult.output(resultModel,response);
    }

    /**
     * 参数校验异常
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(BindException.class)
    public void bindExceptionHandler(BindException exception, HttpServletRequest request, HttpServletResponse response){
        ResultError resultError = new ResultError(exception.getFieldErrors());
        ResultModel resultModel = new ResultModel(ResultCode.ERROR_PARAMETER,resultError.defaultMessage());
        resultModel.addData(HttpConstant.RESULT_VALID_KEY, resultError.getVaildFields());
        response.setStatus(resultModel.getCode());
        ResponseResult.output(resultModel,response);
    }

    @ExceptionHandler(FeignException.class)
    public void feignException(FeignException exception, HttpServletRequest request, HttpServletResponse response){
        ResultModel resultModel = new ResultModel(ResultCode.ERROR,exception.getMessage());
        int status = response.getStatus();
        resultModel.setCode(status);
        ResponseResult.output(resultModel,response);
    }

}
