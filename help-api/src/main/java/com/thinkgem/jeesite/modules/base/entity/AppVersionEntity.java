package com.thinkgem.jeesite.modules.base.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.io.Serializable;

/**
 * app版本
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-12 15:27:08
 */
public class AppVersionEntity extends DataEntity<AppVersionEntity> {


	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 更新内容
	 */
	private String content;

	/**
	 * 设置：版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取：版本号
	 */
	public Integer getVersion() {
		return version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



}
