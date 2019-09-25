package com.zxk.demoserviceuser.controller;

import com.google.common.collect.Maps;
import com.zxk.demoserviceuser.domain.User;
import com.zxk.demoserviceuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/v1.0/service/user/login", method = RequestMethod.POST)
    public @ResponseBody
    Object login(@RequestParam(value = "userName") String userName,
                 @RequestParam(value = "password") String password) {
        User user = userService.getUserByUserNameAndAndPassword(userName, password);
        if(user == null){
            return returnMap("999999", "该用户暂未注册", "");
        }
        return returnMap("000000", "", "");
    }

    @RequestMapping(value = "/v1.0/service/user/reg", method = RequestMethod.POST)
    public @ResponseBody
    Object reg(@RequestParam(value = "userName") String userName,
                           @RequestParam(value = "password") String password) {
        log.info("进入注册方法====================");
        User user1 = new User();
        user1.setUserName(userName.trim());
        user1.setPassword(password.trim());
        User user = userService.CreateUser(user1);
        if(user == null){
            return returnMap("999999", "注册失败", "");
        }
        return returnMap("000000", "注册成功", "");
    }

//    //登陆页面
//    @RequestMapping(method = RequestMethod.GET, value = "/v1.0/service/user/loginpage")
//    public String loginpage(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        request.setCharacterEncoding("utf-8");
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//        return "loginpage";
//    }
//
//    //登陆成功跳转页面
//    @RequestMapping(method = RequestMethod.GET, value = "/v1.0/service/user/loginsuccess")
//    public String loginsuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        request.setCharacterEncoding("utf-8");
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//        return "loginsuccess";
//    }



    public Map<String, Object> returnMap(String code, String msg, Object data) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("code", code);
        map.put("msg", msg);
        map.put("result", data);
        return map;
    }
}
