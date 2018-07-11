package com.jiajie.action.dailyManage;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.http.HttpServletRequest;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.dailyManage.PhotoService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class PhotoAction extends BaseAction{ 
	@Resource(name="photoService")
	private PhotoService photoService;
	private File upload;
	private String uploadFileName;
	private String uploadContentType; 
	private List stuList;
	
	public static void main(String[] args) { 
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
	
	public void saveUpFileInfo() throws IOException { 
		//修改uploadFileName:上传的文件名
		String newfileName = ""+System.currentTimeMillis()+ uploadFileName.substring(uploadFileName.lastIndexOf("."));
		//判断保存路径的文件夹是否存在
		String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
		File disFile = new File(distPath); 
		if(!disFile.exists()){
			disFile.mkdirs();
	    }
		//文件保存路径
		distPath = distPath+File.separator+newfileName;  
		System.out.println(distPath);		
		FileUtils.copyFile(getUpload(), new File(distPath));
		
		//MBspInfo bspInfo = getBspInfo();
		//String XX_JBXX_ID = bspInfo.getOrgan().getOrganCode();
		MsgBean mb = photoService.saveUpFileInfo(newfileName,distPath);		
		System.out.println(new Gson().toJson(mb));
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk"); //text/html;
		response.getWriter().print(new Gson().toJson(mb));		 
	}

	/**
	 * 获取已上传照片zip
	 */
	public void getListPage(){
		HttpServletRequest request = getRequest();
		String schoolname = request.getParameter("school")==null?"":request.getParameter("school").toString();
		String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
		writerPrint(photoService.getPhotoList(schoolname,getBspInfo(),distPath));
	}
	
	/**
	 * 根据指定的zip解压并获取包中照片信息
	 * @return
	 */
	public String getPhotoListReport(){
		HttpServletRequest request = getRequest();
		String zpcjId = request.getParameter("ZPCJ_ID")==null?"":request.getParameter("ZPCJ_ID").toString();
		String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
		stuList = photoService.getPhotoListReport(zpcjId,distPath,getBspInfo());
		return "photoListReport";
	}
	
	/**
	 * 删除已解压的zip文件
	 */
	public void deleteZip(){
		HttpServletRequest request = getRequest();
		String zpcjId = request.getParameter("zpcjId")==null?"":request.getParameter("zpcjId").toString();
		String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
		File file = new File(distPath + File.separator + zpcjId);
		boolean flag = StringUtil.deleteDir(file);
	}		 
	
	 /**
	  * 审核照片
	  */
	public void auditPhotoUpdate(){
		HttpServletRequest request = getRequest();
		String zpcjId = request.getParameter("zpcjId")==null?"":request.getParameter("zpcjId").toString();
		String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
		//审核照片
		MsgBean mb = photoService.auditPhoto(zpcjId);
		if(mb.isSuccess()){
			List list = photoService.auditPhotoUpdate(zpcjId,distPath,getBspInfo());
			//更新学生照片信息
			if(list!=null && list.size()>0){
				writerPrint(photoService.updateStudentPhoto(list,getBspInfo()));
			}else {
				MsgBean msgBean = new MsgBean();
				msgBean.setSuccess(false);
				msgBean.setMsg("有照片文件在服务器上不存在！");
				writerPrint(msgBean);
			}
		}
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
 
	public PhotoService getPhotoService() {
		return photoService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	public List getStuList() {
		return stuList;
	}

	public void setStuList(List stuList) {
		this.stuList = stuList;
	}
	
}
