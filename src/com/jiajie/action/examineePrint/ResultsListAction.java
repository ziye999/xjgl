package com.jiajie.action.examineePrint;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.examineePrint.ResultsListService;

@SuppressWarnings("serial")
public class ResultsListAction extends BaseAction{

	private String xnxq;
	private String bmlcid;
	private String name;
	private String sfzjh;
	private String sfhg;
	private String wj;
		
	@Autowired
	private ResultsListService rrService;
	
	public void getListPage() {
		HttpServletRequest request = getRequest();
		xnxq = request.getParameter("xnxq");
		writerPrint(rrService.getListPage(xnxq,getBspInfo()));		
	}
	
	public void getStudentScore(){
		HttpServletRequest request = getRequest();
		bmlcid = request.getParameter("bmlcid");
		name = request.getParameter("name");
		sfzjh = request.getParameter("sfzjh");
		sfhg = request.getParameter("sfhg");
		wj = request.getParameter("wj");
		writerPrint(rrService.getStudentScore(bmlcid, name, sfzjh, sfhg, wj));
	}
	
	public void checkStatue(){
		writerPrint(rrService.checkStatue(bmlcid));
	}
	
	public String getXnxq() {
		return xnxq;
	}

	public void setXnxq(String xnxq) {
		this.xnxq = xnxq;
	}

	public String getBmlcid() {
		return bmlcid;
	}

	public void setBmlcid(String bmlcid) {
		this.bmlcid = bmlcid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSfzjh() {
		return sfzjh;
	}

	public void setSfzjh(String sfzjh) {
		this.sfzjh = sfzjh;
	}

	public String getSfhg() {
		return sfhg;
	}

	public void setSfhg(String sfhg) {
		this.sfhg = sfhg;
	}

	public String getWj() {
		return wj;
	}

	public void setWj(String wj) {
		this.wj = wj;
	}

	public ResultsListService getRrService() {
		return rrService;
	}

	public void setRrService(ResultsListService rrService) {
		this.rrService = rrService;
	}

	public String getXnxq_id() {
		return xnxq;
	}

	public void setXnxq_id(String xnxq_id) {
		this.xnxq = xnxq_id;
	}


}
