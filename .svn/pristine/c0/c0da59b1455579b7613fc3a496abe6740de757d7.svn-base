package com.jiajie.action.rollReport; 

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import com.jiajie.action.BaseAction; 
import com.jiajie.service.rollReport.CertificatePrintService;
import com.jiajie.util.bean.MBspInfo;

@SuppressWarnings("serial")
public class CertificatePrintAction extends BaseAction{
	private Integer start;
	private Integer limit;
	private String xjh;
	private Integer zstype;
	private String zgjyj;
	
	public String getZgjyj() {
		return zgjyj;
	}

	public void setZgjyj(String zgjyj) {
		this.zgjyj = zgjyj;
	}

	public Integer getZstype() {
		return zstype;
	}

	public void setZstype(Integer zstype) {
		this.zstype = zstype;
	}

	public String getXjh() {
		return xjh;
	}

	public void setXjh(String xjh) {
		this.xjh = xjh;
	}
	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	private CertificatePrintService certificatePrintService;
	@Autowired
	public void setCertificatePrintService(
			CertificatePrintService certificatePrintService) {
		this.certificatePrintService = certificatePrintService;
	}

	public String getStuCertiInfo(){   
		MBspInfo mp = getBspInfo();
		HttpServletRequest request = getRequest();
		request.setAttribute("stulist", certificatePrintService.getStuCertiInfo(mp,xjh,zstype,zgjyj));
		request.setAttribute("zstype",zstype);
		return "certificatePrint";
	}
	
}
