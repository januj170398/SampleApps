package com.example.scrollingpageindicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.example.scrollingpageindicator.scrollingpagerindicator.ScrollingPagerIndicator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int screenWidth = getScreenWidth();

        // Setup ViewPager with indicator
        ViewPager pager = findViewById(R.id.pager_full_screen);
        FullScreenAdapter pagerAdapter = new FullScreenAdapter(8);
        pager.setAdapter(pagerAdapter);

        ScrollingPagerIndicator pagerIndicator = findViewById(R.id.pager_indicator);
        pagerIndicator.attachToPager(pager);

        // Setup RecyclerView with indicator
        // One page will occupy 1/3 of screen width
        RecyclerView recyclerView = findViewById(R.id.recycler_part_pager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        PartScreenAdapter recyclerAdapter = new PartScreenAdapter(8, screenWidth);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.setPadding(screenWidth / 3, 0, screenWidth / 3, 0);

        ScrollingPagerIndicator recyclerIndicator = findViewById(R.id.recycler_indicator);
        // Consider page in the middle current
        recyclerIndicator.attachToRecyclerView(recyclerView);

        pagerIndicator.setVisibleDotCount(5);
        recyclerIndicator.setVisibleDotCount(5);

        pagerAdapter.setCount(10); // full screen image
        recyclerAdapter.setCount(5);
    }

    private int getScreenWidth() {
        @SuppressWarnings("ConstantConditions")
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        return screenSize.x;
    }
}
