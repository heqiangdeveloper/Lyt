package com.cimcitech.lyt.activity.bargemanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.main.BaseActivity;
import com.cimcitech.lyt.activity.main.EditValueActivity;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinAddReq;
import com.cimcitech.lyt.bean.bargemanage.BargeInfo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.utils.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;


public class BargeCabinAddActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView back_Iv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.hatchsize_tv)
    TextView hatchsize_Tv;
    @Bind(R.id.hatchnum_tv)
    TextView hatchnum_Tv;
    @Bind(R.id.holdcapacity_tv)
    TextView holdcapacity_Tv;

    private int bargeid = -1;
    //通知BargeDetailActivity页面进行刷新
    public static final String REFRESH_BROADCAST = "com.cimcitech.lyt.activity.bargemanage" +
            ".bargedetail.refresh";
    private BargeInfo bargeInfo = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bargecabin_add);
        ButterKnife.bind(this);
        initTitle();

        bargeid = getIntent().getIntExtra("bargeid",-1);
        bargeInfo = (BargeInfo) getIntent().getSerializableExtra("bargeInfo");
    }

    public void initTitle(){
        titleName_Tv.setText(getResources().getString(R.string.bargecabin_add_title));
        more_Tv.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_iv,R.id.hatchnum_tv,R.id.hatchsize_tv,R.id.holdcapacity_tv,R.id.commit_tv})
    public void onclick(View view) {
        String title;
        switch (view.getId()){
            case R.id.back_iv:
                Intent i = new Intent(BargeCabinAddActivity.this, BargeDetailActivity.class);
                i.putExtra("bargeInfo",bargeInfo);
                startActivity(i);
                finish();
                break;
            case R.id.hatchnum_tv:
                title = getResources().getString(R.string.hatchnum);
                startEditActivity("int",title,hatchnum_Tv.getText().toString().trim(),0);
                break;
            case R.id.hatchsize_tv:
                title = getResources().getString(R.string.hatchsize);
                startEditActivity("num",title,hatchsize_Tv.getText().toString().trim(),1);
                break;
            case R.id.holdcapacity_tv:
                title = getResources().getString(R.string.holdcapacity);
                startEditActivity("num",title,holdcapacity_Tv.getText().toString().trim(),2);
                break;
            case R.id.commit_tv:
                if(!checkInput()) return;
                commitData();
                break;
        }
    }

    public void startEditActivity(String type,String title,String content,int requestCode){
        Intent intent2 = new Intent(BargeCabinAddActivity.this, EditValueActivity.class);
        intent2.putExtra("type",type);
        intent2.putExtra("title",title);
        intent2.putExtra("content",content);
        startActivityForResult(intent2,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            String result = data.getStringExtra("result");
            switch (requestCode) {
                case 0:
                    hatchnum_Tv.setText(result);
                    break;
                case 1:
                    hatchsize_Tv.setText(result);
                    break;
                case 2:
                    holdcapacity_Tv.setText(result);
                    break;
            }
        }
    }

    public boolean checkInput(){
        if(hatchnum_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeCabinAddActivity.this, "请输入舱口号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(holdcapacity_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeCabinAddActivity.this, "请输入舱容", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(hatchsize_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeCabinAddActivity.this, "请输入舱口尺寸", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void commitData(){
        mCommittingDialog.show();
        int hatchnum = Integer.parseInt(hatchnum_Tv.getText().toString());
        float holdcapacity = holdcapacity_Tv.getText().toString().length() != 0 ?
                Float.parseFloat(holdcapacity_Tv.getText().toString()) : 0;
        float hatchsize = hatchsize_Tv.getText().toString().length() != 0 ?
                Float.parseFloat(hatchsize_Tv.getText().toString()) : 0;

        String json = new Gson().toJson(new BargeCabinAddReq(bargeid,
                hatchnum,
                holdcapacity,
                hatchsize));

        OkHttpUtils
                .postString()
                .url(Config.ADD_BARGECABIN_URL)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("Check_Token",Config.TOKEN)
                .content(json)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mCommittingDialog.dismiss();
                        ToastUtil.showNetError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mCommittingDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(BargeCabinAddActivity.this, jsonObject.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                            if(jsonObject.getBoolean("success")){
                                //添加成功，通知BargeDetailActivity页面进行刷新
//                                Intent i = new Intent(BargeCabinAddActivity.this, BargeDetailActivity.class);
//                                i.setAction(BargeCabinAddActivity.REFRESH_BROADCAST);
//                                LocalBroadcastManager.getInstance(BargeCabinAddActivity.this)
//                                        .sendBroadcast(i);
//                                finish();

                                Intent i = new Intent(BargeCabinAddActivity.this, BargeDetailActivity.class);
                                i.putExtra("bargeInfo",bargeInfo);
                                startActivity(i);
                                finish();
                            }else{
                                //do nothing
                            }
                        }catch (JSONException e){
                            Toast.makeText(BargeCabinAddActivity.this, e + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back_Iv.callOnClick();
    }
}
