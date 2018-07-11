package com.jiajie.action.dailyManagement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.dailyManagement.CusXjBreakstudy;
import com.jiajie.service.dailyManagement.ApplyForStudyService;
import com.jiajie.util.bean.MBspInfo;

@SuppressWarnings("serial")
public class ApplyForStudyAction extends BaseAction{

	@Autowired
	private ApplyForStudyService service;
	private String nj;
	private String bj;
	private String xmxjh;
	private String flag;
	private String xjh;
	private String xm;
	private String yy;//肄业原因
	private String sqrq;//申请日期
	private String yysm;//肄业说明
	private String yyid;
	
	public void getListPage(){
		writerPrint(service.getList(nj, bj, xmxjh, flag,getBspInfo()));
	}
	
	public void saveApply() throws Exception{
		MBspInfo mf = getBspInfo();
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=utf-8");
		CusXjBreakstudy breakstudy = new CusXjBreakstudy();
		breakstudy.setFilepath(filename);
		breakstudy.setXjh(xjh);
		breakstudy.setXm(xm);
		breakstudy.setReason(yy);
		breakstudy.setCjr(mf.getUserId());
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		breakstudy.setCdate(sdf.parse(sqrq)); 
		breakstudy.setMemo(yysm);
		breakstudy.setFlag("0");
		MsgBean mb = service.saveApply(breakstudy);
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void delApply()throws Exception{
		yyid="'"+yyid.replaceAll(", ","','")+"'";
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=utf-8");
		HttpServletRequest request = getRequest();
		MsgBean mb = service.delApply(request,yyid);
		response.getWriter().print(new Gson().toJson(mb));
	}
		
	public void updateApply()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=utf-8");
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		Date dt = sdf.parse(sqrq);
		MsgBean mb = service.updateApply(yyid, yy,sdf.format(dt), yysm,filename);
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	private File upload;
	private String filename;
	public void exportExcelFile()throws Exception{
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk"); //text/html;
		HttpServletRequest request = getRequest();
		MsgBean mb = service.uploadFile(request, upload, filename);
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public ApplyForStudyService getService() {
		return service;
	}

	public void setService(ApplyForStudyService service) {
		this.service = service;
	}

	public String getNj() {
		return nj;
	}

	public void setNj(String nj) {
		this.nj = nj;
	}

	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}

	public String getXmxjh() {
		return xmxjh;
	}

	public void setXmxjh(String xmxjh) {
		this.xmxjh = xmxjh;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getXjh() {
		return xjh;
	}

	public void setXjh(String xjh) {
		this.xjh = xjh;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getYy() {
		return yy;
	}

	public void setYy(String yy) {
		this.yy = yy;
	}

	public String getSqrq() {
		return sqrq;
	}

	public void setSqrq(String sqrq) {
		this.sqrq = sqrq;
	}

	public String getYysm() {
		return yysm;
	}

	public void setYysm(String yysm) {
		this.yysm = yysm;
	}

	public String getYyid() {
		return yyid;
	}

	public void setYyid(String yyid) {
		this.yyid = yyid;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
