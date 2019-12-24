package com.example.sampleappcollection.retrofit.api;

import com.example.sampleappcollection.retrofit.model.RequestModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述：Post请求
 *
 * @author zhangqin
 * @date 2017/5/14
 */
public interface RequestPostApi {

    /**
     * Post请求需要把请求参数放置在请求体中，而非拼接在url后面
     * <p>
     * FormUrlEncode将会自动将请求参数的类型调整为application/x-www-form-urlencoded
     * FormUrlEncode不能用于get请求
     * <p>
     * field注解将每一个请求参数都存放至请求体中，还可以添加encoded参数，该参数为boolean型
     * 例如：@Field(value = "book", encoded = true) String book
     * encoded参数为true的话，key-value-pair将会被编码，即将中文和特殊字符进行编码转换
     */
    @FormUrlEncoded // @FormUrlEncoded的默认编码方式为UTF-8
    @POST("PostServlet")
    Observable<RequestModel> PostData(@Field("name") String name,
                                      @Field("value") String value);

// @FieldMap
// 与get请求中的@QueryMap类似
// 假如说有更多的请求参数，那么通过一个一个的参数传递就显得很麻烦而且容易出错，这个时候就可以用FieldMap
// @FormUrlEncoded
// @POST("book/reviews")
// Call<String> addReviews(@FieldMap Map<String, String> fields);
// 这种方式生成的body格式为text/plain,例如book=123&title=小王子&content=非常好看


    // @Body(将请求参数封装到类中)
    // 如果Post请求参数有多个，那么统一封装到类中应该会更好
//    @FormUrlEncoded
//    @POST("book/reviews")
//    Call<String> addReviews(@Body Reviews reviews);
//
//    public class Reviews {
//        public String book;
//        public String title;
//        public String content;
//        public String rating;
//    }
    // 这种方式生成的body格式为json/plain
//    {
//        "book":"123",
//        "title":"小王子",
//        "content":"非常好看"
//    }

}
