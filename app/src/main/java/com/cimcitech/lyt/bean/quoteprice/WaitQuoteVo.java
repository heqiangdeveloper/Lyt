package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/9.
 */

public class WaitQuoteVo {
    /*
    *   {
  "code": 0,
  "data": {
    "endRow": 7,
    "firstPage": 1,
    "hasNextPage": false,
    "hasPreviousPage": false,
    "isFirstPage": true,
    "isLastPage": true,
    "lastPage": 1,
    "list": [
      {
        "effectivedate": "2018-02-15 04:12:05",
        "fstatus": "1",
        "invaliddate": "2018-10-26 08:38:50",
        "issueUserName": "邱桂林",
        "issuetime": "2018-10-16",
        "issueuserid": 9,
        "remark": "尽快运输",
        "transportreqid": 10,
        "updatUserName": "邱桂林",
        "updatetime": "2018-10-16",
        "updateuserid": 9
      },
      {
        "effectivedate": "2018-02-15 04:12:05",
        "fstatus": "1",
        "invaliddate": "2018-10-16 08:38:50",
        "issueUserName": "齐国仓",
        "issuetime": "2018-10-16",
        "issueuserid": 15,
        "remark": "尽快运输",
        "transportreqid": 9,
        "updatUserName": "雷丰林",
        "updatetime": "2018-10-16",
        "updateuserid": 5
      },
      {
        "effectivedate": "2018-02-15 04:12:05",
        "fstatus": "1",
        "invaliddate": "2018-10-16 08:38:50",
        "issueUserName": "邱桂林",
        "issuetime": "2018-10-16",
        "issueuserid": 9,
        "remark": "尽快运输",
        "transportreqid": 8,
        "updatUserName": "龚迁",
        "updatetime": "2018-10-16",
        "updateuserid": 3
      },
      {
        "effectivedate": "2018-02-15 04:12:05",
        "fstatus": "1",
        "invaliddate": "2018-10-16 08:38:50",
        "issueUserName": "雷丰林",
        "issuetime": "2018-10-16",
        "issueuserid": 5,
        "remark": "尽快运输",
        "transportreqid": 7,
        "updatUserName": "测试",
        "updatetime": "2018-10-16",
        "updateuserid": 2
      },
      {
        "effectivedate": "2018-10-27 04:12:05",
        "fstatus": "1",
        "invaliddate": "2018-10-27 08:38:50",
        "issueUserName": "雷丰林",
        "issuetime": "2018-10-16",
        "issueuserid": 5,
        "remark": "尽快运输",
        "transportreqid": 6,
        "updatUserName": "邱桂林",
        "updatetime": "2018-10-25",
        "updateuserid": 9
      },
      {
        "effectivedate": "2018-10-18 04:12:05",
        "fstatus": "1",
        "invaliddate": "2018-10-26 08:38:50",
        "issueUserName": "齐国仓",
        "issuetime": "2018-10-16",
        "issueuserid": 15,
        "remark": "尽快运输",
        "transportreqid": 5,
        "updatUserName": "齐国仓",
        "updatetime": "2018-10-16",
        "updateuserid": 15
      },
      {
        "effectivedate": "2018-10-01 12:11:10",
        "fstatus": "1",
        "invaliddate": "2018-12-18 12:11:10",
        "issueUserName": "雷丰林",
        "issuetime": "2018-10-12",
        "issueuserid": 5,
        "remark": "后天发布",
        "transportreqid": 2,
        "updatUserName": "雷丰林",
        "updatetime": "2018-10-12",
        "updateuserid": 5
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
    "size": 7,
    "startRow": 1,
    "total": 7
  },
  "msg": "",
  "success": true
}
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
