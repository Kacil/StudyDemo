package com.zxk.demofacade.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="demo-service-hi")
public interface TestService {

    @RequestMapping(value="/v1.0/service/hi", method = RequestMethod.GET)
    public String home(@RequestParam(value = "name") String name);

}
