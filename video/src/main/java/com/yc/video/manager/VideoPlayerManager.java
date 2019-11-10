package com.yc.video.manager;

import com.yc.video.base.IMediaPlayer;
import com.yc.video.constants.VideoConstants;

/**
 * Created by caokun on 2019/11/8 11:16.
 */

public class VideoPlayerManager {

    //缩放类型,默认是等比缩放
    private int VIDEO_DISPLAY_TYPE = VideoConstants.VIDEO_DISPLAY_TYPE_CUT;

    private static VideoPlayerManager mInstance;
    private IMediaPlayer mIMediaPlayer;
    //移动网络下是否允许播放
    private boolean mobileWorkEnable;
    //悬浮窗点击展开的目标Activity
    private static String mActivityClassName = null;
    //是否循环播放
    private boolean mLoop;


    public static VideoPlayerManager getInstance() {
        if (null == mInstance) {
            synchronized (VideoPlayerManager.class) {
                if (null == mInstance) {
                    mInstance = new VideoPlayerManager();
                }
            }
        }
        return mInstance;
    }

    private VideoPlayerManager() {
    }

    /**
     * 指定点击通知栏后打开的Activity对象绝对路径
     *
     * @param className 播放器Activity绝对路径
     */
    public void setPlayerActivityClassName(String className) {
        this.mActivityClassName = className;
    }

    /**
     * 返回点击通知栏后打开的Activity对象绝对路径
     *
     * @return 播放器Activity绝对路径
     */
    public String getPlayerActivityClassName() {
        return mActivityClassName;
    }

    /**
     * 绑定IMediaPlayer
     *
     * @param iMediaPlayer 具体的实现类
     */
    public void setIMediaPlayer(IMediaPlayer iMediaPlayer) {
        mIMediaPlayer = iMediaPlayer;
    }


    public long getDurtion() {
//        if (null != mIMediaPlayer) {
//            return mIMediaPlayer.getDurtion();
//        }
        return 0;
    }

    /**
     * 设置是否允许移动网络环境下工作
     *
     * @param enable true：允许移动网络工作 false：不允许
     */
    public void setMobileWorkEnable(boolean enable) {
//        this.mobileWorkEnable=enable;
    }

    /**
     * 开始、暂停 播放
     */
    public void playOrPause() {
//        if(null!=mIMediaPlayer){
//            mIMediaPlayer.playOrPause();
//        }
    }

    /**
     * 返回播放器内部播放状态
     *
     * @return 内部播放状态
     */
    public int getVideoPlayerState() {
//        if(null!=mIMediaPlayer){
//            return mIMediaPlayer.getVideoPlayerState();
//        }
        return VideoConstants.MUSIC_PLAYER_STOP;
    }

    /**
     * 跳转至指定位置播放
     *
     * @param currentTime 事件位置，单位毫秒
     */
    public void seekTo(long currentTime) {
        if (null != mIMediaPlayer) {
            mIMediaPlayer.seekTo(currentTime);
        }
    }

    /**
     * 返回视频画面缩放模式
     *
     * @return 用户设定的缩放模式
     */
    public int getVideoDisplayType() {
        return VIDEO_DISPLAY_TYPE;
    }

    /**
     * 是否允许移动网络环境下工作
     *
     * @return 是否允许在移动网络下工作
     */
    public boolean isMobileWorkEnable() {
        return mobileWorkEnable;
    }

    /**
     * 返回循环播放模式
     *
     * @return true:循环播放，false:不循环
     */
    public boolean isLoop() {
        return mLoop;
    }


}
