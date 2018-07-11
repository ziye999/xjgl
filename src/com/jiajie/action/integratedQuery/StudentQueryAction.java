package com.jiajie.action.integratedQuery; 

import org.springframework.beans.factory.annotation.Autowired;
import com.jiajie.action.BaseAction;
import com.jiajie.service.integratedQuery.StudentQueryService;
import com.jiajie.util.bean.MBspInfo;

public class StudentQueryAction extends BaseAction{  
	
	private static final long serialVersionUID = 1L;
	private StudentQueryService studentQueryService;
	private String type;
	private String zgjyj;
	private String sch;
	private String bynd;
	private String xsxm;
	private String xjh;	
	
	public String getZgjyj() {
		return zgjyj;
	}

	public void setZgjyj(String zgjyj) {
		this.zgjyj = zgjyj;
	}

	public String getSch() {
		return sch;
	}

	public void setSch(String sch) {
		this.sch = sch;
	}

	public String getBynd() {
		return bynd;
	}

	public void setBynd(String bynd) {
		this.bynd = bynd;
	}

	public String getXsxm() {
		return xsxm;
	}

	public void setXsxm(String xsxm) {
		this.xsxm = xsxm;
	}

	public String getXjh() {
		return xjh;
	}

	public void setXjh(String xjh) {
		this.xjh = xjh;
	}
   		
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Autowired
	public void setStudentQueryService(StudentQueryService studentQueryService) {
		this.studentQueryService = studentQueryService;
	}

	public void getListPage(){
		MBspInfo mp = getBspInfo();
		writerPrint(studentQueryService.graduateStudents(mp,zgjyj,bynd,xsxm,xjh));
	}
	 
}
