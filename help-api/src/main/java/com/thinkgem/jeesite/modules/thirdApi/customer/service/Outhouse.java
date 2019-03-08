package com.thinkgem.jeesite.modules.thirdApi.customer.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for outhouse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="outhouse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billno2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billno3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="company" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="goodsname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gross" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outhousedate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownername2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planquantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qsdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qsfile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qsmaker" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qsqty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="selfweight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sjdriver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sjtelephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="thfs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outhouse", propOrder = { "billno", "billno2", "billno3",
		"company", "dr", "goodsname", "gross", "location", "outhousedate",
		"ownername", "ownername2", "planquantity", "qsdate", "qsfile",
		"qsmaker", "qsqty", "quantity", "selfweight", "ship", "sjdriver",
		"sjtelephone", "thfs" })
public class Outhouse {

	protected String billno;
	protected String billno2;
	protected String billno3;
	protected String company;
	protected String dr;
	protected String goodsname;
	protected String gross;
	protected String location;
	protected String outhousedate;
	protected String ownername;
	protected String ownername2;
	protected String planquantity;
	protected String qsdate;
	protected String qsfile;
	protected String qsmaker;
	protected String qsqty;
	protected String quantity;
	protected String selfweight;
	protected String ship;
	protected String sjdriver;
	protected String sjtelephone;
	protected String thfs;

	/**
	 * Gets the value of the billno property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillno() {
		return billno;
	}

	/**
	 * Sets the value of the billno property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillno(String value) {
		this.billno = value;
	}

	/**
	 * Gets the value of the billno2 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillno2() {
		return billno2;
	}

	/**
	 * Sets the value of the billno2 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillno2(String value) {
		this.billno2 = value;
	}

	/**
	 * Gets the value of the billno3 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillno3() {
		return billno3;
	}

	/**
	 * Sets the value of the billno3 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillno3(String value) {
		this.billno3 = value;
	}

	/**
	 * Gets the value of the company property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Sets the value of the company property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCompany(String value) {
		this.company = value;
	}

	/**
	 * Gets the value of the dr property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDr() {
		return dr;
	}

	/**
	 * Sets the value of the dr property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDr(String value) {
		this.dr = value;
	}

	/**
	 * Gets the value of the goodsname property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getGoodsname() {
		return goodsname;
	}

	/**
	 * Sets the value of the goodsname property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setGoodsname(String value) {
		this.goodsname = value;
	}

	/**
	 * Gets the value of the gross property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getGross() {
		return gross;
	}

	/**
	 * Sets the value of the gross property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setGross(String value) {
		this.gross = value;
	}

	/**
	 * Gets the value of the location property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the value of the location property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLocation(String value) {
		this.location = value;
	}

	/**
	 * Gets the value of the outhousedate property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOuthousedate() {
		return outhousedate;
	}

	/**
	 * Sets the value of the outhousedate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOuthousedate(String value) {
		this.outhousedate = value;
	}

	/**
	 * Gets the value of the ownername property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOwnername() {
		return ownername;
	}

	/**
	 * Sets the value of the ownername property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOwnername(String value) {
		this.ownername = value;
	}

	/**
	 * Gets the value of the ownername2 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOwnername2() {
		return ownername2;
	}

	/**
	 * Sets the value of the ownername2 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOwnername2(String value) {
		this.ownername2 = value;
	}

	/**
	 * Gets the value of the planquantity property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPlanquantity() {
		return planquantity;
	}

	/**
	 * Sets the value of the planquantity property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPlanquantity(String value) {
		this.planquantity = value;
	}

	/**
	 * Gets the value of the qsdate property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQsdate() {
		return qsdate;
	}

	/**
	 * Sets the value of the qsdate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQsdate(String value) {
		this.qsdate = value;
	}

	/**
	 * Gets the value of the qsfile property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQsfile() {
		return qsfile;
	}

	/**
	 * Sets the value of the qsfile property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQsfile(String value) {
		this.qsfile = value;
	}

	/**
	 * Gets the value of the qsmaker property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQsmaker() {
		return qsmaker;
	}

	/**
	 * Sets the value of the qsmaker property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQsmaker(String value) {
		this.qsmaker = value;
	}

	/**
	 * Gets the value of the qsqty property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQsqty() {
		return qsqty;
	}

	/**
	 * Sets the value of the qsqty property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQsqty(String value) {
		this.qsqty = value;
	}

	/**
	 * Gets the value of the quantity property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * Sets the value of the quantity property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQuantity(String value) {
		this.quantity = value;
	}

	/**
	 * Gets the value of the selfweight property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSelfweight() {
		return selfweight;
	}

	/**
	 * Sets the value of the selfweight property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSelfweight(String value) {
		this.selfweight = value;
	}

	/**
	 * Gets the value of the ship property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getShip() {
		return ship;
	}

	/**
	 * Sets the value of the ship property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setShip(String value) {
		this.ship = value;
	}

	/**
	 * Gets the value of the sjdriver property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSjdriver() {
		return sjdriver;
	}

	/**
	 * Sets the value of the sjdriver property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSjdriver(String value) {
		this.sjdriver = value;
	}

	/**
	 * Gets the value of the sjtelephone property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSjtelephone() {
		return sjtelephone;
	}

	/**
	 * Sets the value of the sjtelephone property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSjtelephone(String value) {
		this.sjtelephone = value;
	}

	/**
	 * Gets the value of the thfs property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getThfs() {
		return thfs;
	}

	/**
	 * Sets the value of the thfs property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setThfs(String value) {
		this.thfs = value;
	}

}
