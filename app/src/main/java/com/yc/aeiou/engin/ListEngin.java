package com.yc.aeiou.engin;


import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.yc.aeiou.manager.OnResultCallBack;
import com.yc.aeiou.bean.ListNetBean;
import com.yc.aeiou.constant.Config;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by caokun on 2019/10/29 14:59.
 */

public class ListEngin extends HttpCoreEngin {

    public final int NET_ERROR_CODE_LIST = -201;

    public ListEngin(Context context) {
        super(context);
    }

    public void loadDataList(final OnResultCallBack<ResultInfo<ListNetBean>> callBack) {
        Observable<ResultInfo<ListNetBean>> resultInfoObservable = loadDataListObservable(Config.LIST_URL);
        resultInfoObservable.subscribe(new Subscriber<ResultInfo<ListNetBean>>() {
            @Override
            public void onCompleted() {
                callBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callBack.onError(NET_ERROR_CODE_LIST, e.toString());
            }

            @Override
            public void onNext(ResultInfo<ListNetBean> resultInfo) {
                callBack.onResponse(resultInfo);
            }
        });
    }


    private Observable loadDataListObservable(String url) {
        Map<String, String> params = new HashMap<>();
//        params.put("app_id", appId);
        return rxpost(url, new TypeReference<ResultInfo<ListNetBean>>() {
                }.getType(),
                params,
                true,
                true, true);
    }

}
