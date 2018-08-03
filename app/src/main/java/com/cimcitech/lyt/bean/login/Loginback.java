package com.cimcitech.lyt.bean.login;

/**
 * Created by lyw on 2017/7/26.
 */

public class Loginback {

    /**
     * userId : 2
     * token : 2FD08ED0-E53B-48B1-B8E6-E6B4290A2770
     */
    private Long userId;
    private String realname;
    private String token;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}