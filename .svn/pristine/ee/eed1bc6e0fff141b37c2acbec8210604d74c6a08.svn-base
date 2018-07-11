package com.jiajie.action.examResults;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.examResults.ResultsResgisterService;

@SuppressWarnings("serial")
public class ResultsRegisterAction extends BaseAction{

	private File upload;
	private String lcid;
	private String xnxq_id;
		
	@Autowired
	private ResultsResgisterService rrService;
	
	public void getListPage() {
		writerPrint(rrService.getListPage(xnxq_id,getBspInfo()));		
	}
	
	public void importFile() throws IOException{
		MsgBean mb = rrService.importResults(getUserid(), upload, lcid);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
	}	
	
	public void checkCj(){
		writerPrint(rrService.checkCj(lcid,getUserid()));
	}
	
	public void submitCj(){
		writerPrint(rrService.submitCj(lcid,getUserid()));
	}
	
	public void createTemplate(){
		writerPrint(rrService.createTemplate(lcid));
	}
	
	public void checkFile(){
		writerPrint(rrService.checkFile(lcid));
	}
	
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getXnxq_id() {
		return xnxq_id;
	}

	public void setXnxq_id(String xnxq_id) {
		this.xnxq_id = xnxq_id;
	}
}
