package com.cimcitech.lyt.bean.bargemanage;

/**
 * Created by qianghe on 2018/8/6.
 */

public class BargeVo {
    private String msg;
    private boolean success;
    private BargeData data;

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

    public BargeData getData() {
        return data;
    }

    public void setData(BargeData data) {
        this.data = data;
    }
}
