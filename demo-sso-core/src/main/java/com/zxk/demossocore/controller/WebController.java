package com.zxk.demossocore.controller;

import com.zxk.demossocore.dto.SsoUser;
import com.zxk.demossocore.login.SsoWebLoginHelper;
import com.zxk.demossocore.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WebController {

    @Autowired
    private SsoService ssoService;
    @Autowired
    SsoWebLoginHelper ssoWebLoginHelper;

    @RequestMapping("/v1.0/sso/")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        // login check
        SsoUser xxlUser = ssoWebLoginHelper.loginCheck(request, response);

        if (xxlUser == null) {
            return "redirect:/v2.0/sso/web-login";
        } else {
            request.setAttribute("xxlUser", xxlUser);
            return "index";
        }
    }
}
