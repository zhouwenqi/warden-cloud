package com.microwarp.warden.cloud.service.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * application - 后台系统服务
 * @author zhouwenqi
 */
@MapperScan(basePackages = {"com.microwarp.warden.cloud.service.system.mapper"})
@SpringBootApplication
public class WardenSystemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WardenSystemServiceApplication.class,args);
    }
}
