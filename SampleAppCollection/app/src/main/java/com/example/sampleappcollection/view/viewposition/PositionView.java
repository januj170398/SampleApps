package com.example.sampleappcollection.view.viewposition;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;

/**
 * @author zhangqin
 * @date 2017/4/26
 */
public class PositionView extends AppCompatTextView {

    private Context context;
    private boolean mTranFlag = false;

    public PositionView(Context context) {
        super(context);
        this.context = context;
    }

    public PositionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PositionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setTranslation() {
        if (!mTranFlag) {
            // 正数往右，负数往左
            setTranslationX(100);
            setTranslationY(100);
            // 设置该组件在Z方向（垂直屏幕方向）上的位移，可以看到添加了阴影
//            setTranslationZ(100);
            mTranFlag = true;
        } else {
            // 回复原来的位置
            setTranslationX(0);
            setTranslationY(0);
            // 设置该组件在Z方向（垂直屏幕方向）上的位移。
//            setTranslationZ(0);
            mTranFlag = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int left = getLeft();
        int Right = getRight();
        int Top = getTop();
        int Bottom = getBottom();
//        float Z = getZ();

        LogUtils.i("left: " + left);
        LogUtils.i("right: " + Right);
        LogUtils.i("top: " + Top);
        LogUtils.i("bottom: " + Bottom);
//        LogUtils.i("Z: " + Z);

        int width = Right - left;
        int widthdp = ConvertUtils.px2dp(width);
        int height = Bottom - Top;
        int heightdp = ConvertUtils.px2dp(height);
        // 转换为dp
        LogUtils.i("width:" + width);
        LogUtils.i("宽度（dp）:" + widthdp);
        LogUtils.i("height:" + height);
        LogUtils.i("高度（dp）:" + heightdp);

        float translationX = getTranslationX();
        float translationY = getTranslationY();
//        float translationZ = getTranslationZ();

        LogUtils.i("translationX:" + translationX);
        LogUtils.i("translationY:" + translationY);
//        LogUtils.i("translationZ:" + translationZ);

        // x,y:移动后left与top的位置
        float x = left + translationX;
        float y = Top + translationY;

        LogUtils.i("移动后left的位置" + x);
        LogUtils.i("移动后top的位置" + y);

        super.onDraw(canvas);
    }
}
