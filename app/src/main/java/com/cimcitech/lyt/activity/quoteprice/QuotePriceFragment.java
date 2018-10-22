package com.cimcitech.lyt.activity.quoteprice;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.adapter.quoteprice.WaitQuoteAdapter;
import com.cimcitech.lyt.bean.quoteprice.AlreadyQuoteReq;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteListBean;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteReq;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteVo;
import com.cimcitech.lyt.utils.Config;
import com.cimcitech.lyt.utils.GjsonUtil;
import com.cimcitech.lyt.utils.ToastUtil;
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

public class QuotePriceFragment extends Fragment {
    @Bind(R.id.back_iv)
    ImageView back_Iv;
    @Bind(R.id.waitQuote_ll)
    LinearLayout waitQuote_Ll;
    @Bind(R.id.quoted_ll)
    LinearLayout quoted_Ll;
    @Bind(R.id.sucess_ll)
    LinearLayout sucess_Ll;
    @Bind(R.id.sucess_view)
    View sucess_Vw;
    @Bind(R.id.waitQuote_view)
    View waitQuote_Vw;
    @Bind(R.id.quoted_view)
    View quoted_Vw;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view_layout)
    CoordinatorLayout recyclerViewLayout;

    private List<WaitQuoteListBean> data = new ArrayList<>();

    private View view;
    private WaitQuoteAdapter adapter;
    private Handler handler = new Handler();
    private int pageNum = 1;
    private boolean isLoading;
    private int flag = 0;
    private WaitQuoteVo waitQuoteVo = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            ButterKnife.bind(this, view);
            return view;
        }
        view = inflater.inflate(R.layout.activity_exchange, container, false);
        ButterKnife.bind(this, view);
        initTitle();
        initViewData();
        updateData();
        return view;
    }

    public void initTitle(){
        back_Iv.setVisibility(View.GONE);
        setVisible(waitQuote_Vw);
        flag = 0;
    }

    @OnClick({R.id.waitQuote_ll,R.id.quoted_ll,R.id.sucess_ll})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.waitQuote_ll://待报价
                setVisible(waitQuote_Vw);
                flag = 0;
                updateData();
                break;
            case R.id.quoted_ll://已报价
                setVisible(quoted_Vw);
                flag = 1;
                updateData();
                break;
            case R.id.sucess_ll://已成交
                setVisible(sucess_Vw);
                flag = 2;
                updateData();
                break;
        }
    }

    public void setVisible(View v){
        waitQuote_Vw.setVisibility(View.INVISIBLE);
        quoted_Vw.setVisibility(View.INVISIBLE);
        sucess_Vw.setVisibility(View.INVISIBLE);

        v.setVisibility(View.VISIBLE);
    }

    public void initViewData() {
        //防止切换底部的Fragment时，出现recyclerView内容的重复加载
        if(null != data || data.size() != 0){
            data.clear();
        }
        adapter = new WaitQuoteAdapter(getActivity(), data);
        swipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(null != swipeRefreshLayout)
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
                        if (flag == 0){
                            getWaitQuoteData(); //获取数据
                        }else if(flag == 1){
                            getAlreadyQuoteData();
                        }else{
                            getSuccessQuoteData();
                        }
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
                /*int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }*/
                int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0)
                        ? 0 : recyclerView.getChildAt(0).getTop();
                if (topRowVerticalPosition > 0) {
                    if(null != swipeRefreshLayout)
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
                                if (waitQuoteVo.getData().isHasNextPage()) {
                                    pageNum++;
                                    if (flag == 0){
                                        getWaitQuoteData();
                                    }else if(flag == 1){
                                        getAlreadyQuoteData();
                                    }else{
                                        getSuccessQuoteData();
                                    }
                                }
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
        //给List添加点击事件
        adapter.setOnItemClickListener(new WaitQuoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WaitQuoteListBean listBean = (WaitQuoteListBean) adapter.getAll().get(position);
                Intent intent = new Intent(getActivity(), WaitQuoteDetailActivity.class);
                intent.putExtra("transportreqid", listBean.getTransportreqid());
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
            }
        });
    }

    //刷新数据
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
        if (flag == 0){
            getWaitQuoteData();
        } else if(flag == 1){
            getAlreadyQuoteData();
        }else{
            getSuccessQuoteData();
        }
    }

    public void getWaitQuoteData() {
        WaitQuoteReq req = new WaitQuoteReq(pageNum,10,"");
        String json = new Gson().toJson(req);
        OkHttpUtils
                .postString()
                .url(Config.QUERY_QUOTE_MAIN_URL)
                .addHeader("Check_Token", Config.TOKEN)
                .content(json)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(
                        new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                if(null != swipeRefreshLayout)
                                    swipeRefreshLayout.setRefreshing(false);
                                ToastUtil.showNetError();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                waitQuoteVo = GjsonUtil.parseJsonWithGson(response, WaitQuoteVo.class);
                                if(null != waitQuoteVo){
                                    if(waitQuoteVo.isSuccess()){
                                        if(waitQuoteVo.getData().getList() != null &&
                                                waitQuoteVo.getData().getList().size() > 0){
                                            for(int i = 0 ; i < waitQuoteVo.getData().getList().size(); i++){
                                                data.add(waitQuoteVo.getData().getList().get(i));
                                            }
                                        }
                                        if (waitQuoteVo.getData().isHasNextPage()) {
                                            adapter.setNotMoreData(false);
                                        } else {
                                            adapter.setNotMoreData(true);
                                        }
                                        adapter.notifyDataSetChanged();
                                        if(null != swipeRefreshLayout)
                                            swipeRefreshLayout.setRefreshing(false);
                                        adapter.notifyItemRemoved(adapter.getItemCount());
                                    }
                                } else {
                                    adapter.notifyDataSetChanged();
                                    if(null != swipeRefreshLayout)
                                        swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                );
    }

    public void getAlreadyQuoteData() {
//        WaitQuoteReq.TranSportBean tranSportBean = new WaitQuoteReq.TranSportBean("","",pageNum,10,"");
//        String json = new Gson().toJson(new WaitQuoteReq(tranSportBean));
//        OkHttpUtils
//                .postString()
//                .url(Config.QUERY_ALREADY_QUOTE_URL)
//                .addHeader("Check_Token", Config.TOKEN)
//                .content(json)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .build()
//                .execute(
//                        new StringCallback() {
//                            @Override
//                            public void onError(Call call, Exception e, int id) {
//                                if(null != swipeRefreshLayout)
//                                    swipeRefreshLayout.setRefreshing(false);
//                                ToastUtil.showNetError();
//                            }
//
//                            @Override
//                            public void onResponse(String response, int id) {
//                                waitQuoteVo = GjsonUtil.parseJsonWithGson(response, WaitQuoteVo.class);
//                                if(null != waitQuoteVo){
//                                    if(waitQuoteVo.isSuccess()){
//                                        if(waitQuoteVo.getData().getList() != null &&
//                                                waitQuoteVo.getData().getList().size() > 0){
//                                            for(int i = 0 ; i < waitQuoteVo.getData().getList().size(); i++){
//                                                data.add(waitQuoteVo.getData().getList().get(i));
//                                            }
//                                        }
//                                        if (waitQuoteVo.getData().isHasNextPage()) {
//                                            adapter.setNotMoreData(false);
//                                        } else {
//                                            adapter.setNotMoreData(true);
//                                        }
//                                        adapter.notifyDataSetChanged();
//                                        if(null != swipeRefreshLayout)
//                                            swipeRefreshLayout.setRefreshing(false);
//                                        adapter.notifyItemRemoved(adapter.getItemCount());
//                                    }
//                                } else {
//                                    adapter.notifyDataSetChanged();
//                                    if(null != swipeRefreshLayout)
//                                        swipeRefreshLayout.setRefreshing(false);
//                                }
//                            }
//                        }
//                );
    }

    public void getSuccessQuoteData() {
        if(null != swipeRefreshLayout)
            swipeRefreshLayout.setRefreshing(false);
    }
}
