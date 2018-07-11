package com.jiajie.bean.system;

import java.util.List;

/**
 * AbstractTQxglMenuinfo entity provides the base persistence definition of the
 * TQxglMenuinfo entity. @author MyEclipse Persistence Tools
 */

public class TQxglMenuinfo implements java.io.Serializable {

	// Fields

	private String menucode;
	private String appid;
	private String parentid;
	private String menuname;
	private String linkfile;
	private String meno;
	private String orderid;
	private String state;
	private List<TQxglMenuinfo> children;
	private String checked;
	private String id;
	private String text;
	private String leaf;

	// Constructors

	/** default constructor */
	public TQxglMenuinfo() {
	}

	/** minimal constructor */
	public TQxglMenuinfo(String parentid, String menuname,
			String orderid, String state) {
		this.parentid = parentid;
		this.menuname = menuname;
		this.orderid = orderid;
		this.state = state;
	}

	/** full constructor */
	public TQxglMenuinfo(String appid, String parentid,
			String menuname, String linkfile, String meno, String orderid,
			String state) {
		this.appid = appid;
		this.parentid = parentid;
		this.menuname = menuname;
		this.linkfile = linkfile;
		this.meno = meno;
		this.orderid = orderid;
		this.state = state;
	}

	// Property accessors

	public String getMenucode() {
		return this.menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getLinkfile() {
		return this.linkfile;
	}

	public void setLinkfile(String linkfile) {
		this.linkfile = linkfile;
	}

	public String getMeno() {
		return this.meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<TQxglMenuinfo> getChildren() {
		return children;
	}

	public void setChildren(List<TQxglMenuinfo> children) {
		this.children = children;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

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

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
}