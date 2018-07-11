package com.jiajie.service.userRole; 

import java.util.Map;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.UserInfo;

public interface UserManagerService {
	public PageBean  getUserListPage(String xm,String dlzh,String userType);

	public Map<String,Object> opUser(UserInfo userInfo);

	public String deleteUsers(String users);
	
	public MsgBean updatePassword(String users,String oldpass,String newpass1,String newpass2);
}
