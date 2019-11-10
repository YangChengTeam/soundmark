package com.yc.video.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.video.R;
import com.yc.video.bean.VideoParams;
import com.yc.video.constants.VideoConstants;
import com.yc.video.controller.DefaultCoverController;
import com.yc.video.controller.DefaultGestureController;
import com.yc.video.controller.DefaultVideoController;
import com.yc.video.listener.VideoPlayerEventListener;
import com.yc.video.manager.VideoPlayerManager;
import com.yc.video.manager.VideoWindowManager;
import com.yc.video.utils.VideoUtils;
import com.yc.video.view.VideoTextureView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by caokun on 2019/11/8 10:13.
 */

public abstract class BaseVideoPlayer<V extends BaseVideoController, C extends BaseCoverController
        , G extends BaseGestureController> extends FrameLayout implements VideoPlayerEventListener, View.OnTouchListener {


    private String TAG = "VideoPlayer_BaseVideoPlayer";
    //VideoController
    protected V mVideoController;
    //CoverController
    protected C mCoverController;
    //GestureController
    protected G mGestureController;

    //屏幕方向、手势调节，默认未知
    private int SCRREN_ORIENTATION = 0, GESTURE_SCENE = 0;
    //此播放器是否正在工作,配合列表滚动时，检测工作状态
    private boolean isPlayerWorking = false;

    //视频帧渲染父容器
    public FrameLayout mSurfaceView;
    //常规播放器手势代理，配合悬浮窗使用、全屏手势代理，配合手势调节功能使用
    private GestureDetector mGestureDetector, mFullScreenGestureDetector;

    //全屏的手势触摸、迷你小窗口的手势触摸
    private FrameLayout mTouchViewGroup, mMiniTouchViewGroup;


    //资源地址、视频标题
    private String mDataSource, mTitle;
    //手势跳转播放进度
    private long mSpeedTime = 0;
    //手势结束后是否需要跳转至对应位置播放
    private boolean isSpeedSeek = false;

    //视频ID，悬浮窗打开Activity用到
    private String mVideoID;

    public BaseVideoPlayer(@NonNull Context context) {
        this(context, null);
    }

    public BaseVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        boolean autoSetVideoController = false;
        boolean autoSetCoverController = false;

        View.inflate(context, getLayoutID(), this);
        //默认的初始化
        setVideoController(null, autoSetVideoController);
        setVideoCoverController(null, autoSetCoverController);

        //画面渲染
        mSurfaceView = (FrameLayout) findViewById(R.id.surface_view);
        //GestureDetector手势分发器代为处理手势,全屏播放器是新创建一个ViewGroup至控制器的底层处理手势事件
        if (null != mSurfaceView) {
            mGestureDetector = new GestureDetector(context, new TouchOnGestureListener());
            mSurfaceView.setOnTouchListener(this);
        }
    }

    protected  abstract int getLayoutID() ;

    /**
     * 设置封面控制器
     *
     * @param coverController   自定义VideoPlayerCover控制器
     * @param autoCreateDefault 当 controller 为空，是否自动创建默认的控制器
     */
    public void setVideoCoverController(C coverController, boolean autoCreateDefault) {
        FrameLayout conntrollerView = (FrameLayout) findViewById(R.id.video_cover_controller);
        if (null != conntrollerView) {
            removeGroupView(mCoverController);
            if (conntrollerView.getChildCount() > 0) {
                conntrollerView.removeAllViews();
            }
            if (null != mCoverController) {
                mCoverController.onDestroy();
                mCoverController = null;
            }
            //使用自定义的
            if (null != coverController) {
                mCoverController = coverController;
            } else {
                //是否使用默认的
                if (autoCreateDefault) {
                    mCoverController = (C) new DefaultCoverController(getContext());
                }
            }
            //添加控制器到播放器
            if (null != mCoverController) {
                mCoverController.setOnStartListener(new BaseCoverController.OnStartListener() {
                    @Override
                    public void onStartPlay() {
                        startPlayVideo();
                    }
                });
                conntrollerView.addView(mCoverController, new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
            }
        }
    }

    /**
     * 开始播放的入口开始播放、准备入口
     */
    public void startPlayVideo() {
        if (TextUtils.isEmpty(mDataSource)) {
            Toast.makeText(getContext(), "播放地址为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //还原可能正在进行的播放任务
        IMediaPlayer.getInstance().onReset();
        IMediaPlayer.getInstance().addOnPlayerEventListener(this);
        setPlayerWorking(true);
        //准备画面渲染图层
        if (null != mSurfaceView) {
            addTextrueViewToView(BaseVideoPlayer.this);
            //开始准备播放
            IMediaPlayer.getInstance().startVideoPlayer(mDataSource, getContext());
        }
    }

    /**
     * 设置视频控制器
     *
     * @param videoController   自定义VideoPlayer控制器
     * @param autoCreateDefault 当 controller 为空，是否自动创建默认的控制器
     */
    public void setVideoController(V videoController, boolean autoCreateDefault) {
        FrameLayout conntrollerView = (FrameLayout) findViewById(R.id.video_player_controller);
        if (null != conntrollerView) {
            removeGroupView(mVideoController);
            if (conntrollerView.getChildCount() > 0) {
                conntrollerView.removeAllViews();
            }
            if (null != mVideoController) {
                mVideoController.onDestroy();
                mVideoController = null;
            }
            //使用自定义的
            if (null != videoController) {
                mVideoController = videoController;
            } else {
                //是否使用默认的
                if (autoCreateDefault) {
                    mVideoController = (V) new DefaultVideoController(getContext());
                }
            }
            //添加控制器到播放器
            if (null != mVideoController) {
                mVideoController.setOnFuctionListener(new BaseVideoController.OnFuctionListener() {
                    /**
                     * 转向全屏播放
                     */
                    @Override
                    public void onStartFullScreen(BaseVideoController videoController) {
                        if (SCRREN_ORIENTATION == VideoConstants.SCREEN_ORIENTATION_PORTRAIT) {
                            startFullScreen((V) videoController);
                        } else {
//                            backFullScreenWindow();
                            Log.d(TAG, "onStartFullScreen:  转向全屏播放");
                        }
                    }

                    /**
                     * 转向迷你窗口播放
                     */
                    @Override
                    public void onStartMiniWindow(BaseVideoController miniWindowController) {
//                        startMiniWindow(miniWindowController);
                        Log.d(TAG, "onStartMiniWindow: 转向迷你窗口播放");
                    }

                    /**
                     * 转向悬浮窗播放
                     */
                    @Override
                    public void onStartGlobalWindown(BaseVideoController windowController, boolean defaultCreatCloseIcon) {
//                        startGlobalWindown(windowController, defaultCreatCloseIcon);
                        Log.d(TAG, "onStartGlobalWindown: 转向悬浮窗播放");
                    }

                    /**
                     * 退出迷你窗口
                     */
                    @Override
                    public void onQuiteMiniWindow() {
                        if (SCRREN_ORIENTATION == VideoConstants.SCREEN_ORIENTATION_TINY) {
                            Log.d(TAG, "onQuiteMiniWindow: 退出迷你窗口");
//                            backMiniWindow();
                        }
                    }

                    /**
                     * 从悬浮窗中打开视频播放器
                     */
                    @Override
                    public void onStartActivity() {
                        Log.d(TAG, "onStartActivity: 悬浮窗打开Activity ");
                        //悬浮窗打开Activity
                        startWindowToActivity();
                    }

                    /**
                     * 尝试弹射返回，在Activity onBackPressed方法中调用
                     */
                    @Override
                    public void onBackPressed() {
                        Log.d(TAG, "onBackPressed: 尝试弹射返回，在Activity onBackPressed方法中调用 ");
//                        backPressed();
                    }
                });
                conntrollerView.addView(mVideoController, new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
            }
        }
    }
    /**
     * 从悬浮窗播放器窗口转向VideoPlayerActivity播放
     */
    public void startWindowToActivity() {
        if (!TextUtils.isEmpty(VideoPlayerManager.getInstance().getPlayerActivityClassName())) {
            //先结束悬浮窗播放任务
            BaseVideoPlayer baseVideoPlayer = backGlobalWindownToActivity();
            Intent startIntent = new Intent();
            startIntent.setClassName(VideoUtils.getInstance().getPackageName(getContext().getApplicationContext()),
                    VideoPlayerManager.getInstance().getPlayerActivityClassName());
            startIntent.putExtra(VideoConstants.KEY_VIDEO_PLAYING, true);
            //如果播放器组件未启用，创建新的实例
            //如果播放器组件已启用且在栈顶，复用播放器不传递任何意图
            //反之则清除播放器之上的所有栈，让播放器组件显示在最顶层
            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (null != this.getTag() && getTag() instanceof VideoParams) {
                VideoParams videoParams = (VideoParams) this.getTag();
                startIntent.putExtra(VideoConstants.KEY_VIDEO_PARAMS, videoParams);
            } else {
                VideoParams videoParams = new VideoParams();
                Log.d(TAG, "startWindowToActivity: mTitle:" + mTitle + ",mDataSource:" + mDataSource);
                videoParams.setVideoTitle(mTitle);
                videoParams.setVideoUrl(mDataSource);
                videoParams.setVideoiId(mVideoID);
                startIntent.putExtra(VideoConstants.KEY_VIDEO_PARAMS, videoParams);
            }
            getContext().getApplicationContext().startActivity(startIntent);
            //销毁一下，万不能再界面跳转之前销毁
            if (null != baseVideoPlayer) {
                baseVideoPlayer.onDestroy();
                IMediaPlayer.getInstance().setWindownPlayer(null);
            }
        }
    }

    /**
     * 退出全局悬浮窗口播放
     *
     * @return 返回悬浮窗口播放器实例
     */
    private BaseVideoPlayer backGlobalWindownToActivity() {
        IMediaPlayer.getInstance().setContinuePlay(true);
        if (null != IMediaPlayer.getInstance().getWindownPlayer()) {
            BaseVideoPlayer windownPlayer = IMediaPlayer.getInstance().getWindownPlayer();
            if (windownPlayer.isPlayerWorking()) {
                windownPlayer.reset();
            }
            VideoWindowManager.getInstance().onDestroy();
            return windownPlayer;
        }
        VideoWindowManager.getInstance().onDestroy();
        return null;
    }
    /**
     * 此处返回此组件绑定的工作状态
     *
     * @return true:正在工作 false反之
     */
    public boolean isPlayerWorking() {
        return isPlayerWorking;
    }
    /**
     * 仅释放播放器窗口UI
     */
    public void reset() {
        //先移除存在的TextrueView
        if (null != IMediaPlayer.getInstance().getTextureView()) {
            VideoTextureView textureView = IMediaPlayer.getInstance().getTextureView();
            if (null != textureView.getParent()) {
                ((ViewGroup) textureView.getParent()).removeView(textureView);
            }
        }
        if (null != mVideoController) {
            mVideoController.reset();
        }
        if (null != mCoverController) {
            mCoverController.setVisibility(VISIBLE);
        }
        setPlayerWorking(false);
    }



    /**
     * 开启全屏播放的原理：
     * 1：改变屏幕方向，Activity 属性必须设置为android:configChanges="orientation|screenSize"，避免Activity销毁重建
     * 2：移除常规播放器已有的TextureView组件
     * 3：向Windown ViewGroup 添加一个新的VideoPlayer组件,赋值已有的TextrueView到VideoPlayer，设置新的播放器监听，
     * 结合TextrueView onSurfaceTextureAvailable 回调事件处理
     * 4：根据自身业务，向新的播放器添加控制器
     * 5：记录全屏窗口播放器，退出全屏恢复常规播放用到
     *
     * @param fullScreenVideoController 全屏控制器，为空则使用默认控制器
     */
    public void startFullScreen(V fullScreenVideoController) {
        AppCompatActivity appCompActivity = VideoUtils.getInstance().getAppCompActivity(getContext());
        if (null != appCompActivity) {
            SCRREN_ORIENTATION = VideoConstants.SCREEN_ORIENTATION_FULL;
            setScrrenOrientation(SCRREN_ORIENTATION);
            //改变屏幕方向
            appCompActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            appCompActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ViewGroup viewGroup = (ViewGroup) appCompActivity.getWindow().getDecorView();
            if (null != viewGroup && null != IMediaPlayer.getInstance().getTextureView()) {
                View oldFullVideo = viewGroup.findViewById(R.id.video_full_screen_window);
                //移除Window可能存在的播放器组件
                if (null != oldFullVideo) {
                    viewGroup.removeView(oldFullVideo);
                }
                //保存当前实例
                IMediaPlayer.getInstance().setNoimalPlayer(BaseVideoPlayer.this);

                try {
                    Constructor<? extends BaseVideoPlayer> constructor =
                            BaseVideoPlayer.this.getClass().getConstructor(Context.class);
                    //新实例化自己
                    BaseVideoPlayer videoPlayer = constructor.newInstance(getContext());
                    videoPlayer.setBackgroundColor(Color.parseColor("#000000"));
                    //绑定组件ID
                    videoPlayer.setId(R.id.video_full_screen_window);
                    //保存全屏窗口实例
                    IMediaPlayer.getInstance().setFullScrrenPlayer(videoPlayer);
                    //将新的实例化添加至Window
                    viewGroup.addView(videoPlayer, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    //设置用户自定义全屏播放器控制器
                    if (null != fullScreenVideoController) {
                        videoPlayer.setVideoController(fullScreenVideoController, false);
                    } else {
                        //设置内部默认控制器
                        videoPlayer.setVideoController(null, true);
                    }
                    //更新屏幕方向
                    videoPlayer.setScrrenOrientation(SCRREN_ORIENTATION);
                    //转换为横屏方向
                    videoPlayer.mVideoController.startHorizontal();
                    videoPlayer.setPlayerWorking(true);
                    //设置基础的配置
                    videoPlayer.setDataSource(mDataSource, mTitle);
                    //清除全屏控件的手势事件
                    if (null != videoPlayer.mSurfaceView) {
                        videoPlayer.mSurfaceView.setOnTouchListener(null);
                    }
                    //添加一个不可见的ViewGroup至最底层，用来处理手势事件
                    mTouchViewGroup = new FrameLayout(videoPlayer.getContext());
                    OnFullScreenGestureListener gestureListener =
                            new OnFullScreenGestureListener(videoPlayer.getVideoController());
                    mFullScreenGestureDetector = new GestureDetector(videoPlayer.getContext(), gestureListener);
                    OnFullScreenTouchListener fullScreenTouchListener = new OnFullScreenTouchListener();
                    mTouchViewGroup.setOnTouchListener(fullScreenTouchListener);
                    removeGroupView(mTouchViewGroup);
                    videoPlayer.addView(mTouchViewGroup, 0, new LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    //生成一个默认的亮度、进度、声音手势进度调节View
                    if (null == mGestureController) {
                        Log.d(TAG, "startFullScreen: 使用默认的手势控制器");
                        mGestureController = (G) new DefaultGestureController(videoPlayer.getContext());
                    }
                    removeGroupView(mGestureController);
                    videoPlayer.addView(mGestureController, new LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    //添加TextrueView至播放控件
                    addTextrueViewToView(videoPlayer);
                    //添加监听器
                    IMediaPlayer.getInstance().addOnPlayerEventListener(videoPlayer);
                    //手动检查播放器内部状态，同步常规播放器状态至新的播放器
                    IMediaPlayer.getInstance().checkedVidepPlayerState();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 添加一个视频渲染组件至VideoPlayer
     *
     * @param videoPlayer 播放器实例
     */
    private void addTextrueViewToView(BaseVideoPlayer videoPlayer) {
        //先移除存在的TextrueView
        if (null != IMediaPlayer.getInstance().getTextureView()) {
            VideoTextureView textureView = IMediaPlayer.getInstance().getTextureView();
            if (null != textureView.getParent()) {
                ((ViewGroup) textureView.getParent()).removeView(textureView);
            }
        }
        if (null == videoPlayer.mSurfaceView) return;
        if (null != IMediaPlayer.getInstance().getTextureView()) {
            videoPlayer.mSurfaceView.addView(IMediaPlayer.getInstance().getTextureView(),
                    new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
        } else {
            VideoTextureView textureView = new VideoTextureView(getContext());
            IMediaPlayer.getInstance().initTextureView(textureView);
            videoPlayer.mSurfaceView.addView(textureView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, Gravity.CENTER));
        }
    }


    /**
     * 播放器全屏状态下的手势处理，处理上、下、左、右、左侧区域、右侧区域等手势
     */

    private class OnFullScreenGestureListener extends GestureDetector.SimpleOnGestureListener {
        public String TAG = "OnFullScreenGestureListener";
        private BaseVideoController videoController;
        private int mVideoPlayerWidth, mVideoPlayerHeight, mMaxVolume, mCurrentVolume;
        //音量管理者
        private AudioManager mAudioManager;
        //窗口
        private Window mWindow = null;

        /**
         * 构造器
         *
         * @param controller
         */
        public OnFullScreenGestureListener(BaseVideoController controller) {
            this.videoController = controller;
            if (null != videoController) {
                OnFullScreenGestureListener.this.videoController.getViewTreeObserver()
                        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                OnFullScreenGestureListener.this.videoController.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                                mVideoPlayerWidth = OnFullScreenGestureListener.this.videoController.getWidth();
                                mVideoPlayerHeight = OnFullScreenGestureListener.this.videoController.getHeight();
                                Log.d(TAG, "onGlobalLayout: setVideoController-->VIDEO_PLAYER_WIDTH:" + mVideoPlayerWidth
                                        + ",VIDEO_PLAYER_HEIGHT:" + mVideoPlayerHeight);
                            }
                        });
                AppCompatActivity appCompActivity = VideoUtils.getInstance().getAppCompActivity(
                        videoController.getContext());
                if (null != appCompActivity) {
                    mWindow = appCompActivity.getWindow();
                }
                mAudioManager = (AudioManager) videoController.getContext().getApplicationContext()
                        .getSystemService(Context.AUDIO_SERVICE);
                mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            }
        }
    }

    /**
     * 全屏播放器手势,交给手势控制器接管 OnFullScreenGestureListener
     */
    private class OnFullScreenTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //手指离开屏幕，还原手势控制器
            if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                GESTURE_SCENE = BaseGestureController.SCENE_PROGRESS;
                //如果用户手势意意图是快进快退，则UI回显提示框应立即消失
                if (isSpeedSeek && mSpeedTime > 0) {
                    //复原
                    if (null != mGestureController) {
                        mGestureController.onReset(0);
                    }
                    IMediaPlayer.getInstance().seekTo(mSpeedTime);
                    isSpeedSeek = false;
                } else {
                    //复原
                    if (null != mGestureController) {
                        mGestureController.onReset(800);
                    }
                }
            }
            if (null != mFullScreenGestureDetector) {
                return mFullScreenGestureDetector.onTouchEvent(event);
            }
            return false;
        }
    }


    /**
     * 返回视频控制器
     *
     * @return 返回用户设置的或默认的视频控制器
     */
    public V getVideoController() {
        return mVideoController;
    }

    public void setPlayerWorking(boolean playerWorking) {
        isPlayerWorking = playerWorking;
    }

    /**
     * 设置播放资源
     *
     * @param path  暂支持file、http、https等协议
     * @param title 视频描述
     */
    public void setDataSource(String path, String title) {
        if (null != mVideoController) {
            mVideoController.setTitle(title);
        }
        this.mDataSource = path;
        this.mTitle = title;
    }
    /**
     * 设置播放资源
     *
     * @param path    暂支持file、http、https等协议
     * @param title   视频描述
     * @param videoID 视频ID
     */
    public void setDataSource(String path, String title, String videoID) {
        if (null != mVideoController) {
            mVideoController.setTitle(title);
        }
        this.mDataSource = path;
        this.mTitle = title;
        this.mVideoID = videoID;
    }


    /**
     * 更新播放器方向
     *
     * @param scrrenOrientation 详见 VideoConstants 常量定义
     */
    public void setScrrenOrientation(int scrrenOrientation) {
        this.SCRREN_ORIENTATION = scrrenOrientation;
        if (null != mVideoController) {
            mVideoController.setScrrenOrientation(scrrenOrientation);
        }
    }

    /**
     * 将自己从父Parent中移除
     *
     * @param viewGroup view
     */
    private void removeGroupView(ViewGroup viewGroup) {
        if (null != viewGroup && null != viewGroup.getParent()) {
            Log.d(TAG, "removeGroupView: ");
            ViewGroup parent = (ViewGroup) viewGroup.getParent();
            parent.removeView(viewGroup);
        }
    }


    //========================================播放器手势处理=========================================

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (null != mGestureDetector) mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 拦截触摸屏幕的所有事件
     */
    private class TouchOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
//            if (VideoPlayerManager.getInstance().isPlaying()) {
//                if (null != mVideoController) {
//                    mVideoController.changeControllerState(SCRREN_ORIENTATION, true);
//                }
//            }
            return true;
        }
    }

    //======================================播放器内部状态回调========================================
    @Override
    public void onVideoPlayerState(int playerState, String message) {
        Log.d(TAG, "onVideoPlayerState: playerState " + playerState + " message " + message);
    }

    @Override
    public void onPrepared(long totalDurtion) {
        Log.d(TAG, "onPrepared: totalDurtion " + totalDurtion);
    }

    @Override
    public void onBufferingUpdate(int percent) {
        Log.d(TAG, "onBufferingUpdate: percent " + percent);
    }

    @Override
    public void onInfo(int event, int extra) {
        Log.d(TAG, "onInfo: event " + event + " extra " + extra);
    }

    @Override
    public void onVideoPathInvalid() {
        Log.d(TAG, "onVideoPathInvalid: ");
    }

    @Override
    public void onTaskRuntime(long totalDurtion, long currentDurtion, int bufferPercent) {
        Log.d(TAG, "onTaskRuntime: totalDurtion " + totalDurtion + " currentDurtion " + currentDurtion + " bufferPercent " + bufferPercent);
    }

    @Override
    public void currentPosition(long totalPosition, long currentPosition, int bufferPercent) {
        Log.d(TAG, "currentPosition: totalPosition " + totalPosition + " currentPosition " + currentPosition + " bufferPercent " + bufferPercent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
    }
}
