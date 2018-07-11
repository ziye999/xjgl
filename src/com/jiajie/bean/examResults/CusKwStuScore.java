package com.jiajie.bean.examResults;

/**
 * CusKwExamroom entity. @author MyEclipse Persistence Tools
 */

public class CusKwStuScore implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1493237296176853349L;
	private String xscjid;
	private String lcid;
	private String kmid;
	private String examcode;
	private String xjh;
	private Float score;
	private String grade_m;
	private String writer;
	
	public String getXscjid() {
		return xscjid;
	}
	public void setXscjid(String xscjid) {
		this.xscjid = xscjid;
	}
	public String getLcid() {
		return lcid;
	}
	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	public String getKmid() {
		return kmid;
	}
	public void setKmid(String kmid) {
		this.kmid = kmid;
	}
	public String getExamcode() {
		return examcode;
	}
	public void setExamcode(String examcode) {
		this.examcode = examcode;
	}
	public String getXjh() {
		return xjh;
	}
	public void setXjh(String xjh) {
		this.xjh = xjh;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getGrade_m() {
		return grade_m;
	}
	public void setGrade_m(String grade_m) {
		this.grade_m = grade_m;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	@Override
	public String toString() {
		return "CusKwStuScore [xscjid=" + xscjid + ", lcid=" + lcid + ", kmid="
				+ kmid + ", examcode=" + examcode + ", xjh=" + xjh + ", score="
				+ score + ", grade_m=" + grade_m + ", writer=" + writer + "]";
	}
	
	

	
}