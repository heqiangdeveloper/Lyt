package com.cimcitech.lyt.activity.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.cimcitech.lyt.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qianghe on 2018/9/7.
 */

public class AgreementActivity extends BaseActivity {
    @Bind(R.id.agree_content_tv)
    TextView agree_content_Tv;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
        initView();
    }

    public void initView(){
        String content = getResources().getString(R.string.agree_content2);
        agree_content_Tv.setText(content);
    }

    @OnClick({R.id.back})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
