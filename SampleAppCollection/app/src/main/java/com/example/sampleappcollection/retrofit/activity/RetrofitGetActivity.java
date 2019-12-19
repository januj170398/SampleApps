package com.example.sampleappcollection.retrofit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.sdwfqin.sample.R;
import com.sdwfqin.sample.retrofit.api.RequestGetApi;
import com.sdwfqin.sample.retrofit.model.RequestModel;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述：Get请求
 *
 * @author zhangqin
 */
public class RetrofitGetActivity extends AppCompatActivity {

    @BindView(R.id.retrofit2_tv)
    TextView mRetrofit2Tv;
    private Retrofit mRetrofit;
    private RequestGetApi searchApi;

    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_get);
        ButterKnife.bind(this);

        mHandler = new MyHandler(this);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://test.sdwfqin.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        searchApi = mRetrofit.create(RequestGetApi.class);
    }

    /**
     * 设置TextView
     *
     * @param s
     */
    private void setText(String s) {
        try {
            mRetrofit2Tv.setText(s);
        } catch (Exception e) {
            LogUtils.e("setText: ", e);
        }
    }

    @OnClick({R.id.retrofit2_btn_async, R.id.retrofit2_btn_sync})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 异步请求
            case R.id.retrofit2_btn_async:
                // 调用请求方法，并得到Call实例
                Call<RequestModel> call = searchApi.getData("码农Mrz", "get请求");
                call.enqueue(new Callback<RequestModel>() {
                    @Override
                    public void onResponse(Call<RequestModel> call, Response<RequestModel> response) {
                        setText("异步请求结果: " + response.body().toString());
                        LogUtils.e("请求头: " + response.toString());
                    }

                    @Override
                    public void onFailure(Call<RequestModel> call, Throwable t) {
                        LogUtils.e("onFailure: ", t);
                    }
                });
                break;
            // 同步请求
            case R.id.retrofit2_btn_sync:
                new Thread(() -> {
                    Call<RequestModel> call1 = searchApi.getData("码农Mrz", "get同步请求");
                    try {
                        RequestModel response = call1.execute().body();
                        LogUtils.e("run: " + response.toString());

                        Bundle bundle = new Bundle();
                        bundle.putString("value", "同步请求结果: " + response.toString());
                        Message message = new Message();
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    } catch (IOException e) {
                        LogUtils.e("run: ", e);
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    static class MyHandler extends Handler {

        private WeakReference<RetrofitGetActivity> mActivity;

        public MyHandler(RetrofitGetActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RetrofitGetActivity activity = mActivity.get();
            activity.setText(msg.getData().getString("value"));
        }
    }
}
