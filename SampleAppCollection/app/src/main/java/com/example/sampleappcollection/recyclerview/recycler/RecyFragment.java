package com.example.sampleappcollection.recyclerview.recycler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleappcollection.R;
import com.example.sampleappcollection.recyclerview.recycler.data.MyData;
import com.example.sampleappcollection.recyclerview.recycler.data.MyDataLab;

import java.util.List;

/**
 * @author zhangqin
 * @date 2016/7/10
 */
public class RecyFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_recycler, container, false);

        initData();

        return v;
    }

    public void initData() {

        mRecyclerView = v.findViewById(R.id.main_recycler);
        // 创建后转交给LinearLayoutManager对象，在屏幕上定位列表项，并且负责定义屏幕的滚动行为。
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updataUI();

    }

    private void updataUI() {

        MyDataLab myDataLab = MyDataLab.getMyData(getActivity());
        List<MyData> myDatas = myDataLab.getDatas();

        mMyAdapter = new MyAdapter(myDatas);
        mRecyclerView.setAdapter(mMyAdapter);

    }

    /**
     * 定义ViewHolder内部类
     */
    private class myItemData extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyData mData;

        public TextView tv_title;
        public TextView tv_text;

        public myItemData(View itemView) {
            super(itemView);

            Log.e("test", "myItemData");

            itemView.setOnClickListener(this);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_text = itemView.findViewById(R.id.tv_text);
        }

        public void bindData(MyData myData) {

            Log.e("test", "bindData");

            mData = myData;
            tv_title.setText(mData.getTitle());
            tv_text.setText(mData.getText());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mData.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<myItemData> {

        private List<MyData> mDatas;

        public MyAdapter(List<MyData> datas) {
            mDatas = datas;
        }

        //创建布局
        @Override
        public myItemData onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_recycler, parent, false);

            return new myItemData(view);
        }

        //设置数据
        @Override
        public void onBindViewHolder(myItemData holder, int position) {

            MyData data = mDatas.get(position);
            holder.bindData(data);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }
}
