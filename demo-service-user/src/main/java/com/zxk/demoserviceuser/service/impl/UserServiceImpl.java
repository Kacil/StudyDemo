package com.zxk.demoserviceuser.service.impl;

import com.zxk.demoserviceuser.dao.UserRepository;
import com.zxk.demoserviceuser.domain.User;
import com.zxk.demoserviceuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUserNameAndAndPassword(String userName, String password) {
        em.clear();
        User user = userRepository.findUserByUserNameAndAndPassword(userName,password);
        return user;
    }

    @Transactional
    @Override
    public User CreateUser(User user) {
        User user1 = userRepository.save(user);
        em.flush();
        return user1;
    }


}
