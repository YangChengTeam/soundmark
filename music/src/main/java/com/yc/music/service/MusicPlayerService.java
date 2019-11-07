package com.yc.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import com.yc.music.listener.MusicPlayerCallBack;

import java.lang.reflect.Method;
import java.util.Map;

public class MusicPlayerService extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener {

    private String TAG = "Music-->log--> MusicPlayerService";
    //当前正在工作的播放器对象
    private MediaPlayer mMediaPlayer;
    private MusicPlayerCallBack musicPlayerCallBack;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: intent " + intent + " flags " + flags + " startId " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: intent " + intent);

        //设置代理，所有播放相关的操作通过代理暴露给Avtivity
        MusicPlayerBinder musicPlayerBinder = new MusicPlayerBinder(MusicPlayerService.this);

//        prepareMusic();
        return musicPlayerBinder;
    }

    /**
     * 准备MediaPlayer环境
     */
    private void prepareMusic(String path) {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            return;
        }
        if (TextUtils.isEmpty(path)) {
            new NullPointerException("音乐path为空");
            return;
        }
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); //设置音频流的类型。
            mMediaPlayer.setOnPreparedListener(this); // 准备好
            mMediaPlayer.setOnCompletionListener(this); //播放完成事件监听器
            mMediaPlayer.setOnBufferingUpdateListener(this); //缓冲变化
            mMediaPlayer.setOnErrorListener(this); //出错
            mMediaPlayer.setOnInfoListener(this); //音频信息
            mMediaPlayer.setWakeMode(MusicPlayerService.this, PowerManager.PARTIAL_WAKE_LOCK); //为MediaPlayer设置低级电源管理行为

            Class<MediaPlayer> clazz = MediaPlayer.class;
            Method method = clazz.getDeclaredMethod("setDataSource", String.class, Map.class);
//            String path = "http://mp3.9ku.com/hot/2007/11-01/91161.mp3";  //音乐文件
            Log.e(TAG, "playMusic: startPlay-->: ID: ,PATH:" + path);
            method.invoke(mMediaPlayer, path, null);

            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暴露给代理Binder的 开始播放 的方法
     */
    public void startPlayMusic(String path) {
        Log.d(TAG, "startMusic: ");
        playMusic(path);
    }

    private void playMusic(String path) {
        prepareMusic(path);

    }

    /**
     * 暴露给代理Binder的 停止播放
     */
    public void stopPlayMusic() {
        mMediaPlayer.stop();
    }

    /**
     * 暴露给代理Binder的 暂停播放
     */
    public void pausePlayMusic() {
        Log.d(TAG, "pause: ");
        mMediaPlayer.pause();
    }


    public void addOnPlayerEventListener(MusicPlayerCallBack musicPlayerCallBack) {
        this.musicPlayerCallBack = musicPlayerCallBack;
    }


    /**
     * MediaPlayer 初始化完成
     *
     * @param mediaPlayer
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared: MediaPlayer 初始化完成  ");
        musicPlayerCallBack.onMusicPlayerPrepared(mediaPlayer.getDuration());
        mMediaPlayer.start();
    }


    /**
     * 缓冲百分比
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(TAG, "onBufferingUpdate:  mp " + mp + " percent " + percent);
    }


    /**
     * 播放完成调用
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion:  mp " + mp);
    }

    /**
     * 播放失败
     */
    @Override
    public boolean onError(MediaPlayer mp, int event, int extra) {
        String content = getErrorMessage(event);
        Log.d(TAG, "onError:  mp " + mp + " extra " + extra + " event " + event + " content " + content);
        musicPlayerCallBack.onMusicPlayerError(extra, extra, content);
        return false;
    }

    /**
     * 获取音频信息
     */
    @Override
    public boolean onInfo(MediaPlayer mp, int event, int extra) {
        Log.d(TAG, "onInfo:  mp " + mp + " event " + event + " extra " + extra);
        return false;
    }

    /**
     * 与Activity解除绑定，即Activity销毁时回调的生命周期方法
     */
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: intent " + intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private String getErrorMessage(int event) {
        String content = "播放失败，未知错误";
        switch (event) {
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                content = "播放失败，未知错误";
                break;
            //收到次错误APP必须重新实例化新的MediaPlayer
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                content = "播放器内部错误";
                break;
            //流开始位置错误
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                content = "媒体流错误";
                break;
            //IO,超时错误
            case MediaPlayer.MEDIA_ERROR_IO:
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                content = "网络连接超时";
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                content = "请求播放失败：403";
                break;
            case -2147483648:
                content = "系统错误";
                break;
        }
        return content;
    }
}
