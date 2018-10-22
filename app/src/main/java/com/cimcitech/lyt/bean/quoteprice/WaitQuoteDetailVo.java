package com.cimcitech.lyt.bean.quoteprice;

import java.util.List;

/**
 * Created by qianghe on 2018/10/9.
 */

public class WaitQuoteDetailVo {
    /*
    *     {
          "code": 0,
          "data": [
            {
              "confirmton": 1500,
              "detailid": 2,
              "endPortName": "上海",
              "fstatus": "1",
              "issueUserName": "雷丰林",
              "issuetime": "2018-10-16",
              "planarrivedate": "2018-02-15",
              "planloadingdate": "2018-02-05",
              "startPortName": "佛山港",
              "totalton": 2000,
              "transportreqid": 3,
              "updateUserName": "雷丰林",
              "updatetime": "2018-10-16",
              "varietyName": "大麦"
            },
            {
              "confirmton": 1800,
              "detailid": 102,
              "endPortName": "台湾",
              "fstatus": "1",
              "issueUserName": "齐国仓",
              "issuetime": "2018-10-15",
              "planarrivedate": "2018-10-15",
              "planloadingdate": "2018-10-15",
              "startPortName": "台湾",
              "totalton": 11800,
              "transportreqid": 3,
              "updateUserName": "齐国仓",
              "updatetime": "2018-10-15",
              "varietyName": "小麦"
            }
          ],
          "msg": "",
          "success": true
        }
     */
    private List<WaitQuoteDetailListBean> data;
    private String msg;
    private boolean success;

    public List<WaitQuoteDetailListBean> getData() {
        return data;
    }

    public void setData(List<WaitQuoteDetailListBean> data) {
        this.data = data;
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
