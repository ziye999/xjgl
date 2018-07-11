package com.jiajie.action.exambasic;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.exambasic.CusKwExamsubject;
import com.jiajie.service.exambasic.ExamSubjectService;
import com.opensymphony.xwork2.ModelDriven;
@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class ExamSubjectAction extends BaseAction implements ModelDriven<CusKwExamsubject> {
	
	private ExamSubjectService examSubjectService;
	private CusKwExamsubject cusExamsubject=new CusKwExamsubject();
	private String ids;
	private String xnxqValue;
	
	public void getListPage(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("subject", cusExamsubject);
		writerPrint(examSubjectService.getList(map,this.getBspInfo()));
	}
	public void saveOrUpdateExamSubject(){		
		writerPrint(examSubjectService.saveOrUpdateExamSubject(cusExamsubject,xnxqValue));
	}
	public void copyExamSubject(){
		String dwid=getBspInfo().getOrgan().getOrganCode();
		writerPrint(examSubjectService.copyExamSubject(cusExamsubject,xnxqValue,dwid));
	}
	public void deleteExamSubject(){
		writerPrint(examSubjectService.deleteExamSubject(ids));
	}
	public void getExamSubject(){
		writerPrint(examSubjectService.getExamSubject(cusExamsubject.getKmid()));
	}
	
	public ExamSubjectService getExamSubjectService() {
		return examSubjectService;
	}
	@Resource
	public void setExamSubjectService(ExamSubjectService examSubjectService) {
		this.examSubjectService = examSubjectService;
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getXnxqValue() {
		return xnxqValue;
	}
	public void setXnxqValue(String xnxqValue) {
		this.xnxqValue = xnxqValue;
	}
	public CusKwExamsubject getModel() {
		return cusExamsubject;
	}
	
}
