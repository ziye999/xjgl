package com.jiajie.action.examArrangement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.exambasic.CusKwExamschedule;
import com.jiajie.service.examArrangement.ExamTimeArrangeService;
import com.jiajie.util.StringUtil;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class ExamTimeArrangeAction extends BaseAction{
	private ExamTimeArrangeService examTimeArrangeService;
	private String lcId;
	private String kd;
	
	public String goExamTimeArrange(){
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setAttribute("lcId", lcId);
		request.setAttribute("kd",kd);
		return "goExamTimeArrange";
	}
		
	//考试科目
	public void getSubject() throws Exception{			
		HttpServletRequest request=ServletActionContext.getRequest();
		String result = "";
		List<Map<String, Object>> list=examTimeArrangeService.getSubjectNo(lcId, kd);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map=list.get(i);
			map.put("Id", map.get("ID"));
			map.remove("ID");
			map.put("Name", map.get("NAME"));
			map.remove("NAME");
			map.put("Duration", map.get("DURATION"));
			map.remove("DURATION");
		}
		JSONArray array=JSONArray.fromObject(list);
		result=array.toString();
		writerPrint(result);
	}
		
	//默认安排
	public void getDefault() throws Exception{
		/*String result = "["+
				"    {Id : 'e10', ResourceId: '2015-04-28', Name : 'Assignment 1', StartDate : '2011-09-01T08:00', EndDate : '2011-09-01T09:00', Duration : 1},"+
				"    {Id : 'e21', ResourceId: '2015-05-01', Name : 'Assignment 2', StartDate : '2011-09-01T10:00', EndDate : '2011-09-01T12:00', Duration : 2}"+
		"]";*/
		List<Map<String, Object>> list=examTimeArrangeService.getDefault(lcId, kd);
		JSONArray array=JSONArray.fromObject(list);
		String result=array.toString();
		writerPrint(result);
	}
		
	//考试起始时间
	public void getDuration() throws Exception{
		/*String result = "["+
				"    {Id : 'r1',  Name : '星期二  2015-03-20'},"+
				"    {Id : 'r2',  Name : '星期三  2015-03-21'},"+
				"    {Id : 'r3',  Name : '星期四  2015-03-22'},"+
				"    {Id : 'r4',  Name : '星期五  2015-03-23'}"+
		"]";*/
		List<Map<String, Object>> list=examTimeArrangeService.getDuration(lcId);
		JSONArray array=JSONArray.fromObject(list);
		String result=array.toString();
		writerPrint(result);
	}
	
	public void saveExamTimeArrange() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		String objList=request.getParameter("objList"); 
		String dels=request.getParameter("dels"); 
		JSONArray json = JSONArray.fromObject(objList);
		List<Map<String, Object>> list = (List<Map<String, Object>>)JSONArray.toCollection(json,Map.class);
		
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/text;charset=utf-8");
		PrintWriter out=response.getWriter();
		String rtn = examTimeArrangeService.saveExamTimeArrange(lcId, kd, list, dels); 
		out.print(rtn);		
	}	
		
	//创建考试时间安排模板
	public void createTemplate(){
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		writerPrint(examTimeArrangeService.createTemplate(lcid));
	}
	
	//验证考试时间安排模板是否存在
	public void checkFile(){
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		writerPrint(examTimeArrangeService.checkFile(lcid));
	}
	
	public ExamTimeArrangeService getExamTimeArrangeService() {
		return examTimeArrangeService;
	}
	
	@Resource(name="examTimeArrangeServiceImpl")
	public void setExamTimeArrangeService(
			ExamTimeArrangeService examTimeArrangeService) {
		this.examTimeArrangeService = examTimeArrangeService;
	}
	
	public String getLcId() {
		return lcId;
	}
	public void setLcId(String lcId) {
		this.lcId = lcId;
	}
	public String getKd() {
		return kd;
	}
	public void setKd(String kd) {
		this.kd = kd;
	}
	
}
