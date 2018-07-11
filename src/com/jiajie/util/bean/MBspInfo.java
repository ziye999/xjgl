package com.jiajie.util.bean;

import com.jiajie.bean.SystemBean;

public class MBspInfo extends SystemBean {
	
	private static final long serialVersionUID = 1L;
	private String userId;//用户id
	private String userName;//用户名
	private String userType;//用户类型	
	private MBspOrgan organ = new MBspOrgan();//组织机构
	private String regionCode;
	private String regionName;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}	
	public MBspOrgan getOrgan() {
		return organ;
	}
	public void setOrgan(MBspOrgan organ) {
		this.organ = organ;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}
