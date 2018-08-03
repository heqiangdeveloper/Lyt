package com.cimcitech.lyt.bean.login;

/**
 * Created by cimcitech on 2017/7/31.
 */

public class LoginVo {
    /*
    *
    * {
        "code": 0,
        "data": {
            "userId": 13464,
            "realname": "系统管理员",
            "token": "2FA56C85-C3CA-4897-891B-4FE41A4E07E8"
        },
        "msg": "登录成功！",
        "success": true
       }
     */

    private int code;
    private String msg;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
