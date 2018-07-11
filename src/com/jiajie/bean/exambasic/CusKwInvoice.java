package com.jiajie.bean.exambasic;

public class CusKwInvoice implements java.io.Serializable{
	
	private String bmlcid;
	private String dwname;
	private String dpno;
	private String dwaddress;
	private String lxrdh;
	private String email;
	public CusKwInvoice(String bmlcid, String dwname, String dpno,
			String dwaddress, String lxrdh,String email) {
		super();
		this.bmlcid = bmlcid;
		this.dwname = dwname;
		this.dpno = dpno;
		this.dwaddress = dwaddress;
		this.lxrdh = lxrdh;
		this.setEmail(email);
	}
	public String getBmlcid() {
		return bmlcid;
	}
	public void setBmlcid(String bmlcid) {
		this.bmlcid = bmlcid;
	}
	public String getDwname() {
		return dwname;
	}
	public void setDwname(String dwname) {
		this.dwname = dwname;
	}
	public String getDpno() {
		return dpno;
	}
	public void setDpno(String dpno) {
		this.dpno = dpno;
	}
	public String getDwaddress() {
		return dwaddress;
	}
	public void setDwaddress(String dwaddress) {
		this.dwaddress = dwaddress;
	}
	public String getLxrdh() {
		return lxrdh;
	}
	public void setLxrdh(String lxrdh) {
		this.lxrdh = lxrdh;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
