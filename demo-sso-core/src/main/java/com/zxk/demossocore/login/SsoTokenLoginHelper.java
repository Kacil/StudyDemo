package com.zxk.demossocore.login;


import com.zxk.demossocore.conf.Conf;
import com.zxk.demossocore.dto.SsoUser;
import com.zxk.demossocore.store.SsoSessionIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangchaofeng
 */
@Component
@Slf4j
public class SsoTokenLoginHelper {

    /**
     * client login
     *
     * @param sessionId
     * @param xxlUser
     */
    public  void login(String sessionId, SsoUser xxlUser) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }
//        ssoLoginStore.put(storeKey, xxlUser);
    }

    /**
     * client logout
     *
     * @param sessionId
     */
    public void logout(String sessionId) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return;
        }
//        ssoLoginStore.remove(storeKey);
    }
    /**
     * client logout
     *
     * @param request
     */
    public void logout(HttpServletRequest request) {
        String headerSessionId = request.getHeader(Conf.SSO_SESSIONID);
        logout(headerSessionId);
    }


    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public SsoUser loginCheck(String  sessionId){

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return null;
        }
        log.info("==================storeKey=[{}]",storeKey);
//        SsoUser xxlUser = ssoLoginStore.get(storeKey);
//        if (xxlUser != null) {
//            log.info("===================loginCheck查到User：User=[{}]",xxlUser.toString());
//
//            // After the expiration time has passed half, Auto refresh
//            if ((System.currentTimeMillis() - xxlUser.getExpireFreshTime()) > xxlUser.getExpireMinite()/2) {
//                xxlUser.setExpireFreshTime(System.currentTimeMillis());
//                ssoLoginStore.put(storeKey, xxlUser);
//            }
//
//            return xxlUser;
//        }
        log.info("==========================loginCheck未查到User===================");
        return null;
    }


    /**
     * login check
     *
     * @param request
     * @return
     */
    public SsoUser loginCheck(HttpServletRequest request){
        String headerSessionId = request.getHeader(Conf.SSO_SESSIONID);
        log.info("======================Token=[{}]",headerSessionId);
        return loginCheck(headerSessionId);
    }

    public SsoUser loginCheckSessionId(String headerSessionId){
//        String headerSessionId = request.getHeader(Conf.SSO_SESSIONID);
        log.info("======================NewToken=[{}]",headerSessionId);
        if(headerSessionId == null) {
            return null;
        }
        return loginCheck(headerSessionId);
    }



}
