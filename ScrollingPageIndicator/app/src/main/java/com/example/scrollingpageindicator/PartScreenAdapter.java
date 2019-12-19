package com.example.scrollingpageindicator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Nikita Olifer
 */
public class PartScreenAdapter extends RecyclerView.Adapter<PartScreenAdapter.ViewHolder> {

    private int count;
    private final int screenWidth;

    PartScreenAdapter(int count, int screenWidth) {
        this.count = count;
        this.screenWidth = screenWidth;
    }

    void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.part_screen_page, parent, false);
       // view.getLayoutParams().width = screenWidth / 2;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.title.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.demo_page_label);
        }
    }
}
