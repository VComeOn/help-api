package com.thinkgem.jeesite.modules.thirdApi.customer.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for appTransport complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="appTransport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billno2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cstate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="driver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evaluate1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evaluate2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evaluate3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pk" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiveqty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receivetime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sendqty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sendtime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="warehouse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "appTransport", propOrder = { "billno", "billno2", "cstate",
		"driver", "evaluate1", "evaluate2", "evaluate3", "mobile", "pk",
		"receiveqty", "receivetime", "sendqty", "sendtime", "ship", "warehouse" })
public class AppTransport {

	protected String billno;
	protected String billno2;
	protected String cstate;
	protected String driver;
	protected String evaluate1;
	protected String evaluate2;
	protected String evaluate3;
	protected String mobile;
	protected String pk;
	protected String receiveqty;
	protected String receivetime;
	protected String sendqty;
	protected String sendtime;
	protected String ship;
	protected String warehouse;

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
	 * Gets the value of the cstate property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCstate() {
		return cstate;
	}

	/**
	 * Sets the value of the cstate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCstate(String value) {
		this.cstate = value;
	}

	/**
	 * Gets the value of the driver property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * Sets the value of the driver property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDriver(String value) {
		this.driver = value;
	}

	/**
	 * Gets the value of the evaluate1 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEvaluate1() {
		return evaluate1;
	}

	/**
	 * Sets the value of the evaluate1 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEvaluate1(String value) {
		this.evaluate1 = value;
	}

	/**
	 * Gets the value of the evaluate2 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEvaluate2() {
		return evaluate2;
	}

	/**
	 * Sets the value of the evaluate2 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEvaluate2(String value) {
		this.evaluate2 = value;
	}

	/**
	 * Gets the value of the evaluate3 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEvaluate3() {
		return evaluate3;
	}

	/**
	 * Sets the value of the evaluate3 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEvaluate3(String value) {
		this.evaluate3 = value;
	}

	/**
	 * Gets the value of the mobile property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the value of the mobile property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMobile(String value) {
		this.mobile = value;
	}

	/**
	 * Gets the value of the pk property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPk() {
		return pk;
	}

	/**
	 * Sets the value of the pk property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPk(String value) {
		this.pk = value;
	}

	/**
	 * Gets the value of the receiveqty property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReceiveqty() {
		return receiveqty;
	}

	/**
	 * Sets the value of the receiveqty property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReceiveqty(String value) {
		this.receiveqty = value;
	}

	/**
	 * Gets the value of the receivetime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReceivetime() {
		return receivetime;
	}

	/**
	 * Sets the value of the receivetime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReceivetime(String value) {
		this.receivetime = value;
	}

	/**
	 * Gets the value of the sendqty property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSendqty() {
		return sendqty;
	}

	/**
	 * Sets the value of the sendqty property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSendqty(String value) {
		this.sendqty = value;
	}

	/**
	 * Gets the value of the sendtime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSendtime() {
		return sendtime;
	}

	/**
	 * Sets the value of the sendtime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSendtime(String value) {
		this.sendtime = value;
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
	 * Gets the value of the warehouse property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getWarehouse() {
		return warehouse;
	}

	/**
	 * Sets the value of the warehouse property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setWarehouse(String value) {
		this.warehouse = value;
	}

}
