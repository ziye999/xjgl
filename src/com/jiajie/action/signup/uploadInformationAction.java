package com.jiajie.action.signup;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.signup.uploadInformationService;

@Controller
public class uploadInformationAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private uploadInformationService uploadInforService;
	
	private String xnxq;
	private String sfzjh;
	private File upload;
	private String uploadFileName;
	private String uploadContentType; 
	
	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	
	public String getXnxq() {
		return xnxq;
	}

	public void setXnxq(String xnxq) {
		this.xnxq = xnxq;
	}

	public String getSfzjh() {
		return sfzjh;
	}

	public void setSfzjh(String sfzjh) {
		this.sfzjh = sfzjh;
	}

	public void getListPage(){
		writerPrint(uploadInforService.getListPage(xnxq,sfzjh,getBspInfo()));
	}
	
	
	public uploadInformationService getUploadInforService() {
		return uploadInforService;
	}

	public void setUploadInforService(uploadInformationService uploadInforService) {
		this.uploadInforService = uploadInforService;
	}



	public void exportKsxxgz() {
		  try {
			  MsgBean mb = this.uploadInforService.exportKsxxgz(this.upload, getBspInfo());
			  HttpServletResponse response = getResponse();
			  response.setCharacterEncoding("UTF-8");
			  response.setContentType("text/html; charset=gbk");
			  response.getWriter().print(new Gson().toJson(mb));
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
}
