package com.microwarp.warden.cloud.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.util.JsonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * configuration - sentinel限流结果处理
 * @author zhouwenqi
 */
@Configuration
public class SentinelLimitRequestHandlerConfig {
    public SentinelLimitRequestHandlerConfig(){
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                ResultModel resultModel = new ResultModel(ResultCode.LIMIT_REQUEST, "服务器压力过大，请稍候访问");
                String json = JsonUtil.objectToJson(resultModel);
                return ServerResponse.status(resultModel.getCode()).body(Mono.just(json),String.class);
            }
        });
    }
}
