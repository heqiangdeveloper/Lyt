package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/9.
 */

public class AlreadyQuoteVo {
    /*
    *       			{
    "code": 0,
    "data": {
        "endRow": 3,
        "firstPage": 1,
        "hasNextPage": false,
        "hasPreviousPage": false,
        "isFirstPage": true,
        "isLastPage": true,
        "lastPage": 1,
        "list": [
             {
                "bargeQuoteMain": {
                  "effectivedate": "2018-10-01 12:11:10",
                  "fstatus": "1",
                  "invaliddate": "2018-11-13 12:11:10",
                  "issueuserid": 15,
                  "quoteMainUserName": "雷丰林",
                  "remark": "后天发布"
                },
                "bargeid": 30,
                "detailid": 4,
                "fstatus": "1",
                "issueuserid": 15,
                "quote": 1,
                "quoteid": 6,
                "quotetime": "2018-10-22",
                "reqid": 1,
                "ton": 1500
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
        "size": 3,
        "startRow": 1,
        "total": 3
    },
    "msg": "",
    "success": true
}

     */
    private AlreadyQuoteData data;
    private String msg;
    private boolean success;

    public AlreadyQuoteData getData() {
        return data;
    }

    public void setData(AlreadyQuoteData data) {
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
