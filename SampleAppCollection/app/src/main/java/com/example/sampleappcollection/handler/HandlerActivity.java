package com.example.sampleappcollection.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhangqin
 */
public class HandlerActivity extends AppCompatActivity {

    private static final String TAG = "HandlerActivity";
    private MyHandler mMyHandler;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        mMyHandler = new MyHandler(this);

        mMyHandler.sendEmptyMessage(1);

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mMyHandler.sendEmptyMessage(2);
            }
        }, 1000, 1000);
        mMyHandler.sendEmptyMessage(3);
    }

    static class MyHandler extends Handler {

        private WeakReference<HandlerActivity> mActivity;

        public MyHandler(HandlerActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerActivity activity = mActivity.get();
            switch (msg.what) {
                case 1:
                    Log.e(TAG, "handlerA:case:1");
                    break;
                case 2:
                    Log.e(TAG, "handlerA:case:2");
                    break;
                case 3:
                    Log.e(TAG, "handlerA:case:3");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        mMyHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
