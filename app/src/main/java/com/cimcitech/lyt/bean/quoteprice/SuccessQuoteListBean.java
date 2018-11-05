package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/9.
 */

public class SuccessQuoteListBean {
    /*
    *   {
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
     */

    private String bargeName;
    private BargeQuoteMainBean bargeQuoteMain;
    private int bargeid;
    private int detailid;
    private String fstatus;
    private int issueuserid;
    private float quote;
    private int quoteid;
    private String quotetime;
    private int reqid;
    private float ton;

    public String getBargeName() {
        return bargeName;
    }

    public void setBargeName(String bargeName) {
        this.bargeName = bargeName;
    }

    public BargeQuoteMainBean getBargeQuoteMain() {
        return bargeQuoteMain;
    }

    public void setBargeQuoteMain(BargeQuoteMainBean bargeQuoteMain) {
        this.bargeQuoteMain = bargeQuoteMain;
    }

    public int getBargeid() {
        return bargeid;
    }

    public void setBargeid(int bargeid) {
        this.bargeid = bargeid;
    }

    public int getDetailid() {
        return detailid;
    }

    public void setDetailid(int detailid) {
        this.detailid = detailid;
    }

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus;
    }

    public int getIssueuserid() {
        return issueuserid;
    }

    public void setIssueuserid(int issueuserid) {
        this.issueuserid = issueuserid;
    }

    public float getQuote() {
        return quote;
    }

    public void setQuote(float quote) {
        this.quote = quote;
    }

    public int getQuoteid() {
        return quoteid;
    }

    public void setQuoteid(int quoteid) {
        this.quoteid = quoteid;
    }

    public String getQuotetime() {
        return quotetime;
    }

    public void setQuotetime(String quotetime) {
        this.quotetime = quotetime;
    }

    public int getReqid() {
        return reqid;
    }

    public void setReqid(int reqid) {
        this.reqid = reqid;
    }

    public float getTon() {
        return ton;
    }

    public void setTon(float ton) {
        this.ton = ton;
    }

    public static class BargeQuoteMainBean{
        private String effectivedate;
        private String fstatus;
        private String invaliddate;
        private int issueuserid;
        private String quoteMainUserName;
        private String remark;

        public String getEffectivedate() {
            return effectivedate;
        }

        public void setEffectivedate(String effectivedate) {
            this.effectivedate = effectivedate;
        }

        public String getFstatus() {
            return fstatus;
        }

        public void setFstatus(String fstatus) {
            this.fstatus = fstatus;
        }

        public String getInvaliddate() {
            return invaliddate;
        }

        public void setInvaliddate(String invaliddate) {
            this.invaliddate = invaliddate;
        }

        public int getIssueuserid() {
            return issueuserid;
        }

        public void setIssueuserid(int issueuserid) {
            this.issueuserid = issueuserid;
        }

        public String getQuoteMainUserName() {
            return quoteMainUserName;
        }

        public void setQuoteMainUserName(String quoteMainUserName) {
            this.quoteMainUserName = quoteMainUserName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
