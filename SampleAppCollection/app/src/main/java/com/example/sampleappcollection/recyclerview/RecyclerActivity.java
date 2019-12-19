package com.example.sampleappcollection.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;
import com.example.sampleappcollection.recyclerview.doublelistlinkage.DoublelistlinkageActivity;
import com.example.sampleappcollection.recyclerview.vlayout.VLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：Recycler列表
 *
 * @author zhangqin
 * @date 2017/11/8
 */
public class RecyclerActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView mRecyclerList;

    private String[] mTitle = new String[]{"基本使用", "VLayout", "双列表联动"};
    private Class[] mClasses = new Class[]{RecyclerActivity.class, VLayoutActivity.class,
            DoublelistlinkageActivity.class};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        mRecyclerList.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, mTitle));

        mRecyclerList.setOnItemClickListener((parent, view, position, id) ->
                startActivity(new Intent(RecyclerActivity.this, mClasses[position]))
        );
    }
}
