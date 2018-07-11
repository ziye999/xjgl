package com.jiajie.action.scoreModify;

import org.springframework.beans.factory.annotation.Autowired;
import com.jiajie.action.BaseAction; 
import com.jiajie.service.scoreModify.StuScoreService;

@SuppressWarnings("serial")
public class StuScoreAction extends BaseAction{

	private String xnxq_id; 

	@Autowired
	private StuScoreService stuScoreService;
	
	public void getListPage(){
		writerPrint(stuScoreService.getListPage());
	}
	  
	
	public String getXnxq_id() {
		return xnxq_id;
	}

	public void setXnxq_id(String xnxq_id) {
		this.xnxq_id = xnxq_id;
	} 

}
