package com.example.sampleappcollection.rxjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述：RxJava2线程切换
 *
 * @author zhangqin
 */
public class RxJavaSchedulersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        Observable<Integer> observable = Observable.create(emitter -> {
            LogUtils.e("Observable thread is : " + Thread.currentThread().getName());
            LogUtils.e("emit 1");
            emitter.onNext(1);
        });

        // Consumer(消费者)表示只关心onNext事件
        Consumer<Integer> consumer = integer -> {
            LogUtils.e("Observer thread is :" + Thread.currentThread().getName());
            LogUtils.e("onNext: " + integer);
        };

        /**
         * subscribeOn： 指定Observable发送事件的线程
         * observeOn： 指定Observer接收事件的线程
         *
         * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
         * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
         * Schedulers.newThread() 代表一个常规的新线程
         * AndroidSchedulers.mainThread() 代表Android的主线程
         */
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
