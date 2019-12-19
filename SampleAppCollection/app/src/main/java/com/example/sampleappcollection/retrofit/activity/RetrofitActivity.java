package com.example.sampleappcollection.retrofit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;
import com.example.sampleappcollection.retrofit.api.ApiStores;
import com.example.sampleappcollection.retrofit.model.WeatherModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * 描述：简单例子
 *
 * @author zhangqin
 */
public class RetrofitActivity extends AppCompatActivity {

    @BindView(R.id.retrofit1_tv)
    TextView mRetrofit1Tv;

    private Retrofit mRetrofit;
    private ApiStores mApiStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        //01:获取Retrofit对象
        mRetrofit = new Retrofit.Builder()
                //02采用链式结构绑定Base url
                .baseUrl(ApiStores.API_SERVER_URL)
                // 注意：字符创解析器要放在Gson解析器前面，不然无法解析字符串
                //使用工厂模式创建字符串解析器
                .addConverterFactory(ScalarsConverterFactory.create())
                //使用工厂模式创建Gson的解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //04获取API接口的实现类的实例对象
        mApiStores = mRetrofit.create(ApiStores.class);

    }

    /**
     * 设置TextView
     * @param s 要设置的字符
     */
    private void setText(String s) {
        try {
            mRetrofit1Tv.setText(s);
        } catch (Exception e) {
            LogUtils.e("setText: ", e);
        }
    }

    @OnClick({R.id.retrofit1_btn_json, R.id.retrofit1_btn_str})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 返回JSON
            case R.id.retrofit1_btn_json:
                LogUtils.e("onViewClicked: " + "返回json数据");
                // 调用请求方法，并得到Call实例
                Call<WeatherModel> mModelCall = mApiStores.loadDataByJson("101190201");
                // 异步请求
                mModelCall.enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                        if (response.body() == null) {
                            setText("null");
                        } else {
                            setText(response.body().getWeatherinfo().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        LogUtils.e("onFailure: ", t);
                    }
                });
                break;
            // 返回字符串
            case R.id.retrofit1_btn_str:
                Call<String> mModelCall2 = mApiStores.loadDataByString("101190201");
                mModelCall2.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body() == null) {
                            setText("null");
                        } else {
                            setText(response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        LogUtils.e("onFailure: ", t);
                    }
                });
                break;
            default:
                break;
        }
    }
}
