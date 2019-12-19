package com.example.sampleappcollection.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopupMainActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView popupList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        mContext = this;

        initView();
    }

    private void initView() {

        String[] strings = new String[]{"PopDropDownBg", "PopshowAtLocation", "PopupAnim",
                "PopupShowAsDropDown","任意位置长按显示弹窗"};
        popupList.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, R.id.tv_items, strings));

        popupList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(mContext, PopDropDownBgActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(mContext, PopshowAtLocationActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(mContext, PopupAnimActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(mContext, PopupShowAsDropDownActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(mContext, LongShowPopupActivity.class));
                    break;
            }
        });
    }
}
