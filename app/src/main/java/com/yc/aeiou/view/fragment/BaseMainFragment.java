package com.yc.aeiou.view.fragment;

import com.yc.aeiou.view.activity.BaseActivity;
import com.yc.aeiou.view.activity.MainActivity;

/**
 * Created by caokun on 2019/11/5 15:43.
 */

public abstract class BaseMainFragment extends BaseFragment {

    protected MainActivity mMainActivity;

    @Override
    protected void initParameter() {
        mMainActivity = (MainActivity) mContext;
    }
}
