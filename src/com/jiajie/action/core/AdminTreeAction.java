package com.jiajie.action.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.core.AdminTreeService;

public class AdminTreeAction extends BaseAction{
	
	private static final long serialVersionUID = -609557800330459693L;
	@Autowired
	private AdminTreeService treeService;

	public void getAdminRegionTree(){
		HttpServletRequest request =  getRequest();
		writerPrint(treeService.getAdminRegionTree(request));
	}

	public void setTreeService(AdminTreeService treeService) {
		this.treeService = treeService;
	}
		
}
