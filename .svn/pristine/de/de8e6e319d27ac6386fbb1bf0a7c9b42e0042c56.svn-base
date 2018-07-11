package com.jiajie.bean.foreignkey;

import java.util.List;

public class ForeignBean implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 被引用主键名
	 */
	private String foreignName;//被引用主键名
	/**
	 * 被引用功能名
	 */
	private String foreignComment;//被引用功能名
	/**
	 * 是否启用外键检测
	 */
	private boolean isEnable = true;//是否启用外键检测
	/**
	 * 被引用字段集合
	 */
	private List<ForeignColumnBean> foreignColumnList;//被引用字段集合
	public String getForeignName() {
		return foreignName;
	}
	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}
	public String getForeignComment() {
		return foreignComment;
	}
	public void setForeignComment(String foreignComment) {
		this.foreignComment = foreignComment;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	public List<ForeignColumnBean> getForeignColumnList() {
		return foreignColumnList;
	}
	public void setForeignColumnList(List<ForeignColumnBean> foreignColumnList) {
		this.foreignColumnList = foreignColumnList;
	}
}
