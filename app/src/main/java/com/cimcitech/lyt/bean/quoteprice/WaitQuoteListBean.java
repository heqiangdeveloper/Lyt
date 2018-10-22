package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/9.
 */

public class WaitQuoteListBean {
    /*
    *  {
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
     */
    private String effectivedate;
    private String fstatus;
    private String invaliddate;
    private String issueUserName;
    private String issuetime;
    private int issueuserid;
    private String remark;
    private int transportreqid;
    private String updatUserName;
    private String updatetime;
    private int updateuserid;

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

    public String getIssueUserName() {
        return issueUserName;
    }

    public void setIssueUserName(String issueUserName) {
        this.issueUserName = issueUserName;
    }

    public String getIssuetime() {
        return issuetime;
    }

    public void setIssuetime(String issuetime) {
        this.issuetime = issuetime;
    }

    public int getIssueuserid() {
        return issueuserid;
    }

    public void setIssueuserid(int issueuserid) {
        this.issueuserid = issueuserid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTransportreqid() {
        return transportreqid;
    }

    public void setTransportreqid(int transportreqid) {
        this.transportreqid = transportreqid;
    }

    public String getUpdatUserName() {
        return updatUserName;
    }

    public void setUpdatUserName(String updatUserName) {
        this.updatUserName = updatUserName;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getUpdateuserid() {
        return updateuserid;
    }

    public void setUpdateuserid(int updateuserid) {
        this.updateuserid = updateuserid;
    }
}
