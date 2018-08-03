package com.cimcitech.lyt.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.bean.login.LoginVo;
import com.cimcitech.lyt.rsa.RSAUtils;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.user_name_tv)
    EditText userNameTv;
    @Bind(R.id.password_tv)
    EditText passwordTv;
    @Bind(R.id.login_bt)
    Button loginBt;
    @Bind(R.id.forget_psd_tv)
    TextView forget_psd_Tv;
    @Bind(R.id.new_user_tv)
    TextView new_user_Tv;
    @Bind(R.id.welcome_tv1)
    TextView welcome_Tv1;
    @Bind(R.id.welcome_tv2)
    TextView welcome_Tv2;

    private LoginVo loginVo;
    private SharedPreferences sp;

    private String afterencrypt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences(Config.KEY_LOGIN_AUTO, MODE_PRIVATE);//如果存在则打开它，否则创建新的Preferences

        userNameTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(userNameTv.getText().toString().trim().length() != 0 &&
                        passwordTv.getText().toString().trim().length() != 0){
                    loginBtnOn();
                }else{
                    loginBtnOff();
                }
                if(userNameTv.getText().toString().trim().length() != 0 &&
                        sp.getString("user_name", "").length() != 0 &&
                        userNameTv.getText().toString().trim().equals(sp.getString("user_name", ""))){
                    welcome_Tv1.setVisibility(View.VISIBLE);
                    welcome_Tv2.setVisibility(View.VISIBLE);
                    welcome_Tv1.setText("Hello!  " + sp.getString("user_name", ""));
                    welcome_Tv2.setText("欢迎回来");
                }else{
                    welcome_Tv1.setVisibility(View.INVISIBLE);
                    welcome_Tv2.setVisibility(View.VISIBLE);
                    welcome_Tv2.setText("欢迎使用管理平台");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(userNameTv.getText().toString().trim().length() != 0 &&
                        passwordTv.getText().toString().trim().length() != 0){
                    loginBtnOn();
                }else{
                    loginBtnOff();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //如果是已登录过的，直接跳过登录界面
        if(sp.getString("user_name","").length() != 0 &&
                sp.getString("password","").length() != 0 &&
                sp.getString("realName","").length() != 0 &&
                sp.getLong("userId",0) != 0){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            if (sp.getString("user_name", "") != "") {
                userNameTv.setText(sp.getString("user_name", ""));
            }else{
                userNameTv.setText("");
            }
            passwordTv.setText("");
            if(userNameTv.getText().toString().trim().length() != 0){
                userNameTv.setSelection(userNameTv.getText().toString().trim().length());
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//不能省
        userNameTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(userNameTv.getText().toString().trim().length() != 0 &&
                        passwordTv.getText().toString().trim().length() != 0){
                    loginBtnOn();
                }else{
                    loginBtnOff();
                }
                if(userNameTv.getText().toString().trim().length() != 0 &&
                        sp.getString("user_name", "").length() != 0 &&
                        userNameTv.getText().toString().trim().equals(sp.getString("user_name", ""))){
                    welcome_Tv1.setVisibility(View.VISIBLE);
                    welcome_Tv2.setVisibility(View.VISIBLE);
                    welcome_Tv1.setText("Hello!  " + sp.getString("user_name", ""));
                    welcome_Tv2.setText("欢迎回来");
                }else{
                    welcome_Tv1.setVisibility(View.INVISIBLE);
                    welcome_Tv2.setVisibility(View.VISIBLE);
                    welcome_Tv2.setText("欢迎使用管理平台");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(userNameTv.getText().toString().trim().length() != 0 &&
                        passwordTv.getText().toString().trim().length() != 0){
                    loginBtnOn();
                }else{
                    loginBtnOff();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //如果是已登录过的，直接跳过登录界面
        if(sp.getString("user_name","").length() != 0 &&
                sp.getString("password","").length() != 0 &&
                sp.getString("realName","").length() != 0 &&
                sp.getLong("userId",0) != 0){
            Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent2);
            finish();
        }else{
            if (sp.getString("user_name", "") != "") {
                userNameTv.setText(sp.getString("user_name", ""));
            }else{
                userNameTv.setText("");
            }
            passwordTv.setText("");
            if(userNameTv.getText().toString().trim().length() != 0){
                userNameTv.setSelection(userNameTv.getText().toString().trim().length());
            }
        }
    }

    public void loginBtnOn(){
        loginBt.setBackgroundResource(R.drawable.shape_login_button_on);
        loginBt.setClickable(true);
    }

    public void loginBtnOff(){
        loginBt.setBackgroundResource(R.drawable.shape_login_button_off);
        loginBt.setClickable(false);
    }

    public void showLoginBtn(){
        String username = userNameTv.getText().toString().trim();
        String password = passwordTv.getText().toString().trim();
        if(username.length() != 0 && password.length() != 0){
            loginBtnOn();
        }else{
            loginBtnOff();
        }
    }
    @OnClick({R.id.clear_name_iv, R.id.clear_password_iv,R.id.forget_psd_tv,R.id.login_bt,R.id
            .new_user_tv})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.clear_name_iv:
                userNameTv.setText("");
                break;
            case R.id.clear_password_iv:
                passwordTv.setText("");
                break;
            case R.id.forget_psd_tv:
                break;
            case R.id.new_user_tv://新用户注册
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                //finish();
                break;
            case R.id.login_bt:
                //mLoading.show();
//                if(loginBt.isClickable()){
//                    mLoginDialog.show();
//                    getData();
//                }
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
                break;
        }
    }

    public void getData() {
        String psw = passwordTv.getText().toString().trim();
        try
        {
            afterencrypt = RSAUtils.encrypt(psw,Config.PUBLIC_KEY);//加密后的密码
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"rsa 加密失败！！",Toast.LENGTH_SHORT).show();
        }
        if(null != afterencrypt){
            Log.d("loginlog","afterencrypt is: " + afterencrypt);
            OkHttpUtils
                    .post()
                    .url(Config.USER_LOGIN_URL)
                    .addParams("userName", userNameTv.getText().toString().trim())
                    .addParams("password", afterencrypt)
                    .build()
                    .execute(
                            new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    //ToastUtil.showNetError();
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    Log.d("heqlogin","error is: " + e);
                                    mLoginDialog.dismiss();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.d("heqlogin",response);
                                    try {
                                        loginVo = GjsonUtil.parseJsonWithGson(response, LoginVo.class);
                                        if (loginVo != null) {
                                            Toast.makeText(LoginActivity.this, loginVo.getMsg(), Toast
                                                    .LENGTH_SHORT).show();
                                            if (loginVo.isSuccess()) {
                                                saveUserInfo();
//                                                Config.userName = userNameTv.getText().toString().trim();
//                                                Config.realName = loginVo.getData().getRealname();
//                                                Config.password = passwordTv.getText().toString().trim();
//                                                Config.userId = loginVo.getData().getUserId();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                           // ToastUtil.showToast("登录失败");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    mLoginDialog.dismiss();
                                }
                            }
                    );
        }
    }

    /***
     * 保存账户与密码
     */
    private void saveUserInfo() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_name", userNameTv.getText().toString().trim());
        editor.putString("password", afterencrypt);
//        editor.putString("realName", loginVo.getData().getRealname());
//        editor.putLong("userId", loginVo.getData().getUserId());
        editor.commit();
    }
}
