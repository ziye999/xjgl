package com.jiajie.bean.dailyManagement;



public class CusXjSchoolcode implements java.io.Serializable {

	
	private String xx_jbxx_id;
	private String schoolcode;
	private String region_code;
	public String getXx_jbxx_id() {
		return xx_jbxx_id;
	}
	public void setXx_jbxx_id(String xx_jbxx_id) {
		this.xx_jbxx_id = xx_jbxx_id;
	}
	public String getSchoolcode() {
		return schoolcode;
	}
	public void setSchoolcode(String schoolcode) {
		this.schoolcode = schoolcode;
	}
	public String getRegion_code() {
		return region_code;
	}
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}
	@Override
	public String toString() {
		return "CusXjSchoolcode [xx_jbxx_id=" + xx_jbxx_id + ", schoolcode="
				+ schoolcode + ", region_code=" + region_code + "]";
	}

	
}