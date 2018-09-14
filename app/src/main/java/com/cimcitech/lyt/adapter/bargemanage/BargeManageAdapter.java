package com.cimcitech.lyt.adapter.bargemanage;

/**
 * Created by cimcitech on 2017/8/2.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.bean.bargemanage.BargeInfo;

import java.util.List;

/**
 * Created by cimcitech on 2017/8/1.
 */

public class BargeManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<BargeInfo> data;
    private LayoutInflater inflater;
    private static final int TYPE_END = 2;
    private boolean isNotMoreData = false;

    public BargeManageAdapter(Context context, List<BargeInfo> data) {
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

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.barge_manage_item_view, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
//            View view = inflater.inflate(R.layout.recycler_foot_view, parent, false);
//            return new FootViewHolder(view);
            View view = inflater.inflate(R.layout.recycler_end_view, parent, false);
            return new FootViewHolder(view);
        } else if (viewType == TYPE_END) {
            View view = inflater.inflate(R.layout.recycler_end_view, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
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
            final BargeInfo item = data.get(position);
            //船名
            ((ItemViewHolder) holder).bargename_Tv.setText(item.getBargename() != null && !item
                    .getBargename().equals("") ? "船名：" + item.getBargename() : "船名：" + "" );
            //船籍
            ((ItemViewHolder) holder).nationality_Tv.setText(item.getNationality() != null && !item
                    .getNationality().equals("") ? "船籍：" + item.getNationality():"船籍：" + "");
            //吨位
            ((ItemViewHolder) holder).deadweightton_Tv.setText("载重吨位：" + item.getDeadweightton());
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
        ImageView pic_Iv;
        TextView bargename_Tv,deadweightton_Tv,nationality_Tv;

        public ItemViewHolder(View view) {
            super(view);
            pic_Iv = view.findViewById(R.id.pic_iv);
            deadweightton_Tv = view.findViewById(R.id.deadweightton_tv);
            bargename_Tv = view.findViewById(R.id.bargename_tv);
            nationality_Tv = view.findViewById(R.id.nationality_tv);
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
    public void updateList(List<BargeInfo> list){
        this.data = list;
        notifyDataSetChanged();
    }
}
