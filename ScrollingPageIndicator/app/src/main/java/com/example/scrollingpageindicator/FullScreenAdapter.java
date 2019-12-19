package com.example.scrollingpageindicator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author Nikita Olifer
 */
class FullScreenAdapter extends PagerAdapter {
    private int pageCount;

    FullScreenAdapter(int pageCount) {
        this.pageCount = pageCount;
    }

    void setCount(int count) {
        this.pageCount = count;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(collection.getContext())
                .inflate(R.layout.full_screen_page, collection, false);
        ImageView label = layout.findViewById(R.id.demo_page_label);
       // label.setText(String.valueOf(position));
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position);
    }
}
