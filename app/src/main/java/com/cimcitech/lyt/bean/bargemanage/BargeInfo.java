package com.cimcitech.lyt.bean.bargemanage;

import java.io.Serializable;

/**
 * Created by qianghe on 2018/7/26.
 */

public class BargeInfo implements Serializable{
    private String bargename;//船名
    private String nationality;//船籍
    private float deadweightton;//载重吨位
    private float width;//船宽
    private float length;//船长
    private float grosston;//总吨
    private float netton;//净吨
    private String builttime;//建造时间
    private String createtime;//注册时间
    private int bargeid;

    public BargeInfo(String bargename, String nationality, float deadweightton, float width,float
            netton, float length, float grosston, String builttime, String createtime, int bargeid) {
        this.bargename = bargename;
        this.nationality = nationality;
        this.deadweightton = deadweightton;
        this.width = width;
        this.length = length;
        this.grosston = grosston;
        this.builttime = builttime;
        this.createtime = createtime;
        this.bargeid = bargeid;
        this.netton = netton;
    }

    public float getNetton() {
        return netton;
    }

    public void setNetton(float netton) {
        this.netton = netton;
    }

    public int getBargeid() {
        return bargeid;
    }

    public void setBargeid(int bargeid) {
        this.bargeid = bargeid;
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

    public float getDeadweightton() {
        return deadweightton;
    }

    public void setDeadweightton(float deadweightton) {
        this.deadweightton = deadweightton;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getGrosston() {
        return grosston;
    }

    public void setGrosston(float grosston) {
        this.grosston = grosston;
    }

    public String getBuilttime() {
        return builttime;
    }

    public void setBuilttime(String builttime) {
        this.builttime = builttime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
