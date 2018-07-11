package com.jiajie.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jiajie.bean.MsgBean;
import com.jiajie.dao.PageManager;

public class ServiceBridge extends PageManager{
	protected  Log logger = LogFactory.getLog(ServiceBridge.class);
	protected MsgBean MsgBean;
	public MsgBean getMsgBean(boolean success,String msg,Object data,boolean show){
		if(this.MsgBean==null){
			MsgBean = new MsgBean();
		}
		MsgBean.setSuccess(success);
		MsgBean.setMsg(msg);
		MsgBean.setData(data);
		MsgBean.setShow(show);
		return this.MsgBean;
	}
	public MsgBean getMsgBean(Object data){
		return this.getMsgBean(true, null, data,true);
	}
	public MsgBean getMsgBean(boolean success,String msg){
		return this.getMsgBean(success, msg, true);
	}
	public MsgBean getMsgBean(String msg,Object result){
		return this.getMsgBean(true, msg, result,true);
	}
	public MsgBean getMsgBean(boolean success,String msg,boolean show){
		return this.getMsgBean(success, msg, null,show);
	}
	public MsgBean getMsgBean(boolean success){
		return this.getMsgBean(success, null, null,false);
	}
	public MsgBean getMsgBean(Object data,boolean show){
		return this.getMsgBean(true, null, data,show);
	}
	
	protected boolean isNull(String value){
		if(value==null){
			return true;
		}
		if(value.equals("")){
			return true;
		}
		if(value.equals("null") || value.equals("NULL") || value.equals("undefined")){
			return true;
		}
		return false;
		
	}
}
