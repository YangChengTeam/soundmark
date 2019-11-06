package com.yc.aeiou.contract;


import com.yc.aeiou.bean.ClassNetBean;
import com.yc.aeiou.bean.ListNetBean;

/**
 * Created by caokun on 2019/10/29 15:07.
 */

public interface ClassContract {
    interface ClassContractView extends BaseContract.ContractView {
        void loadDataClassSuccess(ClassNetBean data);
    }

    interface ClassContractPresenter<V> extends BaseContract.ContractPresenter<V> {
        Object loadDataClass();
    }

}
