package com.cimcitech.lyt.activity.user;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.activity.main.BaseActivity;
import com.cimcitech.lyt.rsa.RSAUtils;
import com.cimcitech.lyt.utils.Config;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ModifyPasswordActivity extends BaseActivity {

    @Bind(R.id.back_rl)
    ImageView backRl;
    @Bind(R.id.old_psd_tv)
    EditText old_psd_Tv;
    @Bind(R.id.new_psd_tv1)
    EditText new_psd_Tv1;
    @Bind(R.id.new_psd_tv2)
    EditText new_psd_Tv2;
    @Bind(R.id.commit_bt)
    Button commit_Bt;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psd);
        ButterKnife.bind(this);
        sp = this.getSharedPreferences(Config.KEY_LOGIN_AUTO, MODE_PRIVATE);//如果存在则打开它，否则创建新的Preferences

        commit_Bt.setClickable(false);
        addTextWatcher(old_psd_Tv);
        addTextWatcher(new_psd_Tv1);
        addTextWatcher(new_psd_Tv2);

        old_psd_Tv.setText("");
        new_psd_Tv1.setText("");
        new_psd_Tv2.setText("");
        //键盘的弹出跟手机有关，有的手机即使加了如下代码，键盘依旧不弹出
        /*InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);*/
    }

    public void addTextWatcher(EditText et ){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(old_psd_Tv.getText().toString().trim().length() != 0 &&
                        new_psd_Tv1.getText().toString().trim().length() != 0 &&
                        new_psd_Tv2.getText().toString().trim().length() != 0){
                    commitBtnOn();
                }else{
                    commitBtnOff();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void commitBtnOn(){
        commit_Bt.setBackgroundResource(R.drawable.shape_modify_psd_button_on);
        commit_Bt.setClickable(true);
        commit_Bt.setTextColor(Color.WHITE);
    }

    public void commitBtnOff(){
        commit_Bt.setBackgroundResource(R.drawable.shape_modify_psd_button_off);
        commit_Bt.setClickable(false);
        commit_Bt.setTextColor(Color.parseColor("#CECECE"));
    }

    @OnClick({R.id.commit_bt,R.id.back_rl})
    public void onclick(View view) {
        switch (view.getId()){
            case R.id.back_rl:
                finish();
                break;
            case R.id.commit_bt:
                if(commit_Bt.isClickable()){
                    //格式检查
                    if(!checkInput()){
                        return;
                    }
                    mLoading.show();
                    //提交至服务器
                    commitData(Config.userId,new_psd_Tv2.getText().toString().trim());
                }
                break;
        }
    }

    public boolean checkInput(){
        String oldPsd = old_psd_Tv.getText().toString().trim();
        String newPsd1 = new_psd_Tv1.getText().toString().trim();
        String newPsd2 = new_psd_Tv2.getText().toString().trim();
        if(oldPsd.length() == 0){
            Toast.makeText(ModifyPasswordActivity.this,"请输入原密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(newPsd1.length() == 0 ){
            Toast.makeText(ModifyPasswordActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(newPsd2.length() == 0 ){
            Toast.makeText(ModifyPasswordActivity.this,"请再次输入新密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        String password = RSAUtils.decrypt(sp.getString("password",""), Config.STR_PRI_KEY);//解密原始密码
        if(password.length() != 0 && !oldPsd.equals(password)){
            Toast.makeText(ModifyPasswordActivity.this,"原密码错误，请确认！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!newPsd1.equals(newPsd2)){
            Toast.makeText(ModifyPasswordActivity.this,"两次输入的新密码不一致，请确认！",Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    public void commitData(Long userId,String newPsd){
        //对新密码进行加密
        try{
            newPsd = RSAUtils.encrypt(newPsd,Config.PUBLIC_KEY);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"rsa 加密失败！！",Toast.LENGTH_SHORT).show();
        }
        OkHttpUtils
                .post()
                .url(Config.MODIFY_PASSWORD)
                .addParams("userId", userId + "")
                .addParams("password", newPsd)
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                mLoading.dismiss();
                                //ToastUtil.showNetError();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                mLoading.dismiss();
                                Log.d(TAG,"modify psd response is:" + response);
                                try{
                                    JSONObject json = new JSONObject(response);
                                    if (json.getBoolean("success")) {
                                        ModifyUserInfoPreference();//修改SharedPreference中的密码
                                        Toast.makeText(ModifyPasswordActivity.this, "密码修改成功", Toast
                                                .LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(ModifyPasswordActivity.this, json
                                                .getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e){
                                    Toast.makeText(ModifyPasswordActivity.this, "密码修改失败", Toast
                                            .LENGTH_SHORT).show();
                                }
                            }
                        });
    }

    public void ModifyUserInfoPreference(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", RSAUtils.encrypt(new_psd_Tv1.getText().toString().trim(),Config.PUBLIC_KEY));
        editor.commit();
    }
}
