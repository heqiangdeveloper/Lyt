package com.cimcitech.lyt.activity.quoteprice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.adapter.quoteprice.WaitQuoteDetailAdapter;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteDetailListBean;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteDetailReq;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteDetailVo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.utils.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;


public class AlreadyQuoteDetailActivity extends AppCompatActivity {
    @Bind(R.id.popup_menu_layout)
    LinearLayout popup_menu_Ll;
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.choose_barge_tv)
    TextView choose_barge_Tv;
    @Bind(R.id.quote_ton_tv)
    TextView quote_ton_Tv;
    @Bind(R.id.quote_price_tv)
    TextView quote_price_Tv;
    @Bind(R.id.quote_price_time_label_tv)
    TextView quote_price_time_label_Tv;
    @Bind(R.id.quote_price_time_tv)
    TextView quote_price_time_Tv;

    private LinearLayoutManager manager = null;
    private WaitQuoteDetailAdapter adapter;
    private List<WaitQuoteDetailListBean> data = new ArrayList<>();
    private WaitQuoteDetailVo waitQuoteDetailVo = null;
    private Float ton = -1f;
    private Float quote = -1f;
    private String bargeName = "";
    private String time = "";
    private int detailid = -2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_quote_detail);
        ButterKnife.bind(this);
        initTitle();
        initPopupMenu();
        bargeName = getIntent().getStringExtra("bargeName");
        ton = getIntent().getFloatExtra("ton",-1);
        quote = getIntent().getFloatExtra("quote",-1);
        time = getIntent().getStringExtra("time");
        initView();
    }

    public void initView(){
        choose_barge_Tv.setText(bargeName);
        quote_price_Tv.setText(quote + "");
        quote_ton_Tv.setText(ton + "");
        quote_price_time_Tv.setText(time);
    }

    public void initTitle(){
        titleName_Tv.setText(getResources().getString(R.string.already_quote_detail_title));
        more_Tv.setVisibility(View.GONE);
        popup_menu_Ll.setVisibility(View.GONE);
        quote_price_time_label_Tv.setText(getResources().getString(R.string.already_quote_price_label));
    }

    public void initPopupMenu(){
        popup_menu_Ll.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);//防止快速切换Fragment导致的FC-->不保存Fragment的状态，让其与activity一同消亡
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
