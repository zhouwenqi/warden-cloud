package com.microwarp.warden.cloud.service.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * application - 用户服务
 * @author zhouwenqi
 */
@MapperScan(basePackages = {"com.microwarp.warden.cloud.service.user.mapper"})
@SpringBootApplication
public class WardenUserServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(WardenUserServiceApplication.class,args);
    }
}
