package com.ksq.com.rxjavaretrofit.gran_timer;

import android.text.TextUtils;

import com.ksq.com.rxjavaretrofit.kApp;
import com.ksq.com.rxjavaretrofit.modle.ApiService;
import com.ksq.com.rxjavaretrofit.pace_1.Api;
import com.ksq.com.rxjavaretrofit.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 黑白 on 2017/11/10.
 * 缓存优化
 */

public class ApiStrategy {
    public static String baseUrl = "https://api.douban.com/v2/movie/";
    //读取超时   单位毫秒
    public static final int READ_TIME_OUT = 7676;
    //链接超时  单位毫秒
    public static final int CONNECT_TIME_OUT = 7676;

    public static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new ApiStrategy();
                }
            }
        }
        return apiService;
    }

    private ApiStrategy() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(kApp.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        //添加头部信息
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")//允许请求json数据
                        .build();
                return chain.proceed(build);
            }
        };

        //创建一个OkheepClient
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mInterceptor)
                .addNetworkInterceptor(mInterceptor)
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

    }

    /**
     * 设置缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 云响应头拦截器，用来设置缓存策略
     */
    private final Interceptor mInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String s = request.cacheControl().toString();
            if (util.isNetConnected(kApp.getContext())) {
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(s) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (util.isNetConnected(kApp.getContext())) {
                return originalResponse.newBuilder()
                        .header("Cache-Control", s)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }

        }
    };


}
