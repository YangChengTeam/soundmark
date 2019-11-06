package com.yc.aeiou.adaper;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.factory.FragmentFactoryMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caokun on 2019/11/5 11:39.
 */

public class PagerAdapterMain extends FragmentPagerAdapter {

    private ArrayList<ListNetListBean> list;
    private FragmentManager fm;


    public PagerAdapterMain(@NonNull FragmentManager fm, int behavior, ArrayList<ListNetListBean> list) {
        super(fm, behavior);
        this.fm = fm;
        this.list = list;
    }

    @Override
    public int getCount() {
        return FragmentFactoryMain.FRAGMENT_SIZE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentFactoryMain.createFragment(position, list);
    }
}
