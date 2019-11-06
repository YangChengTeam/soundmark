package com.yc.aeiou.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.yc.aeiou.manager.OnResultCallBack;
import com.yc.aeiou.bean.InitBean;
import com.yc.aeiou.constant.Config;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by caokun on 2019/11/4 16:13.
 */

public class InitEngin extends HttpCoreEngin {

    private Context context;

    public InitEngin(Context context) {
        super(context);
        this.context = context;
    }

    public void netInitData(final OnResultCallBack<ResultInfo<InitBean>> callBack) {
        Observable<ResultInfo<InitBean>> resultInfoObservable = getObservable(Config.INIT_URL);
        resultInfoObservable.subscribe(new Subscriber<ResultInfo<InitBean>>() {
            @Override
            public void onCompleted() {
                callBack.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callBack.onError(-200, e.toString());
            }

            @Override
            public void onNext(ResultInfo<InitBean> initBeanResultInfo) {
                callBack.onResponse(initBeanResultInfo);
            }
        });
    }

    private Observable<ResultInfo<InitBean>> getObservable(String url) {
        Map<String, String> params = new HashMap<>();
//        params.put("app_id", appId);
        Observable rxpost = rxpost(url, new TypeReference<ResultInfo<InitBean>>() {
                }.getType(),
                params,
                true,
                true, true);
        return rxpost;
    }

}
