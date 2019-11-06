package com.yc.aeiou.manager;

/**
 * Created by caokun on 2019/10/29 15:24.
 */

public abstract class OnResultCallBack<T> {

    public abstract void onCompleted();

    /**
     * 响应成功
     * @param data 实体对象,没有传泛型默认是String.class类型
     */
    public abstract void onResponse(T data);

    /**
     * 响应、解析失败
     * @param code 错误码
     * @param errorMsg 描述信息
     */
    public abstract void onError(int code,String errorMsg);
}
