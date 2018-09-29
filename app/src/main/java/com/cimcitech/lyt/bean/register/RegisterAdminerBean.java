package com.cimcitech.lyt.bean.register;

/**
 * Created by qianghe on 2018/9/25.
 */

public class RegisterAdminerBean {
    private String adminername;
    private String certificatetype;
    private String certificatenum;

    public RegisterAdminerBean(String adminername, String certificatetype, String certificatenum) {
        this.adminername = adminername;
        this.certificatetype = certificatetype;
        this.certificatenum = certificatenum;
    }
}
