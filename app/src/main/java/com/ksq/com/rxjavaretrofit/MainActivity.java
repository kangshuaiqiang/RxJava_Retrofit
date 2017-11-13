package com.ksq.com.rxjavaretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ksq.com.rxjavaretrofit.bean.Movie;
import com.ksq.com.rxjavaretrofit.grace.ProgressObserver;
import com.ksq.com.rxjavaretrofit.gran_timer.ApiStrategy;
import com.ksq.com.rxjavaretrofit.kfinal.kApiThread;
import com.ksq.com.rxjavaretrofit.kfinal.kMovie;
import com.ksq.com.rxjavaretrofit.kfinal.kMyObserver;
import com.ksq.com.rxjavaretrofit.kfinal.kObserverOnNectLinstener;
import com.ksq.com.rxjavaretrofit.modle.ApiService;
import com.ksq.com.rxjavaretrofit.pace_1.ApiMethods;
import com.ksq.com.rxjavaretrofit.pace_2.MyObserver;
import com.ksq.com.rxjavaretrofit.pace_2.ObserverOnNextListener;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String baseUrl = "https://api.douban.com/v2/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        https://api.douban.com/v2/movie/top250?start=0&count=10

        //基本请求
//        testBasic();
        //初级封装
//        testPackaging();
        //二级封装
//        testPackaging2();
        //优雅的封装  具有缓存条
//        testGrace();
        //优雅的封装   性能优化
//        testTimer();c

        //最后的封装
        testfinal();

    }

    private void testfinal() {
        kObserverOnNectLinstener<kMovie> linstener = new kObserverOnNectLinstener<kMovie>() {
            @Override
            public void onNext(kMovie kMovie) {
                Log.d("zzzzz", "onNext: " + kMovie.getTitle());
                List<com.ksq.com.rxjavaretrofit.kfinal.kMovie.SubjectsBean> subjects = kMovie.getSubjects();
                for (com.ksq.com.rxjavaretrofit.kfinal.kMovie.SubjectsBean sub : subjects) {
                    Log.d("zzzzzz", "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                }
            }
        };
        kApiThread.getBean(new kMyObserver<kMovie>(linstener, this), 0, 10);
    }

    private void testTimer() {

    }

    private void testGrace() {

        ObserverOnNextListener<Movie> listener = new ObserverOnNextListener<Movie>() {
            @Override
            public void onNext(Movie movie) {
                Log.d("zzzzzz", "onNext: " + movie.getTitle());
                List<Movie.SubjectsBean> subjects = movie.getSubjects();
                for (Movie.SubjectsBean sub : subjects) {
                    Log.d("zzzzz", "onNext: " + sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                }
            }
        };
        ApiMethods.getTopMOvie(new ProgressObserver<Movie>(listener, this), 0, 10);

    }

    private void testPackaging2() {
        ObserverOnNextListener<Movie> listener = new ObserverOnNextListener<Movie>() {
            @Override
            public void onNext(Movie movie) {
                Log.d("zzzzzz", movie.getTitle());
                List<Movie.SubjectsBean> subjects = movie.getSubjects();

                for (Movie.SubjectsBean sub : subjects) {
                    Log.d("zzzzzz", sub.getId() + "," + sub.getTitle() + "," + sub.getYear());
                }
            }
        };

        ApiMethods.getTopMOvie(new MyObserver<Movie>(listener, this), 0, 10);
    }

    private void testPackaging() {
        Observer<Movie> observer = new Observer<Movie>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("zzzzzz", "onsubscribe");
            }

            @Override
            public void onNext(Movie value) {
                Log.d("zzzzz", value.getTitle());
                List<Movie.SubjectsBean> subjects = value.getSubjects();
                for (Movie.SubjectsBean sub : subjects) {
                    Log.d("zzzzzz", sub.getTitle() + "," + sub.getYear() + "," + sub.getId());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("zzzzzz", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("zzzzzz", "onComplete");
            }
        };
        ApiMethods.getTopMOvie(observer, 0, 10);
    }

    private void testBasic() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create()) //请求转化为基本类型
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("zzzzzz", "onSubscribe");
                    }

                    @Override
                    public void onNext(Movie value) {
                        Log.d("zzzzzz", value.getTitle());
                        List<Movie.SubjectsBean> subjects = value.getSubjects();

                        for (Movie.SubjectsBean sub : subjects) {
                            Log.d("zzzzzz", sub.getId() + "," + sub.getYear() + "," + sub.getTitle());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("zzzzzz", "onError: " + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.d("zzzzzz", "onComplete: Over!");
                    }
                });


    }
}
