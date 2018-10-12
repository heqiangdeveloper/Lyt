/*******************************************************************************
 *
 * Copyright (c) Weaver Info Tech Co. Ltd
 *
 * BaseActivity
 *
 * app.ui.BaseActivity.java
 * TODO: File description or class description.
 *
 * @author: Administrator
 * @since:  2014-9-03
 * @version: 1.0.0
 *
 * @changeLogs:
 *     1.0.0: First created this class.
 *
 ******************************************************************************/
package com.cimcitech.lyt.activity.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cimcitech.lyt.utils.DialogUtils;

/**
 * @author gao_chun
 * 该类为Activity基类
 */
public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "gao_chun";

    //在基类中初始化Dialog
    public Dialog mLoading,mLoginDialog,mCommittingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoading = DialogUtils.createLoadingDialog(this);//加载中
        mLoginDialog = DialogUtils.createLoginDialog(this);//登录中
        mCommittingDialog = DialogUtils.createCommittingDialog(this);//提交中
    }
}
