package com.jiajie.service.core;

import java.util.List;

import com.jiajie.bean.system.TQxglUserinfo;
import com.jiajie.util.bean.MBspInfo;

public interface UserInfoService {
	public MBspInfo getMBspInfo(String code);
		
	TQxglUserinfo getUserLogin(String loginpid, String loginpwd);

	String getRoleid(String loginid, String loginpwd);

	String getUsercode(String username, String password);
			
	TQxglUserinfo findByUsercode(String usercode);
	
	boolean checkE2id(String e2id);

	 List getKCList(String user_id);
}
