package com.jiajie.bean.system;

/**
 * AbstractTQxglUserrole entity provides the base persistence definition of the
 * TQxglUserrole entity. @author MyEclipse Persistence Tools
 */

public class TQxglUserrole implements java.io.Serializable {

	// Fields

	private String userroleid;
	private String usercode;
	private String rolecode;

	// Constructors

	/** default constructor */
	public TQxglUserrole() {
	}

	/** full constructor */
	public TQxglUserrole(String usercode, String rolecode) {
		this.usercode = usercode;
		this.rolecode = rolecode;
	}

	// Property accessors

	public String getUserroleid() {
		return this.userroleid;
	}

	public void setUserroleid(String userroleid) {
		this.userroleid = userroleid;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getRolecode() {
		return this.rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

}