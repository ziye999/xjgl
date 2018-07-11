package com.jiajie.bean.basicsinfo;

/**
 * CusKwRoom entity. @author MyEclipse Persistence Tools
 */
public class CusKwRoom implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private String fjid;
	private String roomcode;
	private String roomname;
	private String lfid;
	private Short floor;
	private Short seats;
	private String typeM;
	private String schoolid;

	// Constructors
	public String getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	/** default constructor */
	public CusKwRoom() {
	}

	/** full constructor */
	public CusKwRoom(String roomcode, String roomname, String lfid,
			Short floor, Short seats, String typeM) {
		this.roomcode = roomcode;
		this.roomname = roomname;
		this.lfid = lfid;
		this.floor = floor;
		this.seats = seats;
		this.typeM = typeM;
	}

	// Property accessors

	public String getFjid() {
		return this.fjid;
	}

	public void setFjid(String fjid) {
		this.fjid = fjid;
	}

	public String getRoomcode() {
		return this.roomcode;
	}

	public void setRoomcode(String roomcode) {
		this.roomcode = roomcode;
	}

	public String getRoomname() {
		return this.roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getLfid() {
		return this.lfid;
	}

	public void setLfid(String lfid) {
		this.lfid = lfid;
	}

	public Short getFloor() {
		return this.floor;
	}

	public void setFloor(Short floor) {
		this.floor = floor;
	}

	public Short getSeats() {
		return this.seats;
	}

	public void setSeats(Short seats) {
		this.seats = seats;
	}

	public String getTypeM() {
		return this.typeM;
	}

	public void setTypeM(String typeM) {
		this.typeM = typeM;
	}

}