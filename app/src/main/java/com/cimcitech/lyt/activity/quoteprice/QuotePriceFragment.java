package com.cimcitech.lyt.activity.quoteprice;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
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
import com.cimcitech.lyt.adapter.quoteprice.AlreadyQuoteAdapter;
import com.cimcitech.lyt.adapter.quoteprice.SuccessQuoteAdapter;
import com.cimcitech.lyt.adapter.quoteprice.WaitQuoteAdapter;
import com.cimcitech.lyt.bean.quoteprice.AlreadyQuoteListBean;
import com.cimcitech.lyt.bean.quoteprice.AlreadyQuoteVo;
import com.cimcitech.lyt.bean.quoteprice.SuccessQuoteListBean;
import com.cimcitech.lyt.bean.quoteprice.SuccessQuoteVo;
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

    private List<WaitQuoteListBean> data_WaitQuote = new ArrayList<>();
    private List<AlreadyQuoteListBean> data_AlreadyQuote = new ArrayList<>();
    private List<SuccessQuoteListBean> data_SuccessQuote = new ArrayList<>();

    private View view;
    private WaitQuoteAdapter adapter_WaitQuote;
    private AlreadyQuoteAdapter adapter_AlreadyQuote;
    private SuccessQuoteAdapter adapter_SuccessQuote;
    private Handler handler = new Handler();
    private int pageNum = 1;
    private boolean isLoading;
    private int flag = 0;
    private WaitQuoteVo waitQuoteVo = null;
    private AlreadyQuoteVo alreadyQuoteVo = null;
    private SuccessQuoteVo successQuoteVo = null;

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
        initViewData_WaitQuote();
        updateData_WaitQuote();
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
                initViewData_WaitQuote();
                updateData_WaitQuote();
                break;
            case R.id.quoted_ll://已报价
                setVisible(quoted_Vw);
                initViewData_AlreadyQuote();
                updateData_AlreadyQuote();
                break;
            case R.id.sucess_ll://已成交
                setVisible(sucess_Vw);
                initViewData_SuccessQuote();
                updateData_SuccessQuote();
                break;
        }
    }

    public void setVisible(View v){
        waitQuote_Vw.setVisibility(View.INVISIBLE);
        quoted_Vw.setVisibility(View.INVISIBLE);
        sucess_Vw.setVisibility(View.INVISIBLE);

        v.setVisibility(View.VISIBLE);
    }

    //待报价刷新数据
    public void initViewData_WaitQuote() {
        //防止切换底部的Fragment时，出现recyclerView内容的重复加载
        if(null != data_WaitQuote || data_WaitQuote.size() != 0){
            data_WaitQuote.clear();
        }
        adapter_WaitQuote = new WaitQuoteAdapter(getActivity(), data_WaitQuote);
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
                        adapter_WaitQuote.notifyDataSetChanged();
                        data_WaitQuote.clear(); //清除数据
                        pageNum = 1;
                        isLoading = false;
                        getWaitQuoteData();
                    }
                }, 1000);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_WaitQuote);
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
                                    getWaitQuoteData();
                                }
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
        //给List添加点击事件
        adapter_WaitQuote.setOnItemClickListener(new WaitQuoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WaitQuoteListBean listBean = (WaitQuoteListBean) adapter_WaitQuote.getAll().get(position);
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
    private void updateData_WaitQuote() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(null != swipeRefreshLayout)
                    swipeRefreshLayout.setRefreshing(true);
            }
        });
        //清除数据
        adapter_WaitQuote.notifyDataSetChanged();
        data_WaitQuote.clear();
        pageNum = 1;
        getWaitQuoteData();
    }

    //已报价刷新数据
    public void initViewData_AlreadyQuote() {
        //防止切换底部的Fragment时，出现recyclerView内容的重复加载
        if(null != data_AlreadyQuote || data_AlreadyQuote.size() != 0){
            data_AlreadyQuote.clear();
        }
        adapter_AlreadyQuote = new AlreadyQuoteAdapter(getActivity(), data_AlreadyQuote);
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
                        adapter_AlreadyQuote.notifyDataSetChanged();
                        data_AlreadyQuote.clear(); //清除数据
                        pageNum = 1;
                        isLoading = false;
                        getAlreadyQuoteData();
                    }
                }, 1000);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_AlreadyQuote);
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
                                if (alreadyQuoteVo.getData().isHasNextPage()) {
                                    pageNum++;
                                    getAlreadyQuoteData();
                                }
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
        //给List添加点击事件
        adapter_AlreadyQuote.setOnItemClickListener(new AlreadyQuoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlreadyQuoteListBean listBean = (AlreadyQuoteListBean) adapter_AlreadyQuote.getAll().get(position);
                Intent intent = new Intent(getActivity(), AlreadyQuoteDetailActivity.class);
                intent.putExtra("bargeName", listBean.getBargeName());
                intent.putExtra("ton", listBean.getTon());
                intent.putExtra("quote", listBean.getQuote());
                intent.putExtra("time", listBean.getQuotetime());
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
            }
        });
    }

    //刷新数据
    private void updateData_AlreadyQuote() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(null != swipeRefreshLayout)
                    swipeRefreshLayout.setRefreshing(true);
            }
        });
        //清除数据
        adapter_AlreadyQuote.notifyDataSetChanged();
        data_AlreadyQuote.clear();
        pageNum = 1;
        getAlreadyQuoteData();
    }

    //已成交刷新数据
    public void initViewData_SuccessQuote() {
        //防止切换底部的Fragment时，出现recyclerView内容的重复加载
        if(null != data_SuccessQuote || data_SuccessQuote.size() != 0){
            data_SuccessQuote.clear();
        }
//        if(data.get(0).getClass().equals(WaitQuoteListBean.class)){
//
//        }
        adapter_SuccessQuote = new SuccessQuoteAdapter(getActivity(), data_SuccessQuote);
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
                        adapter_SuccessQuote.notifyDataSetChanged();
                        data_SuccessQuote.clear(); //清除数据
                        pageNum = 1;
                        isLoading = false;
                        getSuccessQuoteData();
                    }
                }, 1000);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_SuccessQuote);
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
                                if (successQuoteVo.getData().isHasNextPage()) {
                                    pageNum++;
                                    getSuccessQuoteData();
                                }
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
        //给List添加点击事件
        adapter_SuccessQuote.setOnItemClickListener(new SuccessQuoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SuccessQuoteListBean listBean = (SuccessQuoteListBean) adapter_SuccessQuote.getAll().get(position);
                Intent intent = new Intent(getActivity(), SuccessQuoteDetailActivity.class);
                intent.putExtra("bargeName", listBean.getBargeName());
                intent.putExtra("ton", listBean.getTon());
                intent.putExtra("quote", listBean.getQuote());
                intent.putExtra("time",listBean.getQuotetime());
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
            }
        });
    }

    //刷新数据
    private void updateData_SuccessQuote() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(null != swipeRefreshLayout)
                    swipeRefreshLayout.setRefreshing(true);
            }
        });
        //清除数据
        adapter_SuccessQuote.notifyDataSetChanged();
        data_SuccessQuote.clear();
        pageNum = 1;
        getSuccessQuoteData();
    }

    //待报价
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
                                                data_WaitQuote.add(waitQuoteVo.getData().getList().get(i));
                                            }
                                        }
                                        if (waitQuoteVo.getData().isHasNextPage()) {
                                            adapter_WaitQuote.setNotMoreData(false);
                                        } else {
                                            adapter_WaitQuote.setNotMoreData(true);
                                        }
                                        adapter_WaitQuote.notifyDataSetChanged();
                                        if(null != swipeRefreshLayout)
                                            swipeRefreshLayout.setRefreshing(false);
                                        adapter_WaitQuote.notifyItemRemoved(adapter_WaitQuote.getItemCount());
                                    }
                                } else {
                                    adapter_WaitQuote.notifyDataSetChanged();
                                    if(null != swipeRefreshLayout)
                                        swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                );
    }

    //已报价
    public void getAlreadyQuoteData() {
        WaitQuoteReq req = new WaitQuoteReq(pageNum,10,"");
        String json = new Gson().toJson(req);
        OkHttpUtils
                .postString()
                .url(Config.QUERY_MY_QUOTE_URL)
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
                                alreadyQuoteVo = GjsonUtil.parseJsonWithGson(response, AlreadyQuoteVo.class);
                                if(null != alreadyQuoteVo){
                                    if(alreadyQuoteVo.isSuccess()){
                                        if(alreadyQuoteVo.getData().getList() != null &&
                                                alreadyQuoteVo.getData().getList().size() > 0){
                                            for(int i = 0 ; i < alreadyQuoteVo.getData().getList().size(); i++){
                                                data_AlreadyQuote.add(alreadyQuoteVo.getData().getList().get(i));
                                            }
                                        }
                                        if (alreadyQuoteVo.getData().isHasNextPage()) {
                                            adapter_AlreadyQuote.setNotMoreData(false);
                                        } else {
                                            adapter_AlreadyQuote.setNotMoreData(true);
                                        }
                                        adapter_AlreadyQuote.notifyDataSetChanged();
                                        if(null != swipeRefreshLayout)
                                            swipeRefreshLayout.setRefreshing(false);
                                        adapter_AlreadyQuote.notifyItemRemoved(adapter_AlreadyQuote.getItemCount());
                                    }
                                } else {
                                    adapter_AlreadyQuote.notifyDataSetChanged();
                                    if(null != swipeRefreshLayout)
                                        swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                );
    }

    //已成交
    public void getSuccessQuoteData() {
        WaitQuoteReq req = new WaitQuoteReq(pageNum,10,"");
        String json = new Gson().toJson(req);
        OkHttpUtils
                .postString()
                .url(Config.QUERY_MY_SUCCESS_URL)
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
                                successQuoteVo = GjsonUtil.parseJsonWithGson(response, SuccessQuoteVo.class);
                                if(null != successQuoteVo){
                                    if(successQuoteVo.isSuccess()){
                                        if(successQuoteVo.getData().getList() != null &&
                                                successQuoteVo.getData().getList().size() > 0){
                                            for(int i = 0 ; i < successQuoteVo.getData().getList().size(); i++){
                                                data_SuccessQuote.add(successQuoteVo.getData().getList().get(i));
                                            }
                                        }
                                        if (successQuoteVo.getData().isHasNextPage()) {
                                            adapter_SuccessQuote.setNotMoreData(false);
                                        } else {
                                            adapter_SuccessQuote.setNotMoreData(true);
                                        }
                                        adapter_SuccessQuote.notifyDataSetChanged();
                                        if(null != swipeRefreshLayout)
                                            swipeRefreshLayout.setRefreshing(false);
                                        adapter_SuccessQuote.notifyItemRemoved(adapter_SuccessQuote.getItemCount());
                                    }
                                } else {
                                    adapter_SuccessQuote.notifyDataSetChanged();
                                    if(null != swipeRefreshLayout)
                                        swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                );
    }
}
