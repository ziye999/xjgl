package com.jiajie.action.registration;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.service.registration.ExamRegistrationService;

@SuppressWarnings("serial")
public class ExamRegistrationAction extends BaseAction{
	private CusKwExamround cusKwExamround;
	private String lcid;
	private String dwid;
	private String dwtype;
	private String XN;
	private String xx;//参考单位
	private String nj;//年级
	private String bj;//班级
	private String khao;
	private String xxdm;
	
	private File upload;
	@Autowired
	private ExamRegistrationService registrationService;
		
	public void getListPage(){
		writerPrint(registrationService.getList(XN,getBspInfo().getOrgan().getOrganCode(),getBspInfo()));
	}
	
	//获取登陆参考单位所属组考单位的主管单位
	public void sjjyje(){
		if(xxdm==null || "".equals(xxdm)){
			if(dwid==null || "".equals(dwid)){
				dwid = getBspInfo().getOrgan().getOrganCode();
			}
			if(dwtype==null || "".equals(dwtype)){
				dwtype = getBspInfo().getUserType();
			}
			writerPrint(registrationService.getSjjyj(dwid, dwtype));
		}else{
			writerPrint(registrationService.getSjjyj(xxdm, "2"));
		}
	}
	
	private String params;
	public void jyj(){
		//根据上级组考单位获取县级组考单位
		if(dwid==null || "".equals(dwid)){
			dwid = getBspInfo().getOrgan().getOrganCode();			
		}
		if(dwtype==null || "".equals(dwtype)){
			dwtype = getBspInfo().getUserType();			
		}
		writerPrint(registrationService.getJyj(params,dwtype,dwid));
	}
	//获取参考单位下拉
	public void school(){
		if(dwid==null || "".equals(dwid)){
			dwid = getBspInfo().getOrgan().getOrganCode();
		}
		if(dwtype==null||"".equals(dwtype)){
			dwtype = getBspInfo().getUserType();
		}
		writerPrint(registrationService.getSchool(params,dwtype,dwid));
	}
	//保存
	public void addCqxs()throws IOException{		
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String khao = request.getParameter("khao")==null?"":request.getParameter("khao").toString();
		String xjh = request.getParameter("xjh")==null?"":request.getParameter("xjh").toString();
		MsgBean mb = registrationService.saveEaxminee(lcid,khao,xjh);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void updateCqxs()throws IOException{
		MsgBean mb = registrationService.updateEaxminee(lcid);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	//导入
	public void exportExcelFile()throws IOException{
		MsgBean mb = registrationService.exportExcelInfo(upload, getBspInfo(),lcid);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void exportKsFile()throws IOException{
		HttpServletRequest request = getRequest();
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		int rtn = registrationService.exportKsInfo(kmid,upload);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(rtn);
	}
		
	public CusKwExamround getCusKwExamround() {
		return cusKwExamround;
	}
	public void setCusKwExamround(CusKwExamround cusKwExamround) {
		this.cusKwExamround = cusKwExamround;
	}
	public ExamRegistrationService getRegistrationService() {
		return registrationService;
	}
	public void setRegistrationService(ExamRegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getDwid() {
		return dwid;
	}

	public void setDwid(String dwid) {
		this.dwid = dwid;
	}
	public String getXN() {
		return XN;
	}

	public void setXN(String xN) {
		XN = xN;
	}

	public String getDwtype() {
		return dwtype;
	}

	public void setDwtype(String dwtype) {
		this.dwtype = dwtype;
	}

	public String getXx() {
		return xx;
	}

	public void setXx(String xx) {
		this.xx = xx;
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
	
	public String getKhao() {
		return khao;
	}

	public void setKhao(String khao) {
		this.khao = khao;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getXxdm() {
		return xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
}
