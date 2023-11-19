package com.microwarp.warden.cloud.service.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * application - 订单服务
 * @author zhouwenqi
 */
@MapperScan(basePackages = {"com.microwarp.warden.cloud.service.order.mapper"})
@SpringBootApplication
public class WardenOrderServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(WardenOrderServiceApplication.class,args);
    }
}
