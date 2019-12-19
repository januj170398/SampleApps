package com.example.sampleappcollection.view.bottomzoom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ConvertUtils;
import com.sdwfqin.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 按钮放大（属性动画）
 * <p>
 * 使用属性动画ObjectAnimator修改属性时要确保原始对象有get和set方法，如果没有可以尝试用一个类来包装原始对象。
 * 也可以使用ValueAnimator，监听动画实现过程
 * <p>
 * ObjectAnimator是ValueAnimator的子集
 * 属性动画在修改属性时通过不断调用属性的get和set方法实现动画效果。
 * <p>
 * AnimatorSet是一个动画集合
 *
 * @author zhangqin
 */
public class BottomZoomActivity extends AppCompatActivity {

    @BindView(R.id.animator1_btn)
    Button animator1Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator1);
        ButterKnife.bind(this);

        animator1Btn.setOnClickListener(v -> setButtonSize(300, 300));
    }

    private void setButtonSize(int x, int y) {

        ViewWrapper viewWrapper = new ViewWrapper(animator1Btn);

        // 动画集合，同时执行下面的动画效果
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofInt(viewWrapper, "width",
                        ConvertUtils.dp2px(x)),
                ObjectAnimator.ofInt(viewWrapper, "height",
                        ConvertUtils.dp2px(y))
        );

        set.setDuration(5000).start();
    }

    /**
     * 用一个类来包装原始对象，间接为其提供get和set方法
     * 对象的大小要在xml定死，不然与预想的效果不一样
     */
    private class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

        public int getHeight() {
            return mTarget.getLayoutParams().height;
        }

        public void setHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }
    }
}
