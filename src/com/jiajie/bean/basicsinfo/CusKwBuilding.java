package com.jiajie.bean.basicsinfo;

/**
 * CusKwBuilding entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CusKwBuilding implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lfid;
	private String buildname;
	private String schoolid;
	private String address;
	private Long floors;
	private Long rooms;
	private Long perfrooms;

	// Constructors

	/** default constructor */
	public CusKwBuilding() {
	}

	/** minimal constructor */
	public CusKwBuilding(String lfid) {
		this.lfid = lfid;
	}

	/** full constructor */
	public CusKwBuilding(String lfid, String buildname,
			String schoolid, String address, Long floors, Long rooms,
			Long perfrooms) {
		this.lfid = lfid;
		this.buildname = buildname;
		this.schoolid = schoolid;
		this.address = address;
		this.floors = floors;
		this.rooms = rooms;
		this.perfrooms = perfrooms;
	}

	// Property accessors

	public String getLfid() {
		return this.lfid;
	}

	public void setLfid(String lfid) {
		this.lfid = lfid;
	}

	public String getBuildname() {
		return this.buildname;
	}

	public void setBuildname(String buildname) {
		this.buildname = buildname;
	}

	public String getSchoolid() {
		return this.schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getFloors() {
		return this.floors;
	}

	public void setFloors(Long floors) {
		this.floors = floors;
	}

	public Long getRooms() {
		return this.rooms;
	}

	public void setRooms(Long rooms) {
		this.rooms = rooms;
	}

	public Long getPerfrooms() {
		return this.perfrooms;
	}

	public void setPerfrooms(Long perfrooms) {
		this.perfrooms = perfrooms;
	}

}
