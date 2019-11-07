package com.yc.music.listener;

/**
 * Created by caokun on 2019/11/7 13:44.
 */

public interface MusicPlayerCallBack {

    /**
     * 播放器准备好了
     *
     * @param totalDurtion 总时长
     */
    void onMusicPlayerPrepared(long totalDurtion);

    void onMusicPlayerError(int event, int extra, String content);

}
