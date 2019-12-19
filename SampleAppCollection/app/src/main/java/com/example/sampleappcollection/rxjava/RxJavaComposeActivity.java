package com.example.sampleappcollection.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.sdwfqin.sample.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述：RxJava2 compose
 *
 * @author zhangqin
 */
public class RxJavaComposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        rx01();
    }

    private void rx01() {

        //创建一个 Observable (被观察者)
        Observable<Integer> observable = Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            // 完成
            emitter.onComplete();
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
        observable
                /**
                 * compose()是唯一一个能从流中获取原生Observable 的方法，
                 * 影响整个流的操作符（像subscribeOn()和observeOn()）需要使用compose()，
                 */
                .compose(upstream -> upstream.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread()))
                .subscribe(observer);
    }
}
