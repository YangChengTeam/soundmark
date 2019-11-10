package com.yc.video.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.video.R;
import com.yc.video.base.BaseVideoPlayer;
import com.yc.video.controller.DefaultGestureController;
import com.yc.video.controller.DefaultVideoController;
import com.yc.video.controller.DetailsCoverController;

/**
 * Created by caokun on 2019/11/8 16:18.
 */

public class VideoDetailsPlayerTrackView extends BaseVideoPlayer<DefaultVideoController,
        DetailsCoverController, DefaultGestureController> {
    @Override
    protected int getLayoutID() {
        return R.layout.video_default_track_layout;
    }
    public VideoDetailsPlayerTrackView(@NonNull Context context) {
        this(context,null);
    }

    public VideoDetailsPlayerTrackView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoDetailsPlayerTrackView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
