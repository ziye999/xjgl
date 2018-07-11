package com.jiajie.bean.exambasic;

/**
 * CusKwExamround entity. @author MyEclipse Persistence Tools
 */
public class CusKwPaperDetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String xh;
	private String paper_id;
	private String xs_jbxx_id;
	private String exam_info_id;
	private String score;
	public String getXh() {
		return xh;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getPaper_id() {
		return paper_id;
	}
	public void setPaper_id(String paper_id) {
		this.paper_id = paper_id;
	}
	public String getXs_jbxx_id() {
		return xs_jbxx_id;
	}
	public void setXs_jbxx_id(String xs_jbxx_id) {
		this.xs_jbxx_id = xs_jbxx_id;
	}
	public String getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(String exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	 
	public Integer getZt() {
		return zt;
	}
	public void setZt(Integer zt) {
		this.zt = zt;
	}
	public Integer getPx() {
		return px;
	}
	public void setPx(Integer px) {
		this.px = px;
	}
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	public String getCjsj() {
		return cjsj;
	}
	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private Integer zt;
	private Integer px;
	private String cjr;
	private String cjsj;

}