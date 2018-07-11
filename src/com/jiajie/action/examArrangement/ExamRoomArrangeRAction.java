package com.jiajie.action.examArrangement;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiajie.action.BaseAction;
import com.jiajie.service.examArrangement.ExamRoomArrangeService;
import com.jiajie.util.StringUtil;
import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class ExamRoomArrangeRAction extends BaseAction{
	private ExamRoomArrangeService examRoomArrangeService;
	
	public void getListPage(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String lcId=request.getParameter("lcId");
		String schoolId=request.getParameter("schoolId");
		String lfId=request.getParameter("lfId");
		String jslx=request.getParameter("jslx");
		writerPrint(examRoomArrangeService.getExamRooms(lcId,schoolId,lfId,jslx));		
	}
		
	public ExamRoomArrangeService getExamRoomArrangeService() {
		return examRoomArrangeService;
	}
	@Resource(name="examRoomArrangeServiceImpl")
	public void setExamRoomArrangeService(
			ExamRoomArrangeService examRoomArrangeService) {
		this.examRoomArrangeService = examRoomArrangeService;
	}
		
}
