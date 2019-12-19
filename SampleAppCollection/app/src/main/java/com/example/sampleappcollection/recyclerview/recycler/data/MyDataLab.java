package com.example.sampleappcollection.recyclerview.recycler.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 单例
 *
 * @author zhangqin
 * @date 2016/7/7
 */
public class MyDataLab {
    private static MyDataLab sMyDataLab;
    private List<MyData> mDatas;

    private MyDataLab(Context context) {
        mDatas = new ArrayList<>();

        //加载一点测试数据
        for (int i = 0; i < 20; i++) {
            MyData data = new MyData();
            data.setTitle("测试-" + i);
            data.setText(new Date().toString());
            mDatas.add(data);
        }
    }

    public static MyDataLab getMyData(Context context) {
        if (sMyDataLab == null) {
            sMyDataLab = new MyDataLab(context);
        }
        return sMyDataLab;
    }

    public List<MyData> getDatas() {
        return mDatas;
    }

    public MyData getMyData(UUID id) {
        for (MyData data : mDatas) {
            if (data.getId().equals(id)) {
                return data;
            }
        }
        return null;
    }
}
