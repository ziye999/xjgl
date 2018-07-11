package com.jiajie.bean.exambasic;

/**
 * CusKwExamround entity. @author MyEclipse Persistence Tools
 */
public class ExamInfoOpt implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String exam_opt_id;
	private String exam_bh;
	private String exam_info_id;
	private String exam_opt_des;
	public String getExam_opt_id() {
		return exam_opt_id;
	}
	public void setExam_opt_id(String exam_opt_id) {
		this.exam_opt_id = exam_opt_id;
	}
	public String getExam_bh() {
		return exam_bh;
	}
	public void setExam_bh(String exam_bh) {
		this.exam_bh = exam_bh;
	}
	public String getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(String exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	public String getExam_opt_des() {
		return exam_opt_des;
	}
	public void setExam_opt_des(String exam_opt_des) {
		this.exam_opt_des = exam_opt_des;
	}   
	
}