package com.jiajie.action.registration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.PageBean;
import com.jiajie.service.registration.ExamRegistrationService;

@SuppressWarnings("serial")
public class ExamRegistrationXsAction extends BaseAction{
	private String lcid;
	
	@Autowired
	private ExamRegistrationService registrationService;
	
	public void getListPage(){
		HttpServletRequest request = getRequest();
		String school = request.getParameter("school")==null?"":request.getParameter("school").toString();
		String khao = request.getParameter("khao")==null?"":request.getParameter("khao").toString();
		String sfzjh = request.getParameter("sfzjh")==null?"":request.getParameter("sfzjh").toString();	
		if(school!=null || khao!=null || sfzjh!=null){
			PageBean pb  = registrationService.getCqks(school,lcid,getBspInfo(),khao,sfzjh);
			writerPrint(pb);
		}else{
			writerPrint(registrationService.getXsList(lcid));
		}
	}
		
	public ExamRegistrationService getRegistrationService() {
		return registrationService;
	}
	public void setRegistrationService(ExamRegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
		
}
