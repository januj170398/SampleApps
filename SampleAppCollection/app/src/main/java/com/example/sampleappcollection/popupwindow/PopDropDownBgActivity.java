package com.example.sampleappcollection.popupwindow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

/**
 * 描述：PopDropDownBg 在view下面弹出
 *
 * @author zhangqin
 * @date 2016/8/18
 */
public class PopDropDownBgActivity extends AppCompatActivity implements View.OnClickListener {

    private PopupWindow mPopWindow;
    private TextView mMenuTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drop_down_bg_activity);
        mMenuTv = findViewById(R.id.menu);
        mMenuTv.setOnClickListener(v -> {
            if (mPopWindow != null) {
                if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                } else {
                    showPopupWindow();
                }
            } else {
                showPopupWindow();
            }

        });
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(PopDropDownBgActivity.this)
                .inflate(R.layout.drop_down_bg_activity_popup, null);

        /**
         * 默认情况下7.0后如果高度占满全屏showAsDropDown就没啥用了，也就是说可能会跟理想状态下有所区别
         */
        mPopWindow = new BasePopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        TextView tv1 = contentView.findViewById(R.id.pop_computer);
        TextView tv2 = contentView.findViewById(R.id.pop_financial);
        TextView tv3 = contentView.findViewById(R.id.pop_manage);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        // 在mMenuTv下面弹出
        mPopWindow.showAsDropDown(mMenuTv);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pop_computer:
                Toast.makeText(this, "计算机", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            case R.id.pop_financial:
                Toast.makeText(this, "金融", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            case R.id.pop_manage:
                Toast.makeText(this, "管理", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            default:
                break;
        }
    }
}
