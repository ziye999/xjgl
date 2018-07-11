package com.jiajie.bean.foreignkey;

import java.util.List;

public class ForeignColumnBean implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 被引用的字段名
	 */
	private String columnName;
	/**
	 * 被引用字段说明
	 */
	private String columnCue;
	/**
	 * 该字段是否启用检测
	 */
	private boolean isEnable = true;
	/**
	 * 外键表集合
	 */
	private List<ForeignTableBean> foreignTableList;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnCue() {
		return columnCue;
	}
	public void setColumnCue(String columnCue) {
		this.columnCue = columnCue;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	public List<ForeignTableBean> getForeignTableList() {
		return foreignTableList;
	}
	public void setForeignTableList(List<ForeignTableBean> foreignTableList) {
		this.foreignTableList = foreignTableList;
	} 
}
