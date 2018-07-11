package com.jiajie.action.scoreModify;

import org.springframework.beans.factory.annotation.Autowired;
import com.jiajie.action.BaseAction;
import com.jiajie.service.scoreModify.ModifyScoreService;

@SuppressWarnings("serial")
public class ModifyScoreAction extends BaseAction{

	private String lcid;
	private String xnxq_id;
	private String xgkf;
	private String km;
	private String xm_kh_xjh;

	@Autowired
	private ModifyScoreService msService;
	
	public void getListPage(){
		writerPrint(msService.getListPage(xnxq_id,getBspInfo()));
	}
	
	public void autoModify(){
		writerPrint(msService.autoModify(lcid,getUserid()));
	}
	
	public void cheatListPage(){
		writerPrint(msService.cheatListPage(lcid,km,xm_kh_xjh));
	}
		
	public void manualModify(){
		writerPrint(msService.manualModify(lcid,xgkf,getUserid()));
	}
		
	public ModifyScoreService getMsService() {
		return msService;
	}

	public void setMsService(ModifyScoreService msService) {
		this.msService = msService;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	
	public String getXnxq_id() {
		return xnxq_id;
	}

	public void setXnxq_id(String xnxq_id) {
		this.xnxq_id = xnxq_id;
	}
	
	public String getXgkf() {
		return xgkf;
	}

	public void setXgkf(String xgkf) {
		this.xgkf = xgkf;
	}
	
	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getXm_kh_xjh() {
		return xm_kh_xjh;
	}

	public void setXm_kh_xjh(String xm_kh_xjh) {
		this.xm_kh_xjh = xm_kh_xjh;
	}

}
