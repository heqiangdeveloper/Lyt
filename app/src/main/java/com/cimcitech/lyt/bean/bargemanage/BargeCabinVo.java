package com.cimcitech.lyt.bean.bargemanage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qianghe on 2018/8/6.
 */

public class BargeCabinVo implements Serializable{
    private String msg;
    private boolean success;
    private List<BargeCabinData> data;

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

    public List<BargeCabinData> getData() {
        return data;
    }

    public void setData(List<BargeCabinData> data) {
        this.data = data;
    }
}
