package com.thinkgem.jeesite.modules.baiduApi.model;

import java.util.List;

/**
 * @Description:
 * @Date: 2018/2/26
 * @Author: wcf
 */
public class SearchModel extends BaseResponse{
    private int total;
    private int size;
    private List<SearchEntityModel> entities;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<SearchEntityModel> getEntities() {
        return entities;
    }

    public void setEntities(List<SearchEntityModel> entities) {
        this.entities = entities;
    }
}
