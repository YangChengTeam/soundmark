package com.yc.aeiou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caokun on 2019/11/5 10:28.
 */

public class ListNetListBean implements  Serializable {

    public ListNetListBean() {
    }

    /**
     * app_id : 5
     * cover : http://tic.upkao.com/Upload/cover/1.jpg
     * desp : 舌尖抵下齿，舌前部尽量向硬颚抬起。嘴唇向两旁伸开成扁平型。注意一定要把音发足。
     * desp_audio : http://phonetic.upkao.com/video/ttS8dPyzKh.mp3
     * example : [{"app_id":"5","id":"6","letter":"ee","phonetic":"","ptic_id":"1","sort":"500","video":"http://phonetic.upkao.com/video/N4kNaD63tX.mp3","word":"bee","word_phonetic":"/bi:/"},{"app_id":"5","id":"7","letter":"ee  ","phonetic":"","ptic_id":"1","sort":"500","video":"http://phonetic.upkao.com/video/thiCefayQb.mp3","word":"see  ","word_phonetic":"/si:/"},{"app_id":"5","id":"8","letter":"ee","phonetic":"","ptic_id":"1","sort":"500","video":"http://phonetic.upkao.com/video/a88y5Kh7He.mp3","word":"tree","word_phonetic":"/tri:/"},{"app_id":"5","id":"9","letter":"ea","phonetic":"","ptic_id":"1","sort":"500","video":"http://phonetic.upkao.com/video/4WmZ8fE8jR.mp3","word":"tea","word_phonetic":"/ti:/"}]
     * hanzi :
     * id : 1
     * img : http://tic.upkao.com/Upload/Picture/1.png
     * name : /i:/
     * sort : 0
     * strokes_img :
     * type :
     * video : http://tic.upkao.com/Upload/video/1.mp4
     * voice : http://tic.upkao.com/Upload/mp3/1.mp3
     */

    public String app_id;
    public String cover;
    public String desp;
    public String desp_audio;
    public String hanzi;
    public String id;
    public String img;
    public String name;
    public String sort;
    public String strokes_img;
    public String type;
    public String video;
    public String voice;
    public ArrayList<ListNetListExampleBean> example;


    @Override
    public String toString() {
        return "ListNetListBean{" +
                "app_id='" + app_id + '\'' +
                ", cover='" + cover + '\'' +
                ", desp='" + desp + '\'' +
                ", desp_audio='" + desp_audio + '\'' +
                ", hanzi='" + hanzi + '\'' +
                ", id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                ", strokes_img='" + strokes_img + '\'' +
                ", type='" + type + '\'' +
                ", video='" + video + '\'' +
                ", voice='" + voice + '\'' +
                ", example=" + example +
                '}';
    }
}
