package com.example.sampleappcollection.retrofit.api;

import com.example.sampleappcollection.retrofit.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 描述：测试接口
 *
 * @author zhangqin
 * @date 2017/11/8
 */
public interface ApiStores {
    /**
     * baseUrl
     */
    String API_SERVER_URL = "http://www.weather.com.cn/";

    /**
     * 加载天气
     * Path可以用于任何请求，拼接url地址
     * GET表示get请求
     *
     * @param cityIdJson 城市id
     * @return
     */
    @GET("adat/sk/{cityId}.html")
    Call<WeatherModel> loadDataByJson(@Path("cityId") String cityIdJson);

    /**
     * 返回字符串
     *
     * @param cityIdString 城市id
     * @return
     */
    @GET("adat/sk/{cityId}.html")
    Call<String> loadDataByString(@Path("cityId") String cityIdString);
}
