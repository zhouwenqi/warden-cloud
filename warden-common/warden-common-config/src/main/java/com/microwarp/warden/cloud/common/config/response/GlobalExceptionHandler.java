package com.microwarp.warden.cloud.common.config.response;

import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 非控制器异常处理
 * @author zhouwenqi
 */
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@Controller
public class GlobalExceptionHandler extends BasicErrorController {
    @Autowired
    public GlobalExceptionHandler(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Override
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus httpStatus = this.getStatus(request);
        ResultModel resultModel = ResultModel.error();

        if(!ResultUtil.isResultCode(httpStatus.value()) || httpStatus.value() == HttpStatus.INTERNAL_SERVER_ERROR.value()){
            resultModel.setCode(httpStatus.value());
            Map<String, Object> body = this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.ALL));
            resultModel.setMsg(body.get("error").toString());
        }
        return ResponseEntity.status(httpStatus.value()).body(ResultUtil.getEntityMap(resultModel));
    }
}
