package com.yc.aeiou.manager;

import java.util.Observable;

/**
 * Created by caokun on 2019/11/6 14:39.
 */

public class PageChangObservable extends Observable {

    private static  PageChangObservable mInstance = null;

    public static PageChangObservable getInstance() {
        if (null == mInstance) {
            synchronized (PageChangObservable.class) {
                if (null == mInstance) {
                    mInstance = new PageChangObservable();
                }
            }
        }
        return mInstance;
    }

    private PageChangObservable() {

    }

    public void updataSubjectObserivce(Object data) {
        setChanged();
        notifyObservers(data);
    }
}