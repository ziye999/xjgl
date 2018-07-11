package com.jiajie.bean.exambasic;

/**
 * CusKwExamsubject entity. @author MyEclipse Persistence Tools
 */

public class CusKwExamsubject implements java.io.Serializable {

	private static final long serialVersionUID = 4738248390128284255L;
	// Fields
	private String kmid;
	private String xnxqId;
	private String subjectname;
	private String resultstyle;
	private String kch;
	private String lcid;
	private Short score;
	private Short timelength;
	private String xn;
	private String xq;

	// Constructors
	/** default constructor */
	public CusKwExamsubject() {
	}

	/** full constructor */
	public CusKwExamsubject(String xnxqId, String subjectname,
			String resultstyle, String kch, String lcid,
			Short score, Short timelength, String xn, String xq) {
		this.xnxqId = xnxqId;
		this.subjectname = subjectname;
		this.resultstyle = resultstyle;
		this.kch = kch;
		this.lcid = lcid;
		this.score = score;
		this.timelength = timelength;
		this.xn = xn;
		this.xq = xq;
	}

	// Property accessors
	public String getKmid() {
		return this.kmid;
	}

	public void setKmid(String kmid) {
		this.kmid = kmid;
	}

	public String getXnxqId() {
		return this.xnxqId;
	}

	public void setXnxqId(String xnxqId) {
		this.xnxqId = xnxqId;
	}

	public String getSubjectname() {
		return this.subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getResultstyle() {
		return this.resultstyle;
	}

	public void setResultstyle(String resultstyle) {
		this.resultstyle = resultstyle;
	}

	public String getKch() {
		return this.kch;
	}

	public void setKch(String kch) {
		this.kch = kch;
	}

	public String getLcid() {
		return this.lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public Short getScore() {
		return this.score;
	}

	public void setScore(Short score) {
		this.score = score;
	}

	public Short getTimelength() {
		return this.timelength;
	}

	public void setTimelength(Short timelength) {
		this.timelength = timelength;
	}

	public String getXn() {
		return this.xn;
	}

	public void setXn(String xn) {
		this.xn = xn;
	}

	public String getXq() {
		return this.xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

}