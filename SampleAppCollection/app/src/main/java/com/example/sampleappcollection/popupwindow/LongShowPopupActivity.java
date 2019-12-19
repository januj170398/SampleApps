package com.example.sampleappcollection.popupwindow;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ConvertUtils;
import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 触摸事件GestureDetector的使用可以看：
 * http://www.sdwfqin.com/2017/04/26/View%E4%BD%8D%E7%BD%AE%E5%8F%8A%E8%A7%A6%E6%91%B8%E4%BA%8B%E4%BB%B6/
 */
public class LongShowPopupActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    @BindView(R.id.popup_lin)
    LinearLayout popupLin;
    // GestureDetector 辅助检测用户的单击、滑动、长按、双击等行为
    private GestureDetector mGestureDetector;
    private PopupWindow mPopWindow;
    private static final String TAG = "LongShowPopupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_show_popup);
        ButterKnife.bind(this);
        // 给布局添加触摸监听事件
        popupLin.setOnTouchListener(this);
        // 添加手势事件监测（长按）
        mGestureDetector = new GestureDetector(this, onGestureListener);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 接管目标View的onTouch方法
        return mGestureDetector.onTouchEvent(event);
    }

    GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public void onLongPress(MotionEvent e) {
            Log.e(TAG, "onShowPress: " + "长按");
            // 获取点击位置相对于屏幕的坐标
            int x = (int) e.getRawX();
            int y = (int) e.getRawY();
            x -= ConvertUtils.dp2px(55);
            if (mPopWindow != null) {
                if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                    showPopupWindow(x, y);
                } else {
                    showPopupWindow(x, y);
                }
            } else {
                showPopupWindow(x, y);
            }

        }

        @Override
        public boolean onDown(MotionEvent e) {
            // 这里要设置为true，响应事件，不然点一下屏幕也会显示长按
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };

    private void showPopupWindow(int x, int y) {
        View contentView = LayoutInflater.from(LongShowPopupActivity.this).inflate(R.layout.show_as_drop_down_activity_popup, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv1 = contentView.findViewById(R.id.pop_computer);
        TextView tv2 = contentView.findViewById(R.id.pop_financial);
        TextView tv3 = contentView.findViewById(R.id.pop_manage);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        View rootview = LayoutInflater.from(LongShowPopupActivity.this).inflate(R.layout.activity_long_show_popup, null);
        mPopWindow.showAtLocation(rootview, Gravity.NO_GRAVITY, x, y);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pop_computer: {
                Toast.makeText(this, "电脑", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
            case R.id.pop_financial: {
                Toast.makeText(this, "金融", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
            case R.id.pop_manage: {
                Toast.makeText(this, "管理", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
        }
    }
}
