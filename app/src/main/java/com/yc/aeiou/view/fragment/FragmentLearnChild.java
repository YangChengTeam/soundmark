package com.yc.aeiou.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kk.utils.LogUtil;
import com.video.player.lib.base.BaseCoverController;
import com.video.player.lib.base.BaseVideoPlayer;
import com.video.player.lib.base.IMediaPlayer;
import com.video.player.lib.bean.VideoParams;
import com.video.player.lib.constants.VideoConstants;
import com.video.player.lib.controller.DefaultCoverController;
import com.video.player.lib.manager.VideoPlayerManager;
import com.video.player.lib.view.VideoPlayerTrackView;
import com.video.player.lib.view.VideoTextureView;
import com.yc.aeiou.R;
import com.yc.aeiou.adaper.RecyclerLearnChildAdapter;
import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.bean.ListNetListExampleBean;
import com.yc.music.bean.MusicPlayerStatusBean;
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

    private String TAG = "FragmentLearnChild";
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

    @BindView(R.id.video_track)
    VideoPlayerTrackView mVideoPlayer;

    private AnimatorSet mAnimatorSet;
    private String mDespAudio;
    private VideoParams mVideoParams;

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
//        int itemHeight = MusicUtils.getInstance().getScreenWidth(mMainActivity) * 9 / 16;
//        mVideoPlayer.getLayoutParams().height = itemHeight;
//        DetailsCoverController coverController = new DetailsCoverController(mMainActivity);
//        mVideoPlayer.setVideoCoverController(coverController, false);
////        mVideoPlayer.setGlobaEnable(true);
//
////        {videoiId=166707, videoTitle='死前必吃的 43 种巨型美食，想和你吃到天荒地老', videoCover='http://img.kaiyanapp.com/2e3d1a243a0f714f971e2d2aed6c2376.jpeg?imageMogr2/quality/60/format/jpg',
////        videoDesp='当你最爱的食物被放大 10 倍，你就会获得 100 倍的幸福。巨型冰淇淋、汉堡、披萨……还有比这个更幸福的肥宅梦吗？据说是死前必吃榜单，可能是因为都吃完真的会死吧……',
////        videoUrl='http://baobab.kaiyanapp.com/api/v1/playUrl?vid=166707&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss', nickName=' INSIDER',
////        userFront='http://img.kaiyanapp.com/adf64ebd641f23df86fd13f412ae2276.jpeg?imageMogr2/quality/60/format/jpg', userSinger='在INSIDER，我们相信生活是一场冒险。我们讲述食物，旅游，设计，美容，艺术，健康，文化。跟我们一起探索吧！',
////        previewCount=80, durtion=466, lastTime=1572883273000, headTitle='null'}
//        mVideoParams = new VideoParams();
//        mVideoParams.setVideoiId("166707");
//        mVideoParams.setVideoTitle("死前必吃的 43 种巨型美食，想和你吃到天荒地老");
//        mVideoParams.setVideoCover("http://img.kaiyanapp.com/2e3d1a243a0f714f971e2d2aed6c2376.jpeg?imageMogr2/quality/60/format/jpg");
//        mVideoParams.setVideoDesp("当你最爱的食物被放大 10 倍，你就会获得 100 倍的幸福");
////        mVideoParams.setVideoUrl("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=166707&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss");
//        mVideoParams.setVideoUrl("http://voice.wk2.com/video/2017091807.mp4");
//        mVideoParams.setNickName("INSIDER");
//        mVideoParams.setUserFront("http://img.kaiyanapp.com/adf64ebd641f23df86fd13f412ae2276.jpeg?imageMogr2/quality/60/format/jpg");
//        mVideoParams.setUserSinger("在INSIDER，我们相信生活是一场冒险。");
//        mVideoParams.setPreviewCount(80);
//        mVideoParams.setDurtion(466);
//        mVideoParams.setLastTime(1572883273000L);

        VideoPlayerManager.getInstance().setVideoDisplayType(VideoConstants.VIDEO_DISPLAY_TYPE_CUT);
        DefaultCoverController coverController = new DefaultCoverController(mMainActivity);
        mVideoPlayer.setVideoCoverController(coverController, false);


//        VideoPlayerManager.getInstance().onPause();
    }

    private boolean mIsPlaying = false;

    /**
     * 播放器初始化
     *
     * @param isCreate
     */
    private void initVideoParams(boolean isCreate) {
//        mIsPlaying = intent.getBooleanExtra(VideoConstants.KEY_VIDEO_PLAYING, false);
        Log.d(TAG, "initVideoParams: mVideoParams " + mVideoParams.toString());
        if (null != mVideoParams) {
            mVideoPlayer.setDataSource(mVideoParams.getVideoUrl(), mVideoParams.getVideoTitle(), mVideoParams.getVideoiId());
            mVideoPlayer.setPlayerWorking(true);
            mVideoPlayer.setParamsTag(mVideoParams);
            //封面
            if (null != mVideoPlayer.getCoverController()) {
                Glide.with(mMainActivity)
                        .load(mVideoParams.getVideoCover())
                        .placeholder(R.drawable.ic_video_default_cover)
                        .error(R.drawable.ic_video_default_cover)
                        .dontAnimate()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mVideoPlayer.getCoverController().mVideoCover);
            }
            //无缝衔接外部播放任务
            if (mIsPlaying && null != IMediaPlayer.getInstance().getTextureView()) {
                addTextrueViewToView(mVideoPlayer);
                IMediaPlayer.getInstance().addOnPlayerEventListener(mVideoPlayer);
                //手动检查播放器内部状态，同步常规播放器状态至全屏播放器
                IMediaPlayer.getInstance().checkedVidepPlayerState();
            } else {
                //开始全新播放任务
                mVideoPlayer.startPlayVideo();
            }
//            if (null != mPresenter && !TextUtils.isEmpty(mVideoParams.getVideoiId())) {
//                //获取推荐视频
//                mPresenter.getVideosByVideo(mVideoParams.getVideoiId());
//            }
        }
    }

    /**
     * 添加一个视频渲染组件至View
     *
     * @param videoPlayer
     */
    private void addTextrueViewToView(BaseVideoPlayer videoPlayer) {
        //先移除存在的TextrueView
        if (null != IMediaPlayer.getInstance().getTextureView()) {
            VideoTextureView textureView = IMediaPlayer.getInstance().getTextureView();
            if (null != textureView.getParent()) {
                ((ViewGroup) textureView.getParent()).removeView(textureView);
            }
        }
        if (null != IMediaPlayer.getInstance().getTextureView()) {
            videoPlayer.mSurfaceView.addView(IMediaPlayer.getInstance().getTextureView(),
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        }
    }

    @Override
    protected void initData() {
        //为添加媒体播放被观察者对象MusicPlayer
        MusicPlayerObservable.getInstance().addObserver(this);


        Bundle arguments = getArguments();
        if (arguments != null) {
            int fragmentPosition = arguments.getInt("fragmentPosition");
            ListNetListBean listNetListBean = mMainActivity.mList.get(fragmentPosition);
//            ListNetListBean listNetListBean = arguments.getParcelable("listNetListBean");
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

            mVideoParams = new VideoParams();
            mVideoParams.setVideoiId(String.valueOf(fragmentPosition + 1000));
            mVideoParams.setVideoTitle(listNetListBean.desp);
            mVideoParams.setVideoCover(listNetListBean.cover);
            mVideoParams.setVideoDesp("");
//        mVideoParams.setVideoUrl("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=166707&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss");
            mVideoParams.setVideoUrl(listNetListBean.video);
//            mVideoParams.setVideoUrl("http://voice.wk2.com/video/2017091807.mp4");
            mVideoParams.setNickName("INSIDER");
            mVideoParams.setUserFront("http://img.kaiyanapp.com/adf64ebd641f23df86fd13f412ae2276.jpeg?imageMogr2/quality/60/format/jpg");
            mVideoParams.setUserSinger("在INSIDER，我们相信生活是一场冒险。");
            mVideoParams.setPreviewCount(80);
            mVideoParams.setDurtion(466);
            mVideoParams.setLastTime(1572883273000L);

            initVideoParams(false);
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
    public void onResume() {
        super.onResume();
        VideoPlayerManager.getInstance().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoPlayerManager.getInstance().onPause();
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
