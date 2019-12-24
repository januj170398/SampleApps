package com.example.sampleappcollection.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;
import com.example.sampleappcollection.view.bottomzoom.BottomZoomActivity;
import com.example.sampleappcollection.view.courtesycard.CourtesyCardActivity;
import com.example.sampleappcollection.view.delbtn.DeleteButtonActivity;
import com.example.sampleappcollection.view.descircle.DesCircleActivity;
import com.example.sampleappcollection.view.gesturedetector.GestureDetectorActivity;
import com.example.sampleappcollection.view.motionslop.MeTsActivity;
import com.example.sampleappcollection.view.paypwdinput.PayPwdInputActivity;
import com.example.sampleappcollection.view.rippleanimation.RippleAnimationActivity;
import com.example.sampleappcollection.view.scroller.ScrollerActivity;
import com.example.sampleappcollection.view.surface.SurfaceActivity;
import com.example.sampleappcollection.view.surfacepalette.SurfacePaletteActivity;
import com.example.sampleappcollection.view.viewevent.ViewEventActivity;
import com.example.sampleappcollection.view.viewposition.ViewPositionActivity;

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
