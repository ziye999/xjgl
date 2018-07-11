package com.jiajie.action.userRole; 

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.UserInfo;
import com.jiajie.service.userRole.UserManagerService;
import com.jiajie.util.FunctionMenu;

@SuppressWarnings("serial")
public class UserManagerAction extends BaseAction{
	private String xm;
	private String dlzh;
	private String userType;	
	private UserInfo userInfo;  
	private String users;
	private String oldpass;
	private String newpass1;
	private String newpass2;
	private UserManagerService userManagerService;
		 
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public String getOldpass() {
		return oldpass;
	}
	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}
	public String getNewpass1() {
		return newpass1;
	}
	public void setNewpass1(String newpass1) {
		this.newpass1 = newpass1;
	}
	public String getNewpass2() {
		return newpass2;
	}
	public void setNewpass2(String newpass2) {
		this.newpass2 = newpass2;
	}
	@Autowired
	public void setManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getDlzh() {
		return dlzh;
	}
	public void setDlzh(String dlzh) {
		this.dlzh = dlzh;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	  
	public void getListPage(){
		writerPrint(userManagerService.getUserListPage(xm, dlzh, userType)); 
	}
	
	public void opUser(){
		writerPrint(JSONObject.fromObject(userManagerService.opUser(userInfo)));
		HttpServletRequest request = getRequest();
		ApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		// 加载菜单到缓存
		FunctionMenu.getMenuInstance().initMenuInfoToCache(wac);
		// 加载权限组到缓存		
		FunctionMenu.getMenuInstance().initRoleInfoToCache(wac);
		// 加载组菜单到缓存		
		FunctionMenu.getMenuInstance().initRoleMenuToCache(wac);
		// 加载用户组到缓存		
		FunctionMenu.getMenuInstance().initUserRoleToCache(wac);
	}
	
	public void deleteUsers(){
		writerPrint(userManagerService.deleteUsers(users)); 
	}
	
	public void updatePassword(){
		writerPrint(userManagerService.updatePassword(getBspInfo().getUserId(),oldpass,newpass1,newpass2)); 
	}
}
