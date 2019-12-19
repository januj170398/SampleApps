package com.example.sampleappcollection.recyclerview.doublelistlinkage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleappcollection.R;

import java.util.List;

/**
 * 主要是做吸顶效果的，其他用不到
 */
public class ItemHeaderDecoration extends RecyclerView.ItemDecoration {

    private List<SortBean> mDatas;
    private LayoutInflater mInflater;
    private int mTitleHeight;
    private CheckListener mCheckListener;

    private static final String TAG = "ItemHeaderDecoration";

    public static String currentTag = "0";

    ItemHeaderDecoration(Context context, List<SortBean> datas) {
        super();
        this.mDatas = datas;
        // 高度30dp
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        // LayoutInflater是用来加载布局的
        mInflater = LayoutInflater.from(context);
    }

    void setCheckListener(CheckListener checkListener) {
        mCheckListener = checkListener;
    }

    public ItemHeaderDecoration setData(List<SortBean> mDatas) {
        this.mDatas = mDatas;
        return this;
    }

    /**
     * 实现类似padding的效果
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    /**
     * 可以实现类似绘制背景的效果，内容在上面
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     * 可以绘制在内容的上面，覆盖内容
     *
     * @param c
     * @param parent
     * @param state
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDrawOver(Canvas c, final RecyclerView parent, RecyclerView.State state) {
        // 当前屏幕的第一个item
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        String tag = mDatas.get(pos).getTag();
        Log.e(TAG, "onDrawOver: pos：" + pos + "\ttag：" + tag);
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;
        // 当发现下一个title顶上来的时候，将canvas向上平移，产生一种向上挤压的动画效果
        boolean flag = false;
        if ((pos + 1) < mDatas.size()) {
            String suspensionTag = mDatas.get(pos + 1).getTag();
            if (null != tag && !tag.equals(suspensionTag)) {
                if (child.getHeight() + child.getTop() < mTitleHeight) {
                    c.save();
                    flag = true;
                    c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);
                }
            }
        }

        // 绘制一个与列表中标题一样的东西（吸顶效果）
        View topTitleView = mInflater.inflate(R.layout.item_title, parent, false);
        TextView tvTitle = (TextView) topTitleView.findViewById(R.id.tv_title);
        tvTitle.setText("测试数据" + tag);
        int toDrawWidthSpec;//用于测量的widthMeasureSpec
        int toDrawHeightSpec;//用于测量的heightMeasureSpec
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) topTitleView.getLayoutParams();
        if (lp == null) {
            lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//这里是根据复杂布局layout的width height，new一个Lp
            // topTitleView.setLayoutParams(lp);
        }
        topTitleView.setLayoutParams(lp);
        if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            //如果是MATCH_PARENT，则用父控件能分配的最大宽度和EXACTLY构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.EXACTLY);
        } else if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //如果是WRAP_CONTENT，则用父控件能分配的最大宽度和AT_MOST构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
        } else {
            //否则则是具体的宽度数值，则用这个宽度和EXACTLY构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(lp.width, View.MeasureSpec.EXACTLY);
        }
        //高度同理
        if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.EXACTLY);
        } else if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.AT_MOST);
        } else {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(mTitleHeight, View.MeasureSpec.EXACTLY);
        }
        //依次调用 measure,layout,draw方法，将复杂头部显示在屏幕上。
        topTitleView.measure(toDrawWidthSpec, toDrawHeightSpec);
        topTitleView.layout(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getPaddingLeft() + topTitleView.getMeasuredWidth(), parent.getPaddingTop() + topTitleView.getMeasuredHeight());
        topTitleView.draw(c);//Canvas默认在视图顶部，无需平移，直接绘制
        if (flag)
            c.restore();//恢复画布到之前保存的状态
        if (!TextUtils.equals(tag, currentTag)) {
            currentTag = tag;
            Integer integer = Integer.valueOf(currentTag);
            Log.e(TAG, "onDrawOver: " + integer);
            mCheckListener.check(integer);
        }

    }
}
