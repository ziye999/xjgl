package com.jiajie.bean.scoreModify;

public class CusKwModifyScore implements java.io.Serializable{

	private static final long serialVersionUID = -6315918042807832116L;
	private String xgcjid;
	private String lcid	;
	private String kmid;
	private String examcode;
	private String xjh;
	private Float yscj;
	private Float kcfs;
	private Float zzcj;
	private String xgr;
	private String flag;	
	
	public String getXgcjid() {
		return xgcjid;
	}
	public void setXgcjid(String xgcjid) {
		this.xgcjid = xgcjid;
	}
	public String getLcid() {
		return lcid;
	}
	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	public String getKmid() {
		return kmid;
	}
	public void setKmid(String kmid) {
		this.kmid = kmid;
	}
	public String getExamcode() {
		return examcode;
	}
	public void setExamcode(String examcode) {
		this.examcode = examcode;
	}
	public String getXjh() {
		return xjh;
	}
	public void setXjh(String xjh) {
		this.xjh = xjh;
	}
	public Float getYscj() {
		return yscj;
	}
	public void setYscj(Float yscj) {
		this.yscj = yscj;
	}
	public Float getKcfs() {
		return kcfs;
	}
	public void setKcfs(Float kcfs) {
		this.kcfs = kcfs;
	}
	public Float getZzcj() {
		return zzcj;
	}
	public void setZzcj(Float zzcj) {
		this.zzcj = zzcj;
	}
	public String getXgr() {
		return xgr;
	}
	public void setXgr(String xgr) {
		this.xgr = xgr;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "CusKwModifyScore [xgcjid=" + xgcjid + ", lcid=" + lcid
				+ ", kmid=" + kmid + ", examcode=" + examcode + ", xjh=" + xjh
				+ ", yscj=" + yscj + ", kcfs=" + kcfs + ", zzcj=" + zzcj
				+ ", xgr=" + xgr + ", flag=" + flag + "]";
	}
		
}
