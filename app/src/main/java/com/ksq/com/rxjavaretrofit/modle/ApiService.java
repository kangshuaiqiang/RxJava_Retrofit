package com.ksq.com.rxjavaretrofit.modle;

import com.ksq.com.rxjavaretrofit.bean.Movie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 黑白 on 2017/11/9.
 */

public interface ApiService {
    //因为要结合Rxjava使用  所以返回Observable
    @GET("top250")
    Observable<Movie> getTopMovie(@Query("start") int start, @Query("count") int count);
}
