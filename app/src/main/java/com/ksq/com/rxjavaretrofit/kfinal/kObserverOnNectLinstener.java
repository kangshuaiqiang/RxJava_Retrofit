package com.ksq.com.rxjavaretrofit.kfinal;

/**
 * //新建接口   使用泛型达到适用所有类型的数据
 * Created by 黑白 on 2017/11/13.
 */

public interface kObserverOnNectLinstener<T> {
    void onNext(T t);
}
