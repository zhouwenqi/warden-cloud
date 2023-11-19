package com.microwarp.warden.cloud.common.core.model;

import com.microwarp.warden.cloud.common.core.util.JsonUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * flux - 返回包装
 */
public class ResultFlux {
    public static Mono<Void> responseWrite(ServerHttpResponse response, IResultModel iResultModel){
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setRawStatusCode(iResultModel.getCode());
        DataBuffer dataBuffer = response.bufferFactory().wrap(JsonUtil.objectToJson(iResultModel).getBytes());
        return response.writeWith(Mono.just(dataBuffer));

    }
}
