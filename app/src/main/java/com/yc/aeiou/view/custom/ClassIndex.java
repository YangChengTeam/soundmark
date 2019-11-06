package com.yc.aeiou.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yc.aeiou.R;

/**
 * Created by caokun on 2019/11/6 16:30.
 */

public class ClassIndex extends RelativeLayout {
    public ClassIndex(Context context) {
        this(context, null);
    }

    public ClassIndex(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClassIndex);
        Drawable iconIndex = typedArray.getDrawable(R.styleable.ClassIndex_iconIndex);
        boolean isShowVip = typedArray.getBoolean(R.styleable.ClassIndex_isShowVip, false);

        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_class_index, this, true);
        ImageView ivIndex = inflate.findViewById(R.id.iv_class_index_index);
        ImageView ivVip = inflate.findViewById(R.id.iv_class_index_vip);

        Glide.with(context).load(iconIndex).into(ivIndex);
        if (isShowVip) {
            ivVip.setVisibility(VISIBLE);
        } else {
            ivVip.setVisibility(INVISIBLE);
        }

    }
}
