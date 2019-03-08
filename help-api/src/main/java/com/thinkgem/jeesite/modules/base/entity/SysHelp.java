/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 帮助中心Entity
 * @author wcf
 * @version 2018-01-02
 */
public class SysHelp extends DataEntity<SysHelp> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 问题名称
	private String content;		// 问题内容
	private Integer sortNo;		// 排序号
	
	public SysHelp() {
		super();
	}

	public SysHelp(Integer id){
		super(id);
	}

	@Length(min=1, max=50, message="问题名称长度必须介于 1 和 50 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	
}