package com.cimcitech.lyt.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.bean.login.LoginVo;
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

    //17620465675  12345678 或13058015322  123

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
                        sp.getString("accountNo", "").length() != 0 &&
                        userNameTv.getText().toString().trim().equals(sp.getString("accountNo", ""))){
                    welcome_Tv1.setVisibility(View.VISIBLE);
                    welcome_Tv2.setVisibility(View.VISIBLE);
                    welcome_Tv1.setText("Hello!  " + sp.getString("userName", ""));
                    welcome_Tv2.setText("欢迎回来");
                }else{
                    welcome_Tv1.setVisibility(View.INVISIBLE);
                    welcome_Tv2.setVisibility(View.VISIBLE);
                    welcome_Tv2.setText("欢迎使用粮易通");
                }

                //暂时不要welcome1,welcome2
                welcome_Tv1.setVisibility(View.GONE);
                welcome_Tv2.setVisibility(View.GONE);
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

        String accountNo = sp.getString("accountNo","");
        String password = sp.getString("password","");
        int  accountId = sp.getInt("accountId",0);
        String token = sp.getString("token","");
        String userName = sp.getString("userName","");
        int accountType = sp.getInt("accountType",-1);

        //如果是已登录过的，直接跳过登录界面
        if(accountNo.length() != 0 && password.length() != 0 &&  accountId != 0 &&
                token.length() != 0 && userName.length() != 0 && accountType != -1){
            saveConfigs(accountNo,accountId,userName,token, accountType);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            if (accountNo != "") {
                userNameTv.setText(accountNo);
            }else{
                userNameTv.setText("");
            }
            passwordTv.setText("");
            if(userNameTv.getText().toString().trim().length() != 0){
                userNameTv.setSelection(userNameTv.getText().toString().trim().length());
            }
        }
    }

    //launchMode为singleTask的时候，通过Intent启到一个Activity,如果系统已经存在一个实例，系统就会将请求
    // 发送到这个实例上，但这个时候，系统就不会再调用通常情况下我们处理请求数据的onCreate方法，而是调用
    // onNewIntent方法，
    //不要忘记，系统可能会随时杀掉后台运行的Activity，如果这一切发生，那么系统就会调用onCreate方法，而
    // 不调用onNewIntent方法，一个好的解决方法就是在onCreate和onNewIntent方法中调用同一个处理数据的方法
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
                        sp.getString("accountNo", "").length() != 0 &&
                        userNameTv.getText().toString().trim().equals(sp.getString("accountNo", ""))){
                    welcome_Tv1.setVisibility(View.VISIBLE);
                    welcome_Tv2.setVisibility(View.VISIBLE);
                    welcome_Tv1.setText("Hello!  " + sp.getString("accountNo", ""));
                    welcome_Tv2.setText("欢迎回来");
                }else{
                    welcome_Tv1.setVisibility(View.INVISIBLE);
                    welcome_Tv2.setVisibility(View.VISIBLE);
                    welcome_Tv2.setText("欢迎使用粮易通");
                }

                //暂时不要welcome1,welcome2
                welcome_Tv1.setVisibility(View.GONE);
                welcome_Tv2.setVisibility(View.GONE);
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
        String accountNo = sp.getString("accountNo","");
        String password = sp.getString("password","");
        int  accountId = sp.getInt("accountId",0);
        String token = sp.getString("token","");
        String userName = sp.getString("userName","");
        int accountType = sp.getInt("accountType",-1);

        //如果是已登录过的，直接跳过登录界面
        if(accountNo.length() != 0 && password.length() != 0 &&  accountId != 0 &&
                token.length() != 0 && userName.length() != 0 && accountType != -1){
            Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent2);
            finish();
        }else{
            if (sp.getString("accountNo", "") != "") {
                userNameTv.setText(sp.getString("accountNo", ""));
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
    @OnClick({R.id.clear_name_iv, R.id.clear_password_iv,R.id.forget_psd_tv,R.id.login_bt,R.id.new_user_tv})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.clear_name_iv:
                userNameTv.setText("");
                break;
            case R.id.clear_password_iv:
                passwordTv.setText("");
                break;
            case R.id.forget_psd_tv://忘记密码
//                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
//                i.putExtra("type","Forget");
//                startActivity(i);
                break;
            case R.id.new_user_tv://新用户注册
                Intent i2 = new Intent(LoginActivity.this,RegisterFirstActivity.class);
                i2.putExtra("type","New");
                startActivity(i2);
                break;
            case R.id.login_bt:
                if(loginBt.isClickable()){
                    mLoginDialog.show();
                    getData();
                }
                break;
        }
    }

    public void getData() {
        final String psw = passwordTv.getText().toString().trim();
//        try
//        {
//            afterencrypt = RSAUtils.encrypt(psw,Config.PUBLIC_KEY);//加密后的密码
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this,"rsa 加密失败！！",Toast.LENGTH_SHORT).show();
//        }
        OkHttpUtils
                .post()
                .url(Config.USER_LOGIN_URL)
                .addParams("accountno", userNameTv.getText().toString().trim())
                .addParams("password", psw)
                .addParams("isApp",1 + "")
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                mLoginDialog.dismiss();
                                Toast.makeText(LoginActivity.this, e + "", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    loginVo = GjsonUtil.parseJsonWithGson(response, LoginVo.class);
                                    if (loginVo != null) {
                                        if (loginVo.isSuccess()) {
                                            String accountNo = loginVo.getData().getAccountNo();
                                            int accountId = loginVo.getData().getAccountId();
                                            String password = psw;
                                            String userName = loginVo.getData().getUserName();
                                            int accountType = loginVo.getData().getAccountType();
                                            String token = loginVo.getData().getToken();
                                            saveUserInfo(accountNo,accountId,password,userName,token,accountType);
                                            saveConfigs(accountNo,accountId,userName,token, accountType);

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                mLoginDialog.dismiss();
                            }
                        }
                );
    }

    /***
     * 保存账户与密码
     */
    private void saveUserInfo(String accountNo,int accountId,String password,String userName,
                              String token,int accountType) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("accountNo", accountNo);
        editor.putInt("accountId", accountId);
        editor.putString("password", password);
        editor.putString("userName",userName);
        editor.putString("token",token);
        editor.putInt("accountType", accountType);
        editor.commit();
    }

    //保存全局变量
    private void saveConfigs(String accountNo,int accountId,String userName,String token,
                             int accountType){
        Config.USERNAME = userName;
        Config.ACCOUNTID = accountId;
        Config.ACCOUNTNO = accountNo;
        Config.ACCOUNTTYPE = accountType;
        Config.TOKEN = token;
    }
}
