package com.jiajie.bean.resultsStatisticalCollection;

/**
 * CusKwStandard entity. @author MyEclipse Persistence Tools
 */

public class CusKwStandard implements java.io.Serializable {

	// Fields

	private String bzid;
	private String lcid;
	private String bztype;
	private String upline;
	private String downline;
	private String cdate;

	// Constructors

	/** default constructor */
	public CusKwStandard() {
	}

	/** minimal constructor */
	public CusKwStandard(String downline) {
		this.downline = downline;
	}

	/** full constructor */
	public CusKwStandard(String lcid, String bztype, String upline,
			String downline, String cdate) {
		this.lcid = lcid;
		this.bztype = bztype;
		this.upline = upline;
		this.downline = downline;
		this.cdate = cdate;
	}

	// Property accessors

	public String getBzid() {
		return this.bzid;
	}

	public void setBzid(String bzid) {
		this.bzid = bzid;
	}

	public String getLcid() {
		return this.lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getBztype() {
		return this.bztype;
	}

	public void setBztype(String bztype) {
		this.bztype = bztype;
	}

	public String getUpline() {
		return this.upline;
	}

	public void setUpline(String upline) {
		this.upline = upline;
	}

	public String getDownline() {
		return this.downline;
	}

	public void setDownline(String downline) {
		this.downline = downline;
	}

	public String getCdate() {
		return this.cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

}