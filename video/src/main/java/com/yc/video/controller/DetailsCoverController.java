package com.yc.video.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.video.R;
import com.yc.video.base.BaseCoverController;



public class DetailsCoverController extends BaseCoverController {

    private static final String TAG = "DetailsCoverController";
    public ImageView mVideoCover;

    public DetailsCoverController(@NonNull Context context) {
        this(context,null);
    }

    public DetailsCoverController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DetailsCoverController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.video_details_cover_controller_layout,this);
        mVideoCover = (ImageView) findViewById(R.id.video_cover_icon);
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
    }
}