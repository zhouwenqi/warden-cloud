package com.microwarp.warden.cloud.common.core.exception;

import com.google.common.io.CharStreams;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.util.JsonUtil;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.nio.charset.Charset;

/**
 * exception - feign客户端请求异常处理
 * @author zhouwenqi
 */
public class FeignExceptionDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response){
        ResultModel resultModel = ResultModel.error();
        try {
            if(null != response.body()) {
                String body = CharStreams.toString(response.body().asReader(Charset.forName("UTF-8")));
                resultModel = JsonUtil.jsonToObject(body, ResultModel.class);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        // 不让code == 200，统一Warden返回状态机制
        int code = response.status() == 200 ? resultModel.getCode() : response.status();
        resultModel.setCode(code);
        return new WardenException(resultModel.getCode(),resultModel.getMsg());
    }
}
