package com.yc.aeiou.presenter;


import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.yc.aeiou.manager.OnResultCallBack;
import com.yc.aeiou.bean.ClassNetBean;
import com.yc.aeiou.cache.CacheWorker;
import com.yc.aeiou.contract.ClassContract;
import com.yc.aeiou.engin.ClassEngin;

/**
 * Created by caokun on 2019/10/29 14:50.
 */

public class ClassContractPresenter extends BaseContractPresenter<ClassContract.ClassContractView, ClassEngin> implements ClassContract.ClassContractPresenter<ClassContract.ClassContractView> {

    private CacheWorker mCacheWorker;

    public ClassContractPresenter(Context context) {
        super(context);
    }

    @Override
    protected ClassEngin creatEngin() {
        mCacheWorker = new CacheWorker();
        return new ClassEngin(mContext);
    }


    @Override
    public Object loadDataClass() {
        mViewBase.showLoading();
        mEnginBase.loadDataClass(new OnResultCallBack<ResultInfo<ClassNetBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onResponse(ResultInfo<ClassNetBean> data) {
                ClassNetBean classNetBean = data.getData();
                mViewBase.loadDataClassSuccess(classNetBean);
                mCacheWorker.setCache("classNetBean", classNetBean);
            }

            @Override
            public void onError(int code, String errorMsg) {
                mViewBase.showError(code, errorMsg);
            }
        });
        Object cacheObject = mCacheWorker.getCache(mContext, "classNetBean");
        if (cacheObject == null) {
            mViewBase.showLoading();
        }
        return cacheObject;
    }
}
