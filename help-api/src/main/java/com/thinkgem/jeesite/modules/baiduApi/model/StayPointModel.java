package com.thinkgem.jeesite.modules.baiduApi.model;

import java.util.List;

/**
 * @Description:
 * @Date: 2018/2/28
 * @Author: wcf
 */
public class StayPointModel extends BaseResponse{
    private int staypoint_num;
    private List<StayPointEntityModel> stay_points;

    public int getStaypoint_num() {
        return staypoint_num;
    }

    public void setStaypoint_num(int staypoint_num) {
        this.staypoint_num = staypoint_num;
    }

    public List<StayPointEntityModel> getStay_points() {
        return stay_points;
    }

    public void setStay_points(List<StayPointEntityModel> stay_points) {
        this.stay_points = stay_points;
    }
}
