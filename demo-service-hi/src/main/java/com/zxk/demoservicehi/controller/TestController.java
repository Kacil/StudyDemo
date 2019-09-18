package com.zxk.demoservicehi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RefreshScope
public class TestController {
    @Value("${hello}")
    private String hello;

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String name;


    /**
     * 测试读取参数
     * @return
     */
    @RequestMapping(value="/v1.0/service/hi", method = RequestMethod.GET)
    public String home(@RequestParam(value = "name") String nam) {
        log.info("进入测试方法=================");
        return "Hi "+name+",My name is "+ nam +", I am from port:" +port+"============="+hello;
    }
}
