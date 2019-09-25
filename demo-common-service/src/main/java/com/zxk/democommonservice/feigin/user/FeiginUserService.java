package com.zxk.democommonservice.feigin.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value="demo-service-user")
public interface FeiginUserService {

    @RequestMapping(value = "/v1.0/service/user/login", method = RequestMethod.POST)
    public Object login(@RequestParam(value = "userName") String userName,
                        @RequestParam(value = "password") String password);

    @RequestMapping(value = "/v1.0/service/user/reg", method = RequestMethod.POST)
    public Object reg(@RequestParam(value = "userName") String userName,
                      @RequestParam(value = "password") String password);

}
