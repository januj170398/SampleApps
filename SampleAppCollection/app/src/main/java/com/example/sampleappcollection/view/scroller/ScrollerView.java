package com.example.sampleappcollection.view.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.LogUtils;

/**
 * 描述：View滑动
 *
 * @author zhangqin
 * @date 2017/4/26
 */
public class ScrollerView extends AppCompatTextView {

    private Context mContext;
    private Scroller scroller;

    public ScrollerView(Context context) {
        super(context);
        init(context);
    }

    public ScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        scroller = new Scroller(mContext);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 这三种方法都是只改变View内容的位置，不改变View的位置
            smoothScrollTo(-100, -100);
//            scrollTo(-100, -100);
//            scrollBy(-100, -100);
        }
        return true;
    }

    /**
     * 弹性滑动，只滑动内容，不改变位置（destX正左负右，destY正上负下）
     * @param destX
     * @param destY
     */
    private void smoothScrollTo(int destX, int destY) {
        LogUtils.e("smoothScrollTo: ");
        // scrollX,scrollY对应原始位置左上角，水平与竖直方向
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int delta = destX - scrollX;
        int deltb = destY - scrollY;
        // 1000ms内滑向destX
        scroller.startScroll(scrollX, scrollY, delta, deltb, 1000);
        // 调用computeScroll方法
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            // 二次重绘
            postInvalidate();
        }
    }
}
