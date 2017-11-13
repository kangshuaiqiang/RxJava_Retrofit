package com.ksq.com.rxjavaretrofit.kfinal;

import com.ksq.com.rxjavaretrofit.modle.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 黑白 on 2017/11/13.
 */

public class kApi {
    public static String baseUrl = "https://api.douban.com/v2/movie/";

    public static kApiService service;

    public static kApiService getApiService() {
        if (service == null) {
            synchronized (kApi.class) {
                if (service == null) {
                    new kApi();
                }
            }
        }
        return service;
    }

    private kApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(kApiService.class);

    }
}
