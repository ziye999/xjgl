package com.jiajie.action.dailyManage;

import org.springframework.beans.factory.annotation.Autowired;
import com.jiajie.action.BaseAction;
import com.jiajie.service.dailyManage.BreakStudyService;

@SuppressWarnings("serial")
public class BreakStudyAction extends BaseAction{

	private String schools;
	private String flag;
	private String xm_xjh_sfz;
	private String yyids;
	@Autowired
	private BreakStudyService bsService;
	
	public void getListPage(){
		String organCode =  getBspInfo().getOrgan().getOrganCode();
		writerPrint(bsService.getListPage(organCode,schools,flag,xm_xjh_sfz));
	}
	
	public void audit(){
		yyids = yyids.substring(0,yyids.length()-1);
		yyids = yyids.replaceAll(",","','");
		writerPrint(bsService.audit(yyids));
	}

	public String getYyids() {
		return yyids;
	}

	public void setYyids(String yyids) {
		this.yyids = yyids;
	}

	public String getSchools() {
		return schools;
	}

	public void setSchools(String schools) {
		this.schools = schools;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getXm_xjh_sfz() {
		return xm_xjh_sfz;
	}

	public void setXm_xjh_sfz(String xm_xjh_sfz) {
		this.xm_xjh_sfz = xm_xjh_sfz;
	}

	public BreakStudyService getBsService() {
		return bsService;
	}

	public void setBsService(BreakStudyService bsService) {
		this.bsService = bsService;
	}
	
}
