package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/11.
 */

public class AlreadyQuoteReq {
    private int pageNum;
    private int pageSize;
    private String orderBy;

    public AlreadyQuoteReq(int pageNum, int pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }
}
