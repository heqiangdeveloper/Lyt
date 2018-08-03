package com.cimcitech.lyt.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.bean.register.RegisterVo;
import com.cimcitech.lyt.bean.register.VerificationCodeVo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.widget.CountDownTimerButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.user_name_tv)
    EditText userNameTv;
    @Bind(R.id.clear_name_iv)
    ImageView clear_name_Iv;
    @Bind(R.id.password_tv)
    EditText password_Tv;
    @Bind(R.id.clear_password_iv)
    ImageView clear_password_Iv;
    @Bind(R.id.password_tv2)
    EditText password_Tv2;
    @Bind(R.id.clear_password_iv2)
    ImageView clear_password_Iv2;
    @Bind(R.id.verification_code_tv)
    EditText verification_code_Tv;
    @Bind(R.id.register_bt)
    Button register_Bt;
    @Bind(R.id.time_bt)
    CountDownTimerButton time_Bt;

    private VerificationCodeVo verificationCodeVo;
    private RegisterVo registerVo;
    private SharedPreferences sp;

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        sp = this.getSharedPreferences(Config.KEY_LOGIN_AUTO, MODE_PRIVATE);//如果存在则打开它，否则创建新的Preferences
        userNameTv.setFocusable(true);//获取焦点
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        textWatcher(userNameTv);
        textWatcher(verification_code_Tv);
        textWatcher(password_Tv);
        textWatcher(password_Tv2);

        userNameTv.setText("");
        verification_code_Tv.setText("");
        password_Tv.setText("");
        password_Tv2.setText("");
        userNameTv.setFocusableInTouchMode(true);
    }

    public void textWatcher(EditText tv){
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(userNameTv.getText().toString().trim().length() != 0 &&
                        verification_code_Tv.getText().toString().trim().length() != 0 &&
                        password_Tv.getText().toString().trim().length() != 0 &&
                        password_Tv2.getText().toString().trim().length() != 0){
                    registerBtnOn();
                }else{
                    registerBtnOff();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void registerBtnOn(){
        register_Bt.setBackgroundResource(R.drawable.shape_login_button_on);
        register_Bt.setClickable(true);
    }

    public void registerBtnOff(){
        register_Bt.setBackgroundResource(R.drawable.shape_login_button_off);
        register_Bt.setClickable(false);
    }


    @OnClick({R.id.clear_name_iv,R.id.clear_password_iv,R.id.clear_password_iv2,R.id.register_bt,
            R.id.time_bt,R.id.back})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear_name_iv:
                userNameTv.setText("");
                break;
            case R.id.clear_password_iv:
                password_Tv.setText("");
                break;
            case R.id.clear_password_iv2:
                password_Tv2.setText("");
                break;
            case R.id.time_bt:
                String userName = userNameTv.getText().toString().trim();
                if(userName.length() != 0){
                    Config.isUserNameVaild = true;
                    getVerificationCode();//发送验证码
                }else{
                    Config.isUserNameVaild = false;
                    Toast.makeText(this,"请先输入合法的手机或邮箱号码！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_bt:
                //if (!checkInput()) return;
                if(register_Bt.isClickable()){
                    mCommittingDialog.show();
                    getData();
                }
                break;
        }
    }

    public void getVerificationCode(){
        String userName = userNameTv.getText().toString().trim();
        //if(userName.length() != 0 && isUserNameValid(userName)) {
        if(userName.length() != 0 ) {
            Toast.makeText(this,"已将验证码发送至你的手机或邮箱，请查收",Toast.LENGTH_SHORT).show();
            OkHttpUtils
                    .get()
                    .url(Config.VERIFICATION_CODE_URL)
                    .addParams("userName", userName)
                    .build()
                    .execute(
                            new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    //ToastUtil.showNetError();
                                    //mLoading.dismiss();
                                    Log.d("veriflog","get getVerificationCode fail...");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.d("veriflog",response);
                                    try {
                                        verificationCodeVo = GjsonUtil.parseJsonWithGson(response, VerificationCodeVo.class);
                                        if (verificationCodeVo != null) {
                                            if(verificationCodeVo.isSuccess()){//获取验证码成功

                                            }else{//获取验证码失败

                                            }
                                            Toast.makeText(RegisterActivity.this,
                                                    verificationCodeVo.getMsg(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception e){
                                        Log.d("veriflog","exception: " + e);
                                    }
                                }
                            }
                    );
        }else {
            Toast.makeText(this,"请先输入合法的手机或邮箱号码！",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isUserNameValid(String s) {
        return Pattern.matches(REGEX_MOBILE,s) || Pattern.matches(REGEX_EMAIL,s) ? true : false;
    }

    public void getData() {
        String userName = userNameTv.getText().toString().trim();
        String password = password_Tv.getText().toString().trim();
        String verifyCode = verification_code_Tv.getText().toString().trim();//验证码
        //if(userName.length() != 0 && isUserNameValid(userName)){
        if(userName.length() != 0){
            OkHttpUtils
                    .post()
                    .url(Config.REGISTER_USER_URL)
                    .addParams("userName", userName)
                    .addParams("password", password)
                    .addParams("verifyCode", verifyCode)
                    .build()
                    .execute(
                            new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    //ToastUtil.showNetError();
                                    mCommittingDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "网络错误，请检查网络",Toast
                                            .LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.d("regislog","register response is: " + response);
                                    mCommittingDialog.dismiss();
                                    try {
                                        registerVo = GjsonUtil.parseJsonWithGson(response, RegisterVo.class);
                                        if (registerVo != null) {
                                            if (registerVo.isSuccess()) {
                                                Config.isLogin = true;
                                                Toast.makeText(RegisterActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(i);
                                                finish();
                                            }else{
                                                Toast.makeText(RegisterActivity.this, registerVo.getMsg(),Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "注册失败",Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
        }else {
            Toast.makeText(this,"请输入合法的手机或邮箱号码！",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkInput() {
        String username = userNameTv.getText().toString().trim();
        String psw = password_Tv.getText().toString().trim();
        String psw2 = password_Tv2.getText().toString().trim();
        String verification_code = verification_code_Tv.getText().toString().trim();
        if (username.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (psw.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (psw2.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (verification_code.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }

        /*if(!isUserNameValid(username)){//验证合法性
            Toast.makeText(RegisterActivity.this, "请输入合法的手机或邮箱号码", Toast.LENGTH_SHORT).show();
            verification_code_Tv.setText("");
            return false;
        }*/
        if (!psw.equals(psw2)) {
            Toast.makeText(RegisterActivity.this, "两次输入的密码不一致，请确认", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}
