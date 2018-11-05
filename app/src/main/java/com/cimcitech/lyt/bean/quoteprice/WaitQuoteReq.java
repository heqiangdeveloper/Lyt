package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/9.
 */

public class WaitQuoteReq {
    private int quoteid;
    private int pageNum;
    private int pageSize;
    private String orderBy;

    public WaitQuoteReq(int pageNum, int pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public WaitQuoteReq(int quoteid, int pageNum, int pageSize, String orderBy) {
        this.quoteid = quoteid;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }
}
