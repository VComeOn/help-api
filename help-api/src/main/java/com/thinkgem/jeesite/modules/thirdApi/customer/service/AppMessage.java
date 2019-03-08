package com.thinkgem.jeesite.modules.thirdApi.customer.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for appMessage complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="appMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billno2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contenttype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="driver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maketime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ownername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "appMessage", propOrder = { "billno", "billno2", "content",
		"contenttype", "driver", "maketime", "mobile", "ownername", "ship" })
public class AppMessage {

	protected String billno;
	protected String billno2;
	protected String content;
	protected String contenttype;
	protected String driver;
	protected String maketime;
	protected String mobile;
	protected String ownername;
	protected String ship;

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
	 * Gets the value of the content property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the value of the content property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setContent(String value) {
		this.content = value;
	}

	/**
	 * Gets the value of the contenttype property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getContenttype() {
		return contenttype;
	}

	/**
	 * Sets the value of the contenttype property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setContenttype(String value) {
		this.contenttype = value;
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
	 * Gets the value of the maketime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMaketime() {
		return maketime;
	}

	/**
	 * Sets the value of the maketime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMaketime(String value) {
		this.maketime = value;
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

}
