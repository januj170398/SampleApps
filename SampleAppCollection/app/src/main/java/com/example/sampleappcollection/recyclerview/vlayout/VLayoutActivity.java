package com.example.sampleappcollection.recyclerview.vlayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;


/**
 * 描述：天猫VLayout
 *
 * @author zhangqin
 */
public class VLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fl, VLayoutFragment.newInstance())
                .commit();
    }
}
