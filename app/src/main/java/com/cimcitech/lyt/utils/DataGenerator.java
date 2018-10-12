package com.cimcitech.lyt.utils;

import android.support.v4.app.Fragment;

import com.cimcitech.lyt.activity.bargemanage.BargeManageFragment;
import com.cimcitech.lyt.activity.quoteprice.QuotePriceFragment;
import com.cimcitech.lyt.activity.home.HomeFragment;
import com.cimcitech.lyt.activity.user.UserFragment;

/**
 * Created by zhouwei on 17/4/23.
 */

public class DataGenerator {
    public static Fragment[] getFragments() {
        Fragment fragments[] = new Fragment[5];
        fragments[0] = new HomeFragment();//首页
        //fragments[1] = new HomeFragment();//
        fragments[1] = new QuotePriceFragment();//交易大厅
        fragments[2] = new BargeManageFragment();//驳船管理
        fragments[3] = new UserFragment();//我的
        return fragments;
    }
}
