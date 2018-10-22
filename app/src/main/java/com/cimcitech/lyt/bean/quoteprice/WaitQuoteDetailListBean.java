package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/22.
 */

public class WaitQuoteDetailListBean {
    private float totalton;//总吨位
    private float confirmton;//已确认吨位
    private int detailid;
    private String startPortName;//始发港
    private String endPortName;//目的港
    private String fstatus;
    private String issuetime;//发布时间
    private String planarrivedate;//计划到达日期
    private String planloadingdate;//计划装货日期
    private String varietyName;//货物品种

    public float getTotalton() {
        return totalton;
    }

    public void setTotalton(float totalton) {
        this.totalton = totalton;
    }

    public float getConfirmton() {
        return confirmton;
    }

    public void setConfirmton(float confirmton) {
        this.confirmton = confirmton;
    }

    public int getDetailid() {
        return detailid;
    }

    public void setDetailid(int detailid) {
        this.detailid = detailid;
    }

    public String getStartPortName() {
        return startPortName;
    }

    public void setStartPortName(String startPortName) {
        this.startPortName = startPortName;
    }

    public String getEndPortName() {
        return endPortName;
    }

    public void setEndPortName(String endPortName) {
        this.endPortName = endPortName;
    }

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus;
    }

    public String getIssuetime() {
        return issuetime;
    }

    public void setIssuetime(String issuetime) {
        this.issuetime = issuetime;
    }

    public String getPlanarrivedate() {
        return planarrivedate;
    }

    public void setPlanarrivedate(String planarrivedate) {
        this.planarrivedate = planarrivedate;
    }

    public String getPlanloadingdate() {
        return planloadingdate;
    }

    public void setPlanloadingdate(String planloadingdate) {
        this.planloadingdate = planloadingdate;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }
}
