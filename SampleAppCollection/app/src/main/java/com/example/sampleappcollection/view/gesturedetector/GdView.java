package com.example.sampleappcollection.view.gesturedetector;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

/**
 * 描述：手势识别
 *
 * @author zhangqin
 * @date 2017/4/26
 */
public class GdView extends LinearLayout {

    private Context mContext;
    private GestureDetector mGestureDetector;

    public GdView(Context context) {
        super(context);
        init(context);
    }

    public GdView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GdView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mGestureDetector = new GestureDetector(mContext, onGestureListener);
        mGestureDetector.setOnDoubleTapListener(onDoubleTapListener);
        //解决长按屏幕无法拖动,但是会造成无法识别长按事件
//        mGestureDetector.setIsLongpressEnabled(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 接管onTouchEvent
        return mGestureDetector.onTouchEvent(event);
    }

    GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            LogUtils.i("onDown: 按下");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            LogUtils.i("onShowPress: 刚碰上还没松开");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            LogUtils.i("onSingleTapUp: 轻轻一碰后马上松开");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            LogUtils.i("onScroll: 按下后拖动");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            LogUtils.i("onLongPress: 长按屏幕");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            LogUtils.i("onFling: 滑动后松开");
            return true;
        }
    };

    GestureDetector.OnDoubleTapListener onDoubleTapListener = new GestureDetector.OnDoubleTapListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            LogUtils.i("onSingleTapConfirmed: 严格的单击");
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            LogUtils.i("onDoubleTap: 双击");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            LogUtils.i("onDoubleTapEvent: 表示发生双击行为");
            return true;
        }
    };
}
