package com.example.sampleappcollection.rxjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述：map变换
 *
 * @author zhangqin
 */
public class RxJavaMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        // 事件转换
        rxJavaMap();
        // FlatMap将一个发送事件的Observable变换为多个发送事件的Observables，
        // 然后将它们发射的事件合并后放进一个单独的Observable里.
        // 并不保证发送顺序，如果需要保证顺序则需要使用concatMap
        rxJavaFlatMap();

    }

    private void rxJavaFlatMap() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
        }).flatMap(integer -> {
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("I am value " + integer);
            }
            // 10毫秒的延时
            return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> LogUtils.e(s));
    }

    private void rxJavaMap() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
        })
                .map(integer -> "This is result " + integer)
                .subscribe(s -> LogUtils.e(s));
    }
}
