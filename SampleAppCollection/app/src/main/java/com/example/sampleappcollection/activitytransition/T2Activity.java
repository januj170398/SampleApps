package com.example.sampleappcollection.activitytransition;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

/**
 * @author zhangqin
 */
public class T2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t2);
    }

    public void exit(View view){
        finish();
    }
}
