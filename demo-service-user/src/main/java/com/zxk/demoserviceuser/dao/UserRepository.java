package com.zxk.demoserviceuser.dao;

import com.zxk.demoserviceuser.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query("select u from User u where u.userName=:userName and u.password=:password")
    User findUserByUserNameAndAndPassword(@Param("userName") String userName, @Param("password") String password);
}
