package com.jiajie.action.registrationSystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.net.aso.s;

import org.apache.poi.ss.usermodel.DataFormat;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.service.exambasic.ExamRoundService;
import com.jiajie.service.registrationSystem.AnnuaManagementlService;
import com.jiajie.util.bean.MBspInfo;
import com.opensymphony.xwork2.ActionContext;

/**
 * 年度管理
 * */

public class AnnuaManagementlAction extends BaseAction{

	@Autowired
	private AnnuaManagementlService service;
	@Autowired
	 private ExamRoundService ronRoundservice;
	
	public ExamRoundService getRonRoundservice() {
		return ronRoundservice;
	}
	public void setRonRoundservice(ExamRoundService ronRoundservice) {
		this.ronRoundservice = ronRoundservice;
	}
	private	String xnmc;
	private String xqmc ;
	private String xxkssj ;
	private String xxjssj ;
	private ZxxsXnxq zxxsXnxq;
	private String xnxqid;
	private String yxbz;
	private String xnxqids;
	private String XN;
	private String gxr;
	
	//查询数据
	public void getList(){
		writerPrint(service.getList());
	}
	//根据条件查询数据
	public void getListPage(){
		String xn ="";
		String xq ="";
		String xq1 ="";
		if(XN!=null && !"".equals(XN)){
			String[] str = XN.split(" ");
			xn = str[0].substring(0, 4);
			xq = str[1].substring(1,2);	
	
		}
		String xxk="";
		if(xxkssj!=null && !"".equals(xxkssj)){
			String [] b = xxkssj.split("-|T", 10);
			 xxk = b[0]+b[1]+b[2];
			System.out.println(xxk);
	
		}
		String xxj ="";
		if(xxjssj!=null && !"".equals(xxjssj)){
			String [] b = xxjssj.split("-|T", 10);
			xxj = b[0]+b[1]+b[2];
			System.out.println(xxj);
	
		}
		PageBean pageBean = service.getListPage(xn, xq, xxk, xxj);
		writerPrint(pageBean);	
	}
	//根据id查询
	public void getZxxs(){
		writerPrint(this.service.getZxxs(xnxqid));
	}
	//删除数据
	public void deleteZxxsXnxq(){
		writerPrint(service.delZxxsXnxq(xnxqids));
	}
	//修改数据
	public void updateZxxsXnxq(){
		String [] b = xxkssj.split("-");
		String xxk = b[0]+b[1]+b[2];
		String [] c = xxjssj.split("-");
		String xxj = c[0]+c[1]+c[2];
	
		writerPrint(service.updateZxq(xnxqid, xnmc, xqmc, xxk, xxj, yxbz,gxr));
	}
	//添加数据
	public void saveZxxsXnxq(){
		ZxxsXnxq zx = new ZxxsXnxq();
		//分割字符
		
		zx.setXnmc(xnmc);
		zx.setXqmc(xqmc);
		//数据库长度大小不够，传过来的的是2018-05-09格式，大于数据库长度
		String [] b = xxkssj.split("-");
		String xxk = b[0]+b[1]+b[2];
		String [] c = xxjssj.split("-");
		String xxj = c[0]+c[1]+c[2];
		System.out.println(xxk);
		zx.setXxkssj(xxk);
		zx.setXxjssj(xxj);
		
	
	    List list3 = service.selectCuskw(xnmc,xqmc);
		int a = list3.size();
		String s = String.valueOf(a);
			
		zx.setGxr(s);
		
		if(a>0){
			zx.setYxbz("1");
		}
	    if ("".equals(xnxqid)) {
		     zx.setXnxqid(null);
		}else {
			zx.setXnxqid(xnxqid);
		}
		MsgBean msgBean = service.saveZxq(zx,xxkssj,xxjssj);
		writerPrint(msgBean);
	
		System.out.println("上传成功");
	}
	//启动轮次
	  public void addBuilding() {
		  MBspInfo m =getBspInfo();
		  
		  CusKwExamround examround = new CusKwExamround();
		  //获取系统当前时间
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
			System.out.println(df.format(date)+"-结束--");
			System.out.println(df.format(data));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			  
	   
	    String usertype = getBspInfo().getUserType();
//	    examround.setDwtype(getBspInfo().getUserType());
	   
	    xnxqids=xnxqids.replace("'","");
	    ZxxsXnxq str =  service.sle(xnxqids);
		String xnxString= str.getXnmc()+","+str.getXqmc();

		examround.setXnxqId(xnxString);
		

		examround.setExamtypem("1");
	    writerPrint(service.saveOrUpdateExamround(examround, usertype));
	    //查询轮次数
	    List list3 = service.selectCuskw(str.getXnmc(),str.getXqmc());
		int a = list3.size();
		String s = String.valueOf(a);
		if(a>0){
			str.setGxr(s);
			str.setYxbz("1");
			service.saveZxq(str,str8,str4);	
		}
		
	  }
	public AnnuaManagementlService getService() {
		return service;
	}

	public void setService(AnnuaManagementlService service) {
		this.service = service;
	}

	public String getXnmc() {
		return xnmc;
	}

	public void setXnmc(String xnmc) {
		this.xnmc = xnmc;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getXxkssj() {
		return xxkssj;
	}

	public void setXxkssj(String xxkssj) {
		this.xxkssj = xxkssj;
	}

	public String getXxjssj() {
		return xxjssj;
	}

	public void setXxjssj(String xxjssj) {
		this.xxjssj = xxjssj;
	}
	public ZxxsXnxq getZxxsXnxq() {
		return zxxsXnxq;
	}
	public void setZxxsXnxq(ZxxsXnxq zxxsXnxq) {
		this.zxxsXnxq = zxxsXnxq;
	}
	public String getXnxqid() {
		return xnxqid;
	}
	public void setXnxqid(String xnxqid) {
		this.xnxqid = xnxqid;
	}
	public String getYxbz() {
		return yxbz;
	}
	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	public String getXnxqids() {
		return xnxqids;
	}
	public void setXnxqids(String xnxqids) {
		this.xnxqids = xnxqids;
	}
	public String getXN() {
		return XN;
	}
	public void setXN(String xN) {
		XN = xN;
	}
	public String getGxr() {
		return gxr;
	}
	public void setGxr(String gxr) {
		this.gxr = gxr;
	}

	
	
	
}
