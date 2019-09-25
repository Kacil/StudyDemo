package com.zxk.demossocore.conf;


/**
 * conf
 *
 */
public class Conf {

    /**
     * sso sessionid, between browser and sso-server (web + token client)
     */
//    public static final String SSO_SESSIONID = "xxl_sso_sessionid";
    public static final String SSO_SESSIONID = "accessToken";

    /**
     * redirect url (web client)
     */
    public static final String REDIRECT_URL = "redirect_url";

    /**
     * sso user, request attribute (web client)
     */
    public static final String SSO_USER = "xxl_sso_user";


    /**
     * sso server address (web + token client)
     */
    public static final String SSO_SERVER = "sso_server";

    /**
     * login url, server relative path (web client)
     */
    public static final String SSO_LOGIN = "/v1.0/sso/weblogin";
    /**
     * logout url, server relative path (web client)
     */
    public static final String SSO_LOGOUT = "/v1.0/sso/weblogout";


    /**
     * logout path, client relatice path
     */
    public static final String SSO_LOGOUT_PATH = "SSO_LOGOUT_PATH";

    /**
     * excluded paths, client relatice path, include path can be set by "filter-mapping"
     */
    public static final String SSO_EXCLUDED_PATHS = "SSO_EXCLUDED_PATHS";


    /**
     * login fail result
     */
//    public static final ReturnT<String> SSO_LOGIN_FAIL_RESULT = new ReturnT(501, "sso not login.");

    /**login send sms
     *
     * demo:sso:sms:${phone}:${brandId} = ${smsCode}
     * */
    public static final String SMS_REDIS_KEY_PRIFIX = "demo:sso:sms:";


}
