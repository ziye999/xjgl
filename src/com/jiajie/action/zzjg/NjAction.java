package com.jiajie.action.zzjg;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.zzjg.NjService;

@SuppressWarnings("serial")
public class NjAction extends BaseAction{	
	
	@Autowired
	private NjService service;
		
	public void getListPage() {
		HttpServletRequest request =  getRequest();
		String xx_jbxx_id = request.getParameter("xx_jbxx_id")==null?"":request.getParameter("xx_jbxx_id").toString();
		String njmc = request.getParameter("njmc")==null?"":request.getParameter("njmc").toString();
		writerPrint(service.getList(xx_jbxx_id, njmc, getBspInfo()));
	}
	
	public void del(){
		HttpServletRequest request =  getRequest();
		String xx_njxx_id = request.getParameter("xx_njxx_id")==null?"":request.getParameter("xx_njxx_id").toString();
		writerPrint(service.del(xx_njxx_id));
	}
	
	public void update(){
		HttpServletRequest request =  getRequest();
		String xx_njxx_id = request.getParameter("xx_njxx_id")==null?"":request.getParameter("xx_njxx_id").toString();
		String xx_jbxx_id = request.getParameter("xx_jbxx_id")==null?"":request.getParameter("xx_jbxx_id").toString();;
		String njmc = request.getParameter("njmc")==null?"":request.getParameter("njmc").toString();;
		String rxnf = request.getParameter("rxnf")==null?"":request.getParameter("rxnf").toString();;
		String jyjd = request.getParameter("jyjd")==null?"":request.getParameter("jyjd").toString();;
		String djdm = request.getParameter("djdm")==null?"":request.getParameter("djdm").toString();;
		String njzt = request.getParameter("njzt")==null?"":request.getParameter("njzt").toString();;
		writerPrint(service.update(xx_njxx_id,xx_jbxx_id,njmc,rxnf,jyjd,djdm,njzt)); 
	}
		
	public void setService(NjService service) {
		this.service = service;
	}	
	
}
