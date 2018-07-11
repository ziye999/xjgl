package com.jiajie.service.registrationSystem.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import net.rubyeye.xmemcached.transcoders.StringTranscoder;
import net.sf.ehcache.pool.Size;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import sun.text.normalizer.IntTrie;



import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.examArrangement.CusKwExamschool;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.exambasic.CusKwbmExamround;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.bean.zzjg.CusKwZkdw;
import com.jiajie.dao.DaoSupport;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.registrationSystem.AnnuaManagementlService;
import com.jiajie.util.StringUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

import freemarker.cache.MruCacheStorage;

@Service("annuaManagementlService")

public class AnnuaManagementlServiceImpl  extends ServiceBridge implements AnnuaManagementlService{
	
	
	//查询所有的年度
	public List<ZxxsXnxq> select() {
//
		String sql = "select  * from zxxs_xnxq where yxbz=1";
//		List<ZxxsXnxq> list =  getListSQL(sql);
//		
		String hql = " from ZxxsXnxq where yxbz=1";
		List<ZxxsXnxq> list =  seleList(hql);
//		findAllOrder(ZxxsXnxq.class,"XNXQID");
//		
		return list;
	}
	

	//修改数据
	public void updateZxxsXnxq(String xnxqid,String xnmc,String xqmc ,
			String xxkssj,String xxjssj,String yxbz) {
		String sql = "";
		if(StringUtil.isNotNullOrEmpty(xnxqid.trim())){
			sql = "update zxxs_xnxq set xnmc='"+xnmc+"',xqmc='"+xqmc+"',xxkssj='"+xxkssj+"',xxjssj='"+xxjssj+"',yxbz='"+yxbz+"' where xnxq_id='"+xnxqid+"'";
		}
		update(sql);
	
	}

	//删除数据

	public MsgBean delZxxsXnxq(String xnxqid) {
	
			String sql = "delete from zxxs_xnxq where xnxq_id="+xnxqid+"";
			delete(sql);
			getMsgBean(true, MsgBean.DEL_SUCCESS);
			return this.MsgBean;
	}
	//根据条件查询
	public List<ZxxsXnxq> selectZxxsXnxqs(String xnmc, String xqmc,
			String xxkssj, String xxjssj) {
		
		StringBuffer hql = new StringBuffer(" from ZxxsXnxq where yxbz=1 and 1=1 ");
		
		if(!xnmc.equals("")&& xnmc != null){
			hql.append("and xnmc = "+xnmc);
		}
		if(!xqmc.equals("")&& xqmc != null){
			hql.append("and xqmc = "+xqmc);
		}
		if(!xxkssj.equals("")&& xxkssj != null){
			hql.append("and xxkssj = "+xxkssj);
		}
		if(!xxjssj.equals("")&& xxjssj != null){
			hql.append("and xxjssj = "+xxjssj);
		}
//		 if(xnmc!=string.Empty){
//			 sql +="and xnmc= '"+xnmc+"'";
//		 }

		String hql2 = hql.toString();
		List<ZxxsXnxq> list = seleList(hql2);
		
		return list;
	}
	//根据id查询数据
	public ZxxsXnxq sle(String xnxqid) {
		return (ZxxsXnxq) get(ZxxsXnxq.class, xnxqid);
	}
	//根据参考单位id查询
	public CusKwCkdw selectCkdw(String xxjbxxid){
		return (CusKwCkdw) get(CusKwCkdw.class, xxjbxxid);
	}
	//查询轮次数
	public int selCusKw(String xnmc) {
//		String sql = "SELECT COUNT(1) FROM cus_kw_examround  c  JOIN  zxxs_xnxq  z  ON  c.xn = z.xnmc  WHERE z.xnmc ='"+ xnmc +"'";
		getSession().clear();
		String sql = "select count(c.xn) from CusKwExamround c,ZxxsXnxq z where c.xn = z.xnmc and z.xnmc ='"+ xnmc +"'"; 
		Number count = (Number) getSession().createQuery(sql).uniqueResult();
//		Number count = (Number) getSession().createSQLQuery(sql).uniqueResult();
		int a =count.byteValue();
		System.out.println(count.byteValue()+"=====");
		return a;
	}
	//查询轮次数
	public List selectCuskw(String xnmc,String xqmc) {
		
		String hql = "from CusKwExamround c where c.xn = '"+ xnmc +"' and c.xqm ='"+xqmc+"'";
		
		List list = getSession().createQuery(hql).list();
		
		return list;
	}
	//查询报名轮次数
	public List selectCuskwbm(String xnmc,String xqmc) {
		
		String hql = "from CusKwbmExamround c where c.xn = '"+ xnmc +"' and c.xqm ='"+xqmc+"'";
		
		List list = getSession().createQuery(hql).list();
		
		return list;
	}

	public PageBean getList() {
		String sql = "select   z.XNXQ_ID,z.XNMC,z.XQMC,z.XXKSSJ,z.XXJSSJ,z.YXBZ,z.GXR from zxxs_xnxq z where z.YXBZ =1";
		return getSQLPageBean(sql);
	}

	public PageBean getListPage(String xnmc, String xqmc, String xxkssj,
			String xxjssj) {
		
		 String sql = "select z.XNXQ_ID,z.XNMC,(SELECT mc FROM `zd_xq` z1 WHERE z1.dm = z.xqmc) XQMC,z.XXKSSJ,z.XXJSSJ,case when z.YXBZ = 1 then '已开始' else '未开始' end as YXBZ,z.GXR,z.cklc  " +
			 		"from zxxs_xnxq z " +
			 		" where  1 = 1 ";
			 
			 
			 if(xnmc != null && !xnmc.equals("") ){
				 
				 sql = sql + " and z.XNMC='" + xnmc + "'";
			}
			if(xqmc != null && !xqmc.equals("")){
		
				sql = sql + " and z.XQMC='" + xqmc + "'";
			}
			if( xxkssj != null && !xxkssj.equals("")){
		
				sql = sql + " and z.XXKSSJ='" + xxkssj + "'";
			}
			if(xxjssj != null && !xxjssj.equals("")){

				sql = sql + " and z.XXJSSJ='" + xxjssj + "'";
			}

			return getSQLPageBean(sql);

	}


	public MsgBean saveZxq(ZxxsXnxq zxxsXnxq,String xxkssj,String xxjssj){
		List list1 = selectCuskwbm(zxxsXnxq.getXnmc(),zxxsXnxq.getXqmc());
		int string = list1.size();
		String s = String.valueOf(string);
		zxxsXnxq.setCklc(s);
		String mr;
      
    	List<Map<String,String>> ls = getSession().createSQLQuery("select * from zxxs_xnxq where xnmc='" + zxxsXnxq.getXnmc() + "' and xqmc ='"+zxxsXnxq.getXqmc()+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
      
         if (ls.size() > 1) {
        	 getMsgBean(false, "重复年度，季度信息不能保存！");
         }else if(ls.size() == 1){
             if ((zxxsXnxq.getXnxqid() == null) || ("".equals(zxxsXnxq.getXnxqid()))) {
                getMsgBean(false, "重复年度信息不能保存！");
             } else {
                mr = ls.get(0).get("mr");
                zxxsXnxq.setMr(mr);
            	getSession().update(zxxsXnxq);
            	getMsgBean(true,"修改成功"); 
         
              String sql = "update  cus_kw_examround set STARTDATE='"+xxkssj+"' ,ENDDATE='"+xxjssj+"'  where xn='"+zxxsXnxq.getXnmc()+"'and xqm='"+zxxsXnxq.getXqmc()+"' ";
              
            update(sql);
          
           String sql1 = "update  cus_kwbm_examround set STARTDATE='"+xxkssj+"' ,ENDDATE='"+xxjssj+"'  where xn='"+zxxsXnxq.getXnmc()+"'and xqm='"+zxxsXnxq.getXqmc()+"' ";
            
             update(sql1);
             }
       	}else{
       		try {
       			getSession().saveOrUpdate(zxxsXnxq);
        		getMsgBean(true, MsgBean.SAVE_SUCCESS);
       	    } catch (Exception e) {
       	      e.printStackTrace();
       	      getMsgBean(false, MsgBean.SAVE_ERROR);
       	    }
       	   
        }     
		
//		this.getMsgBean(true,"上传成功！");
		return this.MsgBean;

	}

	public com.jiajie.bean.MsgBean updateZxq(String xnxqid, String xnmc,
			String xqmc, String xxkssj, String xxjssj, String yxbz,String GXR) {
		String sql = "";
		List list = selectCuskw(xnmc,xqmc);
		int a = list.size();
		String s = String.valueOf(a);
	      if(StringUtil.isNotNullOrEmpty(xnxqid.trim())){
	    	  sql = "update zxxs_xnxq set xnmc='"+xnmc+"',xqmc='"+xqmc+"',xxkssj='"+xxkssj+"',xxjssj='"+xxjssj+"',yxbz='"+yxbz+"',gxr='"+s+"' where xnxq_id='"+xnxqid+"'";
		}
		update(sql);
					
			this.getMsgBean(true,"修改成功！");
	 
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean getZxxs(String xnxqid) {
		 Object obj = get(ZxxsXnxq.class, xnxqid);
		 getMsgBean(obj);
		return this.MsgBean;
	}
	//启动轮次
	 public MsgBean saveOrUpdateExamround(CusKwExamround examround, String usertype) {
		 boolean isAdd = true;
		 if (examround.getLcid() != null){
	    	  isAdd = false;
	    }
		  String[] xqxqs = examround.getXnxqId().split(",");
	      if (xqxqs.length == 2) {
	          examround.setXn(xqxqs[0]);
	          examround.setXqm(xqxqs[1]);
	      }
	      List<CusKwbmExamround> list3 = new ArrayList<CusKwbmExamround>();
	      List<CusKwExamround> list4 = new ArrayList<CusKwExamround>();
       		List<CusKwZkdw> list = getFzb();
       		
       			for (int i = 0; i < list.size(); i++) {

        			CusKwExamround examround3 = new CusKwExamround();
        			examround3.setDwid(list.get(i).getRegioncode());
    				examround3.setExamname(list.get(i).getRegionedu());
    				examround3.setDwtype("1");
    				examround3.setEnddate(examround.getEnddate());
    				examround3.setStartdate(examround.getStartdate());
    				examround3.setExamtypem(examround.getExamtypem());
    				examround3.setXn(examround.getXn());
    				examround3.setXnxqId(examround.getXnxqId());
    				examround3.setXqm(examround.getXqm());
    				
    				
    				list4.add(examround3);        			
        		}

            	for (int i = 0; i < list4.size(); i++) {
            	 List lst = createSQLQuery("select * from cus_kw_examround where DWID='" + list4.get(i).getDwid() + "' and xn='"+ list4.get(i).getXn()+"' and xqm='"+list4.get(i).getXqm()+"'").list();
            	  if (lst.size() > 0) {
    		          if ((list4.get(i).getLcid() == null) || ("".equals(list4.get(i).getLcid()))) {
    		            getMsgBean(false, "重复考试轮次信息不能保存！");
    		          } else {
    		            update(list4.get(i));
    		            if (("2".equals(usertype)) && (isAdd)) {
    		              CusKwExamschool examschool = new CusKwExamschool();
    		              examschool.setLcid(list4.get(i).getLcid());
    		              save(examschool);
    		            }
    		            this.getMsgBean(true,"轮次启动成功！");
    		          }	
    		        	  
    				}else {
    				    if (xqxqs.length == 2) {
    				    	getSession().save(list4.get(i));
    				    	
    			            
    			          } else {
    			            getMsgBean(false, "轮次启动失败");
    			          }
    		          if (("2".equals(usertype)) && (isAdd)) {
    			            CusKwExamschool examschool = new CusKwExamschool();
    			            examschool.setLcid(list4.get(i).getLcid());
    			            save(examschool);
    			          }
    				}				
    		
    			}
    	   
        		List<CusKwExamround> list5 = getCuskw(examround.getXn(),examround.getXqm());
        		for (CusKwExamround c : list5) {
//					System.out.println(c.getDwid()+"====="+c.getLcid());			
        			List<CusKwCkdw> list2 = getCk(c.getDwid());
        			for (int i = 0; i < list2.size(); i++) {
        				CusKwbmExamround examround2 = new CusKwbmExamround();
        				examround2.setLcid(c.getLcid());
        				
        				examround2.setDwid(list2.get(i).getXxjbxxid());
        				examround2.setExamname(c.getExamname()+list2.get(i).getXxmc());
        				examround2.setDwtype(list2.get(i).getDwlx());
        				examround2.setEnddate(examround.getEnddate());
        				examround2.setStartdate(examround.getStartdate());
        				examround2.setExamtypem(examround.getExamtypem());
        				examround2.setXn(examround.getXn());
        				examround2.setXnxqId(examround.getXnxqId());
        				examround2.setXqm(examround.getXqm());
        			
        				list3.add(examround2);      				
					}			           			
        		}

        	for (int i = 0; i < list3.size(); i++) {
        	 List lst = createSQLQuery("select * from cus_kwbm_examround where DWID='" + list3.get(i).getDwid() + "' and xn='"+ list3.get(i).getXn()+"' and xqm='"+list3.get(i).getXqm()+"'").list();
        	  if (lst.size() > 0) {
		          if ((list3.get(i).getBmlcid() == null) || ("".equals(list3.get(i).getBmlcid()))) {
		            getMsgBean(false, "重复考试轮次信息不能保存！");
		          } else {
		            update(list3.get(i));
		         
		            this.getMsgBean(true,"轮次启动成功！");
		          }	
		        	  
				}else {
				    if (xqxqs.length == 2) {
				    	getSession().save(list3.get(i));
			            this.getMsgBean(true,"轮次启动成功！");
			          } else {
			            getMsgBean(false, "轮次启动失败");
			          }
//		          if (("2".equals(usertype)) && (isAdd)) {
//			            CusKwExamschool examschool = new CusKwExamschool();
//			            examschool.setLcid(list3.get(i).getBmlcid());
//			            save(examschool);
//			          }
//				    getSession().flush();
		         
				}				
				
			}
	   

		    return this.MsgBean;
		  }

	//查询法制办
	
	public List<CusKwZkdw> getFzb() {
		String hql = "from CusKwZkdw";
		return getListHQL(hql);
	}

	 //查询参考单位

	public  List<CusKwCkdw> getCk(String SSZGJYXZDM) {
		String hql = "from CusKwCkdw where sszgjyxzdm = '"+SSZGJYXZDM+"' and dwlx = 2 ";
		return getListHQL(hql);
	}
	public  List<CusKwCkdw> getCk1() {
		String hql = "from CusKwCkdw where	dwlx = 2 ";
		return getListHQL(hql);
	}

	//查询考试轮次
	public List getCuskw(String xnmc ,String xqmc ) {
		
		String hql = "from CusKwExamround where xn='"+xnmc+"' and xqm='"+xqmc+"' ";
		return getListHQL(hql);
	}
	
	
}
