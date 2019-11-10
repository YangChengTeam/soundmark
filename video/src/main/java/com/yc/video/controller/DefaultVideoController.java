package com.yc.video.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.video.R;
import com.yc.video.base.BaseVideoController;
import com.yc.video.base.IMediaPlayer;
import com.yc.video.constants.VideoConstants;
import com.yc.video.manager.VideoPlayerManager;
import com.yc.video.utils.VideoUtils;

/**
 * Created by caokun on 2019/11/8 10:51.
 * <p>
 * 默认的视频播放器
 */

public class DefaultVideoController extends BaseVideoController implements SeekBar.OnSeekBarChangeListener {


    private View mTopBarLayout, mBottomBarLayout, mErrorLayout, mMobileLayout, mVideoStart,
            mBtnBackTiny, mBtnFullWindow, mBtnFull;
    private TextView mVideoTitle, mVideoCurrent, mVideoTotal;
    private ProgressBar mBottomProgressBar, mProgressBar;
    private SeekBar mSeekBar;
    private ImageView mBtnStart;
    //用户是否手指正在持续滚动
    private boolean isTouchSeekBar = false;
    //实时播放位置
    private long mOldPlayProgress;
    //悬浮窗播放功能开关,仅针对按钮入口有效
    private boolean mEnable;

    public DefaultVideoController(@NonNull Context context) {
        this(context, null);
    }

    public DefaultVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultVideoController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.video_default_controller_layout, this);
        mTopBarLayout = findViewById(R.id.video_top_tab);
        mBottomBarLayout = findViewById(R.id.video_bottom_tab);
        mErrorLayout = findViewById(R.id.error_layout);
        mMobileLayout = findViewById(R.id.mobile_layout);

        View btnResetPlay = findViewById(R.id.video_btn_reset_play);
        mBtnBackTiny = findViewById(R.id.video_btn_back_tiny);
        View btnBack = findViewById(R.id.video_btn_back);
        View btnMenu = findViewById(R.id.video_btn_menu);
        mBtnStart = (ImageView) findViewById(R.id.video_btn_start);
        mBtnFull = findViewById(R.id.video_full_screen);
        mBtnFullWindow = findViewById(R.id.video_full_window);
        mVideoTitle = (TextView) findViewById(R.id.video_title);
        mVideoCurrent = (TextView) findViewById(R.id.video_current);
        mVideoTotal = (TextView) findViewById(R.id.video_total);
        mBottomProgressBar = findViewById(R.id.bottom_progress);
        mProgressBar = (ProgressBar) findViewById(R.id.video_loading);
        mSeekBar = (SeekBar) findViewById(R.id.video_seek_progress);
        mVideoStart = findViewById(R.id.video_start);
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.error_layout) {
                    IMediaPlayer.getInstance().reStartVideoPlayer(mOldPlayProgress);
                } else if (id == R.id.video_btn_reset_play) {
                    VideoPlayerManager.getInstance().setMobileWorkEnable(true);
                    IMediaPlayer.getInstance().reStartVideoPlayer(0);
                } else if (id == R.id.video_btn_back_tiny) {
                    if (null != mOnFuctionListener) {
                        mOnFuctionListener.onQuiteMiniWindow();
                    }
                } else if (id == R.id.video_btn_back) {
                    if (null != mOnFuctionListener) {
                        mOnFuctionListener.onBackPressed();
                    }
                } else if (id == R.id.video_btn_menu) {

                } else if (id == R.id.video_btn_start) {
                    VideoPlayerManager.getInstance().playOrPause();
                } else if (id == R.id.video_full_screen) {
                    if (null != mOnFuctionListener) {
                        mOnFuctionListener.onStartFullScreen(null);
                    }
                } else if (id == R.id.video_full_window) {
                    if (null != mOnFuctionListener) {
                        mOnFuctionListener.onStartGlobalWindown(
                                new VideoWindowController(getContext()), true);
                    }
                }
            }
        };
        mErrorLayout.setOnClickListener(onClickListener);
        btnResetPlay.setOnClickListener(onClickListener);
        mBtnBackTiny.setOnClickListener(onClickListener);
        btnBack.setOnClickListener(onClickListener);
        btnMenu.setOnClickListener(onClickListener);
        mBtnStart.setOnClickListener(onClickListener);
        mBtnFull.setOnClickListener(onClickListener);
        if (null != mBtnFullWindow) {
            mBtnFullWindow.setOnClickListener(onClickListener);
        }
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    /**
     * 开始播放器准备中
     */
    @Override
    public void readyPlaying() {
        Log.d(TAG, "readyPlaying: mScrrenOrientation " + mScrrenOrientation);
        removeCallbacks(View.INVISIBLE);
        //小窗口
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
            //悬浮窗
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_WINDOW) {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.GONE);
            //常规、全屏
        } else {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        }
    }

    /**
     * 开始缓冲中
     */
    @Override
    public void startBuffer() {
        Log.d(TAG, "startBuffer:  mScrrenOrientation " + mScrrenOrientation);
        //小窗口
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_WINDOW) {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.GONE);
            //常规、全屏
        } else {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        }
    }

    /**
     * 缓冲结束
     */
    @Override
    public void endBuffer() {
        Log.d(TAG, "endBuffer:  mScrrenOrientation " + mScrrenOrientation);
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
        } else {
            if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_PORTRAIT) {
                updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.VISIBLE);
            } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_WINDOW) {
                updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.GONE);
            } else {
                updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
            }
        }
    }

    @Override
    public void play() {
        Log.d(TAG, "play:  mScrrenOrientation " + mScrrenOrientation);
        if (null != mBtnStart) {
            mBtnStart.setImageResource(R.drawable.ic_video_controller_pause);
        }
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_WINDOW) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.GONE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_PORTRAIT) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.VISIBLE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_FULL) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        }
    }

    @Override
    public void pause() {
        Log.d(TAG, "pause:  mScrrenOrientation " + mScrrenOrientation);
        if (null != mBtnStart) {
            mBtnStart.setImageResource(R.drawable.ic_video_controller_play);
        }
        removeCallbacks(View.VISIBLE);
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_WINDOW) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.GONE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_PORTRAIT) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            removeCallbacks(View.INVISIBLE);
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.VISIBLE, View.GONE, View.GONE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_FULL) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        }
    }

    @Override
    public void repeatPlay() {
        Log.d(TAG, "repeatPlay:  mScrrenOrientation " + mScrrenOrientation);
        if (null != mBtnStart) {
            mBtnStart.setImageResource(R.drawable.ic_video_controller_pause);
        }
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_WINDOW) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.GONE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_PORTRAIT) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.VISIBLE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.VISIBLE, View.GONE, View.GONE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_FULL) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        }
        changeControllerState(mScrrenOrientation, false);
    }

    /**
     * 移动网络环境下工作
     */
    @Override
    public void mobileWorkTips() {
        Log.d(TAG, "mobileWorkTips:  mScrrenOrientation " + mScrrenOrientation);
        removeCallbacks(View.INVISIBLE);
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.VISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
        } else {
            updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.VISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        }
    }

    @Override
    public void error(int errorCode, String errorMessage) {
        Log.d(TAG, "error: errorCode " + errorCode + " errorMessage " + errorMessage + "  mScrrenOrientation " + mScrrenOrientation);
        if (null != mBtnStart) {
            mBtnStart.setImageResource(R.drawable.ic_video_controller_play);
        }
        removeCallbacks(View.INVISIBLE);
        updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE,
                View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
    }

    @Override
    public void reset() {
        Log.d(TAG, "reset:  mScrrenOrientation " + mScrrenOrientation);
        removeCallbacks(View.INVISIBLE);
        updateVideoControllerUI(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        if (null != mVideoTotal) {
            mVideoTotal.setText("00:00");
            mVideoCurrent.setText("00:00");
        }
        if (null != mSeekBar) {
            mSeekBar.setSecondaryProgress(0);
            mSeekBar.setProgress(0);
        }
        if (null != mBottomProgressBar) {
            mBottomProgressBar.setSecondaryProgress(0);
            mBottomProgressBar.setProgress(0);
        }
    }

    /**
     * 更新控制器
     *
     * @param start          开始
     * @param loading        加载中
     * @param bottomProgress 底部进度条
     * @param errorLayout    失败图层
     * @param mobileLayout   移动网络提示图层
     * @param tinyLayout     小窗口删除按钮
     * @param windownBtn     悬浮窗
     * @param fullBtn        全屏  悬浮窗和始终是对立的
     */
    private void updateVideoControllerUI(int start, int loading, int bottomProgress, int errorLayout,
                                         int mobileLayout, int tinyLayout, int windownBtn, int fullBtn) {
        if (null != mVideoStart) {
            mVideoStart.setVisibility(start);
        }
        if (null != mProgressBar) {
            mProgressBar.setVisibility(loading);
        }
        if (null != mBtnBackTiny) {
            mBtnBackTiny.setVisibility(tinyLayout);
        }
        //悬浮窗显示按钮根据用户设置是否生效
        if (null != mBtnFullWindow) {
            if (windownBtn == View.VISIBLE) {
                if (mEnable) {
                    mBtnFullWindow.setVisibility(windownBtn);
                }
            } else {
                mBtnFullWindow.setVisibility(windownBtn);
            }
        }
        if (null != mBtnFull) {
            mBtnFull.setVisibility(fullBtn);
        }
        //如果是显示底部进度条，当底部控制器显示时不应该显示底部进度条
        if (null != mBottomProgressBar) {
            if (bottomProgress == View.VISIBLE) {
                //仅当底部控制器处于非显示状态下，才显示底部进度条
                if (null != mBottomBarLayout && mBottomBarLayout.getVisibility() != VISIBLE) {
                    mBottomProgressBar.setVisibility(bottomProgress);
                }
            } else {
                mBottomProgressBar.setVisibility(bottomProgress);
            }
        }
        if (null != mErrorLayout) {
            mErrorLayout.setVisibility(errorLayout);
        }
        if (null != mMobileLayout) {
            mMobileLayout.setVisibility(mobileLayout);
        }
    }

    /**
     * 横屏模式开启,默认展开控制器
     */
    @Override
    public void startHorizontal() {
        Log.d(TAG, "startHorizontal: ");
        changeControllerState(VideoConstants.SCREEN_ORIENTATION_FULL, false);
    }

    /**
     * 开启小窗口播放
     */
    @Override
    public void startTiny() {
        Log.d(TAG, "startTiny: mScrrenOrientation " + mScrrenOrientation);
        removeCallbacks(View.INVISIBLE);
        updateVideoControllerUI(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                View.INVISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
    }

    /**
     * 设置视频标题
     *
     * @param videoTitle 视频标题内容
     */
    @Override
    protected void setTitle(String videoTitle) {
        if (null != mVideoTitle) {
            mVideoTitle.setText(videoTitle);
        }
    }

    /**
     * 跳转至某处尝试开始播放中
     */
    @Override
    public void startSeek() {
        Log.d(TAG, "startSeek: mScrrenOrientation " + mScrrenOrientation);
        //小窗口
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
        } else if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_WINDOW) {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.GONE);
            //常规、全屏
        } else {
            updateVideoControllerUI(View.INVISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.GONE, View.VISIBLE);
        }
    }

    /**
     * 播放进度
     *
     * @param totalDurtion   视频总长度 单位：毫秒，暂停下为-1
     * @param currentDurtion 播放进度 单位：毫秒，暂停下为-1
     * @param bufferPercent  缓冲进度，单位：百分比
     */
    @Override
    public void onTaskRuntime(long totalDurtion, long currentDurtion, int bufferPercent) {
        Log.d(TAG, "onTaskRuntime: 播放实时进度 onTaskRuntime-->totalDurtion:" + totalDurtion
                + ",currentDurtion:" + currentDurtion);
        if (totalDurtion > -1) {
            mOldPlayProgress = currentDurtion;
            if (!isTouchSeekBar && null != mVideoTotal) {
                mVideoTotal.setText(VideoUtils.getInstance().stringForAudioTime(totalDurtion));
                mVideoCurrent.setText(VideoUtils.getInstance().stringForAudioTime(currentDurtion));
            }
            //得到当前进度
            int progress = (int) (((float) currentDurtion / totalDurtion) * 100);
            if (null != mSeekBar) {
                if (bufferPercent >= 100 && mSeekBar.getSecondaryProgress() < bufferPercent) {
                    mSeekBar.setSecondaryProgress(bufferPercent);
                }
                if (!isTouchSeekBar) {
                    mSeekBar.setProgress(progress);
                }
            }
        }
    }

    /**
     * 实时播放和缓冲进度，子线程更新
     *
     * @param totalPosition   总视频时长，单位：毫秒
     * @param currentPosition 实施播放进度，单位：毫秒
     * @param bufferPercent   缓冲进度，单位：百分比
     */
    @Override
    protected void currentPosition(long totalPosition, long currentPosition, int bufferPercent) {
        if (null != mBottomProgressBar && currentPosition > -1) {
            //播放进度，这里比例是1/1000
            int progress = (int) (((float) currentPosition / totalPosition) * 1000);
            mBottomProgressBar.setProgress(progress);
            //缓冲进度
            if (null != mBottomProgressBar && mBottomProgressBar.getSecondaryProgress() < (bufferPercent * 10)) {
                mBottomProgressBar.setSecondaryProgress(bufferPercent * 10);
            }
        }
    }

    /**
     * 缓冲进度
     *
     * @param percent 实时缓冲进度，单位：百分比
     */
    @Override
    public void onBufferingUpdate(int percent) {
        Log.d(TAG, "onBufferingUpdate: percent-->" + percent);
        if (null != mSeekBar && mSeekBar.getSecondaryProgress() < 100) {
            mSeekBar.setSecondaryProgress(percent);
        }
    }


    /**
     * 显示、隐藏 控制器 上下交互功能区
     * 手动点击，根据播放状态自动处理，手势交互处理等状态
     *
     * @param scrrenOrientation 当前的窗口方向
     * @param isInterceptIntent 为true：用户主动点击
     */
    @Override
    public void changeControllerState(int scrrenOrientation, boolean isInterceptIntent) {
        if (null == mBottomBarLayout) return;
        Log.d(TAG, "changeControllerState-->" + scrrenOrientation
                + ",isInterceptIntent:" + isInterceptIntent);
        //小窗口样式不处理任何事件
        if (scrrenOrientation == VideoConstants.SCREEN_ORIENTATION_TINY) {
            return;
        }
        //重复显示
        if (isInterceptIntent && mBottomBarLayout.getVisibility() == View.VISIBLE) {
            removeCallbacks(View.INVISIBLE);
            return;
        }
        //移除已post的事件
        DefaultVideoController.this.removeCallbacks(controllerRunnable);
        if (mBottomBarLayout.getVisibility() != View.VISIBLE) {
            mBottomBarLayout.setVisibility(View.VISIBLE);
        }
        //横屏下才显示、隐藏顶部菜单栏
        if (scrrenOrientation == VideoConstants.SCREEN_ORIENTATION_FULL) {
            if (mTopBarLayout.getVisibility() != VISIBLE) {
                mTopBarLayout.setVisibility(View.VISIBLE);
            }
        }
        if (null != mBottomProgressBar) {
            mBottomProgressBar.setVisibility(View.INVISIBLE);
        }
        DefaultVideoController.this.postDelayed(controllerRunnable, 5000);
    }


    //=========================================SEEK BAR START=============================================

    /**
     * 用户手指持续拨动中
     *
     * @param seekBar
     * @param progress 实时进度
     * @param fromUser 是否来自用户手动拖动
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            long durtion = VideoPlayerManager.getInstance().getDurtion();
            if (durtion > 0) {
                mVideoCurrent.setText(VideoUtils.getInstance().stringForAudioTime(
                        progress * durtion / 100));
            }
        }
    }

    /**
     * 手指按下
     *
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouchSeekBar = true;
        removeCallbacks(View.VISIBLE);
    }

    /**
     * 手指松开
     *
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isTouchSeekBar = false;
        //只有非暂停下才恢复控制器显示隐藏规则
        if (VideoPlayerManager.getInstance().getVideoPlayerState() != VideoConstants.MUSIC_PLAYER_PAUSE) {
            changeControllerState(mScrrenOrientation, false);
        }
        //跳转至某处
        long durtion = VideoPlayerManager.getInstance().getDurtion();
        if (durtion > 0) {
            long currentTime = seekBar.getProgress() * durtion / 100;
            VideoPlayerManager.getInstance().seekTo(currentTime);
        }
    }


    //=========================================SEEK BAR END=============================================


    /**
     * 移除定时器，保留在最后的状态
     *
     * @param visible 最后的状态
     */
    private void removeCallbacks(int visible) {
        DefaultVideoController.this.removeCallbacks(controllerRunnable);
        if (null != mBottomBarLayout) {
            mBottomBarLayout.setVisibility(visible);
        }
        //横屏下才显示、隐藏顶部菜单栏
        if (mScrrenOrientation == VideoConstants.SCREEN_ORIENTATION_FULL) {
            if (null != mTopBarLayout) {
                mTopBarLayout.setVisibility(visible);
            }
        }
        if (View.INVISIBLE == visible) {
            mBottomProgressBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 负责控制器显示、隐藏
     */
    private Runnable controllerRunnable = new Runnable() {
        @Override
        public void run() {
            if (null != mBottomBarLayout) {
                mBottomBarLayout.setVisibility(INVISIBLE);
            }
            if (null != mTopBarLayout) {
                mTopBarLayout.setVisibility(INVISIBLE);
            }
            if (null != mBottomProgressBar) {
                mBottomProgressBar.setVisibility(VISIBLE);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTopBarLayout = null;
        mBottomBarLayout = null;
        mErrorLayout = null;
        mMobileLayout = null;
        mVideoStart = null;
        mVideoTitle = null;
        mVideoCurrent = null;
        mVideoTotal = null;
        mBottomProgressBar = null;
        mProgressBar = null;
        mSeekBar = null;
        mBtnStart = null;
        isTouchSeekBar = false;
        mOldPlayProgress = 0;
    }
}
