package com.yc.aeiou.factory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.view.fragment.FragmentLearnChild;
import com.yc.aeiou.view.fragment.FragmentReadChild;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caokun on 2019/11/5 11:49.
 */

public class FragmentFactoryRead {


    public static Map<Integer, Fragment> fragments = new HashMap<>();

    public static Fragment createFragment(int position, ListNetListBean listNetListBean) {
        Fragment fragment = fragments.get(position);
        if (fragment != null) {
            return fragment;
        }
        fragment = new FragmentReadChild();
        fragments.put(position, fragment);
        return fragment;
    }
}
