package com.jiajie.action.signup;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.p;
import oracle.net.aso.s;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.signup.BmshService;
import com.jiajie.util.bean.MBspInfo;

public class BmshAction extends BaseAction{
	
	private String xn;
	private String mc;
	private String ckdw;
	private String bmlcid;
	private String sh1;
	private String zkdw;
	private String fpzt;
	public String getZkdw() {
		return zkdw;
	}

	public void setZkdw(String zkdw) {
		this.zkdw = zkdw;
	}

	@Autowired
	private BmshService service;
	
 	public void getListPage(){
 		MBspInfo m =getBspInfo();

 		PageBean pageBean = service.getList(xn, m, mc,ckdw,zkdw,fpzt);
		writerPrint(pageBean);
 	}
 	
 	public void getlist(){
 		writerPrint(service.getObject(bmlcid));
 	}
 	
 	public void update(){
 		writerPrint(service.up(bmlcid));
 	}
 	
 	public void updateZt() throws IOException{
 		MsgBean mb = service.updateZt(bmlcid);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
 	}
 	
 	public void updateBm() throws IOException{
 		MsgBean mb = service.updateBm(bmlcid);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
 	}
 	public  void dao(){
 		writerPrint(service.dxsl(xn, mc,ckdw,zkdw,fpzt));
 	}
 	public void btg(){
 		writerPrint(service.btg(bmlcid, sh1));
 	}

	public String getXn() {
		return xn;
	}

	public void setXn(String xn) {
		this.xn = xn;
	}

	public String getFpzt() {
		return fpzt;
	}

	public void setFpzt(String fpzt) {
		this.fpzt = fpzt;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public BmshService getService() {
		return service;
	}

	public void setService(BmshService service) {
		this.service = service;
	}

	public String getCkdw() {
		return ckdw;
	}

	public void setCkdw(String ckdw) {
		this.ckdw = ckdw;
	}

	public String getBmlcid() {
		return bmlcid;
	}

	public void setBmlcid(String bmlcid) {
		this.bmlcid = bmlcid;
	}

	public String getSh1() {
		return sh1;
	}

	public void setSh1(String sh1) {
		this.sh1 = sh1;
	}



	
	

 	
}
