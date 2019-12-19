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
 * 描述：PopupAnim
 *
 * @author 张钦
 * @date 2016/8/18
 */
public class PopupAnimActivity extends AppCompatActivity implements View.OnClickListener {

    private PopupWindow mPopWindow;
    private TextView mMenuTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity);
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
        View contentView = LayoutInflater.from(PopupAnimActivity.this).inflate(R.layout.anim_activity_popup, null);

        mPopWindow = new BasePopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        TextView tv1 = contentView.findViewById(R.id.pop_computer);
        TextView tv2 = contentView.findViewById(R.id.pop_financial);
        TextView tv3 = contentView.findViewById(R.id.pop_manage);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        mPopWindow.setAnimationStyle(R.style.contextMenuAnim);

        mPopWindow.showAsDropDown(mMenuTv);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pop_computer:
                Toast.makeText(this, "clicked computer", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            case R.id.pop_financial:
                Toast.makeText(this, "clicked financial", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            case R.id.pop_manage:
                Toast.makeText(this, "clicked manage", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
        }
    }
}
