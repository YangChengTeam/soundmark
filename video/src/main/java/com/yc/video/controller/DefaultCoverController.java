package com.yc.video.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.video.R;
import com.yc.video.base.BaseCoverController;

/**
 * Created by caokun on 2019/11/8 15:32.
 *
 * 默认封面控制
 */

public class DefaultCoverController  extends BaseCoverController {

    private   String TAG = "DefaultCoverController";
    public ImageView mVideoCover;
    public TextView mPreCount;
    public TextView mPreDurtion;

    public DefaultCoverController(@NonNull Context context) {
        super(context);
    }

    public DefaultCoverController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultCoverController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.video_default_cover_controller_layout,this);
        mVideoCover = (ImageView) findViewById(R.id.video_cover_icon);
        mPreCount = (TextView) findViewById(R.id.view_cover_count);
        mPreDurtion = (TextView) findViewById(R.id.view_cover_durtion);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null!=mVideoCover){
            mVideoCover.setImageResource(0);
            mVideoCover=null;
        }
        mPreCount=null;mPreDurtion=null;
    }
}
