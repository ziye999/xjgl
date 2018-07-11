package com.jiajie.action.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.core.MessageBoxService;
import com.jiajie.util.bean.MBspInfo;

public class MessageBoxAction extends BaseAction{
	
	private static final long serialVersionUID = -8938794117187050434L;
	@Autowired
	private MessageBoxService messageBoxService;

	public void getBox(){ 
		this.getResponse().setHeader("Content-type", "text/html;charset=UTF-8"); 
		MBspInfo bspInfo = this.getBspInfo();
		writerPrint(messageBoxService.getMessage(bspInfo));
	}

	public void setMessageBoxService(MessageBoxService messageBoxService) {
		this.messageBoxService = messageBoxService;
	}
}
