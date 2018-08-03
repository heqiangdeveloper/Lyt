package com.cimcitech.lyt.activity.exchange;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cimcitech.lyt.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cimcitech on 2017/7/31.
 */

public class ExchangeFragment extends Fragment {
    @Bind(R.id.back_iv)
    ImageView back_Iv;
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            ButterKnife.bind(this, view);
            return view;
        }
        view = inflater.inflate(R.layout.activity_exchange, container, false);
        ButterKnife.bind(this, view);
        initTitle();
        return view;
    }

    public void initTitle(){
        back_Iv.setVisibility(View.GONE);
        titleName_Tv.setText("交易大厅");
    }
}
