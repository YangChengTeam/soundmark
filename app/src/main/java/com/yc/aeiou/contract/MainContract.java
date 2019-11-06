package com.yc.aeiou.contract;


import com.yc.aeiou.bean.ClassNetBean;
import com.yc.aeiou.bean.ListNetBean;

/**
 * Created by caokun on 2019/10/29 15:07.
 */

public interface MainContract {
    interface MainContractView extends BaseContract.ContractView {
        void loadDataListSuccess(ListNetBean data);
        void loadDataClassSuccess(ClassNetBean data);
    }

    interface MainContractPresenter<V> extends BaseContract.ContractPresenter<V> {
        void loadDataList();

        void loadDataClass();
    }

}
