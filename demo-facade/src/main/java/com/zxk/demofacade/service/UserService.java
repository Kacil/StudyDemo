package com.zxk.demofacade.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value="demo-service-user")
public interface UserService {

    @RequestMapping(value = "/v1.0/service/user/login", method = RequestMethod.POST)
    public Object login(@RequestParam(value = "userName") String userName,
                        @RequestParam(value = "password") String password);

    @RequestMapping(value = "/v1.0/service/user/reg", method = RequestMethod.POST)
    public Object reg(@RequestParam(value = "userName") String userName,
                      @RequestParam(value = "password") String password);

}
