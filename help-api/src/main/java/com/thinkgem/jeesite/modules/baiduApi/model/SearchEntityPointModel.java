package com.thinkgem.jeesite.modules.baiduApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Description:
 * @Date: 2018/2/26
 * @Author: wcf
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchEntityPointModel {
    private double latitude;
    private double longitude;
    private double radius;
    private long loc_time;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public long getLoc_time() {
        return loc_time;
    }

    public void setLoc_time(long loc_time) {
        this.loc_time = loc_time;
    }
}
