package com.yc.aeiou.adaper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.factory.FragmentFactoryLearn;
import com.yc.aeiou.factory.FragmentFactoryMain;

import java.util.List;

/**
 * Created by caokun on 2019/11/5 11:39.
 */

public class PagerAdapterLearn extends FragmentPagerAdapter {

    private final List<ListNetListBean> list;
    private FragmentManager fm;


    public PagerAdapterLearn(@NonNull FragmentManager fm, int behavior, List<ListNetListBean> list) {
        super(fm, behavior);
        this.fm = fm;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentFactoryLearn.createFragment(position,list.get(position));
    }


}
