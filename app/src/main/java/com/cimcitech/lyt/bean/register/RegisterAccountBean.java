package com.cimcitech.lyt.bean.register;

/**
 * Created by qianghe on 2018/9/25.
 */

public class RegisterAccountBean {
    private String accountno;//手机号
    private String inputCheckCode;//验证码
    private String username;//用户名
    private String password;

    public RegisterAccountBean(String accountno, String inputCheckCode, String username, String password) {
        this.accountno = accountno;
        this.inputCheckCode = inputCheckCode;
        this.username = username;
        this.password = password;
    }
}
