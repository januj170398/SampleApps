package com.example.sampleappcollection.recyclerview.doublelistlinkage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleappcollection.R;

import java.util.List;

/**
 * 右侧列表
 *
 * @author zhangqin
 * @date 2017/7/20
 */
public class ClassifyDetailAdapter extends RecyclerView.Adapter<ClassifyDetailAdapter.ClassifyHolder> {

    private List<SortBean> list;
    private Context mContext;
    private RvListener listener;
    private LayoutInflater mInflater;

    public ClassifyDetailAdapter(Context mContext, List<SortBean> list, RvListener listener) {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ClassifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(viewType == 0 ? R.layout.item_title : R.layout.item_classify_detail, parent, false);
        return new ClassifyHolder(view, viewType, listener);
    }

    @Override
    public void onBindViewHolder(ClassifyHolder holder, int position) {
        holder.bindHolder(list.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ClassifyHolder extends RecyclerView.ViewHolder {
        private TextView tvCity;
        private ImageView avatar;
        private TextView tvTitle;
        private RvListener mListener;

        public ClassifyHolder(View itemView, int type, RvListener listener) {
            super(itemView);
            switch (type) {
                case 0:
                    tvTitle = itemView.findViewById(R.id.tv_title);
                    break;
                case 1:
                    tvCity = itemView.findViewById(R.id.tvCity);
                    avatar = itemView.findViewById(R.id.ivAvatar);
                    break;
                default:
                    break;
            }

            this.mListener = listener;
            itemView.setOnClickListener(v -> mListener.onItemClick(v.getId(), getAdapterPosition()));
        }

        public void bindHolder(SortBean sortBean, int position) {
            int itemViewType = ClassifyDetailAdapter.this.getItemViewType(position);
            switch (itemViewType) {
                case 0:
                    tvTitle.setText("测试数据" + sortBean.getTag());
                    break;
                case 1:
                    tvCity.setText(sortBean.getName());
                    break;
                default:
                    break;
            }

        }
    }
}
