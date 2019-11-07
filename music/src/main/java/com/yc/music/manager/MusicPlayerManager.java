package com.yc.music.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yc.music.listener.MusicInitializeCallBack;
import com.yc.music.listener.MusicPlayerCallBack;
import com.yc.music.service.MusicPlayerBinder;
import com.yc.music.service.MusicPlayerService;


/**
 * Created by caokun on 2019/11/7 11:11.
 */

public class MusicPlayerManager {

    private static MusicPlayerManager mInstance = null;
    private MusicInitializeCallBack mCallBack;
    private MusicPlayerBinder mBinder;
    private MusicPlayerServiceConnection mConnection;

    public static MusicPlayerManager getInstance() {
        if (null == mInstance) {
            synchronized (MusicPlayerManager.class) {
                if (null == mInstance) {
                    mInstance = new MusicPlayerManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * Activity初始化音乐服务组件，Activity中初始化后调用
     *
     * @param context  Activity上下文
     * @param callBack 初始化成功回调，如果不为空，将尝试还原悬浮窗口
     */
    public void bindService(Context context, MusicInitializeCallBack callBack) {
        if (null != context && context instanceof Activity) {
            this.mCallBack = callBack;
            mConnection = new MusicPlayerServiceConnection();
            Intent intent = new Intent(context, MusicPlayerService.class);

            context.startService(intent);
            context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        } else {
            new IllegalStateException("Must pass in Activity type Context!");
        }
    }

    /**
     * 解绑音乐服务组件
     *
     * @param context Activity上下文
     * @param destroy 是否同步注销内部服务组件，true:注销服务结束播放
     */
    private void unBindService(Context context, boolean destroy) {
        if (null != context && context instanceof Activity) {
            if (null != mConnection) {
                context.unbindService(mConnection);
            }
            if (destroy) {
                context.stopService(new Intent(context, MusicPlayerService.class));
            }
        } else {
            new IllegalStateException("Must pass in Activity type Context!");
        }
    }

    public void startPlayMusic(String url) {
        if (null != mBinder && mBinder.pingBinder()) {
            mBinder.startPlayMusic(url);
        }
    }

    public void addOnPlayerEventListener(MusicPlayerCallBack musicPlayerCallBack) {
        mBinder.addOnPlayerEventListener(musicPlayerCallBack);
    }

    /**
     * MusicPlayer Service Connection
     */
    private class MusicPlayerServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (null != service) {
                if (service instanceof MusicPlayerBinder) {
                    mBinder = (MusicPlayerBinder) service;
                    if (null != mCallBack) {
                        mCallBack.onSuccess();
                    }
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

}
