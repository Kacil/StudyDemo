package com.zxk.demossocore.store;


import com.zxk.demossocore.conf.SsoUserConstants;
import com.zxk.demossocore.dto.SsoUser;
import com.zxk.demossocore.jwt.JWTTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * make client sessionId
 *
 *      client: cookie = [userid#version]
 *      server: redis
 *                  key = [userid]
 *                  value = user (user.version, valid this)
 *
 * //   group         The same group shares the login status, Different groups will not interact
 *
 * @author zhangchaofeng
 */

public class SsoSessionIdHelper {

    private static Logger log = LoggerFactory.getLogger(SsoSessionIdHelper.class);
    /**
     * make client sessionId
     *
     * @param ssoUser
     * @return
     */
    public static String makeSessionId(SsoUser ssoUser){
//        String sessionId = ssoUser.getUserid().concat("_").concat(ssoUser.getIpLastSegment()).concat("_").concat(ssoUser.getVersion());
        Map<String, Object> map = new HashMap<>();
        map.put(SsoUserConstants.PARMS1, ssoUser.getUserId());
        map.put(SsoUserConstants.PARMS2, ssoUser.getUserName());
        String sessionId = JWTTokenUtil.createToken(map);
        return sessionId;
    }

    /**
     * parse version from sessionId
     *
     * @param sessionId
     * @return
     */
    public static String parseVersion(String sessionId) {
        if (sessionId!=null && sessionId.indexOf("_")>-1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length==3
                    && sessionIdArr[2]!=null
                    && sessionIdArr[2].trim().length()>0) {
                String version = sessionIdArr[2].trim();
                return version;
            }
        }
        return null;
    }

    /**
     * parse storeKey from sessionId
     *
     * @param sessionId
     * @return
     */
    public static String parseStoreKey(String sessionId) {
        try {
            log.info("parseStoreKey sessionId={}", sessionId);
            if (sessionId!=null) {
                String redisKey = "demo_"+sessionId.replaceAll(".", "_");
                return redisKey;
            }
        } catch (Exception e) {
            log.info("parseStoreKey 将SessionID转换成redisKey出错=============");
            log.info(e.getMessage());
            return null;
        }
        return null;
    }

}
