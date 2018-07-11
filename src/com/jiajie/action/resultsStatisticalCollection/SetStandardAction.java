package com.jiajie.action.resultsStatisticalCollection;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.resultsStatisticalCollection.CusKwStandard;
import com.jiajie.service.resultsStatisticalCollection.SetStandardService;

@SuppressWarnings("serial")
public class SetStandardAction extends BaseAction{
	
	@Autowired
	private SetStandardService service;
	
	private String jyj;
	private String xn;
	private String lcid;
	private String bztype;
	private String upline;
	private String downline;
	private String bzid;

	public void getListPage(){
		writerPrint(service.getList(xn,lcid,this.getBspInfo()));
	}
	
	public void saveStandard()throws IOException{
		CusKwStandard cusKwStandard = new CusKwStandard();
		cusKwStandard.setBztype("2");
		cusKwStandard.setLcid(lcid);
		cusKwStandard.setUpline(upline);
		cusKwStandard.setDownline(downline);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cusKwStandard.setCdate(df.format(new Date()));
		MsgBean mb = service.saveStand(cusKwStandard); 
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk"); //text/html;
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void updateStandard()throws IOException{
		MsgBean mb = service.updateStand(bzid, "2", upline, downline);  
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk"); //text/html;
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public SetStandardService getService() {
		return service;
	}

	public void setService(SetStandardService service) {
		this.service = service;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getBztype() {
		return bztype;
	}

	public void setBztype(String bztype) {
		this.bztype = bztype;
	}

	public String getUpline() {
		return upline;
	}

	public void setUpline(String upline) {
		this.upline = upline;
	}

	public String getDownline() {
		return downline;
	}

	public void setDownline(String downline) {
		this.downline = downline;
	}

	public String getJyj() {
		return jyj;
	}

	public void setJyj(String jyj) {
		this.jyj = jyj;
	}

	public String getXn() {
		return xn;
	}

	public void setXn(String xn) {
		this.xn = xn;
	}

	public String getBzid() {
		return bzid;
	}

	public void setBzid(String bzid) {
		this.bzid = bzid;
	} 
}
