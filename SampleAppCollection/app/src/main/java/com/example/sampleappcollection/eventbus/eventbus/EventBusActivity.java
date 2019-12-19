package com.example.sampleappcollection.eventbus.eventbus;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventBusActivity extends AppCompatActivity {

    @BindView(R.id.btn_event1_send)
    Button mBtnEvent1Send;
    @BindView(R.id.btn_event1_send2)
    Button btnEvent1Send2;
    @BindView(R.id.tv_event1_msg)
    TextView tvEvent1Msg;

    private static final String TAG = "EventBusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);

        mBtnEvent1Send.setOnClickListener(v -> {
            // post发布消息
            EventBus.getDefault().post("Hello everyone!");
        });

        btnEvent1Send2.setOnClickListener(v -> {
            MessageEvent event = new MessageEvent();
            event.setId(UUID.randomUUID().toString());
            event.setName("张三");
            event.setMessage("测试");
            EventBus.getDefault().post(event);
        });

    }

    /**
     * 事件处理（订阅者）
     * <p>
     * ThreadMode 介绍
     * ThreadMode.POSTING：默认模式，效率高，收到消息就执行。让订阅方法工作在与发布消息同一个线程中。
     * ThreadMode.MAIN：让订阅方法始终切换到 UI主线程中执行
     * ThreadMode.BACKGROUND：启动新线程，让订阅方法在新线程中执行
     * ThreadMode.ASYNC：异步模式，也是工作在新线程中，但是EventBus提供了线程池的管理，避免线程不停创建。
     * <p>
     * priority越大，级别越高
     * sticky = true 接收粘性事件
     */
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN, priority = 100)
    public <T> void onMessageEvent(T t) {
        try {
            tvEvent1Msg.setText(t.toString());
        } catch (Exception e) {
            Log.e(TAG, "onMessageEvent: ", e);
        }
    }
//    接收String
//    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
//    public void onStringEvent(String s) {
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//    }

//    接收类
//    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
//    public void onMessageEvent(MessageEvent event) {
//        try {
//            tvEvent1Msg.setText(event.toString());
//        } catch (Exception e) {
//            Log.e(TAG, "onMessageEvent: ", e);
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        // 反注册
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
