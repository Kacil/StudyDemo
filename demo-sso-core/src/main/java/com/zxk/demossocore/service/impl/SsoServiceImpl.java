package com.zxk.demossocore.service.impl;

import com.zxk.democommonservice.feigin.user.FeiginUserService;
import com.zxk.demossocore.conf.CommonConstants;
import com.zxk.demossocore.conf.ResultWrap;
import com.zxk.demossocore.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SsoServiceImpl implements SsoService {

    @Autowired
    private FeiginUserService feiginUserService;

    @Override
    public Object checkUserLogin(String userName, String password, String smsCode) {
        if (userName == null || userName.trim().length() == 0) {
            return ResultWrap.init(CommonConstants.FALIED, "请输入用户名", null);
        }
        if (password == null || password.trim().length() == 0) {
            return ResultWrap.init(CommonConstants.FALIED, "请输入密码", null);
        }

        return feiginUserService.login(userName, password);
    }
}
