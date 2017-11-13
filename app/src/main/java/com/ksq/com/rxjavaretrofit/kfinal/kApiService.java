package com.ksq.com.rxjavaretrofit.kfinal;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 因为要结合RxJava使用  所以返回的就不是call 而是一个Observale
 * Created by 黑白 on 2017/11/13.
 */

public interface kApiService {
    @GET("top250")
    Observable<kMovie> getBean(@Query("star") int star, @Query("count") int count);
}
