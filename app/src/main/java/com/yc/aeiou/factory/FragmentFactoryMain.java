package com.yc.aeiou.factory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.view.fragment.FragmentHome;
import com.yc.aeiou.view.fragment.FragmentLearn;
import com.yc.aeiou.view.fragment.FragmentPhonics;
import com.yc.aeiou.view.fragment.FragmentRead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caokun on 2019/11/5 11:49.
 */

public class FragmentFactoryMain {
    public static final int FRAGMENT_SIZE = 4;


    public static final int MAIN_FRAGMENT_0 = 0;
    public static final int MAIN_FRAGMENT_1 = 1;
    public static final int MAIN_FRAGMENT_2 = 2;
    public static final int MAIN_FRAGMENT_3 = 3;
//    public static final int MAIN_FRAGMENT_4 = 4;


    public static Map<Integer, Fragment> fragments = new HashMap<>();

    public static Fragment createFragment(int position, ArrayList<ListNetListBean> listNetListBeans) {
        Fragment fragment = fragments.get(position);
        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case MAIN_FRAGMENT_0:
                fragment = new FragmentHome();
                fragments.put(MAIN_FRAGMENT_0, fragment);
                break;
            case MAIN_FRAGMENT_1:
                fragment = new FragmentLearn();
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("listNetListBeans", listNetListBeans);
                fragment.setArguments(bundle1);
                fragments.put(MAIN_FRAGMENT_1, fragment);
                break;
            case MAIN_FRAGMENT_2:
                fragment = new FragmentRead();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList("listNetListBeans", listNetListBeans);
                fragment.setArguments(bundle2);
                fragments.put(MAIN_FRAGMENT_2, fragment);
                break;
            case MAIN_FRAGMENT_3:
                fragment = new FragmentPhonics();
                fragments.put(MAIN_FRAGMENT_3, fragment);
                break;
        }
        return fragment;
    }
}
