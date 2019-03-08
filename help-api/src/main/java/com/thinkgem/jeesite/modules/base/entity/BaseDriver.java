/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 司机信息Entity
 * @author wcf
 * @version 2018-01-02
 */
public class BaseDriver extends DataEntity<BaseDriver> {
	
	private static final long serialVersionUID = 1L;
	private String headImg;		// 头像
	private String pwd;		// 密码
	private String name;		// 姓名
	private String phone;		// 电话号码
	private Integer companyId;		// 公司id
	private Integer point;		// 积分
	private Integer pointTotal;		// 总积分
	private Integer level;		// 等级
	private Integer isOnDuty;		// 是否在执勤
	private String plateNumber;		// 绑定车牌号
	private Date lastSignDate;		// 最后签到时间
	private BaseCompany company;

	private Integer hasNewMessage;	//是否有新消息

	private Integer status;      //审核状态
	private String cardId;    //身份证号
	private String cardImgA;    //身份证照片A面
	private String cardImgB;    //身份证照片B面

	private String licenceId; //驾驶证号
	private String licenceImgA; //驾驶证图片A面
	private String licenceImgB; //驾驶证图片B面

	private String certificateId; //从业资格证号
	private String certificateImgA;//从业资格证图片A面
	private String certificateImgB;//从业资格证图片B面


	public BaseDriver() {
		super();
	}

	public BaseDriver(Integer id){
		super(id);
	}

	@Length(min=0, max=200, message="头像长度必须介于 0 和 200 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	@Length(min=0, max=200, message="密码长度必须介于 0 和 200 之间")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Length(min=1, max=20, message="姓名长度必须介于 1 和 20 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=20, message="电话号码长度必须介于 1 和 20 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotNull(message="公司id不能为空")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@JsonIgnore
	public Integer getPointTotal() {
		return pointTotal;
	}

	public void setPointTotal(Integer pointTotal) {
		this.pointTotal = pointTotal;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Integer getIsOnDuty() {
		return isOnDuty;
	}

	public void setIsOnDuty(Integer isOnDuty) {
		this.isOnDuty = isOnDuty;
	}
	
	@Length(min=0, max=20, message="绑定车牌号长度必须介于 0 和 20 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Date getLastSignDate() {
		return lastSignDate;
	}

	public void setLastSignDate(Date lastSignDate) {
		this.lastSignDate = lastSignDate;
	}

	public BaseCompany getCompany() {
		return company;
	}

	public void setCompany(BaseCompany company) {
		this.company = company;
	}

	public Integer getHasNewMessage() {
		return hasNewMessage;
	}

	public void setHasNewMessage(Integer hasNewMessage) {
		this.hasNewMessage = hasNewMessage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
}