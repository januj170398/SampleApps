package com.example.sampleappcollection.view.motionslop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author zhangqin
 * @date 2017/4/26
 */
public class MeTsView extends LinearLayout {

    public MeTsView(Context context) {
        super(context);
    }

    public MeTsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeTsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float x = 0, y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.i("onTouchEvent: " + "按下");
                LogUtils.i("getX: " + event.getX());
                LogUtils.i("getY: " + event.getY());
                LogUtils.i("getRawX: " + event.getRawX());
                LogUtils.i("getRawY: " + event.getRawY());

                x = event.getX();
                y = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("onTouchEvent: " + "移动");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.i("onTouchEvent: " + "松开" + x);
                if (event.getX() - x > slop) {
                    LogUtils.i("onTouchEvent: " + "往右滑动" + event.getX());
                } else if (x - event.getX() > slop) {
                    LogUtils.i("onTouchEvent: " + "往左滑动" + event.getX());
                } else {
                    LogUtils.i("onTouchEvent: " + "无效滑动" + event.getX());
                }
                x = 0;
                y = 0;
                break;
            default:
                break;
        }
        // 返回true，拦截这个事件
        // 返回false，不拦截
        return true;
    }
}
