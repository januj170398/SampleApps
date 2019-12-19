package com.example.sampleappcollection.view.courtesycard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.sdwfqin.sample.R;

/**
 * 描述：自定义View2凹凸边缘
 *
 * @author zhangqin
 * @date 2017/5/10
 */
public class CourtesyCardView extends LinearLayout {

    private Context mContext;
    private int mColor = Color.WHITE;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 要提高精度可以使用float
    //圆的半径
    private int radius = 10;
    //圆之间的间距
    private int gap = 10;

    public CourtesyCardView(Context context) {
        super(context);
        init(context);
    }

    public CourtesyCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CourtesyCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context) {
        mContext = context;
        mPaint.setColor(mColor);
        gap = ConvertUtils.dp2px(10);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CourtesyCardView);
        // Color.WHITE为默认颜色
        mColor = typedArray.getColor(R.styleable.CourtesyCardView_circle_color, Color.WHITE);
        radius = typedArray.getDimensionPixelSize(R.styleable.CourtesyCardView_radius, radius);
        gap = typedArray.getDimensionPixelSize(R.styleable.CourtesyCardView_gap, gap);
        typedArray.recycle();
        mPaint.setColor(mColor);
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
        mPaint.setColor(mColor);
        // 刷新视图
        invalidate();
    }

    // 读取圆的半径
    public int getRadius() {
        return ConvertUtils.px2dp(radius);
    }

    // 设置圆的半径
    public void setRadius(int radius) {
        this.radius = ConvertUtils.dp2px(radius);
        // 刷新视图
        invalidate();
    }

    public int getGap() {
        return ConvertUtils.px2dp(gap);
    }

    public void setGap(int gap) {
        this.gap = ConvertUtils.dp2px(gap);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取宽度与高度
        int width = getWidth();
        int height = getHeight();
        //圆的个数
        int count;
        try {
            // 计算可以显示多少个圆
            count = (width - gap) / (radius * 2 + gap);
        } catch (Exception e) {
            count = 0;
            LogUtils.e("onDraw: ", e);
        }
        // 圆的直径
        int h = (radius * 2);
        // 圆以外的长度
        int cgap = (width - (count * h));
        // 两侧端点的长度，
        int x1 = (cgap + gap - (gap * count)) / 2;
        // 绘制
        for (int i = 0; i < count; i++) {
            int x = x1 + radius + (h + gap) * i;
            canvas.drawCircle(x, 0, radius, mPaint);
            canvas.drawCircle(x, height, radius, mPaint);
        }
    }
}
