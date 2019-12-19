package com.example.sampleappcollection.recyclerview.vlayout;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.blankj.utilcode.util.ConvertUtils;
import com.example.sampleappcollection.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：VLayout多布局
 *
 * @author zhangqin
 */
public class VLayoutFragment extends Fragment {

    @BindView(R.id.main_view)
    RecyclerView mainView;
    Unbinder unbinder;
    private Context mContext;
    private View mView;
    private VirtualLayoutManager layoutManager;
    private List<DelegateAdapter.Adapter> adapters;

    public static VLayoutFragment newInstance() {
        VLayoutFragment fragment = new VLayoutFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_vlayout, container, false);
        unbinder = ButterKnife.bind(this, mView);
        initView();
        mContext = getActivity();
        return mView;
    }

    private void initView() {
        layoutManager = new VirtualLayoutManager(mContext);
        mainView.setLayoutManager(layoutManager);
        // 实现类似padding的效果
        mainView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 15);
            }
        });
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        mainView.setAdapter(delegateAdapter);
        adapters = new LinkedList<>();

        initBanner();
        initSticky();
        initGrid();
        initLinear();
        delegateAdapter.setAdapters(adapters);
    }

    private void initBanner() {
        adapters.add(new SubAdapter(mContext, new LinearLayoutHelper(), 1,
                new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(200))) {
            @Override
            public int getItemViewType(int position) {
                return 1;
            }
        });
    }

    private void initSticky() {
        StickyLayoutHelper layoutHelper = new StickyLayoutHelper();
        // 设置偏移量(距离顶部或底部)
        // layoutHelper.setOffset(100);
        // 宽与高的比例
        // layoutHelper.setAspectRatio(4);
        // layoutHelper.setBgColor(0xFFF5A623);
        adapters.add(new SubAdapter(mContext, layoutHelper, 1) {
            @Override
            public int getItemViewType(int position) {
                return 3;
            }
        });
    }

    private void initGrid() {
        GridLayoutHelper layoutHelper = new GridLayoutHelper(2);
        layoutHelper.setMargin(7, 0, 7, 0);
        layoutHelper.setHGap(3);
        adapters.add(new SubAdapter(mContext, layoutHelper, 2));

        layoutHelper = new GridLayoutHelper(4);
        layoutHelper.setMargin(7, 0, 7, 0);
        layoutHelper.setHGap(3);
        adapters.add(new SubAdapter(mContext, layoutHelper, 4));
    }

    private void initLinear() {
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        adapters.add(new SubAdapter(mContext, layoutHelper, 10));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}