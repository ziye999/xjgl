package com.jiajie.action.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.core.UserInfoService;
import com.jiajie.util.bean.MBspInfo;

@SuppressWarnings("serial")
public class UserInfoAction extends BaseAction{
	
	@Autowired
	private UserInfoService service;
	public UserInfoService getService() {
		return service;
	}
	public void setService(UserInfoService service) {
		this.service = service;
	}
	public void login() {
		String userId = getRequest().getParameter("data").toString();
		MBspInfo mBspInfo = service.getMBspInfo(userId);
		mBspInfo.setUserId(userId);
		getRequest().getSession().setAttribute("mBspInfo", mBspInfo);		
	}
	
}
