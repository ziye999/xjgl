package com.jiajie.action.gradeAmendment;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.gradeAmendment.GradeAmendmentService;
@SuppressWarnings("serial")
public class GradeAmendmentAction extends BaseAction{
	@Autowired
	private GradeAmendmentService amendmentService;
	
	private String lcid;//轮次
	private String xnxq; //年度
	private String xx;//参考单位
	private String nj;//年级
	private String bj;//班级
	private String khao;//考号
	
	public void getListPage(){
		writerPrint(amendmentService.getList(lcid, xnxq));
	}
	
	//所有成绩修正审核
	public void updateAll() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = amendmentService.updateSH(lcid);
		response.getWriter().print(new Gson().toJson(mb));
	}

	public GradeAmendmentService getAmendmentService() {
		return amendmentService;
	}

	public void setAmendmentService(GradeAmendmentService amendmentService) {
		this.amendmentService = amendmentService;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getXnxq() {
		return xnxq;
	}

	public void setXnxq(String xnxq) {
		this.xnxq = xnxq;
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
	
}
