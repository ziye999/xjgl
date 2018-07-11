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
public class ExamRoomArrangeAction extends BaseAction{
	private ExamRoomArrangeService examRoomArrangeService;
	private String xnxqId;
	
	public void getListPage(){		
		writerPrint(examRoomArrangeService.getExamRounds(xnxqId,getUserid(),getBspInfo()));
	}
		
	public void saveExamRoom(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String lcId=request.getParameter("lcId");
		String schoolId=request.getParameter("schoolId");
		String lfId=request.getParameter("lfId");
		String jslx=request.getParameter("jslx");
		Integer hang=StringUtil.getInteger(request.getParameter("hang"));
		Integer lie=StringUtil.getInteger(request.getParameter("lie"));
		if(examRoomArrangeService.saveExamRooms(lcId,schoolId,lfId,jslx,hang,lie)){
			writerPrint("success");
		}else{
			writerPrint("error");
		}
	}
	
	public void saveOrUpdateExamRoom() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String lcId=request.getParameter("lcid");
		String schoolId=request.getParameter("schoolid");
		String kcId=request.getParameter("kcid");
		String roomId=request.getParameter("roomid");
		Integer hang=StringUtil.getInteger(request.getParameter("hang"));
		Integer lie=StringUtil.getInteger(request.getParameter("lie"));
		writerPrint(examRoomArrangeService.saveOrUpdateExamRooms(lcId,schoolId,kcId,roomId,hang,lie));	
	}
	
	public ExamRoomArrangeService getExamRoomArrangeService() {
		return examRoomArrangeService;
	}
	@Resource(name="examRoomArrangeServiceImpl")
	public void setExamRoomArrangeService(
			ExamRoomArrangeService examRoomArrangeService) {
		this.examRoomArrangeService = examRoomArrangeService;
	}
	public String getXnxqId() {
		return xnxqId;
	}
	public void setXnxqId(String xnxqId) {
		this.xnxqId = xnxqId;
	}
	
	/**
	 * 安排每个考场的考生
	 * @throws Exception
	 */
	public void arrangeStu() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String lcid=request.getParameter("lcid");
		String kcapzgz=request.getParameter("kcapzgz");
		String wskc=request.getParameter("wskc");
		String zwpl=request.getParameter("zwpl");
		String pksx=request.getParameter("pksx");
		writerPrint(examRoomArrangeService.arrangeStu(lcid,kcapzgz,wskc,zwpl,pksx,getBspInfo()));	
	}

}
