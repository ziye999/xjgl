package com.jiajie.bean.exambasic;

import java.util.Date;

/**
 * CusKwExamround entity. @author MyEclipse Persistence Tools
 */

public class CusKwExamround implements java.io.Serializable {

	// Fields
	private String lcid;
	private String xnxqId;
	private String xqm;
	private String examname;
	private String startweek;
	private String startday;
	private Date startdate;
	private String endweek;
	private String endday;
	private Date enddate;
	private String examtypem;
	private String dwid;
	private String dwtype;
	private String xn;

	// Constructors

	/** default constructor */
	public CusKwExamround() {
	}

	/** full constructor */
	public CusKwExamround(String xnxqId, String xqm, String examname,
			String startweek, String startday, Date startdate, String endweek,
			String endday, Date enddate, String examtypem, String dwid,
			String dwtype, String xn) {
		this.xnxqId = xnxqId;
		this.xqm = xqm;
		this.examname = examname;
		this.startweek = startweek;
		this.startday = startday;
		this.startdate = startdate;
		this.endweek = endweek;
		this.endday = endday;
		this.enddate = enddate;
		this.examtypem = examtypem;
		this.dwid = dwid;
		this.dwtype = dwtype;
		this.xn = xn;
	}

	// Property accessors

	public String getLcid() {
		return this.lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getXnxqId() {
		return this.xnxqId;
	}

	public void setXnxqId(String xnxqId) {
		this.xnxqId = xnxqId;
	}

	public String getXqm() {
		return this.xqm;
	}

	public void setXqm(String xqm) {
		this.xqm = xqm;
	}

	public String getExamname() {
		return this.examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getStartweek() {
		return this.startweek;
	}

	public void setStartweek(String startweek) {
		this.startweek = startweek;
	}

	public String getStartday() {
		return this.startday;
	}

	public void setStartday(String startday) {
		this.startday = startday;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getEndweek() {
		return this.endweek;
	}

	public void setEndweek(String endweek) {
		this.endweek = endweek;
	}

	public String getEndday() {
		return this.endday;
	}

	public void setEndday(String endday) {
		this.endday = endday;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getExamtypem() {
		return this.examtypem;
	}

	public void setExamtypem(String examtypem) {
		this.examtypem = examtypem;
	}

	public String getDwid() {
		return this.dwid;
	}

	public void setDwid(String dwid) {
		this.dwid = dwid;
	}

	public String getDwtype() {
		return this.dwtype;
	}

	public void setDwtype(String dwtype) {
		this.dwtype = dwtype;
	}

	public String getXn() {
		return this.xn;
	}

	public void setXn(String xn) {
		this.xn = xn;
	}

}