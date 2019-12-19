package com.example.sampleappcollection.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;

/**
 * 文档：http://www.sdwfqin.com/2017/04/15/Canvas绘图
 *
 * @author zhangqin
 * @date 2017/4/27
 */
public class CanvasView extends LinearLayout {

    public CanvasView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        LogUtils.e("getX: " + event.getX());
        LogUtils.e("getY: " + event.getY());

        return true;
    }

    // TODO: Canvas绘图
    // 如果onDraw方法不调用，需要在构造方法中添加setWillNotDraw(false);
    // 解释：设置view是否更改，如果开发者用自定义的view，重写ondraw（）应该将调用此方法设置为false，这样程序会调用自定义的布局。
    // 参考文章链接：http://blog.csdn.net/look85/article/details/8442675
    @Override
    protected void onDraw(Canvas canvas) {

        LogUtils.e("onDraw: " + getWidth());
        LogUtils.e("onDraw: " + getHeight());

        LogUtils.e("onDraw: " + "画图");

        super.onDraw(canvas);

        // 创建画笔
        Paint p = new Paint();
        // 设置画笔颜色
        p.setColor(Color.RED);

        // 设置绘制的文本大小
        p.setTextSize(60);
        // x,y方向的值均为相对于当前布局
        /**
         * @param text  文本
         * @param x     水平方向起点
         * @param y     竖直方向的文字底部
         * @param paint 画笔
         */
        // 画文本
        canvas.drawText("画圆：", 50, 100, p);

        /**
         * 画圆
         * @param cx     圆心x坐标
         * @param cy     圆心y坐标
         * @param radius 半径
         * @param paint  画笔
         */
        // 小园
        canvas.drawCircle(300, 80, 50, p);
        // 设置画笔的锯齿效果。true是去除
        // 大圆
        p.setAntiAlias(true);
        canvas.drawCircle(500, 80, 100, p);

        canvas.drawText("画线及弧线：", 50, 250, p);
        // 设置绿色
        p.setColor(Color.GREEN);
        //设置线条宽度
        p.setStrokeWidth(10);
        /**
         * 画线条
         * @param startX 起点x坐标
         * @param startX 起点y坐标
         * @param stopX 终点x坐标
         * @param stopY 终点y坐标
         * @param paint  画笔
         */
        // 画线
        canvas.drawLine(500, 225, 700, 225, p);
        // 斜线
        canvas.drawLine(650, 110, 800, 250, p);

        /**
         * 画笑脸方法：创建一个RectF区域（这个区域仅容纳第一笔）画第一笔，
         * 第一笔绘制完成修改RectF区域画第二笔，依次类推
         */
        //设置空心
        p.setStyle(Paint.Style.STROKE);
        /**
         * 表示一块矩形区域(left <= right and top <= bottom)
         * @param left   左上角x
         * @param top    左上角y
         * @param right  右下角x
         * @param bottom 右下角y
         */
        RectF oval1 = new RectF(800, 50, 900, 100);
        /**
         * oval：圆弧所在的RectF对象。
         * startAngle：圆弧的起始角度。
         * sweepAngle：圆弧的角度。
         * useCenter：是否显示半径连线，true表示显示圆弧与圆心的半径连线，false表示不显示。
         * paint：绘制时所使用的画笔。
         */
        //小弧形
        canvas.drawArc(oval1, 180, 180, false, p);
        /**
         * 设置RectF布局，参数与创建RectF相同
         */
        oval1.set(950, 50, 1050, 100);
        //小弧形
        canvas.drawArc(oval1, 180, 180, false, p);
        oval1.set(850, 100, 1000, 200);
        //小弧形
        canvas.drawArc(oval1, 0, 180, false, p);

        //设置填满（默认）
        p.setStyle(Paint.Style.FILL);
        // 设置灰色
        p.setColor(Color.GRAY);
        canvas.drawText("画矩形：", 50, 400, p);
        /**
         * 绘制矩形（两点确定位置，左上角与右下角）
         * @param left   左上角x
         * @param top    左上角y
         * @param right  右下角x
         * @param bottom 右下角y
         * @param paint：绘制时所使用的画笔。
         */
        // 正方形
        canvas.drawRect(300, 350, 500, 400, p);
        // 长方形
        canvas.drawRect(600, 300, 700, 400, p);

        /**
         * Shader类专门用来渲染图像以及一些几何图形
         * LinearGradient是Shader的子类，用来进行梯度渲染
         * Shader类的使用，需要先构建Shader对象，然后通过Paint的setShader方法设置渲染对象，
         * 然后设置渲染对象，然后再绘制时使用这个Paint对象即可。
         *
         * @param x0 起始的x坐标
         * @param y0 起始的y坐标
         * @param x1 结束的x坐标
         * @param y1 结束的y坐标
         * @param  colors 颜色数组
         * @param  positions 这个也是一个数组用来指定颜色数组的相对位置 如果为null 就沿坡度线均匀分布
         * @param  tile 渲染模式
         */
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY}, null, Shader.TileMode.REPEAT);
        p.setShader(mShader);
        canvas.drawText("画扇形和椭圆:", 50, 550, p);
        // 设置个新的长方形，扫描测量
        RectF oval2 = new RectF(450, 450, 650, 650);
        // 画扇形
        canvas.drawArc(oval2, 200, 130, true, p);
        //画椭圆
        oval2.set(650, 450, 900, 650);
        canvas.drawOval(oval2, p);
        // 长方形
        canvas.drawRect(0, 650, 1080, 700, p);
        // 取消Shader
        p.setShader(null);
        // or重置画笔
        // p.reset();
        p.setColor(Color.BLUE);

        canvas.drawText("画三角形：", 50, 800, p);
        // 绘制这个三角形,你可以绘制任意多边形
        // path：多边形封装类
        Path path = new Path();
        // 此点为多边形的起点
        path.moveTo(500, 750);
        path.lineTo(400, 850);
        path.lineTo(600, 850);
        // 使这些点构成封闭的多边形
        path.close();
        canvas.drawPath(path, p);

        //画圆角矩形
        canvas.drawText("画圆角矩形:", 50, 950, p);
        // 设置个新的长方形
        RectF oval3 = new RectF(500, 880, 700, 1000);
        //第二个参数是x半径，第三个参数是y半径
        canvas.drawRoundRect(oval3, 15, 15, p);

        p.setColor(Color.GREEN);
        //画贝塞尔曲线
        canvas.drawText("画贝塞尔曲线:", 50, 1100, p);
        p.setStyle(Paint.Style.STROKE);
        Path path2 = new Path();
        //设置Path的起点
        path2.moveTo(500, 1050);
        //设置贝塞尔曲线的控制点坐标和终点坐标
        path2.quadTo(600, 950, 700, 1050);
        path2.quadTo(800, 1150, 900, 1050);
        canvas.drawPath(path2, p);

        //画点
        p.setStyle(Paint.Style.FILL);
        canvas.drawText("画点：", 50, 1250, p);
        /**
         * x,y坐标
         */
        //画一个点
        canvas.drawPoint(500, 1200, p);
        //画多个点
        canvas.drawPoints(new float[]{600, 1200, 650, 1250, 700, 1200}, p);

        //画图片，就是贴图
        canvas.drawText("画图片：", 50, 1400, p);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        /**
         * @param bitmap The bitmap to be drawn
         * @param left   左上角x坐标
         * @param top    左上角y坐标
         * @param paint  画笔
         */
        canvas.drawBitmap(bitmap, 360, 1300, p);
    }
}
