package com.jiajie.action.cheatStu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.cheatStu.CusKwCheatstu;
import com.jiajie.service.cheatStu.LackOfTestStudentService;
import com.jiajie.util.StringUtil;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class LackOfTestStudentDAction extends BaseAction implements ModelDriven<CusKwCheatstu>{
	private static final long serialVersionUID = 1L;
	private LackOfTestStudentService lackOfTestStudentService;
	private CusKwCheatstu cusKwCheatstu=new CusKwCheatstu();
	private String lcId;
	
	public void getListPage(){
		String xm_kh_xjh=getRequest().getParameter("xm_kh_xjh");
		String schoolId=getRequest().getParameter("schoolId");
		if(StringUtil.isNotNullOrEmpty(schoolId))
			schoolId="'"+schoolId.replace(",", "','")+"'";
		writerPrint(lackOfTestStudentService.getLackOfTestStudent(lcId, xm_kh_xjh, schoolId));
	}
		
	public LackOfTestStudentService getLackOfTestStudentService() {
		return lackOfTestStudentService;
	}
	@Resource(name="lackOfTestStudentServiceImpl")
	public void setLackOfTestStudentService(
			LackOfTestStudentService lackOfTestStudentService) {
		this.lackOfTestStudentService = lackOfTestStudentService;
	}

	public String getLcId() {
		return lcId;
	}
	public void setLcId(String lcId) {
		this.lcId = lcId;
	}
	
	public CusKwCheatstu getModel() {
		return cusKwCheatstu;
	}
	
}
