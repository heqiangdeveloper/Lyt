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
            String startCity = item.getStartingcity();
            String startPort = item.getStartingport();
            String aimCity = item.getAimcity();
            String aimPort = item.getAimport();
            String variety = item.getVariety();
            int istakespellcabin = item.getIstakespellcabin();
            int amount = item.getAmount();
            String createTime = item.getCreatetime();
            String comments = item.getComments();
            ((ItemViewHolder) holder).start_port_Tv.setText(startCity + startPort);
            ((ItemViewHolder) holder).aim_port_Tv.setText(aimCity + aimPort);
            ((ItemViewHolder) holder).variety_Tv.setText(variety);
            ((ItemViewHolder) holder).istakespellcabin_Tv.setText(istakespellcabin == 0 ? "接受拼舱" : "不接受拼舱");
            ((ItemViewHolder) holder).amount_Tv.setText(amount + "吨");
            ((ItemViewHolder) holder).createtime_Tv.setText(createTime);
            ((ItemViewHolder) holder).comments_Tv.setText(comments);
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
        TextView start_port_Tv,aim_port_Tv,variety_Tv,istakespellcabin_Tv,amount_Tv,
                createtime_Tv,comments_Tv;

        public ItemViewHolder(View view) {
            super(view);
            start_port_Tv = view.findViewById(R.id.start_port_tv);
            aim_port_Tv = view.findViewById(R.id.aim_port_tv);
            variety_Tv = view.findViewById(R.id.variety_tv);
            istakespellcabin_Tv = view.findViewById(R.id.istakespellcabin_tv);
            amount_Tv = view.findViewById(R.id.amount_tv);
            createtime_Tv = view.findViewById(R.id.createtime_tv);
            comments_Tv = view.findViewById(R.id.comments_tv);
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
