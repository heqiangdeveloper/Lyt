package com.cimcitech.lyt.adapter.bargemanage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cimcitech.lyt.R;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinData;
import com.cimcitech.lyt.bean.bargemanage.BargeCabinVo;

import java.util.List;

/**
 * Created by qianghe on 2018/10/11.
 */

public class BargeCabinAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<BargeCabinData> data;
    private LayoutInflater inflater;
    private static final int TYPE_END = 2;
    private boolean isNotMoreData = false;
    private Context context;

    public BargeCabinAdapter(Context context, List<BargeCabinData> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
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

        void onDeleteBtnClickListener(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.barge_cabin_item_view, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
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

                ((ItemViewHolder) holder).delete_Iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onDeleteBtnClickListener(((ItemViewHolder) holder).delete_Iv, position);
                    }
                });
            }


            BargeCabinData item = data.get(position);
            ((ItemViewHolder) holder).hatchnum_Tv.setText(item.getHatchnum() + "");
            ((ItemViewHolder) holder).holdcapacity_Tv.setText(item.getHoldcapacity() + "");
            ((ItemViewHolder) holder).hatchsize_Tv.setText(item.getHatchsize() + "");

            //最后一个item的分割线隐藏
            if(position == data.size() - 1){
                ((ItemViewHolder) holder).divide_Vw.setVisibility(View.GONE);
            }

//            if(position % 2 != 0){
//                int color = context.getResources().getColor(R.color.barge_cabin_item_bg);
//                ((ItemViewHolder) holder).root_Ll.setBackgroundColor(color);
//            }
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
        TextView hatchnum_Tv,holdcapacity_Tv,hatchsize_Tv;
        ImageView delete_Iv;
        LinearLayout root_Ll;
        View divide_Vw;

        public ItemViewHolder(View view) {
            super(view);
            hatchnum_Tv = view.findViewById(R.id.hatchnum_tv);
            holdcapacity_Tv = view.findViewById(R.id.holdcapacity_tv);
            hatchsize_Tv = view.findViewById(R.id.hatchsize_tv);
            delete_Iv = view.findViewById(R.id.delete_iv);
            root_Ll = view.findViewById(R.id.root_ll);
            divide_Vw = view.findViewById(R.id.divide_vw);
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
    public void updateList(List<BargeCabinData> list){
        this.data = list;
        notifyDataSetChanged();
    }
}
