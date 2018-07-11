package com.jiajie.action.academicGrade;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.academicGrade.ImpAcademicGradeService;

@SuppressWarnings("serial")
public class ImpAcademicGradeAction extends BaseAction{
	@Autowired
	private ImpAcademicGradeService gradeService;
	private String nj;
	private String bj;
	private String xmxjh;
	private String tgid;
	private String byjyny;
	private String byjyjd;
	private File upload;
	
	public void getListPage(){
		String schools = getRequest().getParameter("schools")==null?"":getRequest().getParameter("schools").toString();
		writerPrint(gradeService.getList(xmxjh,schools, getBspInfo()));
	}
	
	//修改
	public void updateCJ(){
		writerPrint(gradeService.updateCj(tgid, byjyny, byjyjd, getBspInfo()));
	}
	
	//删除
	public void deleteCJ(){
		writerPrint(gradeService.delCj(tgid)); 
	}
	
	//导入
	public void exportExcelFile()throws Exception{
		MsgBean mb = gradeService.exportExcelInfo(upload,getBspInfo().getOrgan().getOrganCode());
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public ImpAcademicGradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(ImpAcademicGradeService gradeService) {
		this.gradeService = gradeService;
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

	public String getTgid() {
		return tgid;
	}

	public void setTgid(String tgid) {
		this.tgid = tgid;
	}

	public String getByjyny() {
		return byjyny;
	}

	public void setByjyny(String byjyny) {
		this.byjyny = byjyny;
	}
	
	public String getByjyjd() {
		return byjyjd;
	}

	public void setByjyjd(String byjyjd) {
		this.byjyjd = byjyjd;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
}
