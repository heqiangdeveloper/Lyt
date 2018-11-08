package com.cimcitech.lyt.adapter.quoteprice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.bean.quoteprice.WaitQuoteListBean;

import java.util.List;

/**
 * Created by qianghe on 2018/10/9.
 */

public class WaitQuoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<WaitQuoteListBean> data;
    private LayoutInflater inflater;
    private static final int TYPE_END = 2;
    private boolean isNotMoreData = false;

    public WaitQuoteAdapter(Context context, List<WaitQuoteListBean> data) {
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
        void onItemClick(View view, int position);

        void onItemLongClickListener(View view, int position);
    }

    private WaitQuoteAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(WaitQuoteAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.wait_quote_item_view, parent, false);
            return new WaitQuoteAdapter.ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.recycler_foot_view, parent, false);
            return new WaitQuoteAdapter.FootViewHolder(view);
        } else if (viewType == TYPE_END) {
            View view = inflater.inflate(R.layout.recycler_end_view, parent, false);
            return new WaitQuoteAdapter.FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WaitQuoteAdapter.ItemViewHolder) {
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                        return false;
                    }
                });
            }
            final WaitQuoteListBean item = data.get(position);
            String issueUserName = item.getIssueUserName();
            String effectivedate = item.getEffectivedate();
            String invaliddate = item.getInvaliddate();
            String fstatus = item.getFstatus();
            String remark = item.getRemark();
            ((ItemViewHolder) holder).issueUserName_Tv.setText(issueUserName);
            ((ItemViewHolder) holder).effectivedate_Tv.setText(effectivedate);
            ((ItemViewHolder) holder).invaliddate_Tv.setText(invaliddate);
            ((ItemViewHolder) holder).fstatus_Tv.setText(fstatus.equals("1") ? "已发布" : "未发布");
            ((ItemViewHolder) holder).remark_Tv.setText(remark);
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
        TextView issueUserName_Tv,fstatus_Tv,effectivedate_Tv,invaliddate_Tv,remark_Tv;

        public ItemViewHolder(View view) {
            super(view);
            issueUserName_Tv = view.findViewById(R.id.issueUserName_tv);
            fstatus_Tv = view.findViewById(R.id.fstatus_tv);
            effectivedate_Tv = view.findViewById(R.id.effectivedate_tv);
            invaliddate_Tv = view.findViewById(R.id.invaliddate_tv);
            remark_Tv = view.findViewById(R.id.remark_tv);
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
    public void updateList(List<WaitQuoteListBean> list){
        this.data = list;
        notifyDataSetChanged();
    }
}
