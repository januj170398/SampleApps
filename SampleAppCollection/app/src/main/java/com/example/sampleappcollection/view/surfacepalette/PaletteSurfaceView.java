package com.example.sampleappcollection.view.surfacepalette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.blankj.utilcode.util.LogUtils;

/**
 * 描述：SurfaceView画板
 *
 * @author zhangqin
 * @date 2017/6/27
 */
public class PaletteSurfaceView extends SurfaceView
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
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    public PaletteSurfaceView(Context context) {
        super(context);
        initView();
    }

    public PaletteSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PaletteSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
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
        long start = System.currentTimeMillis();
        while (mIsDrawing) {
            draw();
        }
        long end = System.currentTimeMillis();
        // 50-100
        if (end - start < 100) {
            try {
                Thread.sleep(100 - (end - start));
            } catch (InterruptedException e) {
                LogUtils.i("run: ", e);
            }
        }
    }

    private void draw() {
        try {
            // 获取Canvas对象
            mCanvas = mHolder.lockCanvas();
            // SurfaceView背景
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
            LogUtils.i("draw: ", e);
        } finally {
            if (mCanvas != null) {
                // 提交画布内容
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

}
