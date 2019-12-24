package com.example.sampleappcollection.view.viewposition;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 描述：
 *
 * @author zhangqin
 * @date 2017/4/27
 */
public class TranslationView extends LinearLayout {

    public TranslationView(Context context) {
        super(context);
    }

    public TranslationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TranslationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float x = 0, y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getRawX();
                y = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                float rawX = event.getRawX();
                float rawY = event.getRawY();

                float translationX = getTranslationX();
                float translationY = getTranslationY();

                // 计算偏移量
                float deltaX = (rawX - x) + translationX;
                float deltaY = (rawY - y) + translationY;

                setTranslation(deltaX, deltaY);

                x = event.getRawX();
                y = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    private void setTranslation(float deltaX, float deltaY) {
        // 正数往右，负数往左
        setTranslationX(deltaX);
        setTranslationY(deltaY);
    }
}
