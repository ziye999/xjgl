package com.jiajie.bean.examArrangement;

/**
 * CusKwExamschool entity. @author MyEclipse Persistence Tools
 */

public class CusKwExamschool implements java.io.Serializable {

	// Fields

	private String kdid;
	private String lcid;
	private String xxdm;
	private String pointname;
	private Integer roomcount;
	private Integer seatcount;

	// Constructors

	/** default constructor */
	public CusKwExamschool() {
	}

	/** full constructor */
	public CusKwExamschool(String lcid, String xxdm, String pointname,
			Integer roomcount, Integer seatcount) {
		this.lcid = lcid;
		this.xxdm = xxdm;
		this.pointname = pointname;
		this.roomcount = roomcount;
		this.seatcount = seatcount;
	}

	// Property accessors

	public String getKdid() {
		return this.kdid;
	}

	public void setKdid(String kdid) {
		this.kdid = kdid;
	}

	public String getLcid() {
		return this.lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getXxdm() {
		return this.xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	public String getPointname() {
		return this.pointname;
	}

	public void setPointname(String pointname) {
		this.pointname = pointname;
	}

	public Integer getRoomcount() {
		return this.roomcount;
	}

	public void setRoomcount(Integer roomcount) {
		this.roomcount = roomcount;
	}

	public Integer getSeatcount() {
		return this.seatcount;
	}

	public void setSeatcount(Integer seatcount) {
		this.seatcount = seatcount;
	}

}