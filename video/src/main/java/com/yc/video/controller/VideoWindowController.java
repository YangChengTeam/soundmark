package com.yc.video.controller;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.video.base.BaseVideoController;

/**
 * Created by caokun on 2019/11/8 11:21.
 */

public class VideoWindowController extends BaseVideoController {


    public VideoWindowController(@NonNull Context context) {
        this(context, null);
    }

    public VideoWindowController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoWindowController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void readyPlaying() {

    }

    @Override
    public void startBuffer() {

    }

    @Override
    public void endBuffer() {

    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void repeatPlay() {

    }

    @Override
    public void mobileWorkTips() {

    }

    @Override
    public void error(int errorCode, String errorMessage) {

    }

    @Override
    public void reset() {

    }


}
