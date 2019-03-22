package com.tldxdy.mydemo.retrofitMvp.api;

import com.tldxdy.mydemo.retrofitMvp.model.TestBean;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface RetrofitService {
    String BASE_URL = "https://news-at.zhihu.com/api/4/";
    /**
     * 测试接口
     *
     * @return
     */
    @GET("news/latest")
    Observable<TestBean> test();
}
