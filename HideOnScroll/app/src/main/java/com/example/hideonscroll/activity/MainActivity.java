package com.example.hideonscroll.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hideonscroll.R;
import com.example.hideonscroll.activity.partone.PartOneActivity;
import com.example.hideonscroll.activity.partthree.PartThreeActivity;
import com.example.hideonscroll.activity.parttwo.PartTwoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button partOneButton;
    private Button partTwoButton;
    private Button partThreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        partOneButton = (Button) findViewById(R.id.partOneButton);
        partTwoButton = (Button) findViewById(R.id.partTwoButton);
        partThreeButton = (Button) findViewById(R.id.partThreeButton);

        partOneButton.setOnClickListener(this);
        partTwoButton.setOnClickListener(this);
        partThreeButton.setOnClickListener(this);


    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
    }

    @Override
    public void onClick(View v) {
        if(v.equals(partOneButton)) {
            startActivity(PartOneActivity.class);
        } else if(v.equals(partTwoButton)) {
            startActivity(PartTwoActivity.class);
        } else {
            startActivity(PartThreeActivity.class);
        }
    }

    private void startActivity(Class<?> activityClass) {
        Intent myIntent = new Intent(this, activityClass);
        startActivity(myIntent);
    }
}
