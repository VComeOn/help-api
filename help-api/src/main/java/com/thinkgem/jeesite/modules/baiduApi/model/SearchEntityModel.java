package com.thinkgem.jeesite.modules.baiduApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Description:
 * @Date: 2018/2/26
 * @Author: wcf
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchEntityModel {
    private String entity_name;
    private String entity_desc;
    private SearchEntityPointModel latest_location;

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public String getEntity_desc() {
        return entity_desc;
    }

    public void setEntity_desc(String entity_desc) {
        this.entity_desc = entity_desc;
    }

    public SearchEntityPointModel getLatest_location() {
        return latest_location;
    }

    public void setLatest_location(SearchEntityPointModel latest_location) {
        this.latest_location = latest_location;
    }
}
