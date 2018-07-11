package com.jiajie.action.registration;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.service.registration.ExamStuModifyService;

@SuppressWarnings("serial")
public class ExamStuModifyNoAction extends BaseAction{
	private CusKwExamround cusKwExamround;
	private String schools_s;
	private String xmxhxj_s;
		
	@Autowired
	private ExamStuModifyService service;
	
	//学生补报获取未参考学生
	public void getListPage() {
		writerPrint(service.getExamNotStu(cusKwExamround.getLcid(),schools_s,xmxhxj_s,getBspInfo()));
	}
	
	public CusKwExamround getCusKwExamround() {
		return cusKwExamround;
	}

	public void setCusKwExamround(CusKwExamround cusKwExamround) {
		this.cusKwExamround = cusKwExamround;
	}

	public ExamStuModifyService getService() {
		return service;
	}

	public void setService(ExamStuModifyService service) {
		this.service = service;
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
	
}
