package com.yc.aeiou.presenter;


import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.yc.aeiou.manager.OnResultCallBack;
import com.yc.aeiou.bean.ClassNetBean;
import com.yc.aeiou.bean.ListNetBean;
import com.yc.aeiou.contract.MainContract;
import com.yc.aeiou.engin.MainEngin;

/**
 * Created by caokun on 2019/10/29 14:50.
 */

public class MainContractPresenter extends BaseContractPresenter<MainContract.MainContractView, MainEngin> implements MainContract.MainContractPresenter<MainContract.MainContractView> {


    public MainContractPresenter(Context context) {
        super(context);
    }

    @Override
    protected MainEngin creatEngin() {
        return new MainEngin(mContext);
    }


    @Override
    public void loadDataList() {
        mViewBase.showLoading();
        mEnginBase.loadDataList(new OnResultCallBack<ResultInfo<ListNetBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onResponse(ResultInfo<ListNetBean> data) {
                ListNetBean listNetBean = data.getData();
                mViewBase.loadDataListSuccess(listNetBean);
            }

            @Override
            public void onError(int code, String errorMsg) {
                mViewBase.showError(code, errorMsg);
            }
        });
    }

    @Override
    public void loadDataClass() {
        mViewBase.showLoading();
        mEnginBase.loadDataClass(new OnResultCallBack<ResultInfo<ClassNetBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onResponse(ResultInfo<ClassNetBean> data) {
                ClassNetBean classNetBean = data.getData();
                mViewBase.loadDataClassSuccess(classNetBean);
            }

            @Override
            public void onError(int code, String errorMsg) {
                mViewBase.showError(code, errorMsg);
            }
        });
    }
}
