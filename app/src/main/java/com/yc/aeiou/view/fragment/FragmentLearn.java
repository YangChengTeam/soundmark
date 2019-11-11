package com.yc.aeiou.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.yc.aeiou.R;
import com.yc.aeiou.adaper.PagerAdapterLearn;
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

public class FragmentLearn extends BaseMainFragment implements Observer {


    @BindViews({R.id.iv_learn_last, R.id.iv_learn_next})
    public List<ImageView> pageOverImageViews;

    @BindView(R.id.tv_learn_page_index)
    public TextView tvPageIndex;

    @BindView(R.id.viewpager_learn)
    public ViewPager viewPager;
    private int mPageSize;
    private PageChangObservable mPageChangObservable;

    @OnPageChange(R.id.viewpager_learn)
    public void onPageSelected(int position) {
        changPageIndex(position);

        mPageChangObservable.updataSubjectObserivce(position);
    }


    @OnClick({R.id.iv_learn_last, R.id.iv_learn_next})
    void onViewClick(View view) {
        int currentItem = viewPager.getCurrentItem();

        switch (view.getId()) {
            case R.id.iv_learn_last:
                if (currentItem == 1) {
                    pageOverImageViews.get(0).setVisibility(View.INVISIBLE);
                }
                if (currentItem == mPageSize - 1) {
                    pageOverImageViews.get(1).setVisibility(View.VISIBLE);
                }
                viewPager.setCurrentItem(currentItem - 1);
                break;
            case R.id.iv_learn_next:
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
        return R.layout.fragment_learn;
    }

    @Override
    protected void initViews() {
        mPageChangObservable = PageChangObservable.getInstance();
        mPageChangObservable.addObserver(this);
    }

    @Override
    protected void initData() {
        ArrayList<ListNetListBean> list = mMainActivity.mList;
        PagerAdapterLearn pagerAdapterLearn = new PagerAdapterLearn(mMainActivity.getSupportFragmentManager(), 0, list);
        viewPager.setAdapter(pagerAdapterLearn);

        mPageSize = list.size();
        changPageIndex(0);
//        Bundle arguments = getArguments();
//        if (arguments != null) {
//            ArrayList<ListNetListBean> list = arguments.getParcelableArrayList("listNetListBeans");
//            PagerAdapterLearn pagerAdapterLearn = new PagerAdapterLearn(mMainActivity.getSupportFragmentManager(), 0, list);
//            viewPager.setAdapter(pagerAdapterLearn);
//
//            mPageSize = list.size();
//            changPageIndex(0);
//
//        }
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
