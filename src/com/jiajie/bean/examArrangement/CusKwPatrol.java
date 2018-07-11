package com.jiajie.bean.examArrangement;

/**
 * AbstractCusKwPatrol entity provides the base persistence definition of the
 * CusKwPatrol entity. @author MyEclipse Persistence Tools
 */

public class CusKwPatrol implements java.io.Serializable {

	private static final long serialVersionUID = -2279603811622360687L;
	// Fields
	private String xkapid;
	private String jklsid;
	private String teaname;
	private String lcid;
	private String kdid;
	private String rang;

	// Constructors
	/** default constructor */
	public CusKwPatrol() {
	}

	/** minimal constructor */
	public CusKwPatrol(String teaname) {
		this.teaname = teaname;
	}

	/** full constructor */
	public CusKwPatrol(String jklsid, String teaname, String lcid,
			String kdid, String rang) {
		this.jklsid = jklsid;
		this.teaname = teaname;
		this.lcid = lcid;
		this.kdid = kdid;
		this.rang = rang;
	}

	// Property accessors
	public String getXkapid() {
		return this.xkapid;
	}

	public void setXkapid(String xkapid) {
		this.xkapid = xkapid;
	}

	public String getJklsid() {
		return this.jklsid;
	}

	public void setJklsid(String jklsid) {
		this.jklsid = jklsid;
	}

	public String getTeaname() {
		return this.teaname;
	}

	public void setTeaname(String teaname) {
		this.teaname = teaname;
	}

	public String getLcid() {
		return this.lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getKdid() {
		return this.kdid;
	}

	public void setKdid(String kdid) {
		this.kdid = kdid;
	}

	public String getRang() {
		return this.rang;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

}