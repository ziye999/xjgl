package com.jiajie.action.registration;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.exambasic.CusKwExaminee;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.service.registration.ExamStuModifyService;

@SuppressWarnings("serial")
public class ExamStuModifyAction extends BaseAction{
	private CusKwExamround cusKwExamround;
	private CusKwExaminee cusKwExaminee;
	private String xnxqId;
	private String sjjyj;
	private String xjjyj;
	private String xxdm;
	private String njdm;
	private String bjdm;
	private String lcid;
	private String ksids;
	private String xjhs;
	private String schools_s;
	private String xmxhxj_s;	
	private String reason;
	
	@Autowired
	private ExamStuModifyService service;
	
	//获取考试轮次
	public void getListPage(){
		writerPrint(service.getExamRoundListPage(xnxqId,getBspInfo()));
	}
		
	//删除报考学生
	public void delExamStu() {
		writerPrint(service.delExamStu(lcid,ksids,reason,getBspInfo()));
	}
	//考生补报
	public void supExamStu() {
		writerPrint(service.supExamStu(cusKwExamround.getLcid(),xjhs,getBspInfo()));
	}
	//考生上报
	public void uploadExamStu(){
		writerPrint(service.uploadExamStu(cusKwExamround.getLcid(),getBspInfo()));
	}
	
	public CusKwExamround getCusKwExamround() {
		return cusKwExamround;
	}

	public void setCusKwExamround(CusKwExamround cusKwExamround) {
		this.cusKwExamround = cusKwExamround;
	}

	public String getXnxqId() {
		return xnxqId;
	}
	public void setXnxqId(String xnxqId) {
		this.xnxqId = xnxqId;
	}
	public ExamStuModifyService getService() {
		return service;
	}
	public void setService(ExamStuModifyService service) {
		this.service = service;
	}
	public String getXxdm() {
		return xxdm;
	}
	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}
	public String getNjdm() {
		return njdm;
	}
	public void setNjdm(String njdm) {
		this.njdm = njdm;
	}
	public String getBjdm() {
		return bjdm;
	}
	public void setBjdm(String bjdm) {
		this.bjdm = bjdm;
	}
	public String getSjjyj() {
		return sjjyj;
	}
	public void setSjjyj(String sjjyj) {
		this.sjjyj = sjjyj;
	}
	public String getXjjyj() {
		return xjjyj;
	}
	public void setXjjyj(String xjjyj) {
		this.xjjyj = xjjyj;
	}
	public String getSchools_s() {
		return schools_s;
	}
	public void setSchools_s(String schools_s) {
		this.schools_s = schools_s;
	}
	public String getXmxhxj_s() {
		return xmxhxj_s;
	}
	public void setXmxhxj_s(String xmxhxj_s) {
		this.xmxhxj_s = xmxhxj_s;
	}
	public CusKwExaminee getCusKwExaminee() {
		return cusKwExaminee;
	}
	public void setCusKwExaminee(CusKwExaminee cusKwExaminee) {
		this.cusKwExaminee = cusKwExaminee;
	}
	public String getXjhs() {
		return xjhs;
	}
	public void setXjhs(String xjhs) {
		this.xjhs = xjhs;
	}	
	public String getLcid() {
		return lcid;
	}
	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	public String getKsids() {
		return ksids;
	}
	public void setKsids(String ksids) {
		this.ksids = ksids;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}
