package com.jiajie.action.examArrangement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.examArrangement.CusKwExamschool;
import com.jiajie.service.examArrangement.ExamSiteSchoolService;

@SuppressWarnings("serial")
public class ExamSiteSchoolAction extends BaseAction{
	private CusKwExamschool cusKwExamschool;
	private String jyj;
	private String lcid;
	private String objList;
	@Autowired
	private ExamSiteSchoolService examSiteSchoolService;

	public void getListPage(){
		writerPrint(examSiteSchoolService.getList(jyj,lcid));
	}

	public void saveExamSchool()throws IOException{		
		JSONArray json = JSONArray.fromObject(objList);
		List<Map<String, Object>> list = (List<Map<String, Object>>)JSONArray.toCollection(json,Map.class);
		HttpServletResponse response= getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk"); //text/html;
		MsgBean mm = examSiteSchoolService.saveSiteSchool(lcid,list);
		response.getWriter().print(new Gson().toJson(mm));
	}
	public void deleteExamSchool()throws IOException{		
		JSONArray json = JSONArray.fromObject(objList);
		List<Map<String, Object>> list = (List<Map<String, Object>>)JSONArray.toCollection(json,Map.class);
		HttpServletResponse response= getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk"); //text/html;
		MsgBean mm = examSiteSchoolService.deleteSiteSchool(lcid,list);
		response.getWriter().print(new Gson().toJson(mm));
	}
	
	public ExamSiteSchoolService getExamSiteSchoolService() {
		return examSiteSchoolService;
	}

	public void setExamSiteSchoolService(ExamSiteSchoolService examSiteSchoolService) {
		this.examSiteSchoolService = examSiteSchoolService;
	}

	public CusKwExamschool getCusKwExamschool() {
		return cusKwExamschool;
	}
	public void setCusKwExamschool(CusKwExamschool cusKwExamschool) {
		this.cusKwExamschool = cusKwExamschool;
	}
	public String getLcid() {
		return lcid;
	}
	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	public String getJyj() {
		return jyj;
	}
	public void setJyj(String jyj) {
		this.jyj = jyj;
	}
	public String getObjList() {
		return objList;
	}
	public void setObjList(String objList) {
		this.objList = objList;
	}
	
}
