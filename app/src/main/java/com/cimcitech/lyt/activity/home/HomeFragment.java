package com.cimcitech.lyt.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.adapter.home.HomeGridAdapter;
import com.cimcitech.lyt.widget.MyGridView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

public class HomeFragment extends Fragment {

    @Bind(R.id.homeGrid)
    MyGridView homeGrid;

    private HomeGridAdapter gridAdapter;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        ButterKnife.bind(this, view);
        initViewData();
        return view;
    }

    public void initViewData() {
        if(null != mContext){
            //listContent.setOnItemClickListener(new listContentItemListener());
            //String appAuthStr = Config.AppAuthStr;
            //gridAdapter = new HomeGridAdapter(mContext,appAuthStr);
            gridAdapter = new HomeGridAdapter(mContext);
            homeGrid.setAdapter(gridAdapter);
            homeGrid.setSelector(R.drawable.hide_listview_yellow_selector);
            homeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.tv_logo);
                    String itemName = textView.getText().toString();
                    switch(itemName){
                        case "用户信息":
                            //startActivity(new Intent(mContext, UsersInfoActivity.class));
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
