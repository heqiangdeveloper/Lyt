package com.cimcitech.lyt.bean.bargemanage;

/**
 * Created by qianghe on 2018/9/5.
 */

public class BargeCabinAddReq {
    private int bargeid;
    private int hatchnum;  //舱口号
    private float holdcapacity;  //舱容
    private float hatchsize;  //舱口尺寸

    public BargeCabinAddReq(int bargeid, int hatchnum, float holdcapacity, float hatchsize) {
        this.bargeid = bargeid;
        this.hatchnum = hatchnum;
        this.holdcapacity = holdcapacity;
        this.hatchsize = hatchsize;
    }

    public int getBargeid() {
        return bargeid;
    }

    public void setBargeid(int bargeid) {
        this.bargeid = bargeid;
    }

    public int getHatchnum() {
        return hatchnum;
    }

    public void setHatchnum(int hatchnum) {
        this.hatchnum = hatchnum;
    }

    public float getHoldcapacity() {
        return holdcapacity;
    }

    public void setHoldcapacity(float holdcapacity) {
        this.holdcapacity = holdcapacity;
    }

    public float getHatchsize() {
        return hatchsize;
    }

    public void setHatchsize(float hatchsize) {
        this.hatchsize = hatchsize;
    }
}
