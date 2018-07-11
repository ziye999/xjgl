package com.jiajie.action.basicsinfo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.basicsinfo.CusKwTeacher;
import com.jiajie.service.basicsinfo.TeacherService;

@SuppressWarnings("serial")
public class TeacherAction extends BaseAction {
	
	private CusKwTeacher teacher = new CusKwTeacher();
	
	private String jsids;
	@Autowired
	private TeacherService teacherService;
	
	public void getListPage(){
		writerPrint(teacherService.getList(teacher,getBspInfo()));
	}
	
	public void loadTeacher(){
		writerPrint(teacherService.getTeacher(teacher.getJsid()));
	}
	
	public void addTeacher(){		
		if("".equals(teacher.getJsid())) {
			teacher.setJsid(null);
		}
		teacher.setXm(teacher.getXm().trim());
		teacher.setSfzjh(teacher.getSfzjh().trim());
		teacher.setGh(teacher.getGh().trim());
		writerPrint(teacherService.saveOrUpdateTeacher(teacher));
	}
	
	public void delTeacher() {
		HttpServletRequest request = getRequest();
		writerPrint(teacherService.delTeacher(request,jsids));
	}
	
	public void getCkdw() {
		writerPrint(teacherService.getCkdw(getBspInfo()));
	}
	
	public CusKwTeacher getTeacher() {
		return teacher;
	}

	public void setTeacher(CusKwTeacher teacher) {
		this.teacher = teacher;
	}

	public String getJsids() {
		return jsids;
	}

	public void setJsids(String jsid) {
		this.jsids = jsid;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public CusKwTeacher getModel() {
		return teacher;
	}
		
}
