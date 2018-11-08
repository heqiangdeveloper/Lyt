package com.cimcitech.lyt.activity.message;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.bargemanage.BargeAddAndModifyActivity;
import com.cimcitech.lyt.activity.bargemanage.BargeCabinAddActivity;
import com.cimcitech.lyt.activity.bargemanage.BargeCabinModifyActivity;
import com.cimcitech.lyt.activity.bargemanage.BargeManageFragment;
import com.cimcitech.lyt.activity.main.BaseActivity;
import com.cimcitech.lyt.adapter.bargemanage.BargeCabinAdapter;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinData;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinVo;
import com.cimcitech.lyt.bean.bargemanage.BargeInfo;
import com.cimcitech.lyt.bean.quoteprice.SuccessQuoteListBean;
import com.cimcitech.lyt.bean.quoteprice.SuccessQuoteVo;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteReq;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.utils.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;


public class MessageDetailActivity extends BaseActivity {
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.popup_menu_layout)
    LinearLayout popup_menu_Ll;

    @Bind(R.id.issueUserName_tv)
    TextView issueUserName_Tv;
    @Bind(R.id.fstatus_tv)
    TextView fstatus_Tv;
    @Bind(R.id.effectivedate_tv)
    TextView effectivedate_Tv;
    @Bind(R.id.invaliddate_tv)
    TextView invaliddate_Tv;
    @Bind(R.id.remark_tv)
    TextView remark_Tv;
    @Bind(R.id.choose_barge_tv)
    TextView choose_barge_Tv;
    @Bind(R.id.quote_ton_tv)
    TextView quote_ton_Tv;
    @Bind(R.id.quote_price_tv)
    TextView quote_price_Tv;
    @Bind(R.id.quote_price_time_tv)
    TextView quote_price_time_Tv;

    private String type;
    private int bargeid = 0;
    private int quoteid;
    //向BargeManageFragment发送广播，通知其更新数据
    public static final String REFRESH_BARGE_BROADCAST = "com.cimcitech.lyt.activity.bargemanage" +
            ".refresh";
    private LinearLayoutManager manager = null;
    private BargeCabinAdapter adapter;
    private List<BargeCabinData> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        initTitle();
        initPopupMenu();

        quoteid = getIntent().getIntExtra("quoteid",-1);
        getData();
    }

    public void initView(SuccessQuoteVo successQuoteVo){
        if(successQuoteVo.isSuccess()){
            if(successQuoteVo.getData().getList() != null && successQuoteVo.getData().getList().size() > 0) {
                SuccessQuoteListBean.BargeQuoteMainBean bargeQuoteMain = successQuoteVo.getData().getList().get(0).getBargeQuoteMain();
                SuccessQuoteListBean successQuoteListBean= successQuoteVo.getData().getList().get(0);

                issueUserName_Tv.setText(null != bargeQuoteMain.getQuoteMainUserName() ? bargeQuoteMain.getQuoteMainUserName() : "");
                fstatus_Tv.setText(bargeQuoteMain.getFstatus().equals("1") ? "已发布": "未发布");
                effectivedate_Tv.setText(null != bargeQuoteMain.getEffectivedate() ? bargeQuoteMain.getEffectivedate() : "");
                invaliddate_Tv.setText(null != bargeQuoteMain.getInvaliddate() ? bargeQuoteMain.getInvaliddate() : "");
                remark_Tv.setText(null != bargeQuoteMain.getRemark() ?  bargeQuoteMain.getRemark() : "");
                choose_barge_Tv.setText(null != successQuoteListBean.getBargeName() ? successQuoteListBean.getBargeName():"");
                quote_ton_Tv.setText(successQuoteListBean.getTon() + "");
                quote_price_Tv.setText(successQuoteListBean.getQuote() + "");
                quote_price_time_Tv.setText(successQuoteListBean.getQuotetime() + "");
            }
        }
    }

    public void initTitle(){
        titleName_Tv.setText(getResources().getString(R.string.sucess_label));
        more_Tv.setVisibility(View.GONE);
        popup_menu_Ll.setVisibility(View.GONE);
    }

    public void initPopupMenu(){
        popup_menu_Ll.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_iv})
    public void onclick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

    //获取船舱信息
    public void getData(){
        mLoading.show();
        WaitQuoteReq req = new WaitQuoteReq(quoteid,1,10,"");
        String json = new Gson().toJson(req);
        OkHttpUtils
                .postString()
                .url(Config.QUERY_MY_SUCCESS_URL)
                .addHeader("Check_Token",Config.TOKEN)
                .content(json)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mLoading.dismiss();
                        ToastUtil.showNetError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mLoading.dismiss();
                        SuccessQuoteVo successQuoteVo = GjsonUtil.parseJsonWithGson(response, SuccessQuoteVo.class);
                        initView(successQuoteVo);
                    }
                });
    }
}
