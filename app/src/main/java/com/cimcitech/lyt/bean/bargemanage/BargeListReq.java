package com.cimcitech.lyt.bean.bargemanage;

/**
 * Created by qianghe on 2018/8/6.
 */

public class BargeListReq {
    //private int bargeid;
    private int accountid;
    private String bargename;//船名
    private String nationality;//驳船国家
    private Float grossTonBegin;//起始吨位
    private Float grossTonEnd;//终值吨位
    private int pageNum;//页码
    private int pageSize;//每页的个数
    private String orderBy;//排序

    public BargeListReq(int accountid,int pageNum, int pageSize, String orderBy) {
        this.accountid = accountid;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

//    public int getBargeid() {
//        return bargeid;
//    }
//
//    public void setBargeid(int bargeid) {
//        this.bargeid = bargeid;
//    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getBargename() {
        return bargename;
    }

    public void setBargename(String bargename) {
        this.bargename = bargename;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Float getGrossTonBegin() {
        return grossTonBegin;
    }

    public void setGrossTonBegin(Float grossTonBegin) {
        this.grossTonBegin = grossTonBegin;
    }

    public Float getGrossTonEnd() {
        return grossTonEnd;
    }

    public void setGrossTonEnd(Float grossTonEnd) {
        this.grossTonEnd = grossTonEnd;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
