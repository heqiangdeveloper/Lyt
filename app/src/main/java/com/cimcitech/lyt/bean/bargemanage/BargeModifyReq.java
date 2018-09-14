package com.cimcitech.lyt.bean.bargemanage;

/**
 * Created by qianghe on 2018/9/5.
 */

public class BargeModifyReq {
    private int bargeid;
    private String accountid;
    private String nationality;
    private String bargename;
    private String createtime;
    private String builttime;
    private float grosston;
    private float netTon;
    private float deadweightton;
    private float length;
    private float width;

    public int getBargeid() {
        return bargeid;
    }

    public void setBargeid(int bargeid) {
        this.bargeid = bargeid;
    }

    public float getDeadweightton() {
        return deadweightton;
    }

    public void setDeadweightton(float deadweightton) {
        this.deadweightton = deadweightton;
    }

    public BargeModifyReq(int bargeid, String accountid, String nationality, String bargename, String createtime,
                          String builttime, float grosston, float netTon, float deadweightton,
                          float length, float width) {
        this.bargeid = bargeid;
        this.accountid = accountid;
        this.nationality = nationality;
        this.bargename = bargename;
        this.createtime = createtime;
        this.builttime = builttime;
        this.grosston = grosston;
        this.netTon = netTon;
        this.deadweightton = deadweightton;
        this.length = length;
        this.width = width;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBargename() {
        return bargename;
    }

    public void setBargename(String bargename) {
        this.bargename = bargename;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getBuilttime() {
        return builttime;
    }

    public void setBuilttime(String builttime) {
        this.builttime = builttime;
    }

    public float getGrosston() {
        return grosston;
    }

    public void setGrosston(float grosston) {
        this.grosston = grosston;
    }

    public float getNetTon() {
        return netTon;
    }

    public void setNetTon(float netTon) {
        this.netTon = netTon;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
