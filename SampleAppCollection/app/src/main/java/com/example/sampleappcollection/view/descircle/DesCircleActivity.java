package com.example.sampleappcollection.view.descircle;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：自定义View1圆
 *
 * @author zhangqin
 */
public class DesCircleActivity extends AppCompatActivity {

    @BindView(R.id.viewz1)
    DesCircleView mViewz1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des_circle);
        ButterKnife.bind(this);

        mViewz1.setColor(Color.RED);
    }
}
