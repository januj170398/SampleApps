package com.example.sampleappcollection.view.rippleanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.sdwfqin.sample.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 描述：波纹动画
 *
 * @author zhangqin
 * @date 2017/7/27
 */
public class RippleAnimationView extends RelativeLayout {

    private int mRippleType;
    private int mRippleColor;
    private int mRippleAmount;
    private float mRippleScale;
    private float mRippleRadius;
    private int mRippleDuration;
    public Paint mPaint;
    public float mRippleStrokeWidth;
    private TypedArray mTypedArray;

    private AnimatorSet mAnimatorSet;
    private boolean mAnimationRunning = false;
    private ArrayList<RippleCircleView> mRippleViewList = new ArrayList<>();

    //默认实心圆圈
    private static final int DEFAULT_FILL_TYPE = 0;
    //默认伸缩大小
    private static final float DEFAULT_SCALE = 5.0f;
    //默认圆圈个数
    private static final int DEFAULT_RIPPLE_COUNT = 5;
    //默认扩散时间
    private static final int DEFAULT_DURATION_TIME = 2500;

    public RippleAnimationView(Context context) {
        super(context);
    }

    public RippleAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {

        //判断View当前是否处于 IDE 布局编辑（预览）状态，只有在编辑状态下才会返回true，
        //在编写只有在运行时才能看到绘制效果的自定义View时，可以使用该方法查看布局预览。
        if (isInEditMode()) {
            return;
        }

        //加载自定义属性
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimationView);
        mRippleType = mTypedArray.getInt(R.styleable.RippleAnimationView_ripple_anim_type, DEFAULT_FILL_TYPE);
        mRippleColor = mTypedArray.getColor(R.styleable.RippleAnimationView_ripple_anim_color, ContextCompat.getColor(context, R.color.rippleColor));
        mRippleAmount = mTypedArray.getInt(R.styleable.RippleAnimationView_ripple_anim_amount, DEFAULT_RIPPLE_COUNT);
        mRippleScale = mTypedArray.getFloat(R.styleable.RippleAnimationView_ripple_anim_scale, DEFAULT_SCALE);
        mRippleRadius = mTypedArray.getDimension(R.styleable.RippleAnimationView_ripple_anim_radius, getResources().getDimension(R.dimen.rippleRadius));
        mRippleDuration = mTypedArray.getInt(R.styleable.RippleAnimationView_ripple_anim_duration, DEFAULT_DURATION_TIME);
        mRippleStrokeWidth = mTypedArray.getDimension(R.styleable.RippleAnimationView_ripple_anim_strokeWidth, getResources().getDimension(R.dimen.rippleStrokeWidth));
        //注意回收TypedArray
        mTypedArray.recycle();

        int rippleDelay = mRippleDuration / mRippleAmount;

        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        if (mRippleType == DEFAULT_FILL_TYPE) {
            mRippleStrokeWidth = 0;
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        mPaint.setColor(mRippleColor);

        LayoutParams rippleParams = new LayoutParams((int) (2 * (mRippleRadius + mRippleStrokeWidth)), (int) (2 * (mRippleRadius + mRippleStrokeWidth)));
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);

        //分析该动画后将其拆分为缩放、渐变
        ArrayList<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < mRippleAmount; i++) {

            RippleCircleView rippleView = new RippleCircleView(this, context);
            addView(rippleView, rippleParams);
            mRippleViewList.add(rippleView);
            //ScaleX缩放
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, mRippleScale);
            //无限重复
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * rippleDelay);
            scaleXAnimator.setDuration(mRippleDuration);
            animatorList.add(scaleXAnimator);
            //ScaleY缩放
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, mRippleScale);
            //无限重复
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * rippleDelay);
            scaleYAnimator.setDuration(mRippleDuration);
            animatorList.add(scaleYAnimator);
            //Alpha渐变
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f);
            //无限重复
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * rippleDelay);
            alphaAnimator.setDuration(mRippleDuration);
            animatorList.add(alphaAnimator);
        }

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet.playTogether(animatorList);
    }

    /**
     * 开始动画
     */
    public void startRippleAnimation() {
        if (!isRippleRunning()) {
            for (RippleCircleView rippleView : mRippleViewList) {
                rippleView.setVisibility(VISIBLE);
            }
            mAnimatorSet.start();
            mAnimationRunning = true;
        }
    }

    /**
     * 停止动画
     */
    public void stopRippleAnimation() {
        if (isRippleRunning()) {
            Collections.reverse(mRippleViewList);
            for (RippleCircleView rippleView : mRippleViewList) {
                rippleView.setVisibility(INVISIBLE);
            }
            mAnimatorSet.end();
            mAnimationRunning = false;
        }
    }

    /**
     * 是否正在执行
     *
     * @return boolean isRippleRunning
     */
    public boolean isRippleRunning() {
        return mAnimationRunning;
    }
}
