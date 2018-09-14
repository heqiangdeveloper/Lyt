package com.cimcitech.lyt.activity.bargemanage;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.main.BaseActivity;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinData;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinVo;
import com.cimcitech.lyt.bean.bargemanage.BargeInfo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class BargeDetailActivity extends BaseActivity {
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.sv)
    ScrollView sv_sv;

    @Bind(R.id.createtime_tv)
    TextView createtime_Tv;
    @Bind(R.id.bargename_tv)
    TextView bargename_Tv;
    @Bind(R.id.nationality_tv)
    TextView nationality_Tv;
    @Bind(R.id.builttime_tv)
    TextView builttime_Tv;
    @Bind(R.id.length_tv)
    TextView length_Tv;
    @Bind(R.id.width_tv)
    TextView width_Tv;
    @Bind(R.id.grosston_tv)
    TextView grosston_Tv;
    @Bind(R.id.deadweightTon_tv)
    TextView deadweightTon_Tv;
    @Bind(R.id.netTon_tv)
    TextView netTon_Tv;
    @Bind(R.id.picture_tv)
    TextView picture_Tv;
    @Bind(R.id.shipcertificatepicture_tv)
    TextView shipcertificatepicture_Tv;
    @Bind(R.id.popup_menu_layout)
    LinearLayout popup_menu_Ll;
    @Bind(R.id.barge_cabin_ll)
    LinearLayout barge_cabin_Ll;

    private String type;
    private int bargeid = 0;
    private BargeInfo bargeInfo;
    //向BargeManageFragment发送广播，通知其更新数据
    public static final String REFRESH_BARGE_BROADCAST = "com.cimcitech.lyt.activity.bargemanage" +
            ".refresh";
    public List<BargeCabinData> bargeCabinDatas = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barge_detail);
        ButterKnife.bind(this);
        initTitle();
        initPopupMenu();

        titleName_Tv.setText(getResources().getString(R.string.barge_detail_title));
        //command_Tv.setText(getResources().getString(R.string.barge_delete));
        bargeInfo = (BargeInfo) getIntent().getSerializableExtra("bargeInfo");
        initView(bargeInfo);
        bargeid = bargeInfo.getBargeid();

        getData();
    }

    public void initView(BargeInfo bargeInfo){
        if(null != bargeInfo){
            createtime_Tv.setText(null != bargeInfo.getCreatetime() ? bargeInfo.getCreatetime() : "");
            bargename_Tv.setText(null != bargeInfo.getBargename() ? bargeInfo.getBargename() : "");
            nationality_Tv.setText(null != bargeInfo.getNationality() ? bargeInfo.getNationality() : "");
            builttime_Tv.setText(null != bargeInfo.getBuilttime() ? bargeInfo.getBuilttime() : "");
            length_Tv.setText(bargeInfo.getLength() + "");
            width_Tv.setText(bargeInfo.getWidth() + "");
            grosston_Tv.setText(bargeInfo.getGrosston() + "");
            deadweightTon_Tv.setText(bargeInfo.getDeadweightton() + "");
            netTon_Tv.setText(bargeInfo.getNetTon() + "");
            picture_Tv.setText(getResources().getString(R.string.not_upload));
            shipcertificatepicture_Tv.setText(getResources().getString(R.string.not_upload));
        }
    }

    public void registerBroadcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BargeCabinAddActivity.REFRESH_BROADCAST);
        LocalBroadcastManager.getInstance(BargeDetailActivity.this).registerReceiver(mBroadcastReceiver,intentFilter);
    }

    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BargeCabinAddActivity.REFRESH_BROADCAST)){

            }
        }
    };

    public void initBargeCabinView( List<BargeCabinData> bargeCabinDatas){
        if(null != bargeCabinDatas){
            int size = bargeCabinDatas.size();
            BargeCabinData bcd = null;
            View addView = null;
            TextView hatchnum_Tv = null;
            TextView holdcapacity_Tv = null;
            TextView hatchsize_Tv = null;
            if(size != 0){
                for(int i = 0; i < size; i ++){
                    bcd = bargeCabinDatas.get(i);
                    addView = getLayoutInflater().inflate(R.layout.barge_cabin_layout, barge_cabin_Ll, false);
                    barge_cabin_Ll.addView(addView);

                    hatchnum_Tv = (TextView)addView.findViewById(R.id.hatchnum_tv);
                    holdcapacity_Tv = (TextView)addView.findViewById(R.id.holdcapacity_tv);
                    hatchsize_Tv = (TextView)addView.findViewById(R.id.hatchsize_tv);

                    hatchnum_Tv.setText(bcd.getHatchnum() + "");
                    holdcapacity_Tv.setText(bcd.getHoldcapacity() + "");
                    hatchsize_Tv.setText(bcd.getHatchsize() + "");
                }
            }
        }
    }

    public void initTitle(){
        titleName_Tv.setText(getResources().getString(R.string.barge_detail_title));
        more_Tv.setVisibility(View.VISIBLE);
        popup_menu_Ll.setVisibility(View.GONE);
    }

    public void initPopupMenu(){
        popup_menu_Ll.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_iv,R.id.more_tv,R.id.item_edit_barge_tv,R.id.item_edit_bagrgecabin_tv,
            R.id.item_add_bagrgecabin_tv,R.id.item_delete_tv})
    public void onclick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.more_tv:
                popup_menu_Ll.setVisibility(View.VISIBLE);
                break;
            case R.id.item_add_bagrgecabin_tv://新增船舱
                popup_menu_Ll.setVisibility(View.GONE);
                Intent i = new Intent(BargeDetailActivity.this,BargeCabinAddActivity.class);
                i.putExtra("bargeid",bargeInfo.getBargeid());
                i.putExtra("bargeInfo",bargeInfo);
                startActivity(i);
                finish();
                break;
            case R.id.item_edit_barge_tv://编辑驳船信息
                popup_menu_Ll.setVisibility(View.GONE);
                Intent i3 = new Intent(BargeDetailActivity.this,BargeAddAndModifyActivity.class);
                i3.putExtra("bargeInfo",bargeInfo);
                //i.putParcelableArrayListExtra("bargeCabinDatas",(ArrayList<BargeCabinData>)
                //    bargeCabinDatas);
                i3.putExtra("type","edit");
                startActivity(i3);
                finish();
                break;
            case R.id.item_edit_bagrgecabin_tv://编辑船舱信息
                popup_menu_Ll.setVisibility(View.GONE);
                Intent i2 = new Intent(BargeDetailActivity.this,BargeCabinModifyAndDeleteActivity.class);
                i2.putParcelableArrayListExtra("bargeCabinDatas",(ArrayList<BargeCabinData>) bargeCabinDatas);
                i2.putExtra("bargeInfo",bargeInfo);
                startActivity(i2);
                finish();
                break;
            case R.id.item_delete_tv://删除驳船
                popup_menu_Ll.setVisibility(View.GONE);
                String content = "确认删除该驳船信息？";
                new AlertDialog.Builder(BargeDetailActivity.this)
                        //.setTitle("提示")
                        .setMessage(content)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                deleteBargeRequest();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                break;
        }
    }

    public void deleteBargeRequest(){
        if(bargeid != 0){
            //String json = new Gson().toJson(new BargeDeleteReq(bargeid));
            OkHttpUtils
                    .post()
                    .url(Config.DELETE_BARGE_URL)
                    //.addHeader("checkTokenKey", Config.TOKEN)
                    //.addHeader("sessionKey", Config.USERID + "")
                    //.mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .addHeader("Check_Token",Config.TOKEN)
                    .addParams("bargeid",bargeid + "")
                    .build()
                    .execute(
                            new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    ToastUtil.showNetError();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    //ToastUtil.showToast(response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if(jsonObject.getBoolean("success")){
                                            ToastUtil.showToast(jsonObject.getString("msg"));
                                            //向BargeManageFragment发送广播，通知其更新数据
                                            Intent i = new Intent();
                                            i.setAction(BargeDetailActivity.REFRESH_BARGE_BROADCAST);
                                            LocalBroadcastManager.getInstance(BargeDetailActivity.this).sendBroadcast(i);

                                            finish();
                                            startActivity(new Intent(BargeDetailActivity.this,
                                                    BargeManageFragment.class));
                                        }else{
                                            ToastUtil.showToast(jsonObject.getString("msg"));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
        }
    }

    //获取船舱信息
    public void getData(){
        mLoading.show();
        OkHttpUtils
                .post()
                .url(Config.QUERY_BARGECABIN_URL)
                .addParams("bargeid",bargeid + "")
                .addHeader("Check_Token",Config.TOKEN)
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
                        BargeCabinVo bargeCabinVo = GjsonUtil.parseJsonWithGson(response, BargeCabinVo.class);
                        if(bargeCabinVo.isSuccess()){
                            bargeCabinDatas = bargeCabinVo.getData();
                            initBargeCabinView(bargeCabinDatas);
                        }
                    }
                });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) ev.getX();
            int y = (int) ev.getY();

            if (null != popup_menu_Ll && popup_menu_Ll.getVisibility() == View.VISIBLE) {
                Rect hitRect = new Rect();
                popup_menu_Ll.getGlobalVisibleRect(hitRect);
                if (!hitRect.contains(x, y)) {
                    popup_menu_Ll.setVisibility(View.GONE);
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
