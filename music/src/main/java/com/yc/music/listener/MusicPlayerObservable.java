package com.yc.music.listener;

import java.util.Observable;

/**
 * Created by caokun on 2019/11/7 14:18.
 */

public class MusicPlayerObservable extends Observable {

    private static MusicPlayerObservable mInstance = null;

    public static MusicPlayerObservable getInstance() {
        if (null == mInstance) {
            synchronized (MusicPlayerObservable.class) {
                if (null == mInstance) {
                    mInstance = new MusicPlayerObservable();
                }
            }
        }
        return mInstance;
    }

    private MusicPlayerObservable() {

    }

    public void updataSubjectObserivce(Object data) {
        setChanged();
        notifyObservers(data);
    }
}