/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 积分商城Entity
 * @author wcf
 * @version 2018-01-07
 */
public class PointMallVo extends DataEntity<PointMallVo> {

	private static final long serialVersionUID = 1L;
	private String title;		// 商品名称
	private String imgUrl;		// 商品图片
	private String introduce;		// 商品说明
	private Integer pointNum;		// 兑换所需积分

	public PointMallVo() {
		super();
	}

	public PointMallVo(Integer id){
		super(id);
	}

	@Length(min=1, max=50, message="商品名称长度必须介于 1 和 50 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=1, max=200, message="商品图片长度必须介于 1 和 200 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@NotNull(message="兑换所需积分不能为空")
	public Integer getPointNum() {
		return pointNum;
	}

	public void setPointNum(Integer pointNum) {
		this.pointNum = pointNum;
	}
	
}