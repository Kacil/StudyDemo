package com.zxk.demofacade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 对外暴露接口服务
 * EnableFeignClients 注入调用的FeignClient类
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableFeignClients(basePackages = "com.zxk.democommonservice.feigin")
public class DemoFacadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoFacadeApplication.class, args);
    }

}
