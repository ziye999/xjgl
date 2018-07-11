package com.jiajie.util.bean;

import java.util.List;

public class OrganTree {
	private String id;
	private String text;
	private int index;//1:组考单位；2：参考单位；3：等级；4：课程；
	private List<OrganTree> children;
	private String checked;
	private String leaf;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<OrganTree> getChildren() {
		return children;
	}
	public void setChildren(List<OrganTree> children) {
		this.children = children;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
