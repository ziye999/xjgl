package com.jiajie.action.zzjg;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.zzjg.BjService;

@SuppressWarnings("serial")
public class BjAction extends BaseAction{
	
	@Autowired
	private BjService service;
	
	public void getListPage() {
		HttpServletRequest request =  getRequest();
		String xx_jbxx_id = request.getParameter("xx_jbxx_id")==null?"":request.getParameter("xx_jbxx_id").toString();
		String bjmc = request.getParameter("bjmc")==null?"":request.getParameter("bjmc").toString();
		writerPrint(service.getList(xx_jbxx_id,bjmc, getBspInfo()));
	}
	
	public void del(){
		HttpServletRequest request =  getRequest();
		String xx_bjxx_id = request.getParameter("xx_bjxx_id")==null?"":request.getParameter("xx_bjxx_id").toString();
		writerPrint(service.del(xx_bjxx_id));
	}
	
	public void update(){
		HttpServletRequest request =  getRequest();
		String xx_njxx_id = request.getParameter("xx_njxx_id")==null?"":request.getParameter("xx_njxx_id").toString();
		String xx_bjxx_id = request.getParameter("xx_bjxx_id")==null?"":request.getParameter("xx_bjxx_id").toString();
		String xx_jbxx_id = request.getParameter("xx_jbxx_id")==null?"":request.getParameter("xx_jbxx_id").toString();
		String bjmc = request.getParameter("bjmc")==null?"":request.getParameter("bjmc").toString();
		String bjzt = request.getParameter("bjzt")==null?"":request.getParameter("bjzt").toString();
		writerPrint(service.update(xx_bjxx_id,xx_jbxx_id,bjmc,xx_njxx_id,bjzt)); 
	}
	
	public void setService(BjService service) {
		this.service = service;
	}
	
}
