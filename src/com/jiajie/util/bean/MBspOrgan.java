package com.jiajie.util.bean;

import java.util.List;

import com.jiajie.bean.SystemBean;

public class MBspOrgan extends SystemBean {
	private String organCode;//机构代码
	private String organName;//机构名称
	private String organType;//机构类型
	private String parentCode;//机构上级
	private MBspOrgan parentOrgans;//机构上级集合
	private int level;//机构级别
	private List<MBspOrgan> childOrgans;//如果机构为市组考单位，则子就存在，为县组考单位
	private List<MBspOrgan> childSchools;//组考单位所属参考单位
	public MBspOrgan getParentOrgans() {
		return parentOrgans;
	}
	public void setParentOrgans(MBspOrgan parentOrgans) {
		this.parentOrgans = parentOrgans;
	}
	public List<MBspOrgan> getChildOrgans() {
		return childOrgans;
	}
	public void setChildOrgans(List<MBspOrgan> childOrgans) {
		this.childOrgans = childOrgans;
	}
	public List<MBspOrgan> getChildSchools() {
		return childSchools;
	}
	public void setChildSchools(List<MBspOrgan> childSchools) {
		this.childSchools = childSchools;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getOrganType() {
		return organType;
	}
	public void setOrganType(String organType) {
		this.organType = organType;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
