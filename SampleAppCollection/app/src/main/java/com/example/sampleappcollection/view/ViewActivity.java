package com.example.sampleappcollection.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sdwfqin.sample.R;
import com.sdwfqin.sample.view.bottomzoom.BottomZoomActivity;
import com.sdwfqin.sample.view.courtesycard.CourtesyCardActivity;
import com.sdwfqin.sample.view.delbtn.DeleteButtonActivity;
import com.sdwfqin.sample.view.descircle.DesCircleActivity;
import com.sdwfqin.sample.view.gesturedetector.GestureDetectorActivity;
import com.sdwfqin.sample.view.motionslop.MeTsActivity;
import com.sdwfqin.sample.view.paypwdinput.PayPwdInputActivity;
import com.sdwfqin.sample.view.rippleanimation.RippleAnimationActivity;
import com.sdwfqin.sample.view.scroller.ScrollerActivity;
import com.sdwfqin.sample.view.surface.SurfaceActivity;
import com.sdwfqin.sample.view.surfacepalette.SurfacePaletteActivity;
import com.sdwfqin.sample.view.viewevent.ViewEventActivity;
import com.sdwfqin.sample.view.viewposition.ViewPositionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：View相关
 *
 * @author zhangqin
 */
public class ViewActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView mViewList;
    private Context mContext;

    private String[] mStrings = new String[]{"View的位置参数", "MotionEvent与TouchSlop", "GestureDetector", "Scroller",
            "View触摸事件分发", "按钮放大（属性动画）", "自定义View1圆", "自定义View2凹凸边缘", "SurfaceView",
            "SurfaceView画板", "自定义输入密码", "网易云听歌识曲", "长按删除按钮"};
    private Class[] mClasses = new Class[]{ViewPositionActivity.class, MeTsActivity.class,
            GestureDetectorActivity.class, ScrollerActivity.class, ViewEventActivity.class,
            BottomZoomActivity.class, DesCircleActivity.class, CourtesyCardActivity.class, SurfaceActivity.class,
            SurfacePaletteActivity.class, PayPwdInputActivity.class,
            RippleAnimationActivity.class, DeleteButtonActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        mViewList.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, mStrings));

        mViewList.setOnItemClickListener((parent, view, position, id) -> startActivity(new Intent(mContext, mClasses[position])));
    }
}
