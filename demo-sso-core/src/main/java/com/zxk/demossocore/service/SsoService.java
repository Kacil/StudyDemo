package com.zxk.demossocore.service;

public interface SsoService {

    Object checkUserLogin(String userName, String password, String smsCode);
}
