package com.zxk.demozipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.internal.EnableZipkinServer;

/**
 * zipkin服务链追踪服务
 * http://localhost:8762/zipkin/
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public class DemoZipkinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoZipkinServerApplication.class, args);
    }

}
