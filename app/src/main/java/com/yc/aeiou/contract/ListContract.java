package com.yc.aeiou.contract;


import com.yc.aeiou.bean.ListNetBean;

/**
 * Created by caokun on 2019/10/29 15:07.
 */

public interface ListContract {
    interface ListContractView extends BaseContract.ContractView {
        void loadDataListSuccess(ListNetBean data);
    }

    interface ListContractPresenter<V> extends BaseContract.ContractPresenter<V> {
        Object loadDataList();
    }

}
