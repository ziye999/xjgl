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
public class ExamTeacherArrangeIAAction extends BaseAction implements ModelDriven<CusKwMonitorteacher>{
	private ExamTeacherArrangeService examTeacherArrangeService;
	private CusKwMonitorteacher cusKwMonitorteacher=new CusKwMonitorteacher();
		
	//监考老师安排获取考点考场监考安排情况
	public void getListPage() {
		String lcid = getRequest().getParameter("lcid");
		writerPrint(examTeacherArrangeService.getIAListPage(lcid));
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
