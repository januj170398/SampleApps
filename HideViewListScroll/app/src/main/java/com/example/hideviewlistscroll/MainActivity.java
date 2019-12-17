package com.example.hideviewlistscroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    String[] data=new String[50];
    ListView lv;
    View ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv=(ListView) findViewById(R.id.mainListView1);
        ll=findViewById(R.id.mainLinearLayout1);
        for (int i=0;i < data.length;i++) data[i]="Item " + i;
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
        lv.setOnTouchListener(new View.OnTouchListener() {
            float height;
            boolean hastouching;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float height = event.getY();
                if (action == MotionEvent.ACTION_DOWN) {
                    this.height = height;
                }
                if (this.height < height) {
                    if (hastouching) ll.animate().setDuration(600).translationY(0);
                    hastouching = false;
                    setTitle("Show");
                } else if (this.height > height) {
                    if (!hastouching) ll.animate().setDuration(600).translationY(500);
                    hastouching = true;
                    setTitle("Hide");
                }
                return false;
            }
        });
    }
}
