package com.jiajie.action.dailyManage;

import org.springframework.beans.factory.annotation.Autowired;
import com.jiajie.action.BaseAction;
import com.jiajie.service.dailyManage.GenerateSchoolCodeService;

@SuppressWarnings("serial")
public class GenerateSchoolCodeAction extends BaseAction{
	
	private String xxids;
	private String xxdm;

	@Autowired
	private GenerateSchoolCodeService gscService;
	
	public void getListPage(){
		String organCode =  getBspInfo().getOrgan().getOrganCode();
		writerPrint(gscService.getListPage(organCode,xxids));
	}
		
	public void generate(){
		String organCode =  getBspInfo().getOrgan().getOrganCode();
		writerPrint(gscService.generate(organCode,xxids));
	}
	
	public void update(){
		String organCode =  getBspInfo().getOrgan().getOrganCode();
		writerPrint(gscService.update(organCode,xxids,xxdm));
	}
	
	public GenerateSchoolCodeService getGscService() {
		return gscService;
	}

	public void setGscService(GenerateSchoolCodeService gscService) {
		this.gscService = gscService;
	}

	public String getXxids() {
		return xxids;
	}

	public void setXxids(String xxids) {
		this.xxids = xxids;
	}

	public String getXxdm() {
		return xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}
	
}
