package com.zxk.demossocore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@ServletComponentScan
@EnableFeignClients(basePackages = "com.zxk.democommonservice.feigin")
public class DemoSsoCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSsoCoreApplication.class, args);
    }

}
