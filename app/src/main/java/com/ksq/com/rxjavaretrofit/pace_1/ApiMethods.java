package com.ksq.com.rxjavaretrofit.pace_1;

import com.ksq.com.rxjavaretrofit.bean.Movie;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑白 on 2017/11/9.
 * 封装线程管理和订阅的过程
 */

public class ApiMethods {

    /**
     * 线程管理和订阅过程
     *
     * @param observable 被观察者
     * @param observer   观察者
     */
    public static void ApiSubscrble(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public static void getTopMOvie(Observer<Movie> observer, int start, int count) {
        ApiSubscrble(Api.getApiService().getTopMovie(start, count), observer);

    }

}
