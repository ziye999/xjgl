package com.jiajie.bean.examArrangement;

/**
 * CusKwPointinfo entity. @author MyEclipse Persistence Tools
 */

public class CusKwPointinfo implements java.io.Serializable {

	// Fields

	private String apid;
	private String lcid;
	private String xxdm;

	// Constructors

	/** default constructor */
	public CusKwPointinfo() {
	}

	/** full constructor */
	public CusKwPointinfo(String lcid, String xxdm) {
		this.lcid = lcid;
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

	public String getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

}