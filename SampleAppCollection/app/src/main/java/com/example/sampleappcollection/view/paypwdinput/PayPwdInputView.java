package com.example.sampleappcollection.view.paypwdinput;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.blankj.utilcode.util.LogUtils;
import com.example.sampleappcollection.R;

/**
 * 描述：验证码
 *
 * @author zhangqin
 */
public class PayPwdInputView extends AppCompatEditText {

    private Context mContext;

    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startX;
    private float startY;

    private float cX;

    /**
     * 实心圆的半径
     */
    private int pwdRadius = 10;

    /**
     * view的高度
     */
    private int height;
    private int width;

    /**
     * 当前输入密码位数
     */
    private int textLength = 0;
    private int bottomLineLength;

    /**
     * 最大输入位数
     */
    private int maxCount = 6;

    /**
     * 圆的颜色   默认BLACK
     */
    private int circleColor = Color.BLACK;

    /**
     * 底部线的颜色   默认GRAY
     */
    private int bottomLineColor = Color.GRAY;

    /**
     * 分割线的颜色
     */
    private int borderColor = Color.GRAY;

    /**
     * 分割线的画笔
     */
    private Paint borderPaint;

    /**
     * 分割线开始的坐标x
     */
    private int divideLineWStartX;

    /**
     * 分割线的宽度  默认2
     */
    private int divideLineWidth = 2;

    /**
     * 竖直分割线的颜色
     */
    private int divideLineColor = Color.GRAY;

    private RectF rectF = new RectF();
    /**
     * 输入框类型(weChat or bottom）
     */
    private int psdType = 0;
    private final static int psdType_weChat = 0;
    private final static int psdType_bottomLine = 1;

    /**
     * 矩形边框的圆角
     */
    private int rectAngle = 0;

    /**
     * 竖直分割线的画笔
     */
    private Paint divideLinePaint;

    /**
     * 圆的画笔
     */
    private Paint circlePaint;

    /**
     * 底部线的画笔
     */
    private Paint bottomLinePaint;

    /**
     * 需要对比的密码（调用者传过来的）
     */
    private String mComparePassword = null;

    private OnPasswordListener mListener;

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public PayPwdInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // 获取attr
        getAtt(attrs);
        // 初始化画笔
        initPaint();

        // 设置EditText背景为透明
        this.setBackgroundColor(Color.TRANSPARENT);
        // 设置不显示光标
        this.setCursorVisible(false);
        // 设置EditText的最大长度
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});

    }

    /**
     * 读取自定义属性值
     *
     * @param attrs
     */
    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PayPwdInputView);
        maxCount = typedArray.getInt(R.styleable.PayPwdInputView_maxCount, maxCount);
        circleColor = typedArray.getColor(R.styleable.PayPwdInputView_circleColor, circleColor);
        bottomLineColor = typedArray.getColor(R.styleable.PayPwdInputView_bottomLineColor, bottomLineColor);
        pwdRadius = typedArray.getDimensionPixelOffset(R.styleable.PayPwdInputView_pwdRadius, pwdRadius);

        divideLineWidth = typedArray.getDimensionPixelSize(R.styleable.PayPwdInputView_divideLineWidth, divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.PayPwdInputView_divideLineColor, divideLineColor);
        psdType = typedArray.getInt(R.styleable.PayPwdInputView_psdType, psdType);
        rectAngle = typedArray.getDimensionPixelOffset(R.styleable.PayPwdInputView_rectAngle, rectAngle);

        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        circlePaint = getPaint(5, Paint.Style.FILL, circleColor);

        bottomLinePaint = getPaint(2, Paint.Style.FILL, bottomLineColor);

        borderPaint = getPaint(3, Paint.Style.STROKE, borderColor);

        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, borderColor);

    }

    /**
     * 设置画笔
     *
     * @param strokeWidth 画笔宽度
     * @param style       画笔风格
     * @param color       画笔颜色
     * @return
     */
    private Paint getPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startX = w / maxCount / 2;
        startY = h / 2;

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.i("onDraw: ");
        //不删除的画会默认绘制输入的文字
//        super.onDraw(canvas);

        switch (psdType) {
            case psdType_weChat:
                drawWeChatBorder(canvas);
                break;
            case psdType_bottomLine:
                drawBottomBorder(canvas);
                break;
            default:
                break;
        }

        drawPsdCircle(canvas);
    }


    /**
     * 画微信支付密码的样式
     *
     * @param canvas
     */
    private void drawWeChatBorder(Canvas canvas) {

        // 画一个长方形框
        canvas.drawRoundRect(rectF, rectAngle, rectAngle, borderPaint);

        // 竖线，两侧已经有了
        for (int i = 0; i < maxCount - 1; i++) {
            // 画竖线
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    divideLinePaint);
        }

    }


    /**
     * 画底部显示的分割线
     *
     * @param canvas
     */
    private void drawBottomBorder(Canvas canvas) {

        // startX是底部横线的长度 cx是x轴横线的中间位置
        // 画底部横线
        for (int i = 0; i < maxCount; i++) {
            cX = startX + i * 2 * startX;
            LogUtils.i("cX" + cX + "startX" + startX);
            canvas.drawLine(cX - bottomLineLength / 2,
                    height,
                    cX + bottomLineLength / 2,
                    height, bottomLinePaint);
        }
    }

    /**
     * 画密码实心圆，在onTextChanged中调用重绘方法
     *
     * @param canvas 画布
     */
    private void drawPsdCircle(Canvas canvas) {
        // 画圆
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + i * 2 * startX,
                    startY,
                    pwdRadius,
                    circlePaint);
        }
    }

    /**
     * 监听文本变化
     *
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        LogUtils.i("onTextChanged: ");
        textLength = text.toString().length();

        // 如果密码输入完成，调用回调方法
        if (mComparePassword != null && textLength == maxCount) {
            if (TextUtils.equals(mComparePassword, getPasswordString())) {
                mListener.onSuccess(getPasswordString());
            } else {
                mListener.onError();
            }
        }
        // 重绘
        invalidate();
    }

    /**
     * 保证光标始终在最后
     *
     * @param selStart
     * @param selEnd
     */
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        LogUtils.i("onSelectionChanged: ");
        super.onSelectionChanged(selStart, selEnd);

        //保证光标始终在最后
        if (selStart == selEnd) {
            setSelection(getText().length());
        }
    }

    /**
     * 获取输入的密码
     *
     * @return String 用户输入的密码
     */
    public String getPasswordString() {
        return getText().toString().trim();
    }

    /**
     * 密码比较监听
     */
    public interface OnPasswordListener {

        /**
         * 匹配成功
         *
         * @param psd 输入的文字
         */
        void onSuccess(String psd);

        /**
         * 匹配失败
         */
        void onError();
    }

    /**
     * 密码比较的外部接口
     *
     * @param comparePassword 原始密码
     * @param listener        比较监听器
     */
    public void setComparePassword(String comparePassword, OnPasswordListener listener) {
        mComparePassword = comparePassword;
        mListener = listener;
    }
}
