package com.cimcitech.lyt.activity.bargemanage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.main.BaseActivity;
import com.cimcitech.lyt.activity.main.EditValueActivity;
import com.cimcitech.lyt.bean.bargemanage.BargeAddReq;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinData;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinVo;
import com.cimcitech.lyt.bean.bargemanage.BargeInfo;
import com.cimcitech.lyt.bean.bargemanage.BargeModifyReq;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.DateTool;
import com.cimcitech.lyt.utils.ToastUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;


public class BargeAddAndModifyActivity extends BaseActivity {
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.commit_tv)
    TextView commit_Tv;
    @Bind(R.id.sv)
    ScrollView sv_sv;
    @Bind(R.id.back_iv)
    ImageView back_Iv;

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
    @Bind(R.id.netTon_tv)
    TextView netTon_Tv;
    @Bind(R.id.deadweightton_tv)
    TextView deadweightton_Tv;
    @Bind(R.id.picture_tv)
    TextView picture_Tv;
    @Bind(R.id.shipcertificatepicture_tv)
    TextView shipcertificatepicture_Tv;
    @Bind(R.id.popup_menu_layout)
    LinearLayout popup_menu_Ll;

    private String type;
    private final int DATE_DIALOG = 1;
    private int mYear, mMonth, mDay;
    private int bargeid = 0;
    private int size = 0;
    private int index= 0;
    private TextView[] hatchnum_bargeCabin_TextViews;
    private TextView[] holdcapacity_bargeCabin_TextViews;
    private TextView[] hatchsize_bargeCabin_TextViews;
    private BargeInfo bargeInfo = null;

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barge_add_and_modify);
        ButterKnife.bind(this);
        initTitle();

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        type = getIntent().getStringExtra("type");
        if(type.equals("add")){//新增
            titleName_Tv.setText(getResources().getString(R.string.barge_add_title));
        }else{//修改
            titleName_Tv.setText(getResources().getString(R.string.barge_edit_title));
            bargeInfo = (BargeInfo) getIntent().getSerializableExtra("bargeInfo");
            //List<BargeCabinData> bargeCabinDatas = getIntent().getParcelableArrayListExtra
            //         ("bargeCabinDatas");
            bargeid = bargeInfo.getBargeid();
            initView(bargeInfo);
        }
    }

    public void initTitle(){
        more_Tv.setVisibility(View.GONE);
        popup_menu_Ll.setVisibility(View.GONE);
        commit_Tv.setVisibility(View.VISIBLE);

        commit_Tv.setText(getResources().getString(R.string.barge_commit));
        createtime_Tv.setText(DateTool.getSystemDate());
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
            deadweightton_Tv.setText(bargeInfo.getDeadweightton() + "");
            netTon_Tv.setText(bargeInfo.getNetton() + "");
            picture_Tv.setText(getResources().getString(R.string.not_upload));
            shipcertificatepicture_Tv.setText(getResources().getString(R.string.not_upload));
        }
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

    @OnClick({R.id.back_iv,R.id.commit_tv,R.id.bargename_tv,R.id.nationality_tv,
              R.id.length_tv,R.id.width_tv,R.id.grosston_tv,R.id.deadweightton_tv,
              R.id.netTon_tv,R.id.builttime_tv})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                if(type.equals("add")) {//新增
                    finish();
                }else{
                    Intent i2 = new Intent(BargeAddAndModifyActivity.this, BargeDetailActivity.class);
                    i2.putExtra("bargeInfo",bargeInfo);
                    startActivity(i2);
                    finish();
                }
                break;
            case R.id.commit_tv:
                if (!checkInput()) return;
                commitData();
                break;
            case R.id.builttime_tv:
                showDialog(DATE_DIALOG);
                break;
            case R.id.bargename_tv:
                startEditActivity(0,"str","船名",bargename_Tv.getText().toString().trim(),0);
                break;
            case R.id.nationality_tv:
                startEditActivity(0,"str","船籍",nationality_Tv.getText().toString().trim(),1);
                break;
            case R.id.length_tv:
                startEditActivity(0,"num","船长",length_Tv.getText().toString().trim(),2);
                break;
            case R.id.width_tv:
                startEditActivity(0,"num","船宽",width_Tv.getText().toString().trim(),3);
                break;
            case R.id.grosston_tv:
                startEditActivity(0,"num","总吨位",grosston_Tv.getText().toString().trim(),4);
                break;
            case R.id.deadweightton_tv:
                startEditActivity(0,"num","载重吨位",deadweightton_Tv.getText().toString().trim(),5);
                break;
            case R.id.netTon_tv:
                startEditActivity(0,"num","净吨位",netTon_Tv.getText().toString().trim(),6);
                break;
        }
    }

    public boolean checkInput() {
        if(createtime_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入注册时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(bargename_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入船名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(nationality_Tv.getText().toString().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入船籍", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(builttime_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入建造时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(length_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入船长", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(width_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入船宽", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(builttime_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入建造时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(grosston_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入总吨位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(netTon_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入净吨位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(builttime_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入建造时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(deadweightton_Tv.getText().toString().trim().length() == 0){
            Toast.makeText(BargeAddAndModifyActivity.this, "请输入载重吨位", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void startEditActivity(int i ,String type,String title,String content,int requestCode){
        Intent intent2 = new Intent(BargeAddAndModifyActivity.this, EditValueActivity.class);
        //intent2.putExtra("index",i);
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
                case 0:
                    bargename_Tv.setText(result);
                    break;
                case 1:
                    nationality_Tv.setText(result);
                    break;
                case 2:
                    length_Tv.setText(result);
                    break;
                case 3:
                    width_Tv.setText(result);
                    break;
                case 4:
                    grosston_Tv.setText(result);
                    break;
                case 5:
                    deadweightton_Tv.setText(result);
                    break;
                case 6:
                    netTon_Tv.setText(result);
                    break;

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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        String string = (new StringBuffer()
                .append(mYear).append("-")
                .append(mMonth + 1).append("-")
                .append(mDay).append(""))
                .toString();
        string = DateTool.transferDateStr(string);//转化为标准日期
        try {
            SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd");
            Date createDate = foramt.parse(createtime_Tv.getText().toString().trim());//注册时间
            Date mDate = foramt.parse(string);//建造时间
            if (mDate.compareTo(createDate) > 0) {
                ToastUtil.showToast("建造时间不能超过注册时间！");
            } else {
                builttime_Tv.setText(string);
                //deliveryDateTv.setTextColor(Color.parseColor("#333333"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void commitData(){
        mCommittingDialog.show();

        String url = "";
        String accountid = Config.ACCOUNTID + "";
        String nationality = nationality_Tv.getText().toString();
        String bargename = bargename_Tv.getText().toString();
        String createtime = createtime_Tv.getText().toString();
        String builttime = builttime_Tv.getText().toString();
        float grosston = grosston_Tv.getText().toString().length() != 0 ?
                Float.parseFloat(grosston_Tv.getText().toString()) : 0;//总吨位
        float netTon = netTon_Tv.getText().toString().length() != 0 ?
                Float.parseFloat(netTon_Tv.getText().toString()) : 0;//净吨位
        float deadweightton = deadweightton_Tv.getText().toString().length() != 0 ?
                Float.parseFloat(deadweightton_Tv.getText().toString()) : 0;//载重吨位
        float width = width_Tv.getText().toString().length() != 0 ?
                Float.parseFloat(width_Tv.getText().toString()) : 0;
        float length = length_Tv.getText().toString().length() != 0 ?
                Float.parseFloat(length_Tv.getText().toString()) : 0;
        String json = "";
        if(type.equals("add")){//新增
            url = Config.ADD_BARGE_URL;
            json = new Gson().toJson(new BargeAddReq(accountid,
                    nationality,
                    bargename,
                    createtime,
                    builttime,
                    grosston,
                    netTon,
                    deadweightton,
                    length,
                    width));
        }else{//更新
            url = Config.UPDATE_BARGE_URL;
            json = new Gson().toJson(new BargeModifyReq(bargeid,accountid,
                    nationality,
                    bargename,
                    createtime,
                    builttime,
                    grosston,
                    netTon,
                    deadweightton,
                    length,
                    width));
        }

        final BargeInfo bargeInfo2 = new BargeInfo(bargename,
                nationality,
                deadweightton,
                width,
                netTon,
                length,
                grosston,
                builttime,
                createtime,
                bargeid);
        OkHttpUtils
                .postString()
                .url(url)
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

                                        //向BargeDetailActivity发送广播，通知其更新数据
                                        Intent i = new Intent();
                                        i.setAction(BargeDetailActivity.REFRESH_BARGE_BROADCAST);
                                        LocalBroadcastManager.getInstance(BargeAddAndModifyActivity.this).sendBroadcast(i);
                                        if(type.equals("add")) {//新增
                                            //do nothing
                                        }else{//修改驳船信息
                                            Intent i2 = new Intent(BargeAddAndModifyActivity.this, BargeDetailActivity.class);
                                            i2.putExtra("bargeInfo",bargeInfo2);
                                            startActivity(i2);
                                        }
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
}
