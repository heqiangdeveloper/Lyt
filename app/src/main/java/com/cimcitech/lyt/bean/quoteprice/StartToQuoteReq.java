package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/24.
 */

public class StartToQuoteReq {
    private int detailid;
    private int reqid;
    private int bargeid;
    private float ton;
    private float quote;

    public StartToQuoteReq(int detailid, int reqid, int bargeid, float ton, float quote) {
        this.detailid = detailid;
        this.reqid = reqid;
        this.bargeid = bargeid;
        this.ton = ton;
        this.quote = quote;
    }
}
