package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/9.
 */

public class WaitQuoteVo {
    /*
    *    "code": 0,
          "data": {
            "endRow": 1,
            "firstPage": 1,
            "hasNextPage": false,
            "hasPreviousPage": false,
            "isFirstPage": true,
            "isLastPage": true,
            "lastPage": 1,
            "list": [
              {
                "account": {
                  "accountid": 2,
                  "username": "测试"
                },
                "accountid": 2,
                "aimcity": "福建",
                "aimport": "福建港",
                "amount": 666,
                "arrivaltime": "2018-08-20",
                "billsid": 2,
                "checkuserid": 2,
                "comments": "需要快速运输",
                "createtime": "2018-08-17",
                "istakespellcabin": 2,
                "startingcity": "海南",
                "startingport": "海南港",
                "startingtime": "2018-08-08",
                "transportid": 16,
                "transporttype": 1,
                "updatetime": "2018-08-17",
                "variety": "五等品"
              }
            ],
            "navigateFirstPage": 1,
            "navigateLastPage": 1,
            "navigatePages": 8,
            "navigatepageNums": [
              1
            ],
            "nextPage": 0,
            "pageNum": 1,
            "pageSize": 10,
            "pages": 1,
            "prePage": 0,
            "size": 1,
            "startRow": 1,
            "total": 1
          },
          "msg": "",
          "success": true
     */
    private WaitQuoteData data;
    private String msg;
    private boolean success;

    public WaitQuoteData getData() {
        return data;
    }

    public void setData(WaitQuoteData data) {
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
