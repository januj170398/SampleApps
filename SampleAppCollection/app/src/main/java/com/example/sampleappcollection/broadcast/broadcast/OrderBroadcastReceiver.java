package com.example.sampleappcollection.broadcast.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;

/**
 * 有序广播
 * 广播接收器的优先级通过receiver的intent-filter中的Android:priority属性来设置,
 * Android:priority属性中，数字越大优先级越高．取值范围为-1000~1000
 * <p>
 * 当广播接收器接收到广播后，可以使用setResult()函数来结果传给下一个广播接收器接收，
 * 然后通过getResult()等函数来取得上个广播接收器接收返回的结果。
 * 当广播接收器接收到广播后，也可以用abortBroadcast()函数来让系统拦截下来该广播，
 * 并将该广播丢弃，使该广播不再传送到别的广播接收器接收
 *
 * @author zhangqin
 * @date 2016/12/10
 */
public class OrderBroadcastReceiver {

    public static class HighPriority extends BroadcastReceiver {
        //高级广播接收者
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.i("onReceive: " + "High");
            //传递结果到下一个广播接收器
            int code = 0;
            String data = "hello";
            Bundle bundle = new Bundle();
            bundle.putString("key", "values");
            setResult(code, data, bundle);
        }
    }

    public static class MidPriority extends BroadcastReceiver {
        //中级广播接收者
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取上个广播接收者增加的Bundle
            Bundle bundle = getResultExtras(true);
            LogUtils.i("onReceive: " + "Mid Bunder:" + bundle.getString("key"));
            //获取上一个广播接收器结果
            int code = getResultCode();
            String data = getResultData();
            LogUtils.i("onReceive: " + "获取到上一个广播接收器结果：" + "code=" + code + "data=" + data);
            // 拦截广播
            abortBroadcast();
        }
    }

    public static class LowPriority extends BroadcastReceiver {
        //低级广播接收者
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.i("Low");
        }
    }
}
