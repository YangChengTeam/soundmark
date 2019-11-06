package com.yc.aeiou.presenter;

import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.yc.aeiou.manager.OnResultCallBack;
import com.yc.aeiou.bean.InitBean;
import com.yc.aeiou.contract.InitContract;
import com.yc.aeiou.engin.InitEngin;

/**
 * Created by caokun on 2019/11/4 18:22.
 */

public class InitContractPresenter extends BaseContractPresenter<InitContract.InitContractView, InitEngin> implements InitContract.InitContractPresenter<InitContract.InitContractView> {


    public InitContractPresenter(Context context) {
        super(context);
    }

    @Override
    public void netInitData() {
        mViewBase.showLoading();
        mEnginBase.netInitData(new OnResultCallBack<ResultInfo<InitBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onResponse(ResultInfo<InitBean> data) {
                InitBean dataInitBean = data.getData();
                mViewBase.initDataSuccess(dataInitBean);
            }

            @Override
            public void onError(int code, String errorMsg) {
                mViewBase.showError(code, errorMsg);
            }
        });
    }

    @Override
    protected InitEngin creatEngin() {
        return new InitEngin(mContext);
    }
}
