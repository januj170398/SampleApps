package com.example.sampleappcollection.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 不可交互的后台服务即是普通的Service，Service的生命周期很简单，
 * 分别为onCreate、onStartCommand、onDestroy这三个。
 * 当我们startService()的时候，首次创建Service会回调onCreate()方法，
 * 然后回调onStartCommand()方法，再次startService()的时候，
 * 就只会执行一次onStartCommand()。
 * 服务一旦开启后，我们就需要通过stopService()方法或者stopSelf()方法，
 * 就能把服务关闭，这时就会回调onDestroy()
 *
 * @author zhangqin
 * @date 2016/12/8
 */
public class NotInteractiveService extends Service {

    private Timer mTimer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e( "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        LogUtils.e( "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e( "onStartCommand: ");
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                LogUtils.e( "====NotInteractiveService====");
            }
        }, 0, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
        LogUtils.e( "onDestroy: ");
        super.onDestroy();
    }
}
