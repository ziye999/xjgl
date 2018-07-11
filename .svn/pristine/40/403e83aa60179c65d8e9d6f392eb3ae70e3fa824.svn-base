package com.jiajie.action.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction; 
import com.jiajie.service.core.ExportDataService;
public class ExportDataAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ExportDataService exportDataService;
	
	public void setExportDataService(ExportDataService exportDataService) {
		this.exportDataService = exportDataService;
	}



	public void ExportRoomData(){
		Map<String,Object> map = exportDataService.ExportRoomData();
	}
	
	public void genScoreImg(){
		exportDataService.genScoreImg();
	}
	
		
}
