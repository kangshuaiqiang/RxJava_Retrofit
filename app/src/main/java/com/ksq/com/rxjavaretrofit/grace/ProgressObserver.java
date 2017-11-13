package com.ksq.com.rxjavaretrofit.grace;

import android.content.Context;
import android.util.Log;

import com.ksq.com.rxjavaretrofit.pace_2.ObserverOnNextListener;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 黑白 on 2017/11/10.
 */

public class ProgressObserver<T> implements Observer<T>, ProgressCancelListener {

    private static final String TAG = "ProgressObserver";
    private ObserverOnNextListener listener;
    private Context context;
    private Disposable d;
    private ProgressDialogHandler progressDialogHandler;

    public ProgressObserver(ObserverOnNextListener listener, Context context) {
        this.listener = listener;
        this.context = context;

        progressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS).sendToTarget();
            progressDialogHandler = null;
        }
    }

    @Override
    public void onCancelPregress() {
        if (!d.isDisposed()) {
            d.dispose();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        showProgressDialog();
    }

    @Override
    public void onNext(T value) {
        listener.onNext(value);
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Log.d(TAG, "onError" + e);
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
        Log.d(TAG, "oncomplete");
    }
}
