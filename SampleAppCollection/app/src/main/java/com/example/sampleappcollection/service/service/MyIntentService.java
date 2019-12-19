package com.example.sampleappcollection.service.service;

import android.app.IntentService;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;

/**
 * 描述：后台服务
 *
 * @author zhangqin
 * @date 2016/12/8
 */
public class MyIntentService extends IntentService {

    /**
     * 需要注意构造方法
     */
    public MyIntentService() {
        super("name");
        LogUtils.e("MyIntentService: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 在此处执行耗时操作
        LogUtils.e("onHandleIntent: " + "开始执行");
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.e("onHandleIntent: " + "执行结束");
    }
}
