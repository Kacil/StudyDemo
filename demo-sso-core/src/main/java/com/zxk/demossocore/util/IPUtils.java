package com.zxk.demossocore.util;

/**
 * @author zhangchaofeng
 * @date 2019/6/1
 * @description 小程序获取不到mac地址，所以小程序传ip过来 其他的应用传mac地址
 */
public class IPUtils {

    public static String getMacStr(String mac) {
        String res="";
        if (mac!=null && mac != "" && mac.indexOf("-") != -1) {
            mac = mac.replaceAll("-","");
            return mac.substring(mac.length()-4,mac.length());
        } else if(mac!=null && mac != "" && mac.indexOf(".") != -1){
            return mac.substring(mac.lastIndexOf(".")+1,mac.length());
        }
        return res;
    }


}
