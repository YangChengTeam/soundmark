package com.yc.video.base;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;

import com.yc.video.constants.VideoConstants;
import com.yc.video.listener.VideoPlayerEventListener;
import com.yc.video.manager.VideoAudioFocusManager;
import com.yc.video.manager.VideoPlayerManager;
import com.yc.video.manager.VideoWindowManager;
import com.yc.video.utils.VideoUtils;
import com.yc.video.view.VideoTextureView;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by caokun on 2019/11/8 11:18.
 */

public class IMediaPlayer implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener {

    private static final String TAG = "VideoPlay_IMediaPlayer";
    private static IMediaPlayer mInstance;

    //播放源
    private String mDataSource;
    //实时播放位置
    private int mPercentIndex;
    private static Context mContext;
    //息屏下WIFI锁
    private static WifiManager.WifiLock mWifiLock;

    //画面渲染
    private VideoTextureView mTextureView;

    private BaseVideoPlayer mNoimalPlayer, mFullScrrenPlayer, mMiniWindowPlayer, mWindownPlayer;
    //播放器组件监听器
    private static VideoPlayerEventListener mOnPlayerEventListeners;
    //内部播放状态
    private static int mMusicPlayerState = VideoConstants.MUSIC_PLAYER_STOP;

    private MediaPlayer mMediaPlayer;
    //进度计时器
    private PlayTimerTask mPlayTimerTask;
    //是否循环播放
    private boolean mLoop;

    //TASK执行次数计时
    private long TIMER_RUN_COUNT = 0;
    //缓冲进度
    private int mBufferPercent;
    //音频焦点管理者
    private static VideoAudioFocusManager mAudioFocusManager;
    private Timer mTimer;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;

    public static IMediaPlayer getInstance() {
        if (null == mInstance) {
            synchronized (IMediaPlayer.class) {
                if (null == mInstance) {
                    mInstance = new IMediaPlayer();
                }
            }
        }
        return mInstance;
    }

    private IMediaPlayer() {
        VideoPlayerManager.getInstance().setIMediaPlayer(this);
    }

    /**
     * 尝试重新播放
     *
     * @param percentIndex 尝试从指定位置重新开始
     */
    public void reStartVideoPlayer(long percentIndex) {
//        if(!TextUtils.isEmpty(mDataSource)){
//            startVideoPlayer(mDataSource,null, (int) percentIndex);
//        }
    }

    /**
     * 跳转至指定位置播放
     *
     * @param currentTime 事件位置，单位毫秒
     */
    public void seekTo(long currentTime) {
//        try {
//            if(null!=mMediaPlayer){
//                //非暂停状态下置为加载中状态
//                if(IMediaPlayer.getInstance().getVideoPlayerState()!=VideoConstants.MUSIC_PLAYER_PAUSE){
//                    IMediaPlayer.this.mMusicPlayerState= VideoConstants.MUSIC_PLAYER_SEEK;
//                    if(null!=mOnPlayerEventListeners){
//                        mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState,null);
//                    }
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    mMediaPlayer.seekTo(currentTime, MediaPlayer.SEEK_CLOSEST);
//                }else{
//                    mMediaPlayer.seekTo((int) currentTime);
//                }
//            }
//        }catch (RuntimeException e){
//            e.printStackTrace();
//        }
    }

    /**
     * 返回承载画面渲染器
     *
     * @return textureView
     */
    public VideoTextureView getTextureView() {
        return mTextureView;
    }


    /**
     * 保存常规播放器
     *
     * @param videoPlayer 播放器实例
     */
    public void setNoimalPlayer(BaseVideoPlayer videoPlayer) {
        this.mNoimalPlayer = videoPlayer;
    }

    /**
     * 保存全屏窗口播放器
     *
     * @param videoPlayer 播放器实例
     */
    public void setFullScrrenPlayer(BaseVideoPlayer videoPlayer) {
        this.mFullScrrenPlayer = videoPlayer;
    }

    /**
     * 注册播放器工作状态监听器
     *
     * @param listener 实现VideoPlayerEventListener的对象
     */
    public void addOnPlayerEventListener(VideoPlayerEventListener listener) {
        this.mOnPlayerEventListeners = listener;
    }

    /**
     * 检查播放器内部状态
     */
    public void checkedVidepPlayerState() {
        if (null != mOnPlayerEventListeners) {
            mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, null);
        }
    }

    /**
     * 画面渲染图层初始化
     *
     * @param textureView 自定义画面渲染器
     */
    public void initTextureView(VideoTextureView textureView) {
        this.mTextureView = textureView;
        textureView.setSurfaceTextureListener(this);
    }

    //=========================================画面渲染状态===========================================

    /**
     * TextureView准备好了回调
     *
     * @param surface 内部surface
     * @param width   TextureView布局宽
     * @param height  TextureView布局高
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "onSurfaceTextureAvailable: -->width:" + width + ",height:" + height);
    }

    /**
     * TextureView宽高发生变化时回调
     *
     * @param surface 内部surface
     * @param width   新的TextureView布局宽
     * @param height  新的TextureView布局高
     */
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.d(TAG, "onSurfaceTextureSizeChanged: -->width:" + width + ",height:" + height);
    }

    /**
     * TextureView销毁时回调
     *
     * @param surface 内部surface
     * @return Most applications should return true.
     */
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.d(TAG, "onSurfaceTextureDestroyed: surface " + surface);
        return false;
    }

    /**
     * TextureView刷新时回调
     *
     * @param surface 内部surface
     */
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        Log.d(TAG, "onSurfaceTextureUpdated: surface " + surface);
    }

    /**
     * 开始异步准备缓冲播放
     *
     * @param dataSource 播放资源地址，支持file、https、http 等协议
     * @param context    全局上下文
     */
    public void startVideoPlayer(String dataSource, Context context) {
        startVideoPlayer(dataSource, context, 0);
    }

    /**
     * 开始异步准备缓冲播放
     *
     * @param dataSource   播放资源地址，支持file、https、http 等协议
     * @param context      全局上下文
     * @param percentIndex 尝试从指定位置开始播放
     */
    public void startVideoPlayer(String dataSource, Context context, int percentIndex) {
        if (TextUtils.isEmpty(dataSource)) {
            IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_STOP;
            if (null != mOnPlayerEventListeners) {
                mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, "播放地址为空");
                mOnPlayerEventListeners.onVideoPathInvalid();
            }
            return;
        }
        if (IMediaPlayer.this.mMusicPlayerState == VideoConstants.MUSIC_PLAYER_PREPARE
                && mDataSource.equals(dataSource)) {
            Log.d(TAG, "startVideoPlayer: 重复调用");
            return;
        }
        this.mPercentIndex = percentIndex;
        this.mDataSource = dataSource;
        if (null != context) {
            this.mContext = context;
            if (null != mContext) {
                mWifiLock = ((WifiManager) mContext.getApplicationContext().getSystemService(
                        Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, "MUSIC_LOCK");
            }
        }
        reset();
        if (VideoUtils.getInstance().isCheckNetwork(mContext)) {
            if (!VideoUtils.getInstance().isWifiConnected(mContext) && !VideoPlayerManager.getInstance().isMobileWorkEnable()) {
                IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_MOBILE;
                if (null != mOnPlayerEventListeners) {
                    mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, "正在使用移动网络");
                }
                return;
            }
            startTimer();
            if (null == mAudioFocusManager) {
                mAudioFocusManager = new VideoAudioFocusManager(mContext.getApplicationContext());
            }
            int requestAudioFocus = mAudioFocusManager.requestAudioFocus(
                    new VideoAudioFocusManager.OnAudioFocusListener() {

                        @Override
                        public void onFocusGet() {
                            play();
                        }

                        @Override
                        public void onFocusOut() {
                            pause();
                        }

                        @Override
                        public boolean isPlaying() {
                            return IMediaPlayer.this.isPlaying();
                        }
                    });

            if (requestAudioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_PREPARE;
                try {
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setOnPreparedListener(this);
                    mMediaPlayer.setOnCompletionListener(this);
                    mMediaPlayer.setOnBufferingUpdateListener(this);
                    mMediaPlayer.setOnSeekCompleteListener(this);
                    mMediaPlayer.setOnErrorListener(this);
                    mMediaPlayer.setOnInfoListener(this);
                    mMediaPlayer.setOnVideoSizeChangedListener(this);
                    mMediaPlayer.setLooping(VideoPlayerManager.getInstance().isLoop());
                    mMediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);
                    Class<MediaPlayer> clazz = MediaPlayer.class;
                    Method method = clazz.getDeclaredMethod("setDataSource", String.class, Map.class);
                    Log.d(TAG, "startVideoPlayer: -->" + ",PATH:" + mDataSource);
                    method.invoke(mMediaPlayer, mDataSource, null);
                    if (null != mOnPlayerEventListeners) {
                        mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, "播放准备中");
                    }
                    if (null != mWifiLock) {
                        mWifiLock.acquire();
                    }
                    mMediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "startVideoPlayer: startPlay-->Exception--e:" + e.getMessage());
                    IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_ERROR;
                    if (null != mOnPlayerEventListeners) {
                        mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, "播放失败，" + e.getMessage());
                    }
                }
            } else {
                IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_ERROR;
                if (null != mOnPlayerEventListeners) {
                    mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, "未成功获取音频输出焦点");
                }
            }
        } else {
            IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_STOP;
            if (null != mOnPlayerEventListeners) {
                mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, "网络未连接");
            }
        }
    }

    /**
     * 开始计时任务
     */
    private void startTimer() {
        if (null == mPlayTimerTask) {
            mTimer = new Timer();
            mPlayTimerTask = new PlayTimerTask();
            mTimer.schedule(mPlayTimerTask, 0, 100);
        }
    }

    /**
     * 结束计时任务
     */
    private void stopTimer() {
        if (null != mPlayTimerTask) {
            mPlayTimerTask.cancel();
            mPlayTimerTask = null;
        }
        if (null != mTimer) {
            mTimer.cancel();
            mTimer = null;
        }
        TIMER_RUN_COUNT = 0;
    }

    /**
     * 恢复播放
     */
    public void play() {
        if (mMusicPlayerState == VideoConstants.MUSIC_PLAYER_START || mMusicPlayerState ==
                VideoConstants.MUSIC_PLAYER_PREPARE
                || mMusicPlayerState == VideoConstants.MUSIC_PLAYER_PLAY) {
            return;
        }
        try {
            if (null != mMediaPlayer) {
                mMediaPlayer.start();
            }
        } catch (RuntimeException e) {

        } finally {
            if (null != mMediaPlayer) {
                IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_PLAY;
                if (null != mOnPlayerEventListeners) {
                    mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, null);
                }
                startTimer();
            }
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        try {
            if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        } catch (RuntimeException e) {

        } finally {
            stopTimer();
            IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_PAUSE;
            if (null != mOnPlayerEventListeners) {
                mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, null);
            }
        }
    }

    /**
     * 返回播放器内部播放状态
     *
     * @return 播放器内部播放状态
     */
    public boolean isPlaying() {
        return null != mMediaPlayer && (mMusicPlayerState == VideoConstants.MUSIC_PLAYER_PLAY
                || mMusicPlayerState == VideoConstants.MUSIC_PLAYER_START);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /**
     * 播放进度、闹钟倒计时进度 计时器
     */
    private class PlayTimerTask extends TimerTask {
        @Override
        public void run() {
            TIMER_RUN_COUNT++;
            //播放进度实时回调
            if (null != mOnPlayerEventListeners) {
                if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
                    mOnPlayerEventListeners.currentPosition(mMediaPlayer.getDuration(),
                            mMediaPlayer.getCurrentPosition(), mBufferPercent);
                } else {
                    mOnPlayerEventListeners.currentPosition(-1, -1, mBufferPercent);
                }
            }
            //每隔1秒播放进度回调，主要用于更新Text和SEEK BAR
            if (TIMER_RUN_COUNT % 10 == 0) {
                if (null != mOnPlayerEventListeners) {
                    if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
                        //+500毫秒是因为1秒一次的播放进度回显，格式化分秒后显示有时候到不了终点时间
                        mOnPlayerEventListeners.onTaskRuntime(mMediaPlayer.getDuration(),
                                mMediaPlayer.getCurrentPosition() + 500, mBufferPercent);
                    } else {
                        mOnPlayerEventListeners.onTaskRuntime(-1, -1, mBufferPercent);
                    }
                }
            }
        }
    }


    /**
     * 只是释放播放器
     */
    private void reset() {
        try {
            if (null != mMediaPlayer) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            mBufferPercent = 0;
            if (null != mAudioFocusManager) {
                mAudioFocusManager.releaseAudioFocus();
            }
        }
    }

    /**
     * 释放、还原播放、监听、渲染等状态
     */
    public void onReset() {
        onStop(true);
        //销毁可能存在的悬浮窗
        VideoWindowManager.getInstance().onDestroy();
        if (null != mWindownPlayer) {
            mWindownPlayer.onDestroy();
            mWindownPlayer = null;
        }
    }

    /**
     * 停止播放
     *
     * @param isReset 是否释放播放器
     */
    public void onStop(boolean isReset) {
        //如果明确结束一切播放并且当前窗口无播放器则完全停止
        if (!isReset && VideoWindowManager.getInstance().isWindowShowing()) {
            return;
        }
        stopTimer();
        try {
            if (null != mMediaPlayer) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            mBufferPercent = 0;
            removeTextureView();
            if (null != mAudioFocusManager) {
                mAudioFocusManager.releaseAudioFocus();
            }
            //还原UI状态
            IMediaPlayer.this.mMusicPlayerState = VideoConstants.MUSIC_PLAYER_STOP;
            if (null != mOnPlayerEventListeners) {
                mOnPlayerEventListeners.onVideoPlayerState(mMusicPlayerState, null);
            }
            VideoWindowManager.getInstance().onDestroy();
            if (null != mWindownPlayer) {
                mWindownPlayer.onDestroy();
                mWindownPlayer = null;
            }
        }
    }
    /**
     * 清空画面渲染图层
     */
    private void removeTextureView() {
        if(null!=mTextureView){
            mTextureView.setSurfaceTextureListener(null);
            if(null!=mTextureView.getParent()){
                ((ViewGroup) mTextureView.getParent()).removeView(mTextureView);
            }
            mTextureView=null;
        }
        if(null!=mSurface){
            mSurface.release();
            mSurface=null;
        }
        mSurfaceTexture=null;
    }
    /**
     * 保存悬浮窗窗口实例
     * @param windownPlayer 播放器实例
     */
    public void setWindownPlayer(BaseVideoPlayer windownPlayer) {
        mWindownPlayer = windownPlayer;
    }

    public void setContinuePlay(boolean continuePlay) {}

    /**
     * 返回悬浮窗窗口实例
     * @return 播放器实例
     */
    public BaseVideoPlayer getWindownPlayer() {
        return mWindownPlayer;
    }
}
