package com.example.sampleappcollection.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.sdwfqin.sample.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 描述：RxJava2 入门
 *
 * @author zhangqin
 */
public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        // 入门
        rx01();
        // 链式操作,dispose()
        rx02();
    }

    private void rx01() {

        LogUtils.e("rx01: ====== start ======");

        //创建一个 Observable (被观察者)
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            /**
             * ObservableEmitter：它可以通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)
             * 发出next事件、complete（完成）事件和error（错误）事件
             *
             * Observable可以发送无限个onNext, Observer也可以接收无限个onNext.
             *
             * 当Observable发送了一个onComplete后, Observable的onComplete之后的事件将会继续发送,
             * 而Observer收到onComplete事件之后将不再继续接收事件.
             *
             * 当Observable发送了一个onError后, Observableon的Error之后的事件将继续发送,
             * 而Observer收到onError事件之后将不再继续接收事件.
             */
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                // 完成
                emitter.onComplete();
            }
        });
        //创建一个 Observer(观察者)
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.e("subscribe");
            }

            @Override
            public void onNext(Integer value) {
                LogUtils.e("" + value);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("error", e);
            }

            @Override
            public void onComplete() {
                LogUtils.e("complete");
                LogUtils.e("rx01: ====== end ======");
            }
        };

        //建立连接
        observable.subscribe(observer);
    }

    private void rx02() {

        LogUtils.e("rx02: ====== start ======");

        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            for (int i = 0; i < 3; i++) {
                LogUtils.e("发送: " + i);
                e.onNext(i);
            }
            e.onComplete();
            LogUtils.e("发送: " + 99);
            e.onNext(99);
        }).subscribe(new Observer<Integer>() {

            // 调用dispose()会导致Observer不在接收事件
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtils.e("绑定: ");
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtils.e("接收: " + integer);
                if (integer == 1) {
                    disposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.e("onError: ", e);
            }

            @Override
            public void onComplete() {
                LogUtils.e("onComplete: ");
                LogUtils.e("rx02: ====== end ======");
            }
        });
    }
}
