package com.zxk.demofacade.controller;

import com.zxk.democommonservice.feigin.user.FeiginUserService;
import com.zxk.demofacade.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Api("User服务接口")
public class UserController {
    /**
     * 暂时废弃此类型该注入，使用demo-common-service 打包类
     * 需修改 @EnableFeignClients(basePackages = "com.zxk.demofacade.service")
     * **/
//    @Autowired
//    private UserService userService;

    //demo-common-service 打包类
    @Autowired
    private FeiginUserService feiginUserService;

    @ApiOperation(value = "登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/v1.0/facade/user/login", method = RequestMethod.POST)
    public @ResponseBody
    Object login(@RequestParam(value = "userName") String userName,
                 @RequestParam(value = "password") String password) {

        return feiginUserService.login(userName, password);
    }

    @ApiOperation(value = "注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/v1.0/facade/user/reg", method = RequestMethod.POST)
    public@ResponseBody Object reg(@RequestParam(value = "userName") String userName,
                        @RequestParam(value = "password") String password) {

        return feiginUserService.reg(userName, password);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/v1.0/facade/user/loginpage")
    public String loginpage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        return "loginpage";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/v1.0/facade/user/loginsuccess")
    public String loginsuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        return "loginsuccess";
    }

}
