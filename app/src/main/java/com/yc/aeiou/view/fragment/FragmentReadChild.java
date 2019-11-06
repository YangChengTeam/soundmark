package com.yc.aeiou.view.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kk.utils.LogUtil;
import com.yc.aeiou.R;
import com.yc.aeiou.bean.ListNetListBean;

import butterknife.BindView;


/**
 * Created by caokun on 2019/11/5 11:51.
 */

public class FragmentReadChild extends BaseMainFragment {


    @BindView(R.id.iv_read_child_soundmark)
    public ImageView ivSoundmark;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_read_child;
    }

    @Override
    protected void initViews() {

    }


    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            ListNetListBean listNetListBean = arguments.getParcelable("listNetListBean");
            Glide.with(mMainActivity).load(listNetListBean.img).into(ivSoundmark);
        }

    }


}
