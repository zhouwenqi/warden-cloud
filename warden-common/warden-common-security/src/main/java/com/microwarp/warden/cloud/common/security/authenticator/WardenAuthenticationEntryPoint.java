package com.microwarp.warden.cloud.common.security.authenticator;

import com.microwarp.warden.cloud.common.core.model.ResponseResult;
import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security 没有凭据入口
 * @author zhouwenqi
 *
 */
public class WardenAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(WardenAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException,ServletException {
        logger.error("entry-point:{}",exception.getMessage());
        ResultModel resultModel = new ResultModel(ResultCode.REQUIRE_TOKEN);
        response.setStatus(resultModel.getCode());
        ResponseResult.output(resultModel,response);
    }
}
