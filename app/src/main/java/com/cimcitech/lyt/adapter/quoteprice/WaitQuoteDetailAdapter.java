package com.cimcitech.lyt.adapter.quoteprice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.adapter.bargemanage.BargeCabinAdapter;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteDetailListBean;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteListBean;

import java.util.List;

/**
 * Created by qianghe on 2018/10/9.
 */

public class WaitQuoteDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<WaitQuoteDetailListBean> data;
    private LayoutInflater inflater;
    private static final int TYPE_END = 2;
    private boolean isNotMoreData = false;

    public WaitQuoteDetailAdapter(Context context, List<WaitQuoteDetailListBean> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void setNotMoreData(boolean b) {
        this.isNotMoreData = b;
    }

    public boolean isNotMoreData() {
        return isNotMoreData;
    }

    public List getAll() {
        return data;
    }

    public interface OnItemClickListener {
        //void onItemClick(View view, int position);
        void onStartQuoteClickListener(View view, int position);
    }

    private WaitQuoteDetailAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(WaitQuoteDetailAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.wait_quote_detail_item_view, parent, false);
            return new WaitQuoteDetailAdapter.ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.recycler_end_view, parent, false);
            return new WaitQuoteDetailAdapter.FootViewHolder(view);
        } else if (viewType == TYPE_END) {
            View view = inflater.inflate(R.layout.recycler_end_view, parent, false);
            return new WaitQuoteDetailAdapter.FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WaitQuoteDetailAdapter.ItemViewHolder) {
            if (onItemClickListener != null) {
                ((WaitQuoteDetailAdapter.ItemViewHolder) holder).start_quote_Tv.setOnClickListener(new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onStartQuoteClickListener(((WaitQuoteDetailAdapter.ItemViewHolder) holder).start_quote_Tv, position);
                    }
                });
            }
            final WaitQuoteDetailListBean item = data.get(position);
            String startPortName = item.getStartPortName();
            String endPortName = item.getEndPortName();
            String planloadingdate = item.getPlanloadingdate();
            String planarrivedate = item.getPlanarrivedate();
            String varietyName = item.getVarietyName();
            String issuetime = item.getIssuetime();
            float amount = item.getTotalton() - item.getConfirmton();
            ((ItemViewHolder) holder).startPortName_Tv.setText(startPortName);
            ((ItemViewHolder) holder).endPortName_Tv.setText(endPortName);
            ((ItemViewHolder) holder).planloadingdate_Tv.setText(planloadingdate);
            ((ItemViewHolder) holder).planarrivedate_Tv.setText(planarrivedate);
            ((ItemViewHolder) holder).varietyName_Tv.setText(varietyName);
            ((ItemViewHolder) holder).issuetime_Tv.setText(issuetime);
            ((ItemViewHolder) holder).amount_Tv.setText(amount + "");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            if (isNotMoreData())
                return TYPE_END;
            else
                return TYPE_FOOTER;
        } else
            return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView startPortName_Tv,endPortName_Tv,planloadingdate_Tv,planarrivedate_Tv,
                varietyName_Tv,amount_Tv,issuetime_Tv,start_quote_Tv;

        public ItemViewHolder(View view) {
            super(view);
            startPortName_Tv = view.findViewById(R.id.startPortName_tv);
            endPortName_Tv = view.findViewById(R.id.endPortName_tv);
            planloadingdate_Tv = view.findViewById(R.id.planloadingdate_tv);
            planarrivedate_Tv = view.findViewById(R.id.planarrivedate_tv);
            varietyName_Tv = view.findViewById(R.id.varietyName_tv);
            amount_Tv = view.findViewById(R.id.amount_tv);
            issuetime_Tv = view.findViewById(R.id.issuetime_tv);
            start_quote_Tv = view.findViewById(R.id.start_quote_tv);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    /**
     * 提供给Activity刷新数据
     * @param list
     */
    public void updateList(List<WaitQuoteDetailListBean> list){
        this.data = list;
        notifyDataSetChanged();
    }
}
