package com.zxk.demoserviceuser.service;

import com.zxk.demoserviceuser.domain.User;

public interface UserService {

    User getUserByUserNameAndAndPassword(String userName, String password);

    User CreateUser(User user);
}
