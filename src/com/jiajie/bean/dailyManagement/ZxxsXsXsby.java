package com.jiajie.bean.dailyManagement;

/**
 * CusXjFinalscore entity. @author MyEclipse Persistence Tools
 */

public class ZxxsXsXsby implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -7541167139214478711L;	
	private String xs_xsby_id;
	private String xs_jbxx_id;
	private String xx_jbxx_id;
	private String xx_njxx_id;
	private String bynd;
	private String xm;
	private String csrq;
	private String xbm;
	private String grbsm;
	private String byjyny;
	private String byzsh;
	private String gxr;
	private String gxsj;
	private String byjyjd;
	private String xx_bjxx_id;
	private String bysj;

	// Constructors
	/** default constructor */
	public ZxxsXsXsby() {
	}

	/** minimal constructor */
	public ZxxsXsXsby(String grbsm, String bysj) {
		this.grbsm = grbsm;
		this.bysj = bysj;
	}

	/** full constructor */
	public ZxxsXsXsby(String xs_xsby_id,String xs_jbxx_id,String xx_jbxx_id,String xx_njxx_id,
	String bynd,String xm,String csrq,String xbm,String grbsm,String byjyny,String byzsh,
	String gxr,String gxsj,String byjyjd,String xx_bjxx_id,String bysj) {
		this.xs_xsby_id = xs_xsby_id;
		this.xs_jbxx_id = xs_jbxx_id;
		this.xx_jbxx_id = xx_jbxx_id;
		this.xx_njxx_id = xx_njxx_id;
		this.xx_bjxx_id = xx_bjxx_id;		
		this.bynd = bynd;
		this.xm = xm;
		this.csrq = csrq;
		this.xbm = xbm;
		this.grbsm = grbsm;
		this.byjyny = byjyny;
		this.byzsh = byzsh;
		this.gxr = gxr;		
		this.gxsj = gxsj;
		this.byjyjd = byjyjd;
		this.bysj = bysj;		
	}
	
	// Property accessors
	public String getXs_xsby_id() {
		return this.xs_xsby_id;
	}

	public void setXs_xsby_id(String xs_xsby_id) {
		this.xs_xsby_id = xs_xsby_id;
	}

	public String getXs_jbxx_id() {
		return this.xs_jbxx_id;
	}

	public void setXs_jbxx_id(String xs_jbxx_id) {
		this.xs_jbxx_id = xs_jbxx_id;
	}
	
	public String getXx_jbxx_id() {
		return this.xx_jbxx_id;
	}

	public void setXx_jbxx_id(String xx_jbxx_id) {
		this.xx_jbxx_id = xx_jbxx_id;
	}

	public String getXx_njxx_id() {
		return this.xx_njxx_id;
	}

	public void setXx_njxx_id(String xx_njxx_id) {
		this.xx_njxx_id = xx_njxx_id;
	}
	
	public String getBynd() {
		return this.bynd;
	}

	public void setBynd(String bynd) {
		this.bynd = bynd;
	}
	
	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
	
	public String getCsrq() {
		return this.csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}
	
	public String getXbm() {
		return this.xbm;
	}

	public void setXbm(String xbm) {
		this.xbm = xbm;
	}
	
	public String getGrbsm() {
		return this.grbsm;
	}

	public void setGrbsm(String grbsm) {
		this.grbsm = grbsm;
	}
	
	public String getByjyny() {
		return this.byjyny;
	}

	public void setByjyny(String byjyny) {
		this.byjyny = byjyny;
	}
	
	public String getByzsh() {
		return this.byzsh;
	}

	public void setByzsh(String byzsh) {
		this.byzsh = byzsh;
	}
	
	public String getGxr() {
		return this.gxr;
	}

	public void setGxr(String gxr) {
		this.gxr = gxr;
	}
	
	public String getGxsj() {
		return this.gxsj;
	}

	public void setGxsj(String gxsj) {
		this.gxsj = gxsj;
	}
	
	public String getByjyjd() {
		return this.byjyjd;
	}

	public void setByjyjd(String byjyjd) {
		this.byjyjd = byjyjd;
	}
	
	public String getXx_bjxx_id() {
		return this.xx_bjxx_id;
	}

	public void setXx_bjxx_id(String xx_bjxx_id) {
		this.xx_bjxx_id = xx_bjxx_id;
	}
	
	public String getBysj() {
		return this.bysj;
	}

	public void setBysj(String bysj) {
		this.bysj = bysj;
	}
}