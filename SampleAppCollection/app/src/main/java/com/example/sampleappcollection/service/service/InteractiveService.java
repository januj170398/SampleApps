package com.example.sampleappcollection.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

/**
 * 可交互的后台服务,返回一个代理对象
 * <p>
 * startService和bindService -> 混合性交互的后台服务，即可以单独运行后台服务，也可以运行后台服务中提供的方法，
 * 其完整的生命周期是：onCreate->onStartCommand->onBind->onUnBind->onDestroy
 *
 * @author zhangqin
 * @date 2016/12/8
 */
public class InteractiveService extends Service {

    // 绑定
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("onBind: ");
        return new MyBind();
    }

    /**
     * 代理类
     */
    public class MyBind extends Binder {
        public void showLog() {
            LogUtils.e("showLog: ");
        }

        public void show(String str) {
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            // 调用外部类
            callLog(str);
        }
    }

    public void callLog(String str) {
        LogUtils.e("callLog: " + str);
    }

    // 解绑
    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e("onUnbind: ");
        return super.onUnbind(intent);
    }
}
