package com.jiajie.text;






import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.directwebremoting.export.Data;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.bean.system.TQxglRoleinfo;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.bean.zzjg.CusKwZkdw;
import com.jiajie.service.core.DropListService;
import com.jiajie.service.core.RoleinfoService;
import com.jiajie.service.registrationSystem.AnnuaManagementlService;
import com.jiajie.service.signup.BmshService;
import com.jiajie.service.signup.SignupService;
import com.jiajie.service.sysManagement.RoleinfoglService;
import com.jiajie.util.bean.MBspInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;


public class test extends SpringTestBase{

	@Autowired
	private AnnuaManagementlService annuaService;
	@Autowired
	private RoleinfoService roleService;
	@Autowired
	private  DropListService dropListService ;
	@Autowired
	private SignupService service;
	@Autowired
	private BmshService sBmshService;
	@Test
	public void test1() {	
		System.out.println("进来了");
		System.out.println(System.currentTimeMillis());
	       Calendar calendar = Calendar.getInstance();
	       System.out.println(new Data()+"---");
	        Date date = new Date(System.currentTimeMillis());
	        calendar.setTime(date);
//	        calendar.add(Calendar.WEEK_OF_YEAR, -1);
	        calendar.add(Calendar.YEAR, 1);
	        date = calendar.getTime();
	        System.out.println(date);

//		List list = (List) sBmshService.getObject("8a8191be63ce97fa0163ce9bed5819c7");
//		System.out.println();
//			String str = "20180921";
//			
//			String str1 = str.substring(0,4);
//			String str2 = str.substring(4, 6);
//			String str3 = str.substring(6,8);
//			
//			String str4 = str1+"-"+str2+"-"+str3;
//			System.out.println(str4);
//		  DateTime dt = new DateTime(20180921);
//		  String string =null;
//		  string.format("{0:yyyy-MM-dd}", dt);
//		  System.out.println(string);
//		  List<CusKwExamround> list3 = new ArrayList<CusKwExamround>();
//		  
//		  MBspInfo mbf = new MBspInfo();
//	System.out.println(mbf.getOrgan().getOrganCode());
//		List<CusKwZkdw> list = annuaService.getFzb();
		
//		for (CusKwZkdw cusKwZkdw : list) {
//			System.out.println(cusKwZkdw.getRegioncode()+"----"+cusKwZkdw.getRegionedu());
			
//			List<CusKwCkdw> list2 = annuaService.getCk(cusKwZkdw.getRegioncode());
//			for (CusKwCkdw cusKwCkdw : list2) {
//				if(cusKwCkdw.getXxjbxxid().equals("")&&cusKwCkdw.getXxjbxxid()==null){
//					System.out.println(cusKwZkdw.getRegioncode()+"----"+cusKwZkdw.getRegionedu());
//				}else{
//					System.out.println(cusKwCkdw.getXxjbxxid()+"===="+cusKwZkdw.getRegionedu()+"----"+cusKwCkdw.getXxmc());
//				}
				
			
//			}
//		}
		
//		List list = (List) service.getList();
//		System.out.println(list.size());
//
//		List<ZxxsXnxq> z = (List<ZxxsXnxq>) dropListService.getXnxqListPage();
		
//		List<TQxglRoleinfo> lis = roleService.findAll();
//		System.out.println(lis.getClass());
//		for (TQxglRoleinfo tQxglRoleinfo : lis) {
//			System.out.println(tQxglRoleinfo.getRolename());
//		}
//		 List<ZxxsXnxq> list = annuaService.select();
////		 List<ZxxsXnxq> zList  = new ArrayList<ZxxsXnxq>();
//		
//		 System.out.println(list.getClass());
//	
//		
//		
//		PageBean pageBean = annuaService.getList();
//		System.out.println(pageBean.getResultList()+"===--=--=");
//		 List<Map<String, Object> >  lmap= new ArrayList<Map<String, Object>>();
//		 for (int i = 0; i < list.size(); i++) {
//			 map.put("i",list.get(i));
//		}
//		 System.out.println(map.get(1));
//		 System.out.println(map.getClass());
//		 Iterator iter = map.entrySet().iterator();  //获得map的Iterator
//			while(iter.hasNext()) {
//				Entry entry = (Entry)iter.next();
//				zList.add((ZxxsXnxq) entry.getValue());
//			}
//		 for (ZxxsXnxq zxxsXnxq : zList) {
//			System.out.println(zxxsXnxq.getXnmc());
//		}
//		 List<ZxxsXnxq> zList  = new ArrayList<ZxxsXnxq>();
//		 ZxxsXnxq z = new ZxxsXnxq();
//		
//		for (ZxxsXnxq zxxsXnxq : list) {
//			System.out.print(zxxsXnxq.getXnxqid()+"-------");
//			System.out.println(zxxsXnxq.getXnmc());
//		}	
//		System.out.println("引入了");
//		
//		//根据id查询数据
//		String aString = "8a8191e963063bd50163063ce7880002";
//		ZxxsXnxq str = annuaService.sle(aString);
//		
//		String xnxString= str.getXnmc()+","+str.getXqmc();
//
//		System.out.print(str.getXnxqid()+"-------");
//		System.out.println(str.getXnmc());
//	
		//根据条件查询
//		String xnmc = "";
//		String xqmc = "";
//		String xxkssj = "";
//		String xxjssj = "";
//
//		PageBean page = annuaService.getListPage(xnmc,xqmc, xxkssj, xxjssj);
//		System.err.println(page.getTotalPages());
//		System.out.println(page.getResultList());
//	
//		
//		List<ZxxsXnxq> list2 = annuaService.selectZxxsXnxqs(xnmc, xqmc, xxkssj, xxjssj);
//
//		for (ZxxsXnxq zxxsXnxq : list2) {
//			System.out.println(zxxsXnxq.getXnxqid()+"======"+zxxsXnxq.getXnmc());
//		}
//		annuaService.delZxxsXnxq("1");
		//查询轮次数
		
//		System.out.println(a);
	
		//根据id查询数据
//		ZxxsXnxq zXnxq = annuaService.sle("ce20800bbfec4ef6920d53a5236cde73");
//		
//		List list3 = annuaService.selectCuskw(zXnxq.getXnmc());
//		int a = list3.size();
//		String s = String.valueOf(a);
//		System.out.println(s+"====");
//		zXnxq.setGxr(s);
//		
//		MsgBean ms1 = annuaService.saveZxq(zXnxq);
//		//添加数据
//		ZxxsXnxq zxxsXnxq = new ZxxsXnxq();
//		zxxsXnxq.setXnmc("2019");
//		zxxsXnxq.setXxkssj("2019-08-21");
//		zxxsXnxq.setXxjssj("2019-12-01");
//		zxxsXnxq.setXqmc("1");
//		zxxsXnxq.setYxbz("1");
//		
//		MsgBean ms = annuaService.saveZxq(zxxsXnxq);
//		
//		System.out.println(ms);
//		//修改数据
//		//annuaService.update(zxxsXnxq);
//				
//		
//		System.out.println("添加成功");
//		
//		
		//根据id修改数据
		//annuaService.updateZxxsXnxq("1", "2018","1" , "20180413", "20180701", "1");
		
		
		//根据id删除数据
//		annuaService.delZxxsXnxq("2");
		
//		System.out.println("删除成功");
		
		
		
	}
}
