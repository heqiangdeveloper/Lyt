package com.cimcitech.lyt.bean.register;

/**
 * Created by cimcitech on 2017/7/31.
 */
//解析验证码的bean
public class VerificationCodeVo {
    private String msg;
    private boolean success;

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
