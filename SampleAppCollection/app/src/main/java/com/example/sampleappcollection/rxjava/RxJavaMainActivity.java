package com.example.sampleappcollection.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：Rxjava2
 *
 * @author zhangqin
 */
public class RxJavaMainActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView mRxJavaList;

    private String[] mTitle = new String[]{"基础入门", "线程调度", "Map与FlatMap",
            "zip", "Flowable"};
    private Class[] mClasses = new Class[]{RxJavaActivity.class, RxJavaSchedulersActivity.class,
            RxJavaMapActivity.class, RxJavaZipActivity.class, RxJavaFlowableActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        mRxJavaList.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, mTitle));

        mRxJavaList.setOnItemClickListener((parent, view, position, id) ->
                startActivity(new Intent(RxJavaMainActivity.this, mClasses[position]))
        );
    }
}
