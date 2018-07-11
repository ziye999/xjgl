package com.jiajie.action.rollReport;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.PageBean;
import com.jiajie.service.rollReport.RollCardService; 

@SuppressWarnings("serial")
public class RollCardAction extends BaseAction{
	private List xsList;
	@Autowired
	private RollCardService cardService;

	public String selectPicInfo(){
		HttpServletRequest request = getRequest(); 
		String organ = request.getParameter("organ")==null?"":request.getParameter("organ").toString();
		String bjids = request.getParameter("bjids")==null?"":request.getParameter("bjids").toString();
		request.setAttribute("list", cardService.getPicList(organ, bjids));
		request.setAttribute("classlist", cardService.getSchoolClassList(organ, bjids));
		return "picPrint";
	}

	public void getClassListPage(){
		HttpServletRequest request = getRequest();
		String orgn = request.getParameter("organ")==null?"":request.getParameter("organ").toString();
		String nj = request.getParameter("nj")==null?"":request.getParameter("nj").toString(); 
		writerPrint(cardService.getClassList(orgn,nj,getBspInfo()));
	}
		
	public void getListPage(){
		HttpServletRequest request = getRequest();
		String orgn = request.getParameter("organ")==null?"":request.getParameter("organ").toString();
		String nj = request.getParameter("nj")==null?"":request.getParameter("nj").toString();
		String njbj = request.getParameter("njbj")==null?"":request.getParameter("njbj").toString();
		String xbm = request.getParameter("xbm")==null?"":request.getParameter("xbm").toString();
		String xmxjh = request.getParameter("xmxjh")==null?"":request.getParameter("xmxjh").toString();
		writerPrint(cardService.getList(orgn,nj,njbj,xbm,xmxjh,getBspInfo()));
	}
	
	public String selectStudentInfo(){
		HttpServletRequest request = getRequest();
		String xjh = request.getParameter("xjh")==null?"":request.getParameter("xjh").toString();
		PageBean pb = cardService.getStudentList(xjh);
		xsList = pb.getResultList();
		return "rollCard";
	}
		
	public String selectStudentRoster(){
		HttpServletRequest request = getRequest();
		String xjh = request.getParameter("xjh")==null?"":request.getParameter("xjh").toString();
		PageBean pb = cardService.getStudentList(xjh);
		xsList = pb.getResultList();
		return "studentRoster";
	}
	
	public List getXsList() {
		return xsList;
	}

	public void setXsList(List xsList) {
		this.xsList = xsList;
	}

	public RollCardService getCardService() {
		return cardService;
	}

	public void setCardService(RollCardService cardService) {
		this.cardService = cardService;
	}
	
	
}
