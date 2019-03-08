/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 关于设置Entity
 * @author wcf
 * @version 2018-01-03
 */
public class SysAbout extends DataEntity<SysAbout> {
	
	private static final long serialVersionUID = 1L;
	private String aboutUs;		// 关于我们
	
	public SysAbout() {
		super();
	}

	public SysAbout(Integer id){
		super(id);
	}

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}
	
}