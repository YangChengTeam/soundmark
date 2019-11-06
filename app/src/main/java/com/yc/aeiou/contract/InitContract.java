package com.yc.aeiou.contract;


import com.yc.aeiou.bean.InitBean;

/**
 * Created by caokun on 2019/10/29 15:07.
 */

public interface InitContract {
    interface InitContractView extends BaseContract.ContractView {
        void initDataSuccess(InitBean data);
    }

    interface InitContractPresenter<V> extends BaseContract.ContractPresenter<V> {
        void netInitData();
    }

}
