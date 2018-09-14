package com.cimcitech.lyt.activity.bargemanage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.adapter.bargemanage.BargeManageAdapter;
import com.cimcitech.lyt.bean.bargemanage.BargeInfo;
import com.cimcitech.lyt.bean.bargemanage.BargeListReq;
import com.cimcitech.lyt.bean.bargemanage.BargeVo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.widget.ClearEditText;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by cimcitech on 2017/7/31.
 */

public class BargeManageFragment extends Fragment {
    @Bind(R.id.back_iv)
    ImageView back_Iv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.search_bar)
    ClearEditText search_Bar;

    private BargeManageAdapter adapter;
    private Handler handler = new Handler();
    private int pageNum = 1;
    private boolean isLoading;
    private List<BargeInfo> data = new ArrayList<>();
    private BargeVo bargeVo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_barge_manage, container, false);
        ButterKnife.bind(this, view);
        initTitle();
        registBroadcast();
        initViewData();
        updateData();
        return view;
    }

    public void registBroadcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BargeDetailActivity.REFRESH_BARGE_BROADCAST);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBroadcastReceiver,intentFilter);
    }

    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateData();
        }
    };

    public void unRegistBroadcast(){
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBroadcastReceiver);
    }

    public void initTitle(){
        back_Iv.setVisibility(View.GONE);
        search_Bar.setVisibility(View.GONE);
    }

    public void initViewData() {
        adapter = new BargeManageAdapter(getActivity(), data);
        swipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //下拉刷新
                        adapter.notifyDataSetChanged();
                        data.clear(); //清除数据
                        pageNum = 1;
                        isLoading = false;
                        getData(); //获取数据
                    }
                }, 1000);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();
                if (topRowVerticalPosition > 0) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        return;
                    }

                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //上拉加载
                                if (bargeVo.getData().isHasNextPage()) {
                                    pageNum++;
                                    getData();
                                }
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
        //给List添加点击事件
        adapter.setOnItemClickListener(new BargeManageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), BargeDetailActivity.class);
                BargeInfo bargeInfo = (BargeInfo)adapter.getAll().get(position);
                intent.putExtra("bargeInfo",bargeInfo);
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
    }

    private void updateData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(null != swipeRefreshLayout)
                    swipeRefreshLayout.setRefreshing(true);
            }
        });
        //清除数据
        adapter.notifyDataSetChanged();
        this.data.clear();
        pageNum = 1;
        getData();
    }

    public void getData() {
        String json = new Gson().toJson(new BargeListReq(Config.ACCOUNTID, pageNum,10,""));
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
                                if(null != swipeRefreshLayout)
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                bargeVo = GjsonUtil.parseJsonWithGson(response,BargeVo.class);
                                if (bargeVo != null) {
                                    //data.clear();
                                    if (bargeVo.isSuccess()) {
                                        if (bargeVo.getData().getList() != null && bargeVo.getData().getList().size() > 0) {
                                            for (int i = 0; i < bargeVo.getData().getList().size(); i++) {
                                                BargeInfo bargeInfo = bargeVo.getData().getList().get(i);
                                                data.add(bargeInfo);
                                            }
                                        }
                                        if (bargeVo.getData().isHasNextPage()) {
                                            adapter.setNotMoreData(false);
                                        } else {
                                            adapter.setNotMoreData(true);
                                        }
                                        if(null != swipeRefreshLayout)
                                        swipeRefreshLayout.setRefreshing(false);
                                        adapter.notifyDataSetChanged();
                                        adapter.notifyItemRemoved(adapter.getItemCount());
                                    }else{
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                } else {
                                    swipeRefreshLayout.setRefreshing(false);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                );
    }

    @OnClick({R.id.add_tv})
    public void onclick(View view) {
        switch (view.getId()){
            case R.id.add_tv:
                Intent intent = new Intent(getActivity(),BargeAddAndModifyActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        unRegistBroadcast();
    }



}
