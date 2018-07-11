package com.jiajie.bean.system;

/**
 * AbstractTQxglRolemenu entity provides the base persistence definition of the
 * TQxglRolemenu entity. @author MyEclipse Persistence Tools
 */

public class TQxglRolemenu implements java.io.Serializable {

	// Fields

	private String rolemenuid;
	private String rolecode;
	private String menucode;

	// Constructors

	/** default constructor */
	public TQxglRolemenu() {
	}

	/** full constructor */
	public TQxglRolemenu(String rolecode, String menucode) {
		this.rolecode = rolecode;
		this.menucode = menucode;
	}

	// Property accessors

	public String getRolemenuid() {
		return this.rolemenuid;
	}

	public void setRolemenuid(String rolemenuid) {
		this.rolemenuid = rolemenuid;
	}

	public String getRolecode() {
		return this.rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getMenucode() {
		return this.menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

}