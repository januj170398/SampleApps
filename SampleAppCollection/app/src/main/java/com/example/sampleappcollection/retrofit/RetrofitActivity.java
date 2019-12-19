package com.example.sampleappcollection.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sdwfqin.sample.R;
import com.sdwfqin.sample.retrofit.activity.RetrofitGetActivity;
import com.sdwfqin.sample.retrofit.activity.RetrofitPostActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：Retrofit网络请求
 *
 * @author zhangqin
 * @date 2017/11/8
 */
public class RetrofitActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView mRetrofitList;

    private String[] mTitle = new String[]{"入门例子", "get请求", "post请求+RxJava"};
    private Class[] mClasses = new Class[]{com.sdwfqin.sample.retrofit.activity.RetrofitActivity.class, RetrofitGetActivity.class,
            RetrofitPostActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        mRetrofitList.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, mTitle));

        mRetrofitList.setOnItemClickListener((parent, view, position, id) ->
                startActivity(new Intent(RetrofitActivity.this, mClasses[position]))
        );
    }
}
