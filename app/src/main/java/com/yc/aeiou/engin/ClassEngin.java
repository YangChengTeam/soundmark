package com.yc.aeiou.engin;


import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.yc.aeiou.manager.OnResultCallBack;
import com.yc.aeiou.bean.ClassNetBean;
import com.yc.aeiou.constant.Config;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by caokun on 2019/10/29 14:59.
 */

public class ClassEngin extends HttpCoreEngin {

    public final int NET_ERROR_CODE_CLASS = -202;

    public ClassEngin(Context context) {
        super(context);
    }


    public void loadDataClass(final OnResultCallBack<ResultInfo<ClassNetBean>> callBack) {
        Observable<ResultInfo<ClassNetBean>> resultInfoObservable = loadDataClassObservable(Config.CLASS_URL);
        resultInfoObservable.subscribe(new Subscriber<ResultInfo<ClassNetBean>>() {
            @Override
            public void onCompleted() {
                callBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callBack.onError(NET_ERROR_CODE_CLASS, e.toString());
            }

            @Override
            public void onNext(ResultInfo<ClassNetBean> resultInfo) {
                callBack.onResponse(resultInfo);
            }
        });
    }


    private Observable loadDataClassObservable(String url) {
        Map<String, String> params = new HashMap<>();
//        params.put("app_id", appId);
        return rxpost(url, new TypeReference<ResultInfo<ClassNetBean>>() {
                }.getType(),
                params,
                true,
                true, true);
    }
}
