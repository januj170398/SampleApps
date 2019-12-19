package com.example.sampleappcollection.view.viewevent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author zhangqin
 * @date 2017/5/4
 */
public class AView extends AppCompatTextView {

    public AView(Context context) {
        super(context);
    }

    public AView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.i("dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i("onTouchEvent: ");
        // 返回true当前view处理，返回false向上传递
        return true;
    }
}
