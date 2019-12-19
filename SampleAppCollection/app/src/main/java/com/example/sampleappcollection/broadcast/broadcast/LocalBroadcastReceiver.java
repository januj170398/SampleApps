package com.example.sampleappcollection.broadcast.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;

/**
 * 本地广播
 * 在API21的Support v4包中新增本地广播，也就是LocalBroadcastManager。
 * 由于之前的广播都是全局的，所有应用程序都可以接收到，这样就会带来安全隐患，
 * LocalBroadcastManager只发送给自己应用内的信息广播，限制在进程内使用
 * <p>
 * 用法：把调用context的sendBroadcast、registerReceiver、unregisterReceiver的地方
 * 换为LocalBroadcastManager.getInstance(Context context)中对应的函数
 *
 * @author zhangqin
 * @date 2016/12/10
 */

public class LocalBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String str = intent.getStringExtra("name");
        LogUtils.i("onReceive(本地广播): " + str);
    }
}
