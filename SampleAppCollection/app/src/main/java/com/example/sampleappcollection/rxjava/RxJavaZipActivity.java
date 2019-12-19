package com.example.sampleappcollection.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.sdwfqin.sample.R;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件.
 * 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
 *
 * @author zhangqin
 */
public class RxJavaZipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        Observable<Integer> observable1 = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {

            for (int i = 0; i < 3; i++) {
                LogUtils.e("emit " + i);
                emitter.onNext(i);
            }
            LogUtils.e("emit complete1");
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create((ObservableOnSubscribe<String>) emitter -> {

            String[] strings = new String[]{"A", "B", "C"};
            for (int i = 0; i < 3; i++) {
                LogUtils.e("emit " + strings[i]);
                emitter.onNext(strings[i]);
            }
            LogUtils.e("emit complete2");
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, (integer, s) -> integer + s)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.e("onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        LogUtils.e("onNext: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("onComplete");
                    }
                });
    }
}
