package com.example.sampleappcollection.recyclerview.doublelistlinkage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleappcollection.R;

import java.util.List;

/**
 * Created by zhangqin on 2017/7/19.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.SortHolder> {

    private List<String> list;
    private Context mContext;
    private LayoutInflater mInflater;
    protected RvListener listener;
    private int checkedPosition;

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public SortAdapter(Context mContext, List<String> list, RvListener listener) {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public SortHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_sort_list, parent, false);
        return new SortHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(SortHolder holder, int position) {
        holder.bindHolder(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SortHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private View mView;
        private RvListener mListener;

        public SortHolder(View itemView, RvListener listener) {
            super(itemView);
            this.mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_sort);
            this.mListener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });
        }

        public void bindHolder(String string, int position) {
            tvName.setText("数据" + string);
            if (position == checkedPosition) {
                mView.setBackgroundColor(Color.parseColor("#f3f3f3"));
                tvName.setTextColor(Color.parseColor("#0068cf"));
            } else {
                mView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvName.setTextColor(Color.parseColor("#1e1d1d"));
            }
        }
    }
}
