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
public class ExamTeacherArrangeTAction extends BaseAction implements ModelDriven<CusKwMonitorteacher>{
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
	
	public void getListPage(){
		Map<String, String> map=getMap();
		writerPrint(examTeacherArrangeService.getTeacherInfo(map));
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

	public CusKwMonitorteacher getModel() {
		return cusKwMonitorteacher;
	}
	
	public String getOrgan() {
		return organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}
		
}
