package com.jiajie.bean.examArrangement;

/**
 * CusKwPointstu entity. @author MyEclipse Persistence Tools
 */

public class CusKwPointstu implements java.io.Serializable {

	// Fields

	private String apid;
	private String lcid;
	private String kdid;
	private String xsid;
	private String xxdm;

	// Constructors

	/** default constructor */
	public CusKwPointstu() {
	}

	/** full constructor */
	public CusKwPointstu(String lcid, String kdid, String xsid, String xxdm) {
		this.lcid = lcid;
		this.kdid = kdid;
		this.xsid = xsid;
		this.xxdm = xxdm;
	}

	// Property accessors

	public String getApid() {
		return this.apid;
	}

	public void setApid(String apid) {
		this.apid = apid;
	}

	public String getLcid() {
		return this.lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getKdid() {
		return this.kdid;
	}

	public void setKdid(String kdid) {
		this.kdid = kdid;
	}

	public String getXsid() {
		return this.xsid;
	}

	public void setXsid(String xsid) {
		this.xsid = xsid;
	}

	public String getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

}