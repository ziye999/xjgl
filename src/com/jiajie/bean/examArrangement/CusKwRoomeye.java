package com.jiajie.bean.examArrangement;

/**
 * AbstractCusKwRoomeye entity provides the base persistence definition of the
 * CusKwRoomeye entity. @author MyEclipse Persistence Tools
 */

public class CusKwRoomeye implements java.io.Serializable {

	// Fields

	private String jkapid;
	private String jklsid;
	private String teaname;
	private String lcid;
	private String kdid;
	private String kcid;
	private String mainflag;
	private String rcid;
	private String njid;

	// Constructors

	/** default constructor */
	public CusKwRoomeye() {
	}

	/** minimal constructor */
	public CusKwRoomeye(String teaname) {
		this.teaname = teaname;
	}

	/** full constructor */
	public CusKwRoomeye(String jklsid, String teaname, String lcid,
			String kdid, String kcid, String mainflag, String rcid, String njid) {
		this.jklsid = jklsid;
		this.teaname = teaname;
		this.lcid = lcid;
		this.kdid = kdid;
		this.kcid = kcid;
		this.mainflag = mainflag;
		this.rcid = rcid;
		this.njid = njid;
	}

	// Property accessors

	public String getJkapid() {
		return this.jkapid;
	}

	public void setJkapid(String jkapid) {
		this.jkapid = jkapid;
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

	public String getKcid() {
		return this.kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}

	public String getMainflag() {
		return this.mainflag;
	}

	public void setMainflag(String mainflag) {
		this.mainflag = mainflag;
	}

	public String getRcid() {
		return this.rcid;
	}

	public void setRcid(String rcid) {
		this.rcid = rcid;
	}

	public String getNjid() {
		return this.njid;
	}

	public void setNjid(String njid) {
		this.njid = njid;
	}

}