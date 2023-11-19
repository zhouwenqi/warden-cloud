package com.microwarp.warden.cloud.common.security.authenticator;

import com.microwarp.warden.cloud.common.core.model.ResponseResult;
import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security - 没有权限句柄
 * @author zhouwenqi
 */
public class WardenAccessDeninedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(WardenAccessDeninedHandler.class);
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        logger.warn("access denied {}", e.getMessage());
        ResultModel resultModel = new ResultModel(ResultCode.REQUIRE_AUTHORIZED);
        response.setStatus(resultModel.getCode());
        ResponseResult.output(resultModel,response);
    }
}
