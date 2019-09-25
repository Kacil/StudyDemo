package com.zxk.demossocore.dto;

import java.io.Serializable;

/**
 * 存储对象信息类
 */
public class SsoUser implements Serializable {

    private static final long serialVersionUID = -5412238852975269093L;

    private String userId;
    private String userName;
    private Object object;

    //过期时间
    private int expireMinite;
    //刷新时间
    private long expireFreshTime;

    public SsoUser(String userId, String userName, Object object, int expireMinite, long expireFreshTime) {
        this.userId = userId;
        this.userName = userName;
        this.object = object;
        this.expireMinite = expireMinite;
        this.expireFreshTime = expireFreshTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getExpireMinite() {
        return expireMinite;
    }

    public void setExpireMinite(int expireMinite) {
        this.expireMinite = expireMinite;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }

    @Override
    public String toString() {
        return "SsoUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", object=" + object +
                ", expireMinite=" + expireMinite +
                ", expireFreshTime=" + expireFreshTime +
                '}';
    }
}
