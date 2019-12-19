package com.example.sampleappcollection;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * 描述：Application
 *
 * @author zhangqin
 * @date 2017/11/8
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
