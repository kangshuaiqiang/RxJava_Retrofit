package com.ksq.com.rxjavaretrofit.kfinal;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 黑白 on 2017/11/13.
 */

public class kMyObserver<T> implements Observer<T> {
    private static final String TAG = "myonserver";
    private kObserverOnNectLinstener linstener;
    private Context context;

    public kMyObserver(kObserverOnNectLinstener linstener, Context context) {
        this.linstener = linstener;
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe");
        //添加业务处理
    }

    @Override
    public void onNext(T value) {
        linstener.onNext(value);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
        //添加业务处理
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
        //添加业务处理
    }
}
