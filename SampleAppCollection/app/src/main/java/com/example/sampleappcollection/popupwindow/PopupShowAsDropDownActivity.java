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
 * 描述：PopupShowAsDropDown
 *
 * @author zhangqin
 * @date 2016/8/18
 */
public class PopupShowAsDropDownActivity extends AppCompatActivity implements View.OnClickListener {
    private PopupWindow mPopWindow;
    private TextView mMenuTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_as_drop_down_activity);
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
        View contentView = LayoutInflater.from(PopupShowAsDropDownActivity.this).inflate(R.layout.show_as_drop_down_activity_popup, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv1 = contentView.findViewById(R.id.pop_computer);
        TextView tv2 = contentView.findViewById(R.id.pop_financial);
        TextView tv3 = contentView.findViewById(R.id.pop_manage);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        mPopWindow.showAsDropDown(mMenuTv);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pop_computer: {
                Toast.makeText(this, "clicked computer", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
            case R.id.pop_financial: {
                Toast.makeText(this, "clicked financial", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
            case R.id.pop_manage: {
                Toast.makeText(this, "clicked manage", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
            break;
            default:
                break;
        }
    }
}
