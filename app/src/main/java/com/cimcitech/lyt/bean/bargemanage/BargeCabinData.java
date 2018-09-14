package com.cimcitech.lyt.bean.bargemanage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by qianghe on 2018/8/6.
 */

public class BargeCabinData implements Parcelable{
    private int bargecabinid;//船舱id
    private int bargeid;//所属的船舱id
    private int hatchnum;//舱口号
    private float holdcapacity;//舱容
    private float hatchsize;//舱口尺寸
    private String updatetime;//维护时间
    private int accountid;////维护人

    public BargeCabinData(int bargecabinid, int bargeid, int hatchnum, float holdcapacity, float hatchsize, String updatetime, int accountid) {
        this.bargecabinid = bargecabinid;
        this.bargeid = bargeid;
        this.hatchnum = hatchnum;
        this.holdcapacity = holdcapacity;
        this.hatchsize = hatchsize;
        this.updatetime = updatetime;
        this.accountid = accountid;
    }

    public int getBargecabinid() {
        return bargecabinid;
    }

    public void setBargecabinid(int bargecabinid) {
        this.bargecabinid = bargecabinid;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public BargeCabinData(Parcel source) {
        hatchsize = source.readFloat();
        bargecabinid = source.readInt();
        bargeid = source.readInt();
        holdcapacity = source.readFloat();
        hatchnum = source.readInt();
        updatetime = source.readString();
        accountid = source.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(hatchsize);
        dest.writeInt(bargecabinid);
        dest.writeInt(bargeid);
        dest.writeFloat(holdcapacity);
        dest.writeInt(hatchnum);
        dest.writeString(updatetime);
        dest.writeInt(accountid);
    }

    public static final Parcelable.Creator<BargeCabinData> CREATOR = new Creator<BargeCabinData>(){
        @Override
        public BargeCabinData[] newArray(int size) {
            return new BargeCabinData[size];
        }

        @Override
        public BargeCabinData createFromParcel(Parcel source) {
            return new BargeCabinData(source);
        }
    };
}
