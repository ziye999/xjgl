package com.jiajie.action.signup;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;

import com.jiajie.service.signup.CjdcService;
import com.jiajie.util.bean.MBspInfo;

public class CjdcAction extends BaseAction{
	@Autowired
	private CjdcService sCjdcService;
	
	private String xn;
	

	public void getPage(){
		MBspInfo m =getBspInfo();
		PageBean pageBean = sCjdcService.getCj(xn,m);
		writerPrint(pageBean);
	}
	public void daochu(){
		MBspInfo m =getBspInfo();
		MsgBean msgBean = sCjdcService.dxsl(m,xn);
		writerPrint(msgBean);
	}
	public CjdcService getsCjdcService() {
		return sCjdcService;
	}

	public void setsCjdcService(CjdcService sCjdcService) {
		this.sCjdcService = sCjdcService;
	}
	public String getXn() {
		return xn;
	}
	public void setXn(String xn) {
		this.xn = xn;
	}

}
