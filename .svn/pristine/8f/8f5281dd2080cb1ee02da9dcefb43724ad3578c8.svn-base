package com.jiajie.action.zzjg;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.exambasic.CusKwbmExamround;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.bean.zzjg.CusKwZkdw;
import com.jiajie.service.zzjg.CkdwService;
import com.jiajie.util.FunctionMenu;
import com.jiajie.util.bean.MBspInfo;

@SuppressWarnings("serial")
public class CkdwAction extends BaseAction{
	
	private CusKwCkdw ckdw;	
	private String xxdms;
	private String mr;
	private String examname;
	private String zk;
	private File upload;
	private String uploadFileName;
	private String uploadContentType; 
	
	@Autowired
	private CkdwService service;
	
	public void getListPage() {
		String dwlx = getRequest().getParameter("dwlx");
		writerPrint(service.getList(ckdw, dwlx, getBspInfo()));
	}
		
	public void loadCkdw(){
		writerPrint(service.getCkdw(ckdw.getXxjbxxid()));
	}
	
	public void addCkdw() {
		if (ckdw.getXxjbxxid()==null||"".equals(ckdw.getXxjbxxid())) {
			ckdw.setLrr(getBspInfo().getUserId());
		}else {
			ckdw.setGxr(getBspInfo().getUserId());
		}
		String dwlx = getRequest().getParameter("dwlx");
		ckdw.setDwlx(dwlx);
		MsgBean msgBean = service.saveOrUpdateCkdw(ckdw);
		writerPrint(msgBean);
	}
		
	public void delCkdw() {
		writerPrint(service.delCkdw(xxdms));
	}
		
	public void getZkdw() {
		writerPrint(service.getZkdw(getBspInfo()));
	}
	//查询默认年度
	public void getZx(){
		writerPrint(service.getZx());
	}
	//添加报名轮次
	public void save(){

		CusKwbmExamround  examround = new CusKwbmExamround();
	  
	
	
	    if ("".equals(examround.getBmlcid())) {
	      examround.setBmlcid(null);
	    }
	    List<CusKwZkdw> zk1 =  service.getFzb(zk);
	    String SSZGJYXZDM = null ;
	    String lcid = null;
	    String xnmc =null;
	    String xqmc = null;
	    String xxkssj = null;
	    String xxjssj =null;
	    String xnxString =null;
	    String dwid =null;
	    String dwlx=null;
	    String SSZGJYXZDM1=null;
	    for (CusKwZkdw cusKwZkdw : zk1) {
			SSZGJYXZDM = cusKwZkdw.getRegioncode();
		}
	 
	    List<CusKwCkdw> ck = service.getCk(examname);
	    for (CusKwCkdw cusKwCkdw : ck) {
	    	SSZGJYXZDM1 = cusKwCkdw.getSszgjyxzdm();
			dwid = cusKwCkdw.getXxjbxxid();
			dwlx = cusKwCkdw.getDwlx();
		}
	    List<ZxxsXnxq> zx = service.getZ();
		for (ZxxsXnxq zxxsXnxq : zx) {
			xnmc = zxxsXnxq.getXnmc();
			xqmc = zxxsXnxq.getXqmc();
			xxjssj = zxxsXnxq.getXxjssj();
			xxkssj = zxxsXnxq.getXxkssj();
			xnxString= zxxsXnxq.getXnmc()+","+zxxsXnxq.getXqmc();
			
		}
		List<CusKwExamround> cu = service.getkl(SSZGJYXZDM,xnxString);
	    for (CusKwExamround cusKwExamround : cu) {
			lcid = cusKwExamround.getLcid();
		}
	  	String str5 = xxkssj.substring(0,4);
		String str6 = xxkssj.substring(4, 6);
		String str7 = xxkssj.substring(6,8);
						
		String str8 = str5+"-"+str6+"-"+str7;
			  
		String str1 = xxjssj.substring(0,4);
		String str2 = xxjssj.substring(4, 6);
		String str3 = xxjssj.substring(6,8);
				
		String str4 = str1+"-"+str2+"-"+str3;
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 try {
			//开始时间和结束时间
			 Date data = df.parse(str8);
			Date date = df.parse(str4);
			examround.setStartdate(data);
			examround.setEnddate(date);
			System.out.println(data+"---");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		examround.setDwid(dwid);
		examround.setXnxqId(xnxString);
	    examround.setLcid(lcid);
	    examround.setExamname(examname);
	    examround.setExamtypem("1");
	    String usertype = getBspInfo().getUserType();
	    examround.setDwtype(dwlx);
	    writerPrint(service.saveBm(examround, usertype));
	  	HttpServletRequest request = getRequest();
				ApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
				// 加载菜单到缓存
				FunctionMenu.getMenuInstance().initMenuInfoToCache(wac);
				// 加载权限组到缓存		
				FunctionMenu.getMenuInstance().initRoleInfoToCache(wac);
				// 加载组菜单到缓存		
				FunctionMenu.getMenuInstance().initRoleMenuToCache(wac);
				// 加载用户组到缓存		
				FunctionMenu.getMenuInstance().initUserRoleToCache(wac);
	  
		
	}
	//导入参考单位
	  public void exportKsInfo() {
		    try {
		    	MBspInfo m =getBspInfo();
		      MsgBean mb = service.exportKsInfo1(this.upload,getBspInfo());
		      HttpServletResponse response = getResponse();
		      response.setCharacterEncoding("UTF-8");
		      response.setContentType("text/html; charset=gbk");
		  	HttpServletRequest request = getRequest();
			ApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
			// 加载菜单到缓存
			FunctionMenu.getMenuInstance().initMenuInfoToCache(wac);
			// 加载权限组到缓存		
			FunctionMenu.getMenuInstance().initRoleInfoToCache(wac);
			// 加载组菜单到缓存		
			FunctionMenu.getMenuInstance().initRoleMenuToCache(wac);
			// 加载用户组到缓存		
			FunctionMenu.getMenuInstance().initUserRoleToCache(wac);
		      response.getWriter().print(new Gson().toJson(mb));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
	  //删除报名轮次
	  public void deletebm(){
		 writerPrint(service.deletebm(xxdms));
	  }
		
			
	public void setService(CkdwService service) {
		this.service = service;
	}
	public CusKwCkdw getCkdw() {
		return ckdw;
	}
	public void setCkdw(CusKwCkdw ckdw) {
		this.ckdw = ckdw;
	}
	public String getXxdms() {
		return xxdms;
	}
	public void setXxdms(String xxdms) {
		this.xxdms = xxdms;
	}

	public String getMr() {
		return mr;
	}

	public void setMr(String mr) {
		this.mr = mr;
	}

	public String getExamname() {
		return examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public String getZk() {
		return zk;
	}

	public void setZk(String zk) {
		this.zk = zk;
	}

	public CkdwService getService() {
		return service;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}	
	
}
