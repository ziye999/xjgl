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
public class ExamTeacherArrangeAction extends BaseAction implements ModelDriven<CusKwMonitorteacher>{
	private ExamTeacherArrangeService examTeacherArrangeService;
	private CusKwMonitorteacher cusKwMonitorteacher=new CusKwMonitorteacher();
	private String lcId;
	private String organ;
	private String shizhou;
	private String quxian;
	private String xuexiao;
	private String name;
	private String gh;
	private String lrjkls;
	private String objList;
	private String ids;//删除id数组	
	private File upload;//上传的文件
		
	public void getListPage(){
		Map<String, String> map=getMap();
		writerPrint(examTeacherArrangeService.getExamTeacherInfo(map));
	}
		
	public void saveChouQuTeacher(){
		JSONArray json = JSONArray.fromObject(objList);
		List<Map<String, Object>> list = (List<Map<String, Object>>)JSONArray.toCollection(json,Map.class);
		writerPrint(examTeacherArrangeService.saveChouQuTeacher(lcId,list));
	}
	
	public void saveLuRuTeacher(){
		String teatype=cusKwMonitorteacher.getTeatype();
		if(teatype==null || "".equals(teatype)){
			cusKwMonitorteacher.setTeatype("1");
		}
		cusKwMonitorteacher.setLcid(lcId);
		writerPrint(examTeacherArrangeService.saveLuRuTeacher(cusKwMonitorteacher));
	}
	
	public void saveDaoRuTeacher() throws IOException{
	    HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = examTeacherArrangeService.saveDaoRuTeacher(upload,lcId);
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void deleteExamTeacher() throws IOException{
		ids="'"+ids.replaceAll(", ","','")+"'";
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = examTeacherArrangeService.deleteExamTeacher(ids);
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public Map<String, String> getMap(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("lcId", lcId);
		map.put("organ", organ);
		map.put("shizhou", shizhou);
		map.put("quxian", quxian);
		if(xuexiao!=null && !"".equals(xuexiao)){
			map.put("xuexiao", "'"+xuexiao+"'");
		}else{
			map.put("xuexiao", getSchoolid());
		}
		map.put("name", name);
		map.put("gh", gh);
		map.put("is_lrjkls", lrjkls);
		return map;
	}
	
	public ExamTeacherArrangeService getExamTeacherArrangeService() {
		return examTeacherArrangeService;
	}
	@Resource(name="examTeacherArrangeServiceImpl")
	public void setExamTeacherArrangeService(
			ExamTeacherArrangeService examTeacherArrangeService) {
		this.examTeacherArrangeService = examTeacherArrangeService;
	}
	public String getLcId() {
		return lcId;
	}
	public void setLcId(String lcId) {
		this.lcId = lcId;
	}
	public String getShizhou() {
		return shizhou;
	}
	public void setShizhou(String shizhou) {
		this.shizhou = shizhou;
	}
	public String getQuxian() {
		return quxian;
	}
	public void setQuxian(String quxian) {
		this.quxian = quxian;
	}
	public String getXuexiao() {
		return xuexiao;
	}
	public void setXuexiao(String xuexiao) {
		this.xuexiao = xuexiao;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getLrjkls() {
		return lrjkls;
	}
	public void setLrjkls(String lrjkls) {
		this.lrjkls = lrjkls;
	}

	public String getObjList() {
		return objList;
	}

	public void setObjList(String objList) {
		this.objList = objList;
	}
	public CusKwMonitorteacher getModel() {
		return cusKwMonitorteacher;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}			
	
	//保存抽取巡考老师安排
	public void savePatrolArrange() {
		String lcid = getRequest().getParameter("lcid");
		String kdid = getRequest().getParameter("kdid");
		String jklsids = getRequest().getParameter("jklsids");
		String xkfws = getRequest().getParameter("xkfws");
		String teanames = getRequest().getParameter("teanames");
		 
		writerPrint(examTeacherArrangeService.savePatrolArrange(lcid,kdid,jklsids,xkfws,teanames));
	}
	//保存抽取监考老师
	public void saveInvigilatorArrange() {
		String dataJson = getRequest().getParameter("dataJson");
	    JSONArray jsonArray = JSONArray.fromObject(dataJson);  
        List<Map<String, Object>> list = (List) JSONArray.toCollection(jsonArray,  
	        		Map.class);  
        writerPrint(examTeacherArrangeService.saveInvigilatorArrange(list));
	}	
	//保存调整监考老师
	public void saveAdjustInvigilator() {
		String lcid = getRequest().getParameter("lcid");
		String kdid = getRequest().getParameter("kdid");
		String kcid = getRequest().getParameter("kcid");
		String rcid = getRequest().getParameter("rcid");
		String njid = getRequest().getParameter("njid");		
		String iszjklsid = getRequest().getParameter("iszjklsid");		
		String jklsids = getRequest().getParameter("jklsids");		
		String teanames = getRequest().getParameter("teanames");		
		writerPrint(examTeacherArrangeService.saveAdjustInvigilator(lcid, kdid, kcid, rcid, njid, iszjklsid, jklsids,teanames));
	}
		
}
