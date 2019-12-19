package com.example.sampleappcollection.broadcast.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;

/**
 * 描述：普通广播，静态注册
 *
 * @author zhangqin
 * @date 2016/12/10
 */

public class OrdinaryBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String str = intent.getStringExtra("name");
        LogUtils.i("onReceive(普通广播，静态注册): " + str);
    }
}
