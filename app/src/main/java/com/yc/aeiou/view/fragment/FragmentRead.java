package com.yc.aeiou.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.yc.aeiou.R;
import com.yc.aeiou.adaper.PagerAdapterRead;
import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.manager.PageChangObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * Created by caokun on 2019/11/5 11:51.
 */

public class FragmentRead extends BaseMainFragment implements Observer {

    @BindView(R.id.viewpager_read)
    ViewPager viewPager;

    @BindViews({R.id.iv_read_last, R.id.iv_read_next})
    public List<ImageView> pageOverImageViews;

    @BindView(R.id.tv_read_page_index)
    public TextView tvPageIndex;

    private int mPageSize;

    @OnPageChange(R.id.viewpager_read)
    public void onPageSelected(int position) {
        changPageIndex(position);

        mPageChangObservable.updataSubjectObserivce(position);
    }

    private PageChangObservable mPageChangObservable;


    @OnClick({R.id.iv_read_last, R.id.iv_read_next})
    void onViewClick(View view) {
        int currentItem = viewPager.getCurrentItem();

        switch (view.getId()) {
            case R.id.iv_read_last:
                if (currentItem == 1) {
                    pageOverImageViews.get(0).setVisibility(View.INVISIBLE);
                }
                if (currentItem == mPageSize - 1) {
                    pageOverImageViews.get(1).setVisibility(View.VISIBLE);
                }
                viewPager.setCurrentItem(currentItem - 1);
                break;
            case R.id.iv_read_next:
                if (currentItem == 0) {
                    pageOverImageViews.get(0).setVisibility(View.VISIBLE);
                }
                if (currentItem == mPageSize - 2) {
                    pageOverImageViews.get(1).setVisibility(View.INVISIBLE);
                }
                viewPager.setCurrentItem(currentItem + 1);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_read;
    }

    @Override
    protected void initViews() {

        mPageChangObservable = PageChangObservable.getInstance();
        mPageChangObservable.addObserver(this);
    }

    @Override
    protected void initData() {
        ArrayList<ListNetListBean> list = mMainActivity.mList;
        PagerAdapterRead pagerAdapterRead = new PagerAdapterRead(mMainActivity.getSupportFragmentManager(), 0, list);
        viewPager.setAdapter(pagerAdapterRead);

        mPageSize = list.size();
        changPageIndex(0);
    }

    private void changPageIndex(int position) {
        tvPageIndex.setText((position + 1) + "/" + mPageSize);

        if (position == 0) {
            pageOverImageViews.get(0).setVisibility(View.INVISIBLE);
        }
        if (position == 1) {
            pageOverImageViews.get(0).setVisibility(View.VISIBLE);
        }
        if (position == mPageSize - 1) {
            pageOverImageViews.get(1).setVisibility(View.INVISIBLE);
        }
        if (position == mPageSize - 2) {
            pageOverImageViews.get(1).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PageChangObservable && arg instanceof Integer) {
            changPageIndex((int) arg);
            viewPager.setCurrentItem((int) arg);
        }
    }
}
