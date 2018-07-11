package com.jiajie.action.examArrangement;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.examArrangement.CusKwPointinfo;
import com.jiajie.service.examArrangement.ExamSchoolArrangeService;
import com.jiajie.util.bean.MBspInfo;
@SuppressWarnings("serial")
public class ExamSchoolArrangeAction extends BaseAction{
	private CusKwPointinfo pointImfo = new CusKwPointinfo();
	private String apid;
	private String lcid;
	
	@Autowired
	private ExamSchoolArrangeService arrangeService;

	public void getListPage(){
		HttpServletRequest request =  getRequest();
		String organ_sel = request.getParameter("organ_sel")==null?"":request.getParameter("organ_sel").toString();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid").toString();
		writerPrint(arrangeService.getList(organ_sel,lcidP,getBspInfo()));
	}
			
	public void addStudentArrange() throws IOException{
		HttpServletRequest request =  getRequest();
		String ksids = request.getParameter("ksids")==null?"":request.getParameter("ksids").toString();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid").toString();
		String xxdms = request.getParameter("xxdms")==null?"":request.getParameter("xxdms").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		HttpServletResponse response = getResponse();
		response.setContentType("text/json; charset=utf-8"); //text/html;
		MsgBean mb = arrangeService.addStudentArrange(ksids,lcidP,xxdms,kdid);
		if(mb.isSuccess()){
			mb = arrangeService.updateData();
		}
		if(mb.isSuccess()){
			response.getWriter().write("{'success':'true','msg':'安排成功！'}"); 
		}else{
			response.getWriter().write("{'success':'true','msg':'安排失败！'}"); 
		}		
	}
	
	public void autoStudentArrange() throws IOException{
		HttpServletRequest request =  getRequest();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid").toString();
		String xxdms = request.getParameter("xxdms")==null?"":request.getParameter("xxdms").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		String aprses = request.getParameter("aprses")==null?"":request.getParameter("aprses").toString();
		HttpServletResponse response = getResponse();
		response.setContentType("text/json; charset=utf-8"); //text/html;
		MsgBean mb = arrangeService.autoStudentArrange(lcidP,kdid,xxdms,aprses);
		if(mb.isSuccess()){
			mb = arrangeService.updateData();
		}
		if(mb.isSuccess()){
			response.getWriter().write("{'success':'true','msg':'安排成功！'}"); 
		}else{
			response.getWriter().write("{'success':'true','msg':'安排失败！'}"); 
		}
	}
	
	public void deleteArrange() throws IOException{
		HttpServletRequest request =  getRequest();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		HttpServletResponse response = getResponse();
		response.setContentType("text/json; charset=utf-8"); //text/html;
		MsgBean mb = arrangeService.deleteArrange(lcidP,kdid);
		if(mb.isSuccess()){
			//清空考点考生统计表
			mb = arrangeService.deleteSchoolArrange();
			if(mb.isSuccess()){
				mb = arrangeService.updateData();
			}
		}
		if(mb.isSuccess()){
			response.getWriter().write("{'success':'true','msg':'清空安排成功！'}"); 
		}else{
			response.getWriter().write("{'success':'true','msg':'清空安排失败！'}"); 
		}
	}
	
	public void saveCKSchool() throws Exception{
		HttpServletRequest request =  getRequest();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid");
		String xxcode = request.getParameter("xxcode");
		MsgBean mb = arrangeService.saveCKSchool(lcidP,xxcode);
		writerPrint(mb);
	}
	public void deleteCKSchool() throws Exception{
		HttpServletRequest request =  getRequest();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid");
		String xxcode = request.getParameter("xxcode");
		MsgBean mb = arrangeService.deleteCKSchool(lcidP,xxcode);
		writerPrint(mb);		
	}
	public void getUserType() throws IOException{
		MBspInfo bsp = getBspInfo();
		HttpServletResponse response = getResponse();
		response.setContentType("text/json; charset=utf-8");
		response.getWriter().write("{'success':'true','msg':'"+bsp.getUserType()+"'}"); 
	}
	
	public CusKwPointinfo getPointImfo() {
		return pointImfo;
	}

	public void setPointImfo(CusKwPointinfo pointImfo) {
		this.pointImfo = pointImfo;
	}

	public String getApid() {
		return apid;
	}

	public void setApid(String apid) {
		this.apid = apid;
	}
	
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public ExamSchoolArrangeService getArrangeService() {
		return arrangeService;
	}

	public void setArrangeService(ExamSchoolArrangeService arrangeService) {
		this.arrangeService = arrangeService;
	}
		
}
