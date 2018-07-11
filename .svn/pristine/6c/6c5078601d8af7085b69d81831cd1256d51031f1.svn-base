package com.jiajie.action.gradeAmendment;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.gradeAmendment.GradeAmendmentService;
@SuppressWarnings("serial")
public class GradeAmendmentXsAction extends BaseAction{
	@Autowired
	private GradeAmendmentService amendmentService;
	
	private String lcid;//轮次
	private String xx;//参考单位
	private String nj;//年级
	private String bj;//班级
	private String sfzjh;//身份证号
	
	public void getListPage(){
		writerPrint(amendmentService.getXzxsList(lcid, xx, nj, bj, sfzjh));
	}

	public GradeAmendmentService getAmendmentService() {
		return amendmentService;
	}

	public void setAmendmentService(GradeAmendmentService amendmentService) {
		this.amendmentService = amendmentService;
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

	public String getSfzjh() {
		return sfzjh;
	}

	public void setSfzjh(String sfzjh) {
		this.sfzjh = sfzjh;
	}
	
}
