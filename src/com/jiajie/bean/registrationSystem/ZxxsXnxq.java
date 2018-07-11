package com.jiajie.bean.registrationSystem;

import javax.persistence.Entity;

/**
 * 年度管理
 * */

public class ZxxsXnxq {
	
	private String xnxqid;
	
	private String xnmc;
	
	private String xqmc;
	
	private String xxkssj;
	
	private String xxjssj;
	
	private String ssdwm;	
	
	private String yxbz;
	
	private String gxr;	
	
	private String gxsj;
	
	private String cklc;
	
	private String mr;

	public String getXnxqid() {
		return xnxqid;
	}

	public void setXnxqid(String xnxqid) {
		this.xnxqid = xnxqid;
	}

	public String getXnmc() {
		return xnmc;
	}

	public void setXnmc(String xnmc) {
		this.xnmc = xnmc;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getXxkssj() {
		return xxkssj;
	}

	public void setXxkssj(String xxkssj) {
		this.xxkssj = xxkssj;
	}

	public String getXxjssj() {
		return xxjssj;
	}

	public void setXxjssj(String xxjssj) {
		this.xxjssj = xxjssj;
	}

	public String getSsdwm() {
		return ssdwm;
	}

	public void setSsdwm(String ssdwm) {
		this.ssdwm = ssdwm;
	}

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}

	public String getGxr() {
		return gxr;
	}

	public void setGxr(String gxr) {
		this.gxr = gxr;
	}

	public String getGxsj() {
		return gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}

	public String getCklc() {
		return cklc;
	}

	public void setCklc(String cklc) {
		this.cklc = cklc;
	}



	public ZxxsXnxq(String xnxqid, String xnmc, String xqmc, String xxkssj,
			String xxjssj, String ssdwm, String yxbz, String gxr, String gxsj,
			String cklc) {
		super();
		this.xnxqid = xnxqid;
		this.xnmc = xnmc;
		this.xqmc = xqmc;
		this.xxkssj = xxkssj;
		this.xxjssj = xxjssj;
		this.ssdwm = ssdwm;
		this.yxbz = yxbz;
		this.gxr = gxr;
		this.gxsj = gxsj;
		this.cklc = cklc;
	}

	public ZxxsXnxq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMr() {
		return mr;
	}

	public void setMr(String mr) {
		this.mr = mr;
	}
	
	
	
	
}
