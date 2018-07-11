package com.jiajie.action.reportPrint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runner.Request;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiajie.action.BaseAction;
import com.jiajie.service.reportPrint.ReportPrintService;
import com.jiajie.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;
@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class ReportPrintAction extends BaseAction{
	private ReportPrintService reportPrintService;
	private List<Map<String, Object>> printList;
	private String time;
	private String username;
	
	private void  getTimeAndUserName(){
		username=getBspInfo().getUserName();
		Date date=new Date();
		SimpleDateFormat formatter =new SimpleDateFormat ("yyyy-MM-dd  HH:mm:ss    ");          
		time=formatter.format(date);
	}
	//无照片学生信息
	public String studentNotPhotoInfo(){
		getTimeAndUserName();
		String schoolid=getSchoolid();
		String xxdm=getRequest().getParameter("xxdm");
		String nj=getRequest().getParameter("nj");
		String bj=getRequest().getParameter("bj");
		printList=reportPrintService.studentNotPhotoInfo(schoolid, xxdm, nj, bj,getBspInfo());
		return "studentNotPhotoInfo";
	}
	//考试不达标换证统计
	public String graduationStuInfo(){
		getTimeAndUserName();
		String schoolid=getRequest().getParameter("schoolid");
		if(!StringUtil.isNotNullOrEmpty(schoolid))
			schoolid=getSchoolid();
		else schoolid="'"+schoolid.replaceAll(",","','")+"'";
		printList=reportPrintService.graduationStuInfo(schoolid);
		return "graduationStuInfo";
	}
	//综合素质不达标换证统计
	public String qualityStuInfo(){
		getTimeAndUserName();
		String schoolid=getRequest().getParameter("schoolid");
		if(!StringUtil.isNotNullOrEmpty(schoolid))
			schoolid=getSchoolid();
		else schoolid="'"+schoolid.replaceAll(",","','")+"'";
		printList=reportPrintService.qualityStuInfo(schoolid);
		return "qualityStuInfo";
	}
	//未达到144个学分换证统计
	public String creditsStuInfo(){
		getTimeAndUserName();
		String schoolid=getRequest().getParameter("schoolid");
		if(!StringUtil.isNotNullOrEmpty(schoolid))
			schoolid=getSchoolid();
		else schoolid="'"+schoolid.replaceAll(",","','")+"'";
		printList=reportPrintService.creditsStuInfo(schoolid);
		return "creditsStuInfo";
	}
	//肄业申请统计
	public String yiyeStuInfo(){
		getTimeAndUserName();
		String schoolid=getRequest().getParameter("schoolid");
		if(!StringUtil.isNotNullOrEmpty(schoolid))
			schoolid=getSchoolid();
		else schoolid="'"+schoolid.replaceAll(",","','")+"'";
		printList=reportPrintService.yiyeStuInfo(schoolid);
		return "yiyeStuInfo";
	}
	
	public ReportPrintService getReportPrintService() {
		return reportPrintService;
	}
	@Resource(name="reportPrintServiceImpl")
	public void setReportPrintService(ReportPrintService reportPrintService) {
		this.reportPrintService = reportPrintService;
	}
	public List<Map<String, Object>> getPrintList() {
		return printList;
	}
	public void setPrintList(List<Map<String, Object>> printList) {
		this.printList = printList;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
