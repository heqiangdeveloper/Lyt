package com.cimcitech.lyt.activity.quoteprice;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.bargemanage.BargeAddAndModifyActivity;
import com.cimcitech.lyt.activity.bargemanage.BargeDetailActivity;
import com.cimcitech.lyt.activity.main.BaseActivity;
import com.cimcitech.lyt.activity.main.EditValueActivity;
import com.cimcitech.lyt.activity.main.MainActivity;
import com.cimcitech.lyt.adapter.user.PopupWindowAdapter;
import com.cimcitech.lyt.bean.bargemanage.BargeAddReq;
import com.cimcitech.lyt.bean.bargemanage.BargeInfo;
import com.cimcitech.lyt.bean.bargemanage.BargeListReq;
import com.cimcitech.lyt.bean.bargemanage.BargeModifyReq;
import com.cimcitech.lyt.bean.bargemanage.BargeVo;
import com.cimcitech.lyt.bean.quoteprice.StartToQuoteReq;
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


public class StartToQuoteActivity extends BaseActivity {
    @Bind(R.id.popup_menu_layout)
    LinearLayout popup_menu_Ll;
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.more_tv)
    TextView more_Tv;
    @Bind(R.id.quote_price_tv)
    TextView quote_price_Tv;
    @Bind(R.id.quote_ton_tv)
    TextView quote_ton_Tv;
    @Bind(R.id.choose_barge_tv)
    TextView choose_barge_Tv;

    private PopupWindow pop;
    private final int QUOTE_PRICE_REQUEST = 1;
    private final int QUOTE_TON_REQUEST = 2;
    private List<BargeInfo> data = new ArrayList<>();
    private BargeVo bargeVo;
    private int bargeId = -1;
    private int reqid = -1;
    private int detailid = -2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_to_quote);
        ButterKnife.bind(this);
        initTitle();
        initPopupMenu();
        reqid = getIntent().getIntExtra("transportreqid",-1);
        detailid = getIntent().getIntExtra("detailid",-2);
        getData();
    }

    public void initTitle(){
        titleName_Tv.setText(getResources().getString(R.string.my_start_quote_label));
        more_Tv.setVisibility(View.GONE);
        popup_menu_Ll.setVisibility(View.GONE);
    }

    public void initPopupMenu(){
        popup_menu_Ll.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_iv,R.id.choose_barge_tv,R.id.quote_price_tv,R.id.quote_ton_tv,R.id.commit_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.choose_barge_tv:
                showContactUsPopWin(StartToQuoteActivity.this,"选择驳船",data);
                pop.showAtLocation(view, Gravity.CENTER, 0, 0);
                break;
            case R.id.quote_price_tv:
                startEditActivity("num",getResources().getString(R.string.quote_price_label),
                        quote_price_Tv.getText().toString().trim(),QUOTE_PRICE_REQUEST);
                break;
            case R.id.quote_ton_tv:
                startEditActivity("num",getResources().getString(R.string.quote_ton_label),
                        quote_ton_Tv.getText().toString().trim(),QUOTE_TON_REQUEST);
                break;
            case R.id.commit_tv:
                commitData();
                break;
        }
    }

    public void showContactUsPopWin(Context context, String title, final List<BargeInfo> data) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.dialog_add_client_view, null);
        view.getBackground().setAlpha(100);
        // 创建PopupWindow对象
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        View pop_reward_view = view.findViewById(R.id.pop_reward_view);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setText(title);
        List<String> list = new ArrayList<>();
        for(BargeInfo bargeInfo : data){
            list.add(bargeInfo.getBargename());
        }
        final PopupWindowAdapter adapter = new PopupWindowAdapter(context, list);
        ListView listView = view.findViewById(R.id.listContent);
        listView.setAdapter(adapter);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop_reward_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                choose_barge_Tv.setText(data.get(position).getBargename());
                bargeId = data.get(position).getBargeid();
                pop.dismiss();
            }
        });
    }

    public void startEditActivity(String type,String title,String content,int requestCode){
        Intent intent2 = new Intent(StartToQuoteActivity.this, EditValueActivity.class);
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
            switch (requestCode){
                case QUOTE_PRICE_REQUEST:
                    quote_price_Tv.setText(result);
                    break;
                case QUOTE_TON_REQUEST:
                    quote_ton_Tv.setText(result);
                    break;
            }
        }
    }

    //获取驳船的名称列表
    public void getData() {
        mLoading.show();
        String json = new Gson().toJson(new BargeListReq(Config.ACCOUNTID, 1,100,""));
        OkHttpUtils
                .postString()
                .url(Config.QUERY_BARGE_URL)
                .addHeader("Check_Token",Config.TOKEN)
                .content(json)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                mLoading.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                mLoading.dismiss();
                                bargeVo = GjsonUtil.parseJsonWithGson(response,BargeVo.class);
                                if (bargeVo != null) {
                                    data.clear();
                                    if (bargeVo.isSuccess()) {
                                        BargeInfo bargeInfo = null;
                                        if (bargeVo.getData().getList() != null && bargeVo.getData().getList().size() > 0) {
                                            for (int i = 0; i < bargeVo.getData().getList().size(); i++) {
                                                bargeInfo = bargeVo.getData().getList().get(i);
                                                data.add(bargeInfo);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                );
    }

    //提交报价信息
    public void commitData(){
        mCommittingDialog.show();
        String ton = quote_ton_Tv.getText().toString().trim();
        String price = quote_price_Tv.getText().toString().trim();
        String json = new Gson().toJson(new StartToQuoteReq(detailid,reqid,bargeId,
                Float.parseFloat(ton),Float.parseFloat(price)));
        OkHttpUtils
                .postString()
                .url(Config.START_QUOTE_URL)
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
                                        Intent i2 = new Intent(StartToQuoteActivity.this, MainActivity.class);
                                        i2.putExtra("transportreqid",reqid);
                                        startActivity(i2);
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
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);//防止快速切换Fragment导致的FC-->不保存Fragment的状态，让其与activity一同消亡
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
