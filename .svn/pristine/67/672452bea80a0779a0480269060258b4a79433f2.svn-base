package com.jiajie.action.cheatStu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.cheatStu.CusKwCheatstu;
import com.jiajie.service.cheatStu.LackOfTestStudentService;
import com.jiajie.util.StringUtil;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class LackOfTestStudentAction extends BaseAction implements ModelDriven<CusKwCheatstu>{
	private static final long serialVersionUID = 1L;
	private LackOfTestStudentService lackOfTestStudentService;
	private CusKwCheatstu cusKwCheatstu=new CusKwCheatstu();
	private String lcId;
	private String ids;
	private File upload;
	InputStream excelStream;  
	
	public void getListPage(){
		String xn=getRequest().getParameter("xn");
		String xq=getRequest().getParameter("xq");		
		writerPrint(lackOfTestStudentService.getExamRounds(xn, xq,getUserid(),getBspInfo()));
	}
	
	public void deleteLackOfTestStudents(){
		ids="'"+ids.replaceAll(", ", "','")+"'";
		writerPrint(lackOfTestStudentService.deleteLackOfTestStudents(ids));
	}
	public void importLackOfTestStudent() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = lackOfTestStudentService.importLackOfTestStudent(lcId,upload,getUserid());
		response.getWriter().print(new Gson().toJson(mb));
	}
	public void saveLackOfTestStudent(){
		Map<String, String> map=new HashMap<String, String>();		
		map.put("lcId", StringUtil.isNotNullOrEmpty(lcId.split(",")[0].trim())?lcId.split(",")[0].trim():lcId.split(",")[1].trim());
		map.put("qkId", getRequest().getParameter("qkId"));
		map.put("kh", getRequest().getParameter("kh"));
		map.put("xjh", getRequest().getParameter("xjh"));
		map.put("xm", getRequest().getParameter("xm"));
		map.put("xb", getRequest().getParameter("xb"));
		map.put("km", getRequest().getParameter("km"));
		map.put("qkyy", getRequest().getParameter("qkyy"));
		writerPrint(lackOfTestStudentService.saveLackOfTestStudent(map));
	}
	
	public void submitQk(){
		writerPrint(lackOfTestStudentService.submitQk(lcId,getUserid()));
	}
     
	public String outExcel(){        
	    excelStream = lackOfTestStudentService.getExcelInputStream(lcId);  
	    return "outExcel";  
	}

	public LackOfTestStudentService getLackOfTestStudentService() {
		return lackOfTestStudentService;
	}
	@Resource(name="lackOfTestStudentServiceImpl")
	public void setLackOfTestStudentService(
			LackOfTestStudentService lackOfTestStudentService) {
		this.lackOfTestStudentService = lackOfTestStudentService;
	}

	public String getLcId() {
		return lcId;
	}
	public void setLcId(String lcId) {
		this.lcId = lcId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public CusKwCheatstu getModel() {
		return cusKwCheatstu;
	}
	
}
