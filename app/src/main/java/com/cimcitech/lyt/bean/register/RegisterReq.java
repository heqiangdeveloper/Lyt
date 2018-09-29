package com.cimcitech.lyt.bean.register;

/**
 * Created by qianghe on 2018/9/25.
 */

public class RegisterReq {
    private RegisterAccountBean account;
    private RegisterAdminerBean adminer;

    public RegisterReq(RegisterAccountBean account, RegisterAdminerBean adminer) {
        this.account = account;
        this.adminer = adminer;
    }
}
