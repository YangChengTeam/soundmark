package com.yc.music.bean;

/**
 * Created by caokun on 2019/11/7 15:04.
 */

public class MusicPlayerStatusBean {

    public final int STATUS_PREPARED = 200;
    public final int STATUS_ERROR = -200;

    private int status;
    public long totalDurtion;
    public int event;
    public int extra;
    public String content;

    public MusicPlayerStatusBean(long totalDurtion) {
        this.totalDurtion = totalDurtion;
    }

    public MusicPlayerStatusBean( int event, int extra, String content) {
        this.event = event;
        this.extra = extra;
        this.content = content;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "MusicPlayerStatusBean{" +
                "status=" + status +
                ", totalDurtion=" + totalDurtion +
                ", event=" + event +
                ", extra=" + extra +
                ", content='" + content + '\'' +
                '}';
    }
}
