package com.cimcitech.lyt.bean.bargemanage;

/**
 * Created by qianghe on 2018/7/26.
 */

public class BargeInfo {
    private String bargeName;//船名
    private String nationality;//船籍
    private Long deadweightTon;//载重吨位

    public BargeInfo(String bargeName, String nationality, Long deadweightTon) {
        this.bargeName = bargeName;
        this.nationality = nationality;
        this.deadweightTon = deadweightTon;
    }

    public String getBargeName() {
        return bargeName;
    }

    public void setBargeName(String bargeName) {
        this.bargeName = bargeName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getDeadweightTon() {
        return deadweightTon;
    }

    public void setDeadweightTon(Long deadweightTon) {
        this.deadweightTon = deadweightTon;
    }
}
