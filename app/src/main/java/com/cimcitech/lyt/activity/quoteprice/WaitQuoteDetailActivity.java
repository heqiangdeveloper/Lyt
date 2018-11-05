package com.cimcitech.lyt.activity.quoteprice;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.bargemanage.BargeCabinModifyActivity;
import com.cimcitech.lyt.activity.bargemanage.BargeDetailActivity;
import com.cimcitech.lyt.activity.message.MessageFragment;
import com.cimcitech.lyt.activity.user.SettingsActivity;
import com.cimcitech.lyt.adapter.bargemanage.BargeCabinAdapter;
import com.cimcitech.lyt.adapter.quoteprice.WaitQuoteDetailAdapter;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinData;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteDetailListBean;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteDetailReq;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteDetailVo;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteVo;
import com.cimcitech.lyt.task.OnTaskFinishedListener;
import com.cimcitech.lyt.task.message.QueryUnReadMessageTask;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.DataGenerator;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.utils.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.MediaType;


public class WaitQuoteDetailActivity extends AppCompatActivity {
    @Bind(R.id.popup_menu_layout)
    LinearLayout popup_menu_Ll;
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager manager = null;
    private WaitQuoteDetailAdapter adapter;
    private List<WaitQuoteDetailListBean> data = new ArrayList<>();
    private WaitQuoteDetailVo waitQuoteDetailVo = null;
    private int transportreqid = -1;
    private int detailid = -2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_quote_detail);
        ButterKnife.bind(this);
        initTitle();
        initPopupMenu();
        transportreqid = getIntent().getIntExtra("transportreqid",-1);
        getData(transportreqid);
    }

    public void initTitle(){
        titleName_Tv.setText(getResources().getString(R.string.barge_detail_title));
        more_Tv.setVisibility(View.GONE);
        popup_menu_Ll.setVisibility(View.GONE);
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

    public void initAdapter(){
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        adapter = new WaitQuoteDetailAdapter(WaitQuoteDetailActivity.this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //给List添加点击事件
        adapter.setOnItemClickListener(new WaitQuoteDetailAdapter.OnItemClickListener() {

            @Override
            public void onStartQuoteClickListener(View view,final int position) {
                String content = getResources().getString(R.string.ask_start_quote_label);
                new AlertDialog.Builder(WaitQuoteDetailActivity.this)
                        .setMessage(content)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                detailid = data.get(position).getDetailid();
                                startToQuote();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    private void startToQuote(){
        //进入报价页面
        Intent intent = new Intent(WaitQuoteDetailActivity.this,StartToQuoteActivity.class);
        intent.putExtra("transportreqid",transportreqid);
        intent.putExtra("detailid",detailid);
        startActivity(intent);
        finish();
    }

    private void getData(int transportreqid){
        if(transportreqid != -1){
            String json = new Gson().toJson(new WaitQuoteDetailReq(transportreqid));
            OkHttpUtils
                    .postString()
                    .url(Config.QUERY_QUOTE_DETAIL_URL)
                    .addHeader("Check_Token", Config.TOKEN)
                    .content(json)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .build()
                    .execute(
                            new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    ToastUtil.showNetError();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    waitQuoteDetailVo = GjsonUtil.parseJsonWithGson(response, WaitQuoteDetailVo.class);
                                    if(null != waitQuoteDetailVo){
                                        if(waitQuoteDetailVo.isSuccess()){
                                            data.clear();
                                            if(waitQuoteDetailVo.getData() != null &&
                                                    waitQuoteDetailVo.getData().size() > 0){
                                                for(int i = 0 ; i < waitQuoteDetailVo.getData().size(); i++){
                                                    data.add(waitQuoteDetailVo.getData().get(i));
                                                }
                                            }
                                        }
                                    }
                                    initAdapter();
                                }
                            }
                    );
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
