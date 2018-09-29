package com.cimcitech.lyt.bean.register;

/**
 * Created by qianghe on 2018/9/25.
 */

public class VerificationCodeReq {
    private String accountno;

    public VerificationCodeReq(String accountno) {
        this.accountno = accountno;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }
}
