package com.yc.aeiou.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by caokun on 2019/11/5 13:34.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Context mContext;
    private Unbinder mBind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), null);
            mBind = ButterKnife.bind(this, mRootView);
            mContext = getActivity();
            initParameter();
            initViews();
            initData();


        }
        return mRootView;
    }

    protected void initParameter() {
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected void initData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
