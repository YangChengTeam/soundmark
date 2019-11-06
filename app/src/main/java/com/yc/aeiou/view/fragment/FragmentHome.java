package com.yc.aeiou.view.fragment;

import android.widget.TextView;

import com.yc.aeiou.R;

import butterknife.BindView;

/**
 * Created by caokun on 2019/11/5 11:51.
 */

public class FragmentHome extends BaseMainFragment {

    @BindView(R.id.tv_home_user)
    TextView tvUser;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        tvUser.setText("用户ID：SE606123");
    }
}
