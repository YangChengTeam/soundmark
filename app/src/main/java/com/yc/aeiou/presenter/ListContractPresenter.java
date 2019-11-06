package com.yc.aeiou.presenter;


import android.content.Context;

import com.kk.securityhttp.domain.ResultInfo;
import com.yc.aeiou.manager.OnResultCallBack;
import com.yc.aeiou.bean.ListNetBean;
import com.yc.aeiou.cache.CacheWorker;
import com.yc.aeiou.contract.ListContract;
import com.yc.aeiou.engin.ListEngin;

/**
 * Created by caokun on 2019/10/29 14:50.
 */

public class ListContractPresenter extends BaseContractPresenter<ListContract.ListContractView, ListEngin> implements ListContract.ListContractPresenter<ListContract.ListContractView> {
    private CacheWorker mCacheWorker;

    public ListContractPresenter(Context context) {
        super(context);
    }

    @Override
    protected ListEngin creatEngin() {
        mCacheWorker = new CacheWorker();
        return new ListEngin(mContext);
    }


    @Override
    public Object loadDataList() {
        mEnginBase.loadDataList(new OnResultCallBack<ResultInfo<ListNetBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onResponse(ResultInfo<ListNetBean> data) {
                if (data == null) {
                    mViewBase.showError(-500, "连接超时，数据为空");
                    return;
                }
                ListNetBean listNetBean = data.getData();
                mViewBase.loadDataListSuccess(listNetBean);
                mCacheWorker.setCache("listNetBean", listNetBean);
            }

            @Override
            public void onError(int code, String errorMsg) {
                mViewBase.showError(code, errorMsg);
            }
        });
        Object cacheObject = mCacheWorker.getCache(mContext, "listNetBean");
        if (cacheObject == null) {
            mViewBase.showLoading();
        }
        return cacheObject;
    }


}
