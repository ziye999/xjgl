package com.jiajie.action.sqck;

import java.util.List;

import oracle.net.aso.s;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.zzjg.CusKwSqCkdw;
import com.jiajie.service.sqck.SqckService;
import com.jiajie.util.bean.MBspInfo;

public class SqckAction extends BaseAction{

	@Autowired
	private SqckService sqckService;
	private String SSZGJYXZDM;
	private String XXMC;
	private String XZXM;
	private  String BGDH ;
	private String  YZBM;
	private String XXDZ;
	private String ckdw;
	private String zkdw;
	private String xxjbxxid;
	private String zt;



	public String savesqck() {
		String SSZGJYXZDM = getRequest().getParameter("SSZGJYXZDM")==null?"":getRequest().getParameter("SSZGJYXZDM").toString();
		String XXMC = getRequest().getParameter("XXMC")==null?"":getRequest().getParameter("XXMC").toString();

		CusKwSqCkdw sqCkdw = new CusKwSqCkdw();

		sqCkdw.setXxmc(XXMC);
		sqCkdw.setSszgjyxzdm(SSZGJYXZDM);
		sqCkdw.setXzxm(XZXM);
		sqCkdw.setBgdh(BGDH);
		sqCkdw.setYzbm(YZBM);
		sqCkdw.setXxdz(XXDZ);
		sqCkdw.setZt("0");
		long xx = System.currentTimeMillis();
		String xxbsm1 = String.valueOf(xx);
		sqCkdw.setXxbsm(xxbsm1);

		MsgBean m = sqckService.saveBean(sqCkdw);

		System.out.println(m.getMsg());
		getRequest().setAttribute("errormsg", m.getMsg());
		return "success";

	}
	public void getSq(){
		writerPrint(sqckService.getSq(ckdw,zkdw,zt));
	}
	public String getList(){
		List list = sqckService.getZk();
		getRequest().setAttribute("list",list);
		
		return "sqck";
		
	}

	public void upZt(){
		MBspInfo m =getBspInfo();
		writerPrint(sqckService.updateBean(xxjbxxid,m));
	}


	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getXxjbxxid() {
		return xxjbxxid;
	}
	public void setXxjbxxid(String xxjbxxid) {
		this.xxjbxxid = xxjbxxid;
	}
	public String getCkdw() {
		return ckdw;
	}
	public void setCkdw(String ckdw) {
		this.ckdw = ckdw;
	}
	public String getZkdw() {
		return zkdw;
	}
	public void setZkdw(String zkdw) {
		this.zkdw = zkdw;
	}



	public String getSSZGJYXZDM() {
		return SSZGJYXZDM;
	}
	public void setSSZGJYXZDM(String sSZGJYXZDM) {
		SSZGJYXZDM = sSZGJYXZDM;
	}
	public String getXXMC() {
		return XXMC;
	}


	public void setXXMC(String xXMC) {
		XXMC = xXMC;
	}


	public String getXZXM() {
		return XZXM;
	}


	public void setXZXM(String xZXM) {
		XZXM = xZXM;
	}


	public String getBGDH() {
		return BGDH;
	}


	public void setBGDH(String bGDH) {
		BGDH = bGDH;
	}


	public String getYZBM() {
		return YZBM;
	}


	public void setYZBM(String yZBM) {
		YZBM = yZBM;
	}


	public String getXXDZ() {
		return XXDZ;
	}


	public void setXXDZ(String xXDZ) {
		XXDZ = xXDZ;
	}


	public SqckService getSqckService() {
		return sqckService;
	}


	public void setSqckService(SqckService sqckService) {
		this.sqckService = sqckService;
	}


}
