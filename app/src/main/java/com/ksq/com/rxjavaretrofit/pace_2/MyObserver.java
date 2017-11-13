package com.ksq.com.rxjavaretrofit.pace_2;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 黑白 on 2017/11/10.
 */

public class MyObserver<T> implements Observer<T> {
    private static final String TAG = "MyObserver";
    private ObserverOnNextListener listener;
    private Context context;

    public MyObserver(ObserverOnNextListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubseribe");
    }

    @Override
    public void onNext(T value) {
        listener.onNext(value);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError" + e);
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }
}
