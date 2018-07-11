package com.jiajie.action.core;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.cheatStu.CusKwCheatstu;
import com.jiajie.service.core.FileUpService;
import com.jiajie.util.bean.MBspInfo;

public class UpFileAction extends BaseAction{
	
	private static final long serialVersionUID = 3938907478064713774L;
	@Autowired
	private FileUpService fileUpService;
	private final static int BUFFER_SIZE = 16 * 1024;  
    private File   upload;
    private File exportExcel;
    private String uploadFileName;  
    private String uploadContentType;  
    private String title;  
    private String ss;
	
	public void saveUpFileInfo() throws IOException {
		//修改uploadFileName:上传的文件名
		String newfileName = System.currentTimeMillis()+ getUploadFileName().substring(getUploadFileName().lastIndexOf('.'));
		//判断保存路径的文件夹是否存在
		String distPath = ServletActionContext.getServletContext().getRealPath(File.separator+"uploadFile"+File.separator+"photo");
		File disFile = new File(distPath); 
		if(!disFile.exists()){
			disFile.mkdirs();
	    }
		//文件保存路径
		distPath = distPath+File.separator+newfileName;  
		MsgBean mb = fileUpService.saveUpFileInfo(upload,distPath);
		String path = "";
		if(mb.getData()!=null && !"".equals(mb.getData())){
			String disPath = mb.getData().toString();
			path = disPath.substring(disPath.indexOf(File.separator+"uploadFile"));
		}
		path = path.replaceAll("\\\\", File.separator);
		mb.setSuccess(true);
		mb.setMsg("上传图片成功！");
		mb.setShow(true);
		mb.setData(path);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));		
	}
	
	public void exportExcelTeacherFile() throws IOException{
		MsgBean mb = fileUpService.exportExcelTeacherInfo(upload,getBspInfo());
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void importExamstInfo() throws IOException{
		MsgBean mb = fileUpService.importExamstInfo(upload, getBspInfo());
//		MsgBean mb = fileUpService.exportExcelInfo(upload, getBspInfo(),getRequest().getParameter("lcid")); 
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));	
	}
	
	public void exportExcelCheatStuFile() throws IOException{
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		CusKwCheatstu cheatStu = new CusKwCheatstu();
		cheatStu.setLcid(lcid);
		MBspInfo spInfo = getBspInfo();
		cheatStu.setCjr(spInfo.getUserId());
		cheatStu.setCdate(new Date());
		MsgBean mb = fileUpService.exportExcelCheatStuFile(upload,cheatStu,getBspInfo());
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
		//writerPrint(mb);
	}
	
	public void getExamKdksInfo() throws ServletException, IOException{
	    String dt = getRequest().getParameter("dt");
	    String room = getRequest().getParameter("room");
	    String type = getRequest().getParameter("type");
	    if ((dt == null) || (type == null)) {
	      getResponse().sendRedirect("地址错误。。。");
	      return;
	    }
	    if (getRequest().getServerPort() == 8080)
	    {
	      Map map = this.fileUpService.getExamKdksInfo(dt, room, type);
	      if (((Boolean)map.get("success")).booleanValue()) {
	        System.out.println(map.get("data"));
	        getResponse().sendRedirect(map.get("data").toString());
	        return;
	      }
	      writerPrint(map.get("msg").toString());
	    }
	    else {
	      writerPrint("Permission denied");
	    }
		
	}
	public void info() throws ServletException, IOException{
	    String dt = getRequest().getParameter("dt");
	    String room = getRequest().getParameter("room");
	    String type = getRequest().getParameter("type");
	    if ((dt == null) || (type == null)) {
	      getResponse().sendRedirect("地址错误。。。");
	      return;
	    }
	    if (getRequest().getServerPort() == 8080)
	    {
	      Map map = this.fileUpService.info(dt, room, type);
	      
	      if (((Boolean)map.get("success")).booleanValue()) {
	        System.out.println(map.get("data"));
	        getResponse().sendRedirect(map.get("data").toString());
	        return;
	      }
	      writerPrint(map.get("msg").toString());
	    }
	    else {
	      writerPrint("Permission denied");
	    }
		
	}
	public void setService(FileUpService upFileService) {
		this.fileUpService = fileUpService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}

	public File getExportExcel() {
		return exportExcel;
	}

	public void setExportExcel(File exportExcel) {
		this.exportExcel = exportExcel;
	}
	public static void main(String[] args) {
		 Calendar cal = Calendar.getInstance();
	      int h = cal.get(13);
		System.out.println(h);
	}
		
}
