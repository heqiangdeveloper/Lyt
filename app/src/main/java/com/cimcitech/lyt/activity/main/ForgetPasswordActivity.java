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
import android.widget.TextView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.bean.register.RegisterAccountBean;
import com.cimcitech.lyt.bean.register.RegisterAdminerBean;
import com.cimcitech.lyt.bean.register.RegisterReq;
import com.cimcitech.lyt.bean.register.RegisterVo;
import com.cimcitech.lyt.bean.register.VerificationCodeReq;
import com.cimcitech.lyt.bean.register.VerificationCodeVo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.widget.CountDownTimerButton;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class ForgetPasswordActivity extends BaseActivity {

    @Bind(R.id.user_name_tv)
    EditText userNameTv;
    @Bind(R.id.clear_name_iv)
    ImageView clear_name_Iv;
    @Bind(R.id.password_tv)
    EditText password_Tv;
    @Bind(R.id.clear_password_iv)
    ImageView clear_password_Iv;
    @Bind(R.id.verification_code_tv)
    EditText verification_code_Tv;
    @Bind(R.id.register_bt)
    Button register_Bt;
    @Bind(R.id.title)
    TextView title_Tv;
    @Bind(R.id.time_bt)
    CountDownTimerButton time_Bt;

    private VerificationCodeVo verificationCodeVo;
    private RegisterVo registerVo;
    private SharedPreferences sp;
    private boolean isRegister = true;
    private String name = "";
    private String idcard = "";

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
        setContentView(R.layout.activity_register_second);
        ButterKnife.bind(this);

        initView();
        userNameTv.setFocusable(true);//获取焦点
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        textWatcher(userNameTv);
        textWatcher(verification_code_Tv);
        textWatcher(password_Tv);

        userNameTv.setText("");
        verification_code_Tv.setText("");
        password_Tv.setText("");
        userNameTv.setFocusableInTouchMode(true);
    }

    public void initView(){
        title_Tv.setText(getResources().getString(R.string.forget_password_title));
        register_Bt.setText(getResources().getString(R.string.forget_password_button_label));
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
                        password_Tv.getText().toString().trim().length() != 0 ){
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
        register_Bt.setTextColor(getResources().getColor(R.color.white));
    }

    public void registerBtnOff(){
        register_Bt.setBackgroundResource(R.drawable.shape_login_button_off);
        register_Bt.setClickable(false);
        register_Bt.setTextColor(getResources().getColor(R.color.login_off_color));
    }

    @OnClick({R.id.clear_name_iv,R.id.clear_password_iv,R.id.register_bt,
            R.id.time_bt,R.id.back,R.id.agree_tv})
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
            case R.id.time_bt://注意，该time_bt是自定义的倒计时按钮
                String userName = userNameTv.getText().toString().trim();
                if(isUserNameValid(userName)){//验证是否是合法的手机号
                    Config.isUserNameVaild = true;
                    getVerificationCode(userName);//发送验证码
                }else{
                    Config.isUserNameVaild = false;
                    Toast.makeText(this,"请先输入合法的手机号！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_bt:
                //if (!checkInput()) return;
                if(register_Bt.isClickable()){
                    mCommittingDialog.show();
                    getData();
                }
                break;
            case R.id.agree_tv:
                startActivity(new Intent(ForgetPasswordActivity.this,AgreementActivity.class));
                break;
        }
    }

    public void getVerificationCode(String phoneNum){
        String json = new Gson().toJson(new VerificationCodeReq(phoneNum));
        OkHttpUtils
                //.postString()
                .post()
                .url(Config.VERIFICATION_CODE_URL)
                //.mediaType(MediaType.parse("application/json; charset=utf-8"))
                //.content(json)
                .addParams("accountno",phoneNum)
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.d("veriflog","get getVerificationCode fail...");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    verificationCodeVo = GjsonUtil.parseJsonWithGson(response, VerificationCodeVo.class);
                                    if (verificationCodeVo != null) {
                                        if(verificationCodeVo.isSuccess()){//获取验证码成功
                                            Toast.makeText(ForgetPasswordActivity.this,"已将验证码发送至你的手机，请查收",
                                                    Toast.LENGTH_SHORT).show();
                                        }else{//获取验证码失败
                                            Toast.makeText(ForgetPasswordActivity.this, verificationCodeVo.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }catch (Exception e){
                                    Log.d("veriflog","exception: " + e);
                                }
                            }
                        }
                );
    }

    public boolean isUserNameValid(String s) {
        return Pattern.matches(REGEX_MOBILE,s) ? true : false;
    }

    public void getData() {
        String accountno = userNameTv.getText().toString().trim();//手机号
        String inputCheckCode = verification_code_Tv.getText().toString().trim();//验证码
        String password = password_Tv.getText().toString().trim();//密码

        OkHttpUtils
                .post()
                .url(Config.FORGET_PASSWORD_URL)
                .addParams("accountno",accountno)
                .addParams("inputCheckCode",inputCheckCode)
                .addParams("password",password)
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                //ToastUtil.showNetError();
                                mCommittingDialog.dismiss();
                                Toast.makeText(ForgetPasswordActivity.this, "网络错误，请检查网络",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.d("regislog","register response is: " + response);
                                mCommittingDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String msg = jsonObject.getString("msg");
                                    if (jsonObject != null) {
                                        if (jsonObject.getBoolean("success")) {
                                           // Config.isLogin = true;
                                            Toast.makeText(ForgetPasswordActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Toast.makeText(ForgetPasswordActivity.this, msg,Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(ForgetPasswordActivity.this, "注册失败",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
    }

    public boolean checkInput() {
        String username = userNameTv.getText().toString().trim();
        String psw = password_Tv.getText().toString().trim();
        String verification_code = verification_code_Tv.getText().toString().trim();
        if (username.equals("")) {
            Toast.makeText(ForgetPasswordActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (psw.equals("")) {
            Toast.makeText(ForgetPasswordActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (verification_code.equals("")) {
            Toast.makeText(ForgetPasswordActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }

        /*if(!isUserNameValid(username)){//验证合法性
            Toast.makeText(RegisterActivity.this, "请输入合法的手机或邮箱号码", Toast.LENGTH_SHORT).show();
            verification_code_Tv.setText("");
            return false;
        }*/
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
        finish();
    }
}
