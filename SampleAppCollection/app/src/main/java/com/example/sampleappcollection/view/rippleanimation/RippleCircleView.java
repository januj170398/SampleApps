package com.example.sampleappcollection.view.rippleanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zhangqin
 * @date 2017/7/27
 */
public class RippleCircleView extends View {

    private RippleAnimationView mRippleAnimationView;

    public RippleCircleView(Context context) {
        super(context);
    }

    public RippleCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RippleCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RippleCircleView(RippleAnimationView rippleAnimationView, Context context) {
        super(context);
        this.mRippleAnimationView = rippleAnimationView;
        this.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = (Math.min(getWidth(), getHeight())) / 2;
        canvas.drawCircle(radius, radius, radius - mRippleAnimationView.mRippleStrokeWidth, mRippleAnimationView.mPaint);
    }
}