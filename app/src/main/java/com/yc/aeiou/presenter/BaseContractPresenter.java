package com.yc.aeiou.presenter;


import android.content.Context;

import com.kk.securityhttp.engin.BaseEngin;
import com.yc.aeiou.contract.BaseContract;

/**
 * Created by caokun on 2019/10/29 14:31.
 */

public abstract class BaseContractPresenter<V extends BaseContract.ContractView, M extends BaseEngin> implements BaseContract.ContractPresenter<V> {
    public V mViewBase;
    public M mEnginBase;
    public Context mContext;

    public BaseContractPresenter(Context context) {
        mContext = context;
        mEnginBase = creatEngin();
    }

    protected abstract M creatEngin();

    @Override
    public void attachView(V view) {

        this.mViewBase = view;
    }

    @Override
    public void detachView() {
        if (mViewBase != null) {
            mViewBase = null;
        }
    }
}
