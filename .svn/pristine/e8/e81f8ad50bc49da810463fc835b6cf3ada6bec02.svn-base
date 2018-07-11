package com.jiajie.action.exambasic;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExaminee;
import com.jiajie.service.exambasic.ExamInfomationService;
import com.jiajie.util.bean.MBspInfo;

@SuppressWarnings("serial")
public class ExamInfomationAction extends BaseAction{
	private CusKwExaminee examinee = new CusKwExaminee();
	private String ksids;
	private String sjjyj;
	private String xjjyj;
	private String xnxqId;
	private String lcid;
	private List ksList;
	private List countList;
	private String type;
		
	@Autowired
	private ExamInfomationService examInfomationService;

	public void getListPage(){
		writerPrint(examInfomationService.getList(xnxqId,lcid,getBspInfo()));
	}
	
	public void getKs(){
		String pc = getRequest().getParameter("pc")==null?"":getRequest().getParameter("pc").toString();
		String kd = getRequest().getParameter("kd")==null?"":getRequest().getParameter("kd").toString();
		String kc = getRequest().getParameter("kc")==null?"":getRequest().getParameter("kc").toString();
		String lcid = getRequest().getParameter("lcid")==null?"":getRequest().getParameter("lcid").toString();
		String ks = getRequest().getParameter("ks")==null?"":getRequest().getParameter("ks").toString();
		try {
			getResponse().getWriter().write(examInfomationService.getKs(pc,kd,kc,lcid,ks));
		}catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void getKsyz(){
		String pc = getRequest().getParameter("pc")==null?"":getRequest().getParameter("pc").toString();
		String lcid = getRequest().getParameter("lcid")==null?"":getRequest().getParameter("lcid").toString();
		String sfzh = getRequest().getParameter("sfzh")==null?"":getRequest().getParameter("sfzh").toString();
		try {
			getResponse().getWriter().write(examInfomationService.getKsyz(pc,lcid,sfzh));
		}catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void getXsListPage(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("subject", examinee);
		String schools = getRequest().getParameter("schools")==null?"":getRequest().getParameter("schools").toString();
		String kmid = getRequest().getParameter("kmid")==null?"":getRequest().getParameter("kmid").toString();
		String kdid = getRequest().getParameter("kdid")==null?"":getRequest().getParameter("kdid").toString();
		String kcid = getRequest().getParameter("kcid")==null?"":getRequest().getParameter("kcid").toString();		
		String xmkhxjh = getRequest().getParameter("xmkhxjh")==null?"":getRequest().getParameter("xmkhxjh").toString();
		writerPrint(examInfomationService.getList(examinee,schools,kmid,kdid,kcid,xmkhxjh,getBspInfo()));
	}
	
	public void loadExamInfomation() throws IOException{
		MBspInfo bsp = getBspInfo();
		Object obj = examInfomationService.getExamInfomationByKsid(ksids);
		HttpServletResponse response = getResponse();
		if(obj == null){
			response.getWriter().write("{'success':false,'msg':'没有该考生信息！'}"); 
		}else{
			response.setContentType("text/json; charset=utf-8"); //text/html;
			String gson = new Gson().toJson(obj);
			response.getWriter().write("{'success':true,'msg':"+gson+"}"); 
		}
	}
	
	public String getExamInfomationByBj() throws IOException{
		//根据班级id，获取班级考生信息
		HttpServletRequest request = getRequest();
		String xxid = request.getParameter("xxid")==null||"".equals(request.getParameter("xxid"))?getBspInfo().getOrgan().getOrganCode():request.getParameter("xxid").toString();
		String lcid = request.getParameter("lcid")==null||"".equals(request.getParameter("lcid"))?"":request.getParameter("lcid").toString();
		String xmkhxjh = getRequest().getParameter("xmkhxjh")==null?"":getRequest().getParameter("xmkhxjh").toString();
		PageBean mb = examInfomationService.getExamInfomationByBjid(xxid,lcid,xmkhxjh);
		ksList = mb.getResultList();
		return "printData";
	}
	
	public String getExamInfoCount(){
		//获取数据统计类型
		type = getRequest().getParameter("type")==null?"":getRequest().getParameter("type").toString();
		String schools = getRequest().getParameter("schools")==null?"":getRequest().getParameter("schools").toString();
		String kslc = getRequest().getParameter("kslc")==null?"":getRequest().getParameter("kslc").toString();
		PageBean pb = examInfomationService.ExamStudentCount(type,schools,kslc,getBspInfo());
		countList = pb.getResultList();
		if(type.endsWith("1")){
			return "examInfo";
		}else{
			return "count";
		}
	   
	}
	
	public CusKwExaminee getExaminee() {
		return examinee;
	}

	public void setExaminee(CusKwExaminee examinee) {
		this.examinee = examinee;
	}

	public String getKsids() {
		return ksids;
	}

	public void setKsids(String ksids) {
		this.ksids = ksids;
	}

	public ExamInfomationService getExamInfomationService() {
		return examInfomationService;
	}

	public void setExamInfomationService(ExamInfomationService examInfomationService) {
		this.examInfomationService = examInfomationService;
	}

	public String getSjjyj() {
		return sjjyj;
	}

	public void setSjjyj(String sjjyj) {
		this.sjjyj = sjjyj;
	}

	public String getXjjyj() {
		return xjjyj;
	}

	public void setXjjyj(String xjjyj) {
		this.xjjyj = xjjyj;
	}

	public String getXnxqId() {
		return xnxqId;
	}

	public void setXnxqId(String xnxqId) {
		this.xnxqId = xnxqId;
	}
	
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public List getKsList() {
		return ksList;
	}

	public void setKsList(List ksList) {
		this.ksList = ksList;
	}

	public List getCountList() {
		return countList;
	}

	public void setCountList(List countList) {
		this.countList = countList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
