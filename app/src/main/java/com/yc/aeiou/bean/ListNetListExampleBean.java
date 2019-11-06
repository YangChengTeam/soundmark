package com.yc.aeiou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by caokun on 2019/11/5 10:29.
 */

public class ListNetListExampleBean implements Parcelable, Serializable {

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

    // 读数据进行恢复
    private ListNetListExampleBean(Parcel in) {

        app_id = in.readString();
        id = in.readString();
        letter = in.readString();
        phonetic = in.readString();
        ptic_id = in.readString();
        sort = in.readString();
        video = in.readString();
        word = in.readString();
        word_phonetic = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // 写数据进行保存
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(app_id);
        out.writeString(id);
        out.writeString(letter);
        out.writeString(phonetic);
        out.writeString(ptic_id);
        out.writeString(sort);
        out.writeString(video);
        out.writeString(word);
        out.writeString(word_phonetic);
    }

    // 用来创建自定义的Parcelable的对象
    public static final Creator<ListNetListExampleBean> CREATOR = new Creator<ListNetListExampleBean>() {

        @Override
        public ListNetListExampleBean createFromParcel(Parcel parcel) {
            return new ListNetListExampleBean(parcel);
        }

        @Override
        public ListNetListExampleBean[] newArray(int i) {
            return new ListNetListExampleBean[i];
        }
    };

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
