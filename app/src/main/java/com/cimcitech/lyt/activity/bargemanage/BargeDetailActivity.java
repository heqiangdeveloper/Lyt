package com.cimcitech.lyt.activity.bargemanage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.message.MessageFragment;
import com.cimcitech.lyt.task.OnTaskFinishedListener;
import com.cimcitech.lyt.task.message.QueryUnReadMessageTask;
import com.cimcitech.lyt.utils.DataGenerator;

import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


public class BargeDetailActivity extends AppCompatActivity {
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.delete_tv)
    TextView delete_Tv;
    @Bind(R.id.sv)
    ScrollView sv_sv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barge_detail);
        ButterKnife.bind(this);
        initTitle();

        String type = getIntent().getStringExtra("type");
        if(type.equals("add")){
            titleName_Tv.setText(getResources().getString(R.string.barge_add_title));
        }else{
            titleName_Tv.setText(getResources().getString(R.string.barge_detail_title));
        }
    }

    public void initTitle(){
        titleName_Tv.setText(getResources().getString(R.string.barge_detail_title));
        more_Tv.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_iv})
    public void onclick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

}