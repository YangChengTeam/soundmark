package com.yc.music.service;

import android.os.Binder;

import com.yc.music.listener.MusicPlayerCallBack;

/**
 * Created by caokun on 2019/8/27 15:18.
 */

public class MusicPlayerBinder extends Binder {
    private MusicPlayerService mService;

    public MusicPlayerBinder(MusicPlayerService musicPlayerService) {
        this.mService = musicPlayerService;
    }

    public void startPlayMusic(String path) {
        mService.startPlayMusic(path);
    }

    public void stopPlayMusic() {
        mService.stopPlayMusic();
    }

    public void pausePlayMusic() {
        mService.pausePlayMusic();
    }

    public void addOnPlayerEventListener(MusicPlayerCallBack musicPlayerCallBack) {
        mService.addOnPlayerEventListener(musicPlayerCallBack);
    }
}
