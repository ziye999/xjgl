package com.jiajie.action.examArrangement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.exambasic.CusKwMonitorteacher;
import com.jiajie.service.examArrangement.ExamTeacherArrangeService;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class ExamTeacherArrangeIASAction extends BaseAction implements ModelDriven<CusKwMonitorteacher>{
	private ExamTeacherArrangeService examTeacherArrangeService;
	private CusKwMonitorteacher cusKwMonitorteacher=new CusKwMonitorteacher();
		
	//获取监考老师数据
	public void getListPage() {
		String lcid = getRequest().getParameter("lcid");
		String kdid = getRequest().getParameter("kdid");
		String njid = getRequest().getParameter("njid");
		String kmid = getRequest().getParameter("kmid");
		String kcid = getRequest().getParameter("kcid");
		writerPrint(examTeacherArrangeService.getIASListPage(lcid,kdid,njid,kmid,kcid).getResultList());
	}
		
	public ExamTeacherArrangeService getExamTeacherArrangeService() {
		return examTeacherArrangeService;
	}
	@Resource(name="examTeacherArrangeServiceImpl")
	public void setExamTeacherArrangeService(
			ExamTeacherArrangeService examTeacherArrangeService) {
		this.examTeacherArrangeService = examTeacherArrangeService;
	}
	
	public CusKwMonitorteacher getModel() {
		return cusKwMonitorteacher;
	}
			
}
