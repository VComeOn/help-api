package com.thinkgem.jeesite.modules.baiduApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Description:
 * @Date: 2018/2/28
 * @Author: wcf
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StayPointEntityModel {
    private long start_time;
    private long end_time;
    private int duration;       //停留时长，单位：秒

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
