package com.example.sampleappcollection.rxjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述：背压处理
 *
 * @author zhangqin
 */
public class RxJavaFlowableActivity extends AppCompatActivity {

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        /**
         * Subscription.request()方法表示Subscriber要处理几个事件
         * emitter.requested()方法表示减少几个事件
         * 在Flowable中使用Subscription.cancel()关闭事件处理
         */
        // BackpressureStrategy.ERROR  默认128kb  超出会抛出MissingBackpressureException异常
        // BackpressureStrategy.BUFFER 无大小限制
        // BackpressureStrategy.DROP   存不下的事件直接丢弃
        // BackpressureStrategy.LATEST 只保留最新的事件，与DROP相反
        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < 128; i++) {
                LogUtils.e("emit " + i);
                emitter.onNext(i);
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                /**
                 * onBackpressureBuffer()
                 * onBackpressureDrop()
                 * onBackpressureLatest()
                 */
                // .onBackpressureDrop()//添加背压策略
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        LogUtils.e("onSubscribe");
                        // 全局的
                        mSubscription = s;
                        // 处理多少个发送过来的事件
                        mSubscription.request(Long.MAX_VALUE);
                        // 取消接收事件
                        // mSubscription.cancel();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtils.e("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e("onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("onComplete");
                    }
                });
    }
}
