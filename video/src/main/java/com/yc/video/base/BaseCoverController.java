package com.yc.video.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by caokun on 2019/11/8 10:24.
 *
 * 封面控制
 */

public class BaseCoverController extends FrameLayout {
    public BaseCoverController(@NonNull Context context) {
        this(context,null);
    }

    public BaseCoverController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseCoverController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mOnStartListener){
                    mOnStartListener.onStartPlay();
                }
            }
        });
    }

    public interface OnStartListener{
        void onStartPlay();
    }

    protected OnStartListener mOnStartListener;

    public void setOnStartListener(OnStartListener onStartListener) {
        mOnStartListener = onStartListener;
    }

    public void onDestroy(){
        mOnStartListener=null;
    }
}
