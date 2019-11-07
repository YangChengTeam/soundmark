package com.yc.aeiou.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kk.utils.LogUtil;
import com.yc.aeiou.R;
import com.yc.aeiou.adaper.RecyclerLearnChildAdapter;
import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.bean.ListNetListExampleBean;
import com.yc.music.bean.MusicPlayerStatusBean;
import com.yc.music.listener.MusicPlayerCallBack;
import com.yc.music.listener.MusicPlayerObservable;
import com.yc.music.manager.MusicPlayerManager;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


/**
 * Created by caokun on 2019/11/5 11:51.
 */

public class FragmentLearnChild extends BaseMainFragment implements Observer {

    @BindView(R.id.tv_learn_child_des)
    public TextView tvDes;
    @BindView(R.id.pb_learn_child)
    public ProgressBar pbFragment;
    @BindView(R.id.iv_learn_child_soundmark)
    public ImageView ivSoundmark;
    @BindView(R.id.iv_learn_child_squirrel)
    public ImageView ivSquirrel;
    @BindView(R.id.iv_learn_child_recyclerview)
    RecyclerView recyclerView;
    private AnimatorSet mAnimatorSet;
    private String mDespAudio;

    @OnClick(R.id.iv_learn_child_squirrel)
    void onClickListent(View view) {
        switch (view.getId()) {
            case R.id.iv_learn_child_squirrel:
                playMusic(false, pbFragment, ivSquirrel, mDespAudio);
                break;
        }
    }

    private void playMusic(boolean isShowAnim, ProgressBar progressBar, View view, String path) {
        FragmentLearnChild.this.pb = progressBar;
        if (isShowAnim) {
            pb.setVisibility(View.VISIBLE);
            startAnim(view, pb);
        }
        MusicPlayerManager.getInstance().startPlayMusic(path);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_learn_child;
    }

    @Override
    protected void initViews() {

    }


    @Override
    protected void initData() {
        //为添加媒体播放被观察者对象MusicPlayer
        MusicPlayerObservable.getInstance().addObserver(this);


        Bundle arguments = getArguments();
        if (arguments != null) {
            ListNetListBean listNetListBean = arguments.getParcelable("listNetListBean");
            Log.d("sssss", "initData: listNetListBean " + listNetListBean.toString());
            mDespAudio = listNetListBean.desp_audio;

            tvDes.setText(listNetListBean.desp);
            Glide.with(mMainActivity).load(listNetListBean.img).into(ivSoundmark);

            ArrayList<ListNetListExampleBean> example = listNetListBean.example;
            Log.d("sssss", "initData: example " + example.toString());

            RecyclerLearnChildAdapter recyclerLearnChildAdapter = new RecyclerLearnChildAdapter(mContext, example, listNetListBean.name);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(recyclerLearnChildAdapter);

            recyclerLearnChildAdapter.setOnClickItemListent((itemView, pb, position) -> {
                ListNetListExampleBean listNetListExampleBean = example.get(position);
                playMusic(true, pb, itemView, listNetListExampleBean.video);
            });
//            recyclerLearnChildAdapter.setOnClickItemListent(new RecyclerLearnChildAdapter.OnClickItemListent() {
//                @Override
//                public void clickItem(LinearLayout itemView, ProgressBar pb, int position) {
//                    FragmentLearnChild.this.pb = pb;
//                    pb.setVisibility(View.VISIBLE);
//                    startAnim(itemView, pb);
//
//                    ListNetListExampleBean listNetListExampleBean = example.get(position);
//                    MusicPlayerManager.getInstance().startPlayMusic(listNetListExampleBean.video);
//                }
//
//            });
        }

    }

    private ProgressBar pb;

    private void startAnim(View view, ProgressBar pb) {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            return;
        }
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
        //组合动画
        mAnimatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f);
        mAnimatorSet.setDuration(800);
        mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mAnimatorSet.play(scaleX).with(scaleY);//两个动画同时开始

        mAnimatorSet.start();
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (pb != null) {
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
        MusicPlayerObservable.getInstance().deleteObserver(this::update);
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof MusicPlayerObservable && arg instanceof MusicPlayerStatusBean) {
            MusicPlayerStatusBean musicPlayerStatusBean = (MusicPlayerStatusBean) arg;
            LogUtil.msg("媒体播放状态 --> musicPlayerStatusBean " + musicPlayerStatusBean.toString());
            if (pb != null) {
                pb.setVisibility(View.GONE);
            }
        }
    }


}
