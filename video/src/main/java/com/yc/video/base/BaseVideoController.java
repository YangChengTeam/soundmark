package com.yc.video.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.video.constants.VideoConstants;

/**
 * Created by caokun on 2019/11/8 10:24.
 * <p>
 * 视频播放器的逻辑和UI
 */

public abstract class BaseVideoController extends FrameLayout {

    protected String TAG = "VideoPlay_BaseVideoController";
    //屏幕方向,默认常规竖屏方向
    protected int mScrrenOrientation = VideoConstants.SCREEN_ORIENTATION_PORTRAIT;

    public BaseVideoController(@NonNull Context context) {
        this(context, null);
    }

    public BaseVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseVideoController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 标准的必须实现关心的方法
     */
    //准备播放中
    public abstract void readyPlaying();

    //开始缓冲中
    public abstract void startBuffer();

    //缓冲结束
    public abstract void endBuffer();

    //开始播放中
    public abstract void play();

    //已暂停播放
    public abstract void pause();

    //已回复播放
    public abstract void repeatPlay();

    //移动网络状态下工作
    public abstract void mobileWorkTips();

    /**
     * 播放失败
     *
     * @param errorCode    错误码
     * @param errorMessage 错误日志
     */
    public abstract void error(int errorCode, String errorMessage);

    //播放器被重置
    public abstract void reset();

    /**
     * 非必须的，根据自身业务逻辑实现
     */
    /**
     * 设置视频标题内容
     * @param videoTitle 视频标题
     */
    protected void setTitle(String videoTitle){}

    /**
     * 开始跳转播放
     */
    protected void startSeek(){}

    /**
     * 悬浮窗功能开关,只针对入口
     * @param enable true:悬浮窗功能入口可见，false:不可见
     */
    protected void setGlobaEnable(boolean enable){};

    /**
     * 播放地址为空
     */
    protected void pathInvalid(){}

    /**
     * 切换为竖屏方向
     */
    protected void startHorizontal(){}

    /**
     * 切换为小窗口播放
     */
    protected void startTiny(){}

    /**
     * 切换为悬浮窗
     */
    protected void startGlobalWindow(){}


    /**
     * 视频总长度、 播放进度、缓冲进度
     *
     * @param totalDurtion   视频总长度 单位：毫秒，暂停下为-1
     * @param currentDurtion 播放进度 单位：毫秒，暂停下为-1
     * @param bufferPercent  缓冲进度，单位：百分比
     */
    protected void onTaskRuntime(long totalDurtion, long currentDurtion, int bufferPercent) {
    }

    /**
     * 播放器内部实时播放进度，只会在子线程被调用,请注意
     *
     * @param totalPosition   总视频时长，单位：毫秒
     * @param currentPosition 实施播放进度，单位：毫秒
     * @param bufferPercent   缓冲进度，单位：百分比
     */
    protected void currentPosition(long totalPosition, long currentPosition, int bufferPercent) {
    }

    /**
     * 缓冲百分比
     *
     * @param percent 实时缓冲进度，单位：百分比
     */
    protected void onBufferingUpdate(int percent) {
    }

    /**
     * 播放器空白位置单击事件，关注此方法实现控制器的现实和隐藏
     *
     * @param scrrenOrientation 当前的窗口方向
     * @param isInterceptIntent 为true：用户主动点击
     */
    protected void changeControllerState(int scrrenOrientation, boolean isInterceptIntent) {
    }

    /**
     * 更新屏幕方向
     *
     * @param scrrenOrientation 1：竖屏，>1：横屏
     */
    public void setScrrenOrientation(int scrrenOrientation) {
        this.mScrrenOrientation = scrrenOrientation;
    }


    protected OnFuctionListener mOnFuctionListener;

    public void setOnFuctionListener(OnFuctionListener onFuctionListener) {
        mOnFuctionListener = onFuctionListener;
    }

    protected void onDestroy() {
        mOnFuctionListener = null;
    }

    //子类控制器实现扩展功能
    public abstract static class OnFuctionListener {
        /**
         * 开启全屏
         *
         * @param videoController 继承自BaseVideoController的自定义控制器
         */
        public void onStartFullScreen(BaseVideoController videoController) {
        }

        /**
         * 开启迷你窗口
         *
         * @param miniWindowController 继承自BaseVideoController的自定义控制器
         */
        public void onStartMiniWindow(BaseVideoController miniWindowController) {
        }

        /**
         * 开启全局悬浮窗
         *
         * @param windowController      继承自BaseVideoController的自定义控制器
         * @param defaultCreatCloseIcon 是否创建一个默认的关闭按钮，位于悬浮窗右上角，若允许创建，
         *                              则播放器内部消化关闭时间
         */
        public void onStartGlobalWindown(BaseVideoController windowController, boolean defaultCreatCloseIcon) {
        }

        //关闭迷你窗口
        public void onQuiteMiniWindow() {
        }

        //打开播放器界面
        public void onStartActivity() {
        }

        //弹射返回
        public void onBackPressed() {
        }
    }
}
