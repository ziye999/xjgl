package com.jiajie.action.cheatStu;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.cheatStu.CusKwCheatstu;
import com.jiajie.service.cheatStu.CheatStudentService;

@SuppressWarnings("serial")
public class CheatStudentAction extends BaseAction{
	private CusKwCheatstu cheatStu = new CusKwCheatstu();
	private String xnxqId;
	private String wjids;
	private String lcid;
	private List cheatList;
	
	@Autowired
	private CheatStudentService cheatService;

	/**
	 * 根据考试轮次id，获取当前轮次的违纪考生
	 */
	public void getListPage(){
		HttpServletRequest request = getRequest();
		String xn="";
		String xq="";
		String xnxq = request.getParameter("xnxqId")==null?"":request.getParameter("xnxqId").toString();
		String flag = request.getParameter("flag")==null?"":request.getParameter("flag").toString();
		String lcidP = request.getParameter("lcid")==null?lcid:request.getParameter("lcid").toString();
		String schools = request.getParameter("schools")==null?"":request.getParameter("schools").toString();
		String xmkhxjh = request.getParameter("xmkhxjh")==null?"":request.getParameter("xmkhxjh").toString();
		String wjid = request.getParameter("wjid")==null?"":request.getParameter("wjid").toString();
		if (lcidP != null && !"".equals(lcidP)) {
			writerPrint(cheatService.getListByLcid(lcid,schools,xmkhxjh,getBspInfo(),flag,wjid));
		}else {
			if(xnxq != null && !"".equals(xnxq)){
				String [] str = xnxq.split(",");
				xn = str[0].toString();
				xq = str[1].toString();
			}
			writerPrint(cheatService.getList(xn,xq,getBspInfo()));
		}		
	}	
	
	public void addCheatStu(){
		cheatStu.setCjr(getUserid());
		cheatStu.setCdate(new Date());
		if("".equals(cheatStu.getWjid())) {
			cheatStu.setWjid(null);
		}
		writerPrint(cheatService.saveOrUpdateCheatStu(cheatStu,getBspInfo()));
	}
	
	public void delCheatStudent() {
		writerPrint(cheatService.deleteCheatStu(wjids));
	}
	
	public void reportedCheatStudent(){
		writerPrint(cheatService.reportedCheatStu(lcid));
	}
	
	/**
	 * 获取所有考试轮次
	 */
	public void getExamRound(){
		HttpServletRequest request = getRequest();
		String xn="";
		String xq="";
		String xnxq = request.getParameter("xnxqId")==null?"":request.getParameter("xnxqId").toString();
		if(xnxq != null && !"".equals(xnxq)){
			String [] str = xnxq.split(",");
			xn = str[0].toString();
			xq = str[1].toString();
		}
		writerPrint(cheatService.getExamRound(xn,xq,getBspInfo()));
	}
	
	public void auditCheatStudent(){
		writerPrint(cheatService.auditCheatStudent(lcid));
	}
	
	public String getCheatStudentByLcid(){
		HttpServletRequest request = getRequest();
		//根据审核情况查询
		String flag = request.getParameter("flag")==null?"":request.getParameter("flag").toString();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String schools = request.getParameter("schools")==null?"":request.getParameter("schools").toString();
		String xmkhxjh = request.getParameter("xmkhxjh")==null?"":request.getParameter("xmkhxjh").toString();
		String wjid = request.getParameter("wjid")==null?"":request.getParameter("wjid").toString();
		PageBean pb = cheatService.getListByLcid(lcid,schools,xmkhxjh,getBspInfo(),flag,wjid);
		cheatList = pb.getResultList();
		return "printData";
	}
	
	public CheatStudentService getCheatService() {
		return cheatService;
	}

	public void setCheatService(CheatStudentService cheatService) {
		this.cheatService = cheatService;
	}

	public CusKwCheatstu getCheatStu() {
		return cheatStu;
	}

	public void setCheatStu(CusKwCheatstu cheatStu) {
		this.cheatStu = cheatStu;
	}

	public String getXnxqId() {
		return xnxqId;
	}

	public void setXnxqId(String xnxqId) {
		this.xnxqId = xnxqId;
	}

	public String getWjids() {
		return wjids;
	}

	public void setWjids(String wjids) {
		this.wjids = wjids;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public List getCheatList() {
		return cheatList;
	}

	public void setCheatList(List cheatList) {
		this.cheatList = cheatList;
	}
		
}
