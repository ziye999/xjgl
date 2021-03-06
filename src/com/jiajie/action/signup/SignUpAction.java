package com.jiajie.action.signup;
/**
 * 报名系统
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.signup.SignupService;
import com.jiajie.util.CompressFileUits;
import com.jiajie.util.bmtime;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.XsInfo;

public class SignUpAction extends BaseAction{
	
	@Autowired
	private SignupService signupService;
	private String dictCode;
	private String xm;
	private String sfzjh;
	private String xxmc;
	private String bh;
	private String xn;
	private String lcid;
	private File upload;
	private String uploadFileName;
	private String uploadContentType; 
	private String dwid;//参考单位id
	private String CKDW;//参考单位名称
	private String sex;
	private String XXMC1;
	private String XXMC;
	private String WHCD;
	private String ZY;
	private String MZM;
	private String mz;
	private String ZZMM;
	private String xsjbxxid;
	private String ZW;
	private String zflx;

	private String zfl;
	private String ZZM;
	private String whc;
	private String fz;
	private String zffw;
	private String	remark;
	private String imagepath;
	private String sfzjh1;
	private String sex3;
	private String xm3;
	private String zp;
	private String bmlcid;
	private String zkdw1;
	
	//查询信息
	public void getList(){
		PageBean pageBean = signupService.getList();
		writerPrint(pageBean);
	}
	//根据条件查找
	public void getPage(){
		PageBean pageBean = signupService.getlist(xm, sfzjh,bmlcid, zp);
		writerPrint(pageBean);
	}
	//查询民族
	 public void getMzz(){
		  writerPrint(signupService.getMz(dictCode));
	  }
	 //查询政治面貌
	 public void getZz(){
		 writerPrint(signupService.getZzmm());
	 }
	 //查询文化程度
	 public void getWh(){
		 writerPrint(signupService.getWhcd());
	 }
	 public void getZflb(){
		 writerPrint(signupService.getZflb());
	 }
	 //查询发证单位
	 public void getFzdww(){
		 writerPrint(signupService.getFzdw());
	 }
	//根据考生身份证查询信息
	public void getSFZ() throws IOException{			
		writerPrint(signupService.getList(sfzjh));
	}
	
	//判断报名时间
	public void getNowTime() throws Exception{
		Properties properties = new Properties();
		InputStream in = SignUpAction.class.getClassLoader().getResourceAsStream("kssj.properties");
		properties.load(in);
		String year = properties.getProperty("year");
		String month = properties.getProperty("month");
		String startday = properties.getProperty("startday");
		String endday = properties.getProperty("endday");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(new Date());
		String nowyear = str.substring(0, 4);
		String nowmonth = str.substring(6, 7);
		String nowday = str.substring(8, 10);
		MsgBean mb = new MsgBean();
		if(nowyear.equals(year)&&nowmonth.equals(month)&&(((Integer.valueOf(startday)<=Integer.valueOf(nowday))&&(Integer.valueOf(endday))>=Integer.valueOf(nowday)))){
			mb.setData(true);
			mb.setSuccess(true);
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/html; charset=gbk");
			response.getWriter().print(new Gson().toJson(mb));
		}else{
			mb.setData(false);
			mb.setSuccess(true);
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");  
			response.setContentType("text/html; charset=gbk");
			response.getWriter().print(new Gson().toJson(mb));
		}
		
	}
	
	//删除文件
	public void delte(){
		writerPrint(signupService.delte(bmlcid, sfzjh));
	}
	//上传信息
	public void save(){
		XsInfo xsInfo = new XsInfo();
		xsInfo.setMz(mz);
		xsInfo.setStuName(xm.trim().replace(" ", ""));
		xsInfo.setSfzjh(sfzjh.trim().replace(" ", ""));
		xsInfo.setStuSex(sex);
		xsInfo.setWhcd(whc);
		xsInfo.setZzmm(ZZM);
		xsInfo.setZffw(zffw.trim().replace(" ", ""));
		xsInfo.setZflb(zfl);
		xsInfo.setRemark(remark.trim().replace(" ", ""));
		xsInfo.setZy(ZY.trim().replace(" ", ""));
		xsInfo.setZw(ZW.trim().replace(" ", ""));
		xsInfo.setPath(imagepath);
		xsInfo.setFzdw(fz);
		xsInfo.setParticipatingUntisName(XXMC1);
		MBspInfo m =getBspInfo();
	
		MsgBean msgBean= signupService.saveBean(xsInfo, bmlcid, XXMC1,m);
		
		writerPrint(msgBean);
	}
	
	public void getFzdwDm() throws IOException{
		List list = (List) signupService.getZkdw(zkdw1);
		MsgBean mb = new MsgBean();
		mb.setData(list.get(0));
		mb.setSuccess(true);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void update(){
		XsInfo xsInfo = new XsInfo();
		xsInfo.setFzdw(fz);
		xsInfo.setMz(mz);
		xsInfo.setStuName(xm3);
		xsInfo.setSfzjh(sfzjh);
		xsInfo.setStuSex(sex3);
		xsInfo.setWhcd(whc);
		xsInfo.setZzmm(ZZM);
		xsInfo.setZffw(zffw);
		xsInfo.setZflb(zfl);
		xsInfo.setRemark(remark);
		xsInfo.setZy(ZY);
		xsInfo.setZw(ZW);
		xsInfo.setPath(imagepath);
		xsInfo.setParticipatingUntisName(XXMC);
		xsInfo.setSfzjh1(sfzjh1);
		MsgBean msgBean = signupService.update(xsInfo,bmlcid);
		writerPrint(msgBean);
	}
	

	//上传考生信息
	  public void exportKsInfo() {
		    try {
		    	MBspInfo m =getBspInfo();
		      MsgBean mb = this.signupService.exportKsInfo(this.upload, getBspInfo(), getRequest().getParameter("bmlcid"),CKDW);
		      HttpServletResponse response = getResponse();
		      response.setCharacterEncoding("UTF-8");
		      response.setContentType("text/html; charset=gbk");
		      response.getWriter().print(new Gson().toJson(mb));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  
	  public void exportPhonto() throws IOException {
			//判断保存路径的文件夹是否存在
			  System.out.println(uploadFileName);
			  String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
			  String newFileName = UUID.randomUUID().toString();
			  newFileName = newFileName.replace("-", "") + uploadFileName.substring(uploadFileName.lastIndexOf("."));
			  File disFile = new File(distPath); 
				if(!disFile.exists()){
					disFile.mkdirs();
			    }
				//文件保存路径
				distPath = distPath+File.separator+newFileName;  
				System.out.println(distPath);		
				FileUtils.copyFile(getUpload(), new File(distPath));
				HttpServletResponse response = getResponse();
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("text/html; charset=gbk");
				MsgBean mb = new MsgBean();
				mb.setMsg("上传成功,请点击保存！");
				mb.setData(newFileName);
				mb.setSuccess(true);
				response.getWriter().print(new Gson().toJson(mb));
		  }
	  	  
	  
	  public void exportCertificates() throws IOException {
			//判断保存路径的文件夹是否存在
			  System.out.println(uploadFileName);
			  String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"certificates");
			  String newFileName = UUID.randomUUID().toString();
			  newFileName = newFileName.replace("-", "") + uploadFileName.substring(uploadFileName.lastIndexOf("."));
			  File disFile = new File(distPath); 
				if(!disFile.exists()){
					disFile.mkdirs();
			    }
				//文件保存路径
				distPath = distPath+File.separator+newFileName;  
				System.out.println(distPath);		
				FileUtils.copyFile(getUpload(), new File(distPath));
				HttpServletResponse response = getResponse();
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("text/html; charset=gbk");
				MsgBean mb = new MsgBean();
				mb.setMsg("上传成功");
				mb.setData(newFileName);
				mb.setSuccess(true);
				response.getWriter().print(new Gson().toJson(mb));
		  }
	  
//上传照片

	  public void exportPhontoInfo() throws IOException {
		  boolean bool = bmtime.checkTime();
		  HttpServletResponse response = getResponse();
		  response.setCharacterEncoding("UTF-8");  
		  response.setContentType("text/html; charset=gbk");
		  MsgBean mb = new MsgBean();
		  mb.setMsg("上传成功");
		  mb.setSuccess(true);
		  
		  if(bool){
			//判断保存路径的文件夹是否存在
			  System.out.println(uploadFileName);
			  String newfileName = ""+bmlcid+ uploadFileName.substring(uploadFileName.lastIndexOf("."));
				String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photozip");
				File disFile = new File(distPath); 
				if(!disFile.exists()){
					disFile.mkdirs();
			    }
			
				//解压文件路径
				  String dwbm=bmlcid.replaceAll("'", "");
			      String descDir1 = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo")+File.separator+dwbm+ File.separator;
			      String descDir = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo")+"\\";
				//文件保存路径
				distPath = distPath+File.separator+newfileName;  
//				System.out.println(distPath);	
				FileUtils.copyFile(getUpload(), new File(distPath));
				String outPath = null;
				CompressFileUits.unZipFiles(distPath, descDir1);
				
				
				if (mb.getMsg().equals("上传成功")) {
					
					 writerPrint(signupService.glzp(bmlcid,descDir1));
				
				}else{
					mb.setMsg("上传失败");
					response.getWriter().print(new Gson().toJson(mb));
				}
		  }else{
			  mb.setMsg("上传失败,不是报名时间，不能导入照片！");
			  response.getWriter().print(new Gson().toJson(mb));
		  }
			
	  }	
	public SignupService getSignupService() {
		return signupService;
	}
	public void setSignupService(SignupService signupService) {
		this.signupService = signupService;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getSfzjh() {
		return sfzjh;
	}
	public void setSfzjh(String sfzjh) {
		this.sfzjh = sfzjh;
	}
	public String getXxmc() {
		return xxmc;
	}
	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getXn() {
		return xn;
	}
	public void setXn(String xn) {
		this.xn = xn;
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
	public String getDwid() {
		return dwid;
	}
	public void setDwid(String dwid) {
		this.dwid = dwid;
	}
	public String getCKDW() {
		return CKDW;
	}
	public void setCKDW(String cKDW) {
		CKDW = cKDW;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getWHCD() {
		return WHCD;
	}
	public void setWHCD(String wHCD) {
		WHCD = wHCD;
	}
	public String getZY() {
		return ZY;
	}
	public void setZY(String zY) {
		ZY = zY;
	}
	public String getMZM() {
		return MZM;
	}
	public void setMZM(String mZM) {
		MZM = mZM;
	}
	public String getZZMM() {
		return ZZMM;
	}
	public void setZZMM(String zZMM) {
		ZZMM = zZMM;
	}
	public String getZW() {
		return ZW;
	}
	public void setZW(String zW) {
		ZW = zW;
	}
	public String getZflx() {
		return zflx;
	}
	public void setZflx(String zflx) {
		this.zflx = zflx;
	}
	public String getZffw() {
		return zffw;
	}
	public void setZffw(String zffw) {
		this.zffw = zffw;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getXXMC() {
		return XXMC;
	}
	public void setXXMC(String xXMC) {
		XXMC = xXMC;
	}
	public String getXsjbxxid() {
		return xsjbxxid;
	}
	public void setXsjbxxid(String xsjbxxid) {
		this.xsjbxxid = xsjbxxid;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getZZM() {
		return ZZM;
	}
	public void setZZM(String zZM) {
		ZZM = zZM;
	}
	public String getMz() {
		return mz;
	}
	public void setMz(String mz) {
		this.mz = mz;
	}
	public String getZfl() {
		return zfl;
	}
	public void setZfl(String zfl) {
		this.zfl = zfl;
	}

	public String getWhc() {
		return whc;
	}
	public void setWhc(String whc) {
		this.whc = whc;
	}
	public String getSfzjh1() {
		return sfzjh1;
	}
	public void setSfzjh1(String sfzjh1) {
		this.sfzjh1 = sfzjh1;
	}
	public String getXXMC1() {
		return XXMC1;
	}
	public void setXXMC1(String xXMC1) {
		XXMC1 = xXMC1;
	}
	public String getSex3() {
		return sex3;
	}
	public void setSex3(String sex3) {
		this.sex3 = sex3;
	}
	public String getXm3() {
		return xm3;
	}
	public void setXm3(String xm3) {
		this.xm3 = xm3;
	}
	public String getZp() {
		return zp;
	}
	public void setZp(String zp) {
		this.zp = zp;
	}
	public String getBmlcid() {
		return bmlcid;
	}
	public void setBmlcid(String bmlcid) {
		this.bmlcid = bmlcid;
	}
	public String getFz() {
		return fz;
	}
	public void setFz(String fz) {
		this.fz = fz;
	}
	public String getZkdw1() {
		return zkdw1;
	}
	public void setZkdw1(String zkdw1) {
		this.zkdw1 = zkdw1;
	}	
			
}
