package com.jiajie.bean.exambasic;

import java.math.BigDecimal;

/**
 * AbstractCusKwAdditionee entity provides the base persistence definition of
 * the CusKwAdditionee entity. @author MyEclipse Persistence Tools
 */

public class CusKwAdditionee implements java.io.Serializable {

	// Fields

	private String scksid;
	private String lcid;
	private String xjh;
	private String xm;
	private String xxdm;
	private String nj;
	private String bh;
	private String xbm;
	private String sfzjh;
	private String reason;
	private BigDecimal kscode;
	private String flag;

	// Constructors

	/** default constructor */
	public CusKwAdditionee() {
	}

	/** full constructor */
	public CusKwAdditionee(String lcid, String xjh, String xm,
			String xxdm, String nj, String bh, String xbm, String sfzjh,
			String reason, BigDecimal kscode, String flag) {
		this.lcid = lcid;
		this.xjh = xjh;
		this.xm = xm;
		this.xxdm = xxdm;
		this.nj = nj;
		this.bh = bh;
		this.xbm = xbm;
		this.sfzjh = sfzjh;
		this.reason = reason;
		this.kscode = kscode;
		this.flag = flag;
	}

	// Property accessors

	public String getScksid() {
		return this.scksid;
	}

	public void setScksid(String scksid) {
		this.scksid = scksid;
	}

	public String getLcid() {
		return this.lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getXjh() {
		return this.xjh;
	}

	public void setXjh(String xjh) {
		this.xjh = xjh;
	}

	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	public String getNj() {
		return this.nj;
	}

	public void setNj(String nj) {
		this.nj = nj;
	}

	public String getBh() {
		return this.bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getXbm() {
		return this.xbm;
	}

	public void setXbm(String xbm) {
		this.xbm = xbm;
	}

	public String getSfzjh() {
		return this.sfzjh;
	}

	public void setSfzjh(String sfzjh) {
		this.sfzjh = sfzjh;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getKscode() {
		return this.kscode;
	}

	public void setKscode(BigDecimal kscode) {
		this.kscode = kscode;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}