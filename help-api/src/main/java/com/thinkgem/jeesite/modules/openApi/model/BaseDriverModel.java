/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.openApi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 司机信息Entity
 * @author wcf
 * @version 2018-01-02
 */
public class BaseDriverModel extends DataEntity<BaseDriverModel> {

	private static final long serialVersionUID = 1L;
	@Length(min=0, max=200, message="头像长度必须介于 0 和 200 之间")
	private String headImg;		// 头像

	//@NotEmpty
	@Length(min=0, max=200, message="密码长度必须介于 0 和 200 之间")
	private String pwd;		// 密码

	@Length(min=1, max=20, message="姓名长度必须介于 1 和 20 之间")
	private String name;		// 姓名

	@NotEmpty
	@Length(min=1, max=20, message="电话号码长度必须介于 1 和 20 之间")
	private String phone;		// 电话号码

	@Length(min=1, max=50, message="所属车队公司机构代码长度必须介于 1 和 50 之间")
	private String baseCompanyCode; //所属车队公司机构代码

	private String cardId;    //身份证号
	private String cardImgA;    //身份证照片A面
	private String cardImgB;    //身份证照片B面

	private String licenceId; //驾驶证号
	private String licenceImgA; //驾驶证图片A面
	private String licenceImgB; //驾驶证图片B面

	private String certificateId; //从业资格证号
	private String certificateImgA;//从业资格证图片A面
	private String certificateImgB;//从业资格证图片B面


	public BaseDriverModel() {
		super();
	}

	public BaseDriverModel(Integer id){
		super(id);
	}


	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}


	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}


	public String getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}

	public String getCardImgA() {
		return cardImgA;
	}

	public void setCardImgA(String cardImgA) {
		this.cardImgA = cardImgA;
	}

	public String getCardImgB() {
		return cardImgB;
	}

	public void setCardImgB(String cardImgB) {
		this.cardImgB = cardImgB;
	}

	public String getLicenceImgA() {
		return licenceImgA;
	}

	public void setLicenceImgA(String licenceImgA) {
		this.licenceImgA = licenceImgA;
	}

	public String getLicenceImgB() {
		return licenceImgB;
	}

	public void setLicenceImgB(String licenceImgB) {
		this.licenceImgB = licenceImgB;
	}

	public String getCertificateImgA() {
		return certificateImgA;
	}

	public void setCertificateImgA(String certificateImgA) {
		this.certificateImgA = certificateImgA;
	}

	public String getCertificateImgB() {
		return certificateImgB;
	}

	public void setCertificateImgB(String certificateImgB) {
		this.certificateImgB = certificateImgB;
	}

	public String getBaseCompanyCode() {
		return baseCompanyCode;
	}

	public void setBaseCompanyCode(String baseCompanyCode) {
		this.baseCompanyCode = baseCompanyCode;
	}
}