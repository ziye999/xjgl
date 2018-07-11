package com.jiajie.action.exambasic;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.exambasic.ExamService;
import com.jiajie.service.exambasic.impl.ExamServiceImpl;
import com.jiajie.util.SecUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.bouncycastle.crypto.generators.MGF1BytesGenerator;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Controller
public class ExamAction extends BaseAction
{
  private static final Log log = LogFactory.getLog(ExamServiceImpl.class);
  private static final long serialVersionUID = 1L;
  private ExamService examService;
  private String lcid;
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
  private List<Map<String, Object>> dataList;
  @Autowired
  public void setExamService(ExamService examService) {
    this.examService = examService;
  }
  public void getSysDate() {
    writerPrint(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
  }
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

  public String getLcid() {
	return lcid;
}
public void setLcid(String lcid) {
	this.lcid = lcid;
}
//张昕添加关联照片接口
  //date 2017-10-19
  public void glzp() {
	    writerPrint(this.examService.glzp(this.lcid));
	  }
  
  
  public void createScore() {
	  HttpServletRequest request = getRequest();
	   String lcid = request.getParameter("lcId")==null?"":request.getParameter("lcId").toString();
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		String sfzh = request.getParameter("sfzh")==null?"":request.getParameter("sfzh").toString();
		String xuexiao = request.getParameter("xuexiao")==null?"":request.getParameter("xuexiao").toString();
	    writerPrint(this.examService.createScore(this.lcid,kmid,kdid,kcid,sfzh,xuexiao));
	  }
  
  public void exportPhontoInfo() throws IOException {
	//判断保存路径的文件夹是否存在
	  System.out.println(uploadFileName);
//	  String newfileName = ""+lcid+ uploadFileName.substring(uploadFileName.lastIndexOf("."));
		String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photozip");
		File disFile = new File(distPath); 
		if(!disFile.exists()){
			disFile.mkdirs();
	    }
		//文件保存路径
		distPath = distPath+File.separator+uploadFileName;  
		System.out.println(distPath);		
		FileUtils.copyFile(getUpload(), new File(distPath));
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		MsgBean mb = new MsgBean();
		mb.setMsg("上传成功");
		mb.setSuccess(true);

		response.getWriter().print(new Gson().toJson(mb));
  }
  
  public void exportPhonto() throws IOException {
		//判断保存路径的文件夹是否存在
		  System.out.println(uploadFileName);
//		  String newfileName = ""+sfzjh+ uploadFileName.substring(uploadFileName.lastIndexOf("."));
			String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
			File disFile = new File(distPath); 
			if(!disFile.exists()){
				disFile.mkdirs();
		    }
			//文件保存路径
			distPath = distPath+File.separator+uploadFileName;  
			System.out.println(distPath);		
			FileUtils.copyFile(getUpload(), new File(distPath));
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/html; charset=gbk");
			MsgBean mb = new MsgBean();
			mb.setMsg("上传成功");
			mb.setData(uploadFileName);
			mb.setSuccess(true);
			response.getWriter().print(new Gson().toJson(mb));
	  }
  	  
  public void exportKSPhonto() throws IOException {
		//判断保存路径的文件夹是否存在
		  System.out.println(uploadFileName);
		  String newfileName = ""+sfzjh+ uploadFileName.substring(uploadFileName.lastIndexOf("."));
			String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
			File disFile = new File(distPath); 
			if(!disFile.exists()){
				disFile.mkdirs();
		    }
			//文件保存路径
			distPath = distPath+File.separator+newfileName;  
			System.out.println(distPath);		
			FileUtils.copyFile(getUpload(), new File(distPath));
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/html; charset=gbk");
			MsgBean mb = new MsgBean();
			mb.setMsg("上传成功");
			mb.setData(newfileName);
			mb.setSuccess(true);
			response.getWriter().print(new Gson().toJson(mb));
	  }
  
  public String getSfzjh() {
	return sfzjh;
}

public void setSfzjh(String sfzjh) {
	this.sfzjh = sfzjh;
}

/**
	 * 获取准考证信息
	 * @return
	 */
	public String getExamCard(){
		HttpServletRequest request = getRequest();
		String sfzjh = request.getParameter("sfzjh")==null?"":request.getParameter("sfzjh").toString();
		PageBean pb=examService.getExamCard(sfzjh);
		dataList = pb.getResultList();
		return "getExamCardBySfz";
	}
	//上传考生信息
	  public void exportKsInfo() {
		    try {
		      MsgBean mb = this.examService.exportKsInfo(this.upload, getBspInfo(), getRequest().getParameter("lcid"));
		      HttpServletResponse response = getResponse();
		      response.setCharacterEncoding("UTF-8");
		      response.setContentType("text/html; charset=gbk");
		      response.getWriter().print(new Gson().toJson(mb));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public void exportKswj() {
		    try {
		      MsgBean mb = this.examService.exportKswj(this.upload, getBspInfo());
		      HttpServletResponse response = getResponse();
		      response.setCharacterEncoding("UTF-8");
		      response.setContentType("text/html; charset=gbk");
		      response.getWriter().print(new Gson().toJson(mb));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public void exportKsxxgz() {
		  try {
			  MsgBean mb = this.examService.exportKsxxgz(this.upload, getBspInfo());
			  HttpServletResponse response = getResponse();
			  response.setCharacterEncoding("UTF-8");
			  response.setContentType("text/html; charset=gbk");
			  response.getWriter().print(new Gson().toJson(mb));
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }
	  public void exportXls() {
		  try {
			    HttpServletRequest request = getRequest();
			    String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
				String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
				String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
				String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
				String xuehao = request.getParameter("xuexiao")==null?"":request.getParameter("xuexiao").toString();
				MsgBean mb = this.examService.exportXls(lcid,kmid,kcid,kdid,xuehao);
			  HttpServletResponse response = getResponse();
			  response.setCharacterEncoding("UTF-8");
			  response.setContentType("text/html; charset=gbk");
			  response.getWriter().print(new Gson().toJson(mb));
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }
	  public void exportPaper() {
		  try {
			    HttpServletRequest request = getRequest();
			    String sfzjh = request.getParameter("sfzjh")==null?"":request.getParameter("sfzjh").toString();
				MsgBean mb = this.examService.exportPaper(sfzjh);
			  HttpServletResponse response = getResponse();
			  response.setCharacterEncoding("UTF-8");
			  response.setContentType("text/html; charset=gbk");
			  response.getWriter().print(new Gson().toJson(mb));
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }
	  public void searchSore() {
		    	HttpServletRequest request = getRequest();
				String lcid = request.getParameter("lcId")==null?"":request.getParameter("lcId").toString();
				String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
				String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
				String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
				String sfzh = request.getParameter("sfzh")==null?"":request.getParameter("sfzh").toString();
				String xuexiao = request.getParameter("xuexiao")==null?"":request.getParameter("xuexiao").toString();
				//分页参数
			    String pagesize=request.getParameter("limit")==null?"20":request.getParameter("limit").toString();
				String pageindex=request.getParameter("start")==null?"0":request.getParameter("start").toString();
		    	writerPrint(examService.searchSore(lcid,kmid,kdid,kcid,sfzh,xuexiao,pageindex,pagesize));
	  }
	  public void getscoreList() {
		  HttpServletRequest request = getRequest();
		  //分页参数
		  String pagesize=request.getParameter("limit")==null?"20":request.getParameter("limit").toString();
		  String pageindex=request.getParameter("start")==null?"0":request.getParameter("start").toString();
		  String sfzjh=request.getParameter("sfzjh")==null?"0":request.getParameter("sfzjh").toString();
		  writerPrint(examService.getscoreList(pageindex,pagesize,sfzjh));
	  }
	  public void queryScore() {
	    	HttpServletRequest request = getRequest();
			String xm = request.getParameter("xm")==null?"":request.getParameter("xm").toString();
			String sfzh = request.getParameter("sfzh")==null?"":request.getParameter("sfzh").toString();
			if(xm.equals("") || sfzh.equals("")){
				writerPrint("参数异常");
				return;
			}
	    	writerPrint(examService.queryScore(xm,sfzh));
}
	  public void getExamKdDetail() {
		    writerPrint(this.examService.getExamKdDetail());
		  }
	  public void getExamZkdwDetail() {
		  writerPrint(this.examService.getExamZkdwDetail());
	  }
	  public void creatmm() {
		  writerPrint(this.examService.creatmm());
	  }
	  public void checksj() {
		  writerPrint(this.examService.checksj());
	  }
	  public void ruku() {
		  writerPrint(this.examService.ruku());
	  }
	  public void getExamKdDetailSDD() {
		    writerPrint(this.examService.getExamKdDetailSDD());
		  }
	  public static void main(String[] args) {
		try {
			System.out.println(SecUtils.decode("Yf7gQL3IVlA="));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}