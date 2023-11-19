package com.microwarp.warden.cloud.gateway.config;

import com.microwarp.warden.cloud.common.core.model.ResultFlux;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * configuration - gateway
 */
@Order(-1)
@Configuration
public class GatewayExceptionHandlerConfig implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable){
        ServerHttpResponse response = exchange.getResponse();
        if(response.isCommitted()){
            return Mono.error(throwable);
        }
        ResultModel resultModel = ResultModel.error(throwable.getMessage());
        return ResultFlux.responseWrite(response,resultModel);
    }
}
