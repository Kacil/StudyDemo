package com.zxk.democonfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * config配置中心
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class DemoConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoConfigServerApplication.class, args);
    }

}
