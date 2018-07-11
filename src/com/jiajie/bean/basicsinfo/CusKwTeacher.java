package com.jiajie.bean.basicsinfo;

import java.util.Date;

/**
 * AbstractCusKwTeacher entity provides the base persistence definition of the
 * CusKwTeacher entity. @author MyEclipse Persistence Tools
 */
public class CusKwTeacher implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private String jsid;
	private String xm;
	private String xbm;
	private String jtzz;
	private Date csrq;
	private String jg;
	private String sfzjlxm;
	private String sfzjh;
	private String lxdh;
	private String zzmmm;
	private String jkzkm;
	private String bzrM;
	private String mzm;
	private String gh;
	private String photopath;
	private String rjkmm;
	private String schoolid;

	// Constructors
	/** default constructor */
	public CusKwTeacher() {
	}

	/** full constructor */
	public CusKwTeacher(String xm, String xbm, String jtzz, Date csrq,
			String jg,String sfzjlxm, String sfzjh, String lxdh, String zzmmm, String jkzkm,
			String bzrM, String mzm, String gh, String photopath, String rjkmm,
			String schoolid) {
		this.xm = xm;
		this.xbm = xbm;
		this.jtzz = jtzz;
		this.csrq = csrq;
		this.jg = jg;
		this.sfzjlxm = sfzjlxm;
		this.sfzjh = sfzjh;
		this.lxdh = lxdh;
		this.zzmmm = zzmmm;
		this.jkzkm = jkzkm;
		this.bzrM = bzrM;
		this.mzm = mzm;
		this.gh = gh;
		this.photopath = photopath;
		this.rjkmm = rjkmm;
		this.schoolid = schoolid;
	}

	// Property accessors
	public String getJsid() {
		return this.jsid;
	}

	public void setJsid(String jsid) {
		this.jsid = jsid;
	}

	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXbm() {
		return this.xbm;
	}

	public void setXbm(String xbm) {
		this.xbm = xbm;
	}

	public String getJtzz() {
		return this.jtzz;
	}

	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}

	public Date getCsrq() {
		return this.csrq;
	}

	public void setCsrq(Date csrq) {
		this.csrq = csrq;
	}

	public String getJg() {
		return this.jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getSfzjh() {
		return this.sfzjh;
	}

	public void setSfzjh(String sfzjh) {
		this.sfzjh = sfzjh;
	}

	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getZzmmm() {
		return this.zzmmm;
	}

	public void setZzmmm(String zzmmm) {
		this.zzmmm = zzmmm;
	}

	public String getJkzkm() {
		return this.jkzkm;
	}

	public void setJkzkm(String jkzkm) {
		this.jkzkm = jkzkm;
	}

	public String getBzrM() {
		return this.bzrM;
	}

	public void setBzrM(String bzrM) {
		this.bzrM = bzrM;
	}

	public String getMzm() {
		return this.mzm;
	}

	public void setMzm(String mzm) {
		this.mzm = mzm;
	}

	public String getGh() {
		return this.gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getPhotopath() {
		return this.photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public String getRjkmm() {
		return this.rjkmm;
	}

	public void setRjkmm(String rjkmm) {
		this.rjkmm = rjkmm;
	}

	public String getSchoolid() {
		return this.schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	public String getSfzjlxm() {
		return sfzjlxm;
	}

	public void setSfzjlxm(String sfzjlxm) {
		this.sfzjlxm = sfzjlxm;
	}

}