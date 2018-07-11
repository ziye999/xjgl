package com.jiajie.action.examArrangement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.service.examArrangement.SubExamRoomArrangeService;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class SubExamRoomArrangeAction extends BaseAction{
	private String lcId;
	private String kmid;
	private String lfid;
	private String kdid;
	private String kcid;

	@Autowired
	private SubExamRoomArrangeService subExamRoomArrangeService;
	public SubExamRoomArrangeService getSubExamRoomArrangeService() {
		return subExamRoomArrangeService;
	}

	public void setSubExamRoomArrangeService(SubExamRoomArrangeService subExamRoomArrangeService) {
		this.subExamRoomArrangeService = subExamRoomArrangeService;
	}	

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}
	
	public String getKmid() {
		return kmid;
	}

	public void setKmid(String kmid) {
		this.kmid = kmid;
	}

	public String getKdid() {
		return kdid;
	}

	public void setKdid(String kdid) {
		this.kdid = kdid;
	}

	public String getExamRoomArrange(){
		getRequest().setAttribute("data", subExamRoomArrangeService.getExamRoomArrange(lcId,kmid,lfid,kdid));
		return "subExamRoomArrange";
	}
	
	public void getKcStus() {
		writerPrint(new Gson().toJson(subExamRoomArrangeService.getKcStus(lcId,kcid))); 
	}
	
	public void updataKcStu() {
		String arrayData = getRequest().getParameter("arrayData").toString();
		JSONArray jsonObj = JSONArray.fromObject(arrayData);
		writerPrint(subExamRoomArrangeService.updataKcStu(jsonObj));
	}
	
	public String getLfid() {
		return lfid;
	}
	public void setLfid(String lfid) {
		this.lfid = lfid;
	}
	public String getLcId() {
		return lcId;
	}
	public void setLcId(String lcId) {
		this.lcId = lcId;
	}
	
}
