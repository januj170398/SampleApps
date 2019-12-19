package com.example.sampleappcollection.view.viewevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author zhangqin
 * @date 2017/5/4
 */
public class GroupView extends LinearLayout {

    private static final String TAG = "GroupView";

    public GroupView(Context context) {
        super(context);
    }

    public GroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 首先调用这个方法，必须调用super才回继续向下执行，不然不会继续执行
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: ");

        boolean consume = false;

        if (onInterceptTouchEvent(ev)) {
            consume = onTouchEvent(ev);
        } else {
            consume = super.dispatchTouchEvent(ev);
        }
        Log.e(TAG, "dispatchTouchEvent: " + consume);
        return consume;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: ");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: ");
        // 返回true消费事件
        return super.onTouchEvent(event);
    }
}
