package com.yc.aeiou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by caokun on 2019/11/5 10:29.
 */

public class ListNetListExampleBean implements Serializable {

    public ListNetListExampleBean() {
    }

    /**
     * app_id : 5
     * id : 6
     * letter : ee
     * phonetic :
     * ptic_id : 1
     * sort : 500
     * video : http://phonetic.upkao.com/video/N4kNaD63tX.mp3
     * word : bee
     * word_phonetic : /bi:/
     */

    public String app_id;
    public String id;
    public String letter;
    public String phonetic;
    public String ptic_id;
    public String sort;
    public String video;
    public String word;
    public String word_phonetic;


    @Override
    public String toString() {
        return "ListNetListExampleBean{" +
                "app_id='" + app_id + '\'' +
                ", id='" + id + '\'' +
                ", letter='" + letter + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", ptic_id='" + ptic_id + '\'' +
                ", sort='" + sort + '\'' +
                ", video='" + video + '\'' +
                ", word='" + word + '\'' +
                ", word_phonetic='" + word_phonetic + '\'' +
                '}';
    }
}
