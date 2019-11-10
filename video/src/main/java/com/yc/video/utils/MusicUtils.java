package com.yc.video.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * TinyHung@Outlook.com
 * 2018/3/922
 */

public class MusicUtils {

    private static final String TAG = "MusicUtils";
    private static volatile MusicUtils mInstance;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    public static MusicUtils getInstance() {
        if(null==mInstance){
            synchronized (MusicUtils.class) {
                if (null == mInstance) {
                    mInstance = new MusicUtils();
                }
            }
        }
        return mInstance;
    }

    private MusicUtils(){}

    //设备屏幕宽度
    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}