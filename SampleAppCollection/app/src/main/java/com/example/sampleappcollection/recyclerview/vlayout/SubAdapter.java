package com.example.sampleappcollection.recyclerview.vlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangqin
 * @date 2017/6/28
 */
public class SubAdapter extends DelegateAdapter.Adapter {

    private int TYPE_Banner = 1;
    private int TYPE_Main = 2;
    private int TYPE_Sticky = 3;

    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private VirtualLayoutManager.LayoutParams mLayoutParams;
    private int mCount = 0;

    public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mLayoutParams = layoutParams;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_Banner) {
            return new BannerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vlayout_banner, parent, false));
        } else if (viewType == TYPE_Sticky) {
            return new StickyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vlayout_sticky, parent, false));
        } else {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vlayout, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_Main;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setLayoutParams(
                new VirtualLayoutManager.LayoutParams(mLayoutParams));
    }

    /**
     * 跟onBindViewHolder方法一样，多了个参数offsetTotal，参数为item对应实际位置
     */
    @Override
    protected void onBindViewHolderWithOffset(RecyclerView.ViewHolder holder, int position, int offsetTotal) {
        // super.onBindViewHolderWithOffset(holder, position, offsetTotal);
        if (holder instanceof SubAdapter.MainViewHolder) {
            ((SubAdapter.MainViewHolder) holder).bindData("position:" + position + "    offsetTotal:" + offsetTotal);
        } else if (holder instanceof SubAdapter.BannerViewHolder) {
            ((BannerViewHolder) holder).bindData();
        } else if (holder instanceof SubAdapter.StickyViewHolder) {
            ((StickyViewHolder) holder).bindData("position:" + position + "    offsetTotal:" + offsetTotal + "    修改背景");
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public MainViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.vl_title);
        }

        public void bindData(String s) {
            title.setText(s);
        }
    }

    public class StickyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public StickyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.vl_title);
        }

        public void bindData(String s) {
            title.setText(s);
            title.setBackgroundColor(0x883F51B5);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        private Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }

        public void bindData() {

            // banner图片
            List<String> imgs = new LinkedList<>();
            imgs.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498471769363&di=cd41ec2c450f3da07a11cf378fd47dfb&imgtype=0&src=http%3A%2F%2Fwww.1tong.com%2Fuploads%2Fwallpaper%2Flandscapes%2F273-1-1920x1200.jpg");
            imgs.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498471769361&di=3e0b350eecc9655fb72a7c1da7970245&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Ff7246b600c338744af80e6575b0fd9f9d72aa050.jpg");
            imgs.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1499066949&di=5ac6694646dd38015675ccaa28cea326&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.bz55.com%2Fuploads%2Fallimg%2F150317%2F140-15031G04118-50.jpg");
            imgs.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498472230107&di=339698d9da2f4e18e2bd23ebeb0a5cd7&imgtype=0&src=http%3A%2F%2Fimage227-c.poco.cn%2Fmypoco%2Fmyphoto%2F20140730%2F09%2F17362892420140730095950014_440.jpg%3F1024x763_120");
            imgs.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498472230093&di=285b7c5376e001dc3cab281aefbf5f7f&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fitbbs%2F1409%2F08%2Fc3%2F38363133_1410144825921.jpg");

            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(imgs);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }

    /**
     * banner加载图片
     */
    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }
}
