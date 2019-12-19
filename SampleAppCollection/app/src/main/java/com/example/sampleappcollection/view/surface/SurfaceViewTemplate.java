package com.example.sampleappcollection.view.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.blankj.utilcode.util.LogUtils;

/**
 * 描述：SurfaceView模板
 *
 * @author zhangqin
 * @date 2017/6/27
 */
public class SurfaceViewTemplate extends SurfaceView
        implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    /**
     * 用于绘图的Canvas
     */
    private Canvas mCanvas;
    /**
     * 子线程标志位
     */
    private boolean mIsDrawing;

    public SurfaceViewTemplate(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        // 注册回掉方法
        mHolder.addCallback(this);
        // 设置此视图可以接收焦点
        setFocusable(true);
        // 设置此视图是否可以在触摸模式下接收焦点。
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        // mHolder.setFormat(PixelFormat.OPAQUE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            draw();
        }
    }

    private void draw() {
        try {
            // 获取Canvas对象
            mCanvas = mHolder.lockCanvas();
        } catch (Exception e) {
            LogUtils.e("draw: ", e);
        } finally {
            if (mCanvas != null) {
                // 提交画布内容
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
