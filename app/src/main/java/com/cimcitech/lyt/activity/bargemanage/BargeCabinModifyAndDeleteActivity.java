package com.cimcitech.lyt.activity.bargemanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
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
import com.cimcitech.lyt.utils.GjsonUtil;
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


public class BargeCabinModifyAndDeleteActivity extends BaseActivity {
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.barge_cabin_info_ll)
    LinearLayout barge_cabin_info_Ll;
    @Bind(R.id.barge_cabin_rg)
    RadioGroup barge_cabin_Rg;
    @Bind(R.id.popup_menu_layout)
    LinearLayout popup_menu_Ll;
    @Bind(R.id.item_edit_bagrgecabin_tv)
    TextView item_edit_bagrgecabin_Tv;
    @Bind(R.id.item_delete_tv)
    TextView item_delete_Tv;
    @Bind(R.id.item_edit_barge_tv)
    TextView item_edit_barge_Tv;
    @Bind(R.id.item_add_bagrgecabin_tv)
    TextView item_add_bagrgecabin_Tv;
    @Bind(R.id.item_delete_barge_cabin_tv)
    TextView item_delete_barge_cabin_Tv;
    @Bind(R.id.back_iv)
    ImageView back_Iv;

    private String type;
    private final int DATE_DIALOG = 1;
    private int mYear, mMonth, mDay;
    private int size = 0;
    private TextView[] hatchnum_bargeCabin_TextViews;
    private TextView[] holdcapacity_bargeCabin_TextViews;
    private TextView[] hatchsize_bargeCabin_TextViews;
    private RadioButton[] select_rbs;
    private int currentIndex = 0;
    private int selectedIndex = -1;
    private List<BargeCabinData> bargeCabinDatas = null;
    private BargeInfo bargeInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bargecabin_modify_and_delete);
        ButterKnife.bind(this);
        initTitle();
        initPopupMenu();

        //bargeCabinDatas = getIntent().getParcelableArrayListExtra("bargeCabinDatas");
        bargeInfo = (BargeInfo) getIntent().getSerializableExtra("bargeInfo");
        initView(bargeCabinDatas);
    }

    public void initTitle(){
        more_Tv.setVisibility(View.VISIBLE);
        titleName_Tv.setText(getResources().getString(R.string.barge_cabin_manage));
    }

    public void initPopupMenu(){
        popup_menu_Ll.setVisibility(View.GONE);
        item_delete_Tv.setVisibility(View.GONE);
        item_add_bagrgecabin_Tv.setVisibility(View.GONE);
        item_edit_bagrgecabin_Tv.setVisibility(View.VISIBLE);
        item_edit_barge_Tv.setVisibility(View.GONE);
        item_delete_barge_cabin_Tv.setVisibility(View.VISIBLE);

        item_delete_barge_cabin_Tv.setText(getResources().getString(R.string.barge_cabin_delete));
        item_edit_bagrgecabin_Tv.setText(getResources().getString(R.string.barge_cabin_commit));
    }

    public void initView(List<BargeCabinData> bargeCabinDatas){
        if(null != bargeCabinDatas){
            size = bargeCabinDatas == null ? 0 : bargeCabinDatas.size();
            BargeCabinData bcd = null;
            View RadioButtonView = null;
            View LinearLayoutView = null;
            if(size != 0){
                hatchnum_bargeCabin_TextViews = new TextView[size];
                holdcapacity_bargeCabin_TextViews = new TextView[size];
                hatchsize_bargeCabin_TextViews = new TextView[size];
                //select_rbs = new RadioButton[size];

                for(int i = 0; i < size; i ++){
                    currentIndex = i;
                    bcd = bargeCabinDatas.get(i);
                    //动态添加显示船舱信息的view
//                    RadioButtonView = getLayoutInflater().inflate(R.layout.new_barge_cabin_radiobutton,
//                            barge_cabin_Rg, false);
                    final RadioButton rb = new RadioButton(this);
                    rb.setTag(i);
                    //将RadioButton的height设置为120dp
                    LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout
                            .LayoutParams.WRAP_CONTENT,
                            ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            120, getResources().getDisplayMetrics())));

                    rb.setLayoutParams(rlp);
                    rb.setGravity(Gravity.CENTER );
                    rb.setOnClickListener( new View.OnClickListener( ) {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(BargeCabinDetailActivity.this, "clicked: " + currentIndex, Toast
//                                    .LENGTH_SHORT).show();
                            //先将所有的待填写的TextView禁用
                            selectedIndex = (int)rb.getTag();
                            setTextViewClickOff();
                            //再将当前所选中的待填写的TextView启用
                            setSelectTextViewOn(selectedIndex);
                        }
                    });
                    barge_cabin_Rg.addView(rb);

                    LinearLayoutView = getLayoutInflater().inflate(R.layout.new_barge_cabin_linearlayout,
                            barge_cabin_info_Ll, false);
                    //barge_cabin_Rg.addView(RadioButtonView);
                    barge_cabin_info_Ll.addView(LinearLayoutView);

                    hatchnum_bargeCabin_TextViews[i] = (TextView)LinearLayoutView.findViewById(R.id.hatchnum_tv);
                    holdcapacity_bargeCabin_TextViews[i] = (TextView)LinearLayoutView.findViewById(R.id
                            .holdcapacity_tv);
                    hatchsize_bargeCabin_TextViews[i] = (TextView)LinearLayoutView.findViewById(R.id
                            .hatchsize_tv);
                    //select_rbs[i] = RadioButtonView.findViewById(R.id.select_rb);

                    hatchnum_bargeCabin_TextViews[i].setText(bcd.getHatchnum() + "");
                    holdcapacity_bargeCabin_TextViews[i].setText(bcd.getHoldcapacity() + "");
                    hatchsize_bargeCabin_TextViews[i].setText(bcd.getHatchsize() + "");

                    MySetOnClickListener(i,"num","舱口号",hatchnum_bargeCabin_TextViews[i],20);
                    MySetOnClickListener(i,"num","舱容",holdcapacity_bargeCabin_TextViews[i],21);
                    MySetOnClickListener(i,"num","舱口尺寸",hatchsize_bargeCabin_TextViews[i],22);
                }
                //先将所有的待填写的TextView禁用
                setTextViewClickOff();
            }

        }
    }

    public void setTextViewClickOff(){
        for (int i = 0; i < size ; i++){
            hatchnum_bargeCabin_TextViews[i].setClickable(false);
            holdcapacity_bargeCabin_TextViews[i].setClickable(false);
            hatchsize_bargeCabin_TextViews[i].setClickable(false);
        }
    }

    public void setSelectTextViewOn(int k){
        hatchnum_bargeCabin_TextViews[k].setClickable(true);
        holdcapacity_bargeCabin_TextViews[k].setClickable(true);
        hatchsize_bargeCabin_TextViews[k].setClickable(true);
    }

    public void MySetOnClickListener(final int index,final String type,final String content,
                                     final TextView tv, final int requestCode){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditActivity(index,type,content,tv.getText().toString().trim(),requestCode);
            }
        });
    }

    @OnClick({R.id.back_iv,R.id.item_edit_bagrgecabin_tv,R.id.item_delete_barge_cabin_tv,R.id.more_tv})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                Intent i = new Intent(BargeCabinModifyAndDeleteActivity.this, BargeDetailActivity.class);
                i.putExtra("bargeInfo",bargeInfo);
                startActivity(i);
                finish();
                break;
            case R.id.more_tv:
                popup_menu_Ll.setVisibility(View.VISIBLE);
                break;
            case R.id.item_edit_bagrgecabin_tv://提交
                popup_menu_Ll.setVisibility(View.GONE);
                if(selectedIndex == -1) {
                    Toast.makeText(BargeCabinModifyAndDeleteActivity.this,"请选择要提交的船舱",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    commitData();
                }
                break;
            case R.id.item_delete_barge_cabin_tv://删除
                popup_menu_Ll.setVisibility(View.GONE);
                if(selectedIndex == -1) {
                    Toast.makeText(BargeCabinModifyAndDeleteActivity.this,"请选择要删除的船舱",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String content = "确认删除该船舱？";
                    new AlertDialog.Builder(BargeCabinModifyAndDeleteActivity.this)
                            //.setTitle("提示")
                            .setMessage(content)
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    commitDeleteData();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
                break;
        }
    }

    public void startEditActivity(int i ,String type,String title,String content,int requestCode){
        Intent intent2 = new Intent(BargeCabinModifyAndDeleteActivity.this, EditValueActivity.class);
        intent2.putExtra("index",i);
        intent2.putExtra("type",type);
        intent2.putExtra("title",title);
        intent2.putExtra("content",content);
        startActivityForResult(intent2,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK == resultCode){
            String result = data.getStringExtra("result");
            int index = data.getIntExtra("index",-1);
            switch (requestCode){
                case 20:
                    hatchnum_bargeCabin_TextViews[index].setText(result);
                    break;
                case 21:
                    holdcapacity_bargeCabin_TextViews[index].setText(result);
                    break;
                case 22:
                    hatchsize_bargeCabin_TextViews[index].setText(result);
                    break;
            }
        }
    }

    public void commitData(){
        mCommittingDialog.show();
        int bargecabinid = bargeCabinDatas.get(selectedIndex).getBargecabinid();
        int bargeid = bargeInfo.getBargeid();
        int hatchnum = Integer.parseInt(hatchnum_bargeCabin_TextViews[selectedIndex].getText()
                .toString());
        float holdcapacity = Float.parseFloat(holdcapacity_bargeCabin_TextViews[selectedIndex]
                .getText().toString());
        float hatchsize = Float.parseFloat(hatchsize_bargeCabin_TextViews[selectedIndex].getText
                ().toString());
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

                                        Intent i = new Intent(BargeCabinModifyAndDeleteActivity.this,
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

    public void commitDeleteData(){
        mCommittingDialog.show();
        int bargecabinid = bargeCabinDatas.get(selectedIndex).getBargecabinid();
        OkHttpUtils
                .post()
                .url(Config.DELETE_BARGECABIN_URL)
                .addHeader("Check_Token",Config.TOKEN)
                .addParams("bargecabinid",bargecabinid + "")
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
                                        Intent i = new Intent(BargeCabinModifyAndDeleteActivity.this,
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
