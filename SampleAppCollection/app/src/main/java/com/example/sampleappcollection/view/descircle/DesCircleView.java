package com.example.sampleappcollection.view.descircle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sdwfqin.sample.R;

/**
 * 描述：自定义View1圆
 *
 * @author zhangqin
 */
public class DesCircleView extends View {

    private int mColor = Color.BLUE;
    /**
     * 抗锯齿
     */
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DesCircleView(Context context) {
        super(context);
        init();
    }

    public DesCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DesCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DesCircleView);
        // Color.BLUE为默认颜色
        mColor = typedArray.getColor(R.styleable.DesCircleView_bg, Color.BLUE);
        typedArray.recycle();
        mPaint.setColor(mColor);
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
        mPaint.setColor(mColor);
        // 刷新视图
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取边距，自己处理，不然会不显示
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        // Math.min返回这两个数的较小值
        int radius = Math.min(width, height) / 2;

        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);
    }
}
