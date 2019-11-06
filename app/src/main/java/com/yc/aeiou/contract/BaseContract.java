package com.yc.aeiou.contract;

/**
 * Created by caokun on 2019/10/29 14:32.
 */

public interface BaseContract {

    interface ContractView {
        void showLoading();

        void showError(int code, String msg);
    }

    interface ContractPresenter<V> {
        void attachView(V view);

        void detachView();
    }

}
