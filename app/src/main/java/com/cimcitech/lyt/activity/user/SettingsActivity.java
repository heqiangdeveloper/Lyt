package com.cimcitech.lyt.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.main.ForgetPasswordActivity;
import com.cimcitech.lyt.activity.main.LoginActivity;
import com.cimcitech.lyt.utils.ApkUpdateUtil;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.DataCleanManager;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsActivity extends AppCompatActivity {
    @Bind(R.id.titleName_tv)
    TextView titleName_Tv;
    @Bind(R.id.clear_cache_tv)
    TextView clear_cache_Tv;
    @Bind(R.id.back_iv)
    ImageView back_Iv;
    @Bind(R.id.more_tv)
    TextView more_Tv;

    public DataCleanManager manager = null;
    private final Context context = SettingsActivity.this;
    private SharedPreferences sp;

    public static final String CALL_FINISH = "com.cimcitech.lyt.mainactivity.finish";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        sp = getSharedPreferences(Config.KEY_LOGIN_AUTO, MODE_PRIVATE);
        initTitle();
        checkCache();
    }

    public void checkCache(){
        //add for cache
        manager = new DataCleanManager();
        File file = context.getCacheDir() ;
        Log.d("hqlog",file.getPath());
        try{
            clear_cache_Tv.setText(manager.getTotalCacheSize(context));
        }catch(Exception e){
            clear_cache_Tv.setText("0.0KB");
            Log.d("hqlog","get total cache tell a exception:" + e);
        }
    }

    public void initTitle(){
        titleName_Tv.setText(getResources().getString(R.string.settings_title));
        back_Iv.setVisibility(View.VISIBLE);
        more_Tv.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_iv,R.id.clear_cache_linear,R.id.modify_psd_tv,R.id.cust_agree_tv,R.id
            .service_agree_tv,R.id.about_tv,R.id.check_version_tv,R.id.out_login})
    public void onclick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.clear_cache_linear://清除缓存
                new AlertDialog.Builder(context)
                        //.setTitle("提示")
                        .setMessage("是否要清除缓存？")
                        .setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                manager.clearAllCache(context);
                                clear_cache_Tv.setText("0.0KB");
                                Toast.makeText(context,"缓存已清理完毕！",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.modify_psd_tv://修改密码
                startActivity(new Intent(context, ModifyPasswordActivity2.class));
                break;
            case R.id.cust_agree_tv://用户协议
                break;
            case R.id.service_agree_tv://服务协议
                break;
            case R.id.about_tv://关于
                startActivity(new Intent(context, AboutActivity.class));
                break;
            case R.id.check_version_tv://检查版本
                break;
            case R.id.out_login:
                new AlertDialog.Builder(context)
                        //.setTitle("提示")
                        .setMessage("您确定要退出登录吗？")
                        .setCancelable(false)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ModifyUserInfoPreference();//清除登录信息
                                startActivity(new Intent(context, LoginActivity.class));
                                sendMainActivityFinishBroadcast();
                                finish();
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

    public void ModifyUserInfoPreference(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", "");
        editor.putInt("accountId", 0);
        editor.putString("token","");
        //  editor.putString("userName","");
        editor.putInt("accountType",-1);
        editor.commit();
    }

    public void sendMainActivityFinishBroadcast(){
        Intent i = new Intent();
        i.setAction(SettingsActivity.CALL_FINISH);
        LocalBroadcastManager.getInstance(SettingsActivity.this).sendBroadcast(i);
    }
}
