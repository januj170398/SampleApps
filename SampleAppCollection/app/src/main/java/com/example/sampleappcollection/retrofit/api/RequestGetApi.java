package com.example.sampleappcollection.retrofit.api;

import com.sdwfqin.sample.retrofit.model.RequestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 描述：get请求
 *
 * @author zhangqin
 * @date 2017/5/14
 */
public interface RequestGetApi {

    // @get注解就表示get请求，@query表示请求参数，将会以key=value的方式拼接在url后面
    // Query非必填可以用null填充，例如：("小王子", null, 0, 3)
    @GET("GetServlet")
    Call<RequestModel> getData(@Query("name") String name,
                               @Query("value") String tag);

    // 如果Query参数比较多，那么可以通过@QueryMap方式将所有的参数集成在一个Map统一传递
//    public interface BlueService {
//        @GET("book/search")
//        Call<RequestModel> getSearchBooks(@QueryMap Map<String, String> options);
//    }

    // 调用的时候将所有的参数集合在统一的map中即可
//    Map<String, String> options = new HashMap<>();
//    map.put("q","小王子");
//    map.put("tag",null);
//    map.put("start","0");
//    map.put("count","3");
//    Call<RequestModel> call = mBlueService.getSearchBooks(options);

    // 假如你需要添加相同Key值，但是value却有多个的情况，一种方式是添加多个@query参数，
    // 还有一种简便的方式是将所有的value放置在列表中，然后在同一个@query下完成添加，
//    public interface BlueService {
//        @GET("book/search")
//        Call<RequestModel> getSearchBooks(@Query("q") List<String> name);
//    }
}
