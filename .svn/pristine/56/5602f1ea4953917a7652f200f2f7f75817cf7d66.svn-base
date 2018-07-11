package com.jiajie.action.registration;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.registration.ExamReviewService;

@SuppressWarnings("serial")
public class ExamReviewKsAction extends BaseAction{
	
	@Autowired
	private ExamReviewService reviewService;
	
	private String lcid;
	private String xx;
	private String nj;
	private String bj;
	private String xjh;
	
	//查询考生名单列表
	public void getListPage(){
		writerPrint(reviewService.getKsList(lcid, xx, nj, bj, xjh));
	}
			
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getXx() {
		return xx;
	}

	public void setXx(String xx) {
		this.xx = xx;
	}

	public String getNj() {
		return nj;
	}

	public void setNj(String nj) {
		this.nj = nj;
	}

	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}	

	public String getXjh() {
		return xjh;
	}

	public void setXjh(String xjh) {
		this.xjh = xjh;
	}
	
}
