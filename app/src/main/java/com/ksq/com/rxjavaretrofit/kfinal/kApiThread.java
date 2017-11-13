package com.ksq.com.rxjavaretrofit.kfinal;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装线程管理和订阅
 * Created by 黑白 on 2017/11/13.
 */

public class kApiThread {
    /**
     * 管理观察者和别观察者的线程
     *
     * @param observable 被观察者
     * @param observer   观察者
     */
    public static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())//子线程请求数据
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getBean(Observer<kMovie> observer, int start, int count) {
        ApiSubscribe(kApi.getApiService().getBean(start, count), observer);
    }

}
