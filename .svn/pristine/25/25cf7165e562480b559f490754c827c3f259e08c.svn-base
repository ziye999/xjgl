package com.jiajie.action.core;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jiajie.action.BaseAction;

@SuppressWarnings("serial")
public class LogoutAction extends BaseAction{
	public void logout(){  
		Subject currentUser = SecurityUtils.getSubject();  
        currentUser.logout();
        getRequest().getSession().removeAttribute("mBspInfo");
        getRequest().getSession().removeAttribute("loginFlag");
        writerPrint("true");
    }
}
