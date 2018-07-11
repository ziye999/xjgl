package com.jiajie.action.examArrangement;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.examArrangement.ExamSchoolArrangeService;
@SuppressWarnings("serial")
public class ExamSchoolArrangeXsAction extends BaseAction{
	private String lcid;
	
	@Autowired
	private ExamSchoolArrangeService arrangeService;
	
	public void getListPage(){
		HttpServletRequest request =  getRequest();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid").toString();
		String jyj = request.getParameter("jyj")==null?"":request.getParameter("jyj").toString();
		String xxdm = request.getParameter("xxdm")==null?"":request.getParameter("xxdm").toString();
		String nj = request.getParameter("nj")==null?"":request.getParameter("nj").toString();
		String bj = request.getParameter("bj")==null?"":request.getParameter("bj").toString();
		writerPrint(arrangeService.getStudent(lcidP,jyj,xxdm,nj,bj));
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
