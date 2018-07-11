package com.jiajie.action.examArrangement;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.examArrangement.ExamSchoolArrangeService;
@SuppressWarnings("serial")
public class ExamSchoolArrangeStAction extends BaseAction{
	private String lcid;
	
	@Autowired
	private ExamSchoolArrangeService arrangeService;

	public void getListPage(){
		HttpServletRequest request =  getRequest();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid").toString();
		String xxdm = request.getParameter("xxdm")==null?"":request.getParameter("xxdm").toString();
		writerPrint(arrangeService.getSeating(kdid,lcidP,xxdm));
	}
	
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public ExamSchoolArrangeService getArrangeService() {
		return arrangeService;
	}

	public void setArrangeService(ExamSchoolArrangeService arrangeService) {
		this.arrangeService = arrangeService;
	}
		
}
