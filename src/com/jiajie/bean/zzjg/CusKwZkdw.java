package com.jiajie.bean.zzjg;

/**
 * CusKwZkdw entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CusKwZkdw implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String regioncode;
	private String regionedu;
	private String regionname;
	private String regionshort;
	private String regionlevel;
	private String parentcode;
	private String areatype;
	private String inuse;
	private String sjc;
	
	// Constructors
	/** default constructor */
	public CusKwZkdw() {
	}

	/** minimal constructor */
	public CusKwZkdw(String regioncode) {
		this.regioncode = regioncode;
	}

	/** full constructor */
	public CusKwZkdw(String regioncode, String regionedu, String regionname,
			String regionshort, String regionlevel, String parentcode,
			String areatype, String inuse, String sjc) {
		this.regioncode = regioncode;
		this.regionedu = regionedu;
		this.regionname = regionname;
		this.regionshort = regionshort;
		this.regionlevel = regionlevel;
		this.parentcode = parentcode;
		this.areatype = areatype;
		this.inuse = inuse;
		this.sjc = sjc;
	}

	// Property accessors
	public String getRegioncode() {
		return this.regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getRegionedu() {
		return this.regionedu;
	}

	public void setRegionedu(String regionedu) {
		this.regionedu = regionedu;
	}

	public String getRegionname() {
		return this.regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}

	public String getRegionshort() {
		return this.regionshort;
	}

	public void setRegionshort(String regionshort) {
		this.regionshort = regionshort;
	}

	public String getRegionlevel() {
		return this.regionlevel;
	}

	public void setRegionlevel(String regionlevel) {
		this.regionlevel = regionlevel;
	}

	public String getParentcode() {
		return this.parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public String getAreatype() {
		return this.areatype;
	}

	public void setAreatype(String areatype) {
		this.areatype = areatype;
	}
		
	public String getInuse() {
		return this.inuse;
	}

	public void setInuse(String inuse) {
		this.inuse = inuse;
	}
	
	public String getSjc() {
		return this.sjc;
	}

	public void setSjc(String sjc) {
		this.sjc = sjc;
	}

}
