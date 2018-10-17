package com.cimcitech.lyt.activity.bargemanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.main.BaseActivity;
import com.cimcitech.lyt.activity.main.EditValueActivity;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinData;
import com.cimcitech.lyt.bean.bargemanage.BargeInfo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.DateTool;
import com.cimcitech.lyt.utils.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;


public class BargeCabinModifyActivity extends BaseActivity {
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.back_iv)
    ImageView back_Iv;
    @Bind(R.id.hatchnum_tv)
    TextView hatchnum_Tv;
    @Bind(R.id.holdcapacity_tv)
    TextView holdcapacity_Tv;
    @Bind(R.id.hatchsize_tv)
    TextView hatchsize_Tv;

    private String type;
    private final int DATE_DIALOG = 1;
    private int mYear, mMonth, mDay;
    private int size = 0;
    private RadioButton[] select_rbs;
    private int currentIndex = 0;
    private int selectedIndex = -1;
    private BargeCabinData bargeCabinData = null;
    private BargeInfo bargeInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bargecabin_modify);
        ButterKnife.bind(this);
        initTitle();

        bargeCabinData = (BargeCabinData)getIntent().getSerializableExtra("bargeCabinData");
        bargeInfo = (BargeInfo) getIntent().getSerializableExtra("bargeInfo");
        initView2(bargeCabinData);
    }

    public void initTitle(){
        more_Tv.setVisibility(View.GONE);
        titleName_Tv.setText(getResources().getString(R.string.barge_cabin_manage));
    }

    public void initView2(BargeCabinData bargeCabinData){
        hatchnum_Tv.setText(bargeCabinData.getHatchnum() + "");
        holdcapacity_Tv.setText(bargeCabinData.getHoldcapacity() + "");
        hatchsize_Tv.setText(bargeCabinData.getHatchsize() + "");
    }


    @OnClick({R.id.back_iv,R.id.hatchnum_tv,R.id.holdcapacity_tv,R.id.hatchsize_tv,R.id.commit_tv})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                Intent i = new Intent(BargeCabinModifyActivity.this, BargeDetailActivity.class);
                i.putExtra("bargeInfo",bargeInfo);
                startActivity(i);
                finish();
                break;
            case R.id.hatchnum_tv://舱口号暂不允许修改
                break;
            case R.id.holdcapacity_tv://舱容
                startEditActivity("num",getResources().getString(R.string.holdcapacity),
                        holdcapacity_Tv.getText().toString().trim(),1);
                break;
            case R.id.hatchsize_tv://舱口尺寸
                startEditActivity("num",getResources().getString(R.string.hatchsize),
                        hatchsize_Tv.getText().toString().trim(),2);
                break;
            case R.id.commit_tv:
                commitData();
                break;
        }
    }

    public void startEditActivity(String type,String title,String content,int requestCode){
        Intent intent2 = new Intent(BargeCabinModifyActivity.this, EditValueActivity.class);
        intent2.putExtra("type",type);
        intent2.putExtra("title",title);
        intent2.putExtra("content",content);
        startActivityForResult(intent2,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            String result = data.getStringExtra("result");
            switch (requestCode){
                case 0:
                    break;
                case 1://舱容
                    holdcapacity_Tv.setText(result);
                    break;
                case 2://舱口尺寸
                    hatchsize_Tv.setText(result);
                    break;
            }
        }
    }

    public void commitData(){
        mCommittingDialog.show();
        int bargecabinid = bargeCabinData.getBargecabinid();
        int bargeid = bargeInfo.getBargeid();
        int hatchnum = Integer.parseInt(hatchnum_Tv.getText().toString());
        float holdcapacity = Float.parseFloat(holdcapacity_Tv.getText().toString());
        float hatchsize = Float.parseFloat(hatchsize_Tv.getText().toString());
        String updatetime = DateTool.getSystemDate();//2018-06-21格式
        int accountid = Config.ACCOUNTID;

        String json = new Gson().toJson(new BargeCabinData(bargecabinid,
                bargeid,
                hatchnum,
                holdcapacity,
                hatchsize,
                updatetime,
                accountid
                ));

        OkHttpUtils
                .postString()
                .url(Config.UPDATE_BARGECABIN_URL)
                .addHeader("Check_Token",Config.TOKEN)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                mCommittingDialog.dismiss();
                                ToastUtil.showNetError();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                mCommittingDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getBoolean("success")){
                                        ToastUtil.showToast(jsonObject.getString("msg"));

                                        Intent i = new Intent(BargeCabinModifyActivity.this,
                                                BargeDetailActivity.class);
                                        i.putExtra("bargeInfo",bargeInfo);
                                        startActivity(i);
                                        finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back_Iv.callOnClick();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
