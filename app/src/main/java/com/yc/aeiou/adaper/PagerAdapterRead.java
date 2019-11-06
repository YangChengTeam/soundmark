package com.yc.aeiou.adaper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.factory.FragmentFactoryLearn;
import com.yc.aeiou.factory.FragmentFactoryRead;

import java.util.List;

/**
 * Created by caokun on 2019/11/5 11:39.
 */

public class PagerAdapterRead extends FragmentPagerAdapter {

    private final List<ListNetListBean> list;
    private FragmentManager fm;


    public PagerAdapterRead(@NonNull FragmentManager fm, int behavior, List<ListNetListBean> list) {
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
        return FragmentFactoryRead.createFragment(position,list.get(position));
    }


}
