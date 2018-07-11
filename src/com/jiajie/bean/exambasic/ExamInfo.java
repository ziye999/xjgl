package com.jiajie.bean.exambasic;

import java.util.Set;

import com.jiajie.util.SecUtils;

/**
 * CusKwExamround entity. @author MyEclipse Persistence Tools
 */
public class ExamInfo implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String exam_info_id;
	private String exam_type_id;
	private String scores;
	private String exam_info_bh;
	private String exam_cont;
	private Set<ExamInfoOpt> opts;
	public String getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(String exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	public String getExam_type_id() {
		return exam_type_id;
	}
	public void setExam_type_id(String exam_type_id) {
		this.exam_type_id = exam_type_id;
	}
	public String getScores() {
		return scores;
	}
	public void setScores(String scores) {
		this.scores = scores;
	}
	public String getExam_info_bh() {
		return exam_info_bh;
	}
	public void setExam_info_bh(String exam_info_bh) {
		this.exam_info_bh = exam_info_bh;
	}
	public String getExam_cont() {
		return SecUtils.decode(exam_cont);
	}
	public void setExam_cont(String exam_cont) {
		this.exam_cont = exam_cont;
	}
	public Set<ExamInfoOpt> getOpts() {
		return opts;
	}
	public void setOpts(Set<ExamInfoOpt> opts) {
		this.opts = opts;
	}
	
	
	 
}