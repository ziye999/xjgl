package com.jiajie.service.sqck.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.bean.zzjg.CusKwSqCkdw;
import com.jiajie.bean.zzjg.CusKwZkdw;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.sqck.SqckService;
import com.jiajie.util.bean.MBspInfo;
@Service("SqckService")
public class SqckServiceImpl extends ServiceBridge implements SqckService{

	@Override
	public com.jiajie.bean.MsgBean saveBean(CusKwSqCkdw sqCkdw) {
		
		List<CusKwZkdw> lsZ =getFzb(sqCkdw.getSszgjyxzdm());
		for (CusKwZkdw cusKwZkdw : lsZ) {
			sqCkdw.setSszgjyxzmc(cusKwZkdw.getRegionedu());
		}
		String sql = "select * from zxxs_xxsq_jbxx where xxmc='"+sqCkdw.getXxmc()+"' and  SSZGJYXZMC ='"+sqCkdw.getSszgjyxzmc()+"'";
		
		List<Map<String,String>> ls = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
     
		if(ls.size()>=1){
			if(ls.get(0).get("XX_JBXX_ID")== null || ("".equals(ls.get(0).get("XX_JBXX_ID")))){
				 getMsgBean(false, "重复申请不能提交！");
			}else{
				
				getSession().update(sqCkdw);
            	getMsgBean(true,"申请已修改"); 
			}
		}else {
			getSession().save(sqCkdw);
			getMsgBean(true, "申请提交成功");
		}	
		return MsgBean;
	}
	//查询法制办
	public List<CusKwZkdw> getFzb(String regioncode) {
	  	String hql = "from CusKwZkdw where regioncode ='"+regioncode+"'";
	  	return getListHQL(hql);
	  }
	@Override
	public PageBean getSq(String ckdw ,String zkdw,String zt) {
		
		String sql = "select XX_JBXX_ID,XXMC,SSZGJYXZMC,XZXM,BGDH,YZBM," +
				"case zt  when '1' then '审核通过' else '未审核' end  as zt" +
				" from zxxs_xxsq_jbxx WHERE 1=1 ";
		if((ckdw != null) && (!"".equals(ckdw))){
	    	sql = sql + " and XXMC like '%" + ckdw + "%'";
	   }
		if((zkdw != null) && (!"".equals(zkdw))){
			sql = sql + " and SSZGJYXZMC like '%" + zkdw + "%'";
		}
		if(zt!=null && !("").equals(zt)){
			if(zt.equals("0")){
				sql +=" and zt = '"+zt+"'" ;
			}
			if(zt.equals("1")){
				sql +=" and zt = '"+zt+"'" ;
			}
			if(zt.equals("2")){
				
			}

		}else{
		
				sql +=" and zt = '0'" ;
			
		}
		return getSQLPageBean(sql); 
	}
	@Override
	public com.jiajie.bean.MsgBean updateBean(String xxjbxxid,MBspInfo mbf) {
		WebApplicationContext wac = null;
		SessionFactory sf = null;   
		Session session = null;

		wac = ContextLoader.getCurrentWebApplicationContext(); 
			sf = (SessionFactory)wac.getBean("sessionFactory");
			session = sf.openSession();
		
		String hql = "from CusKwSqCkdw where xxjbxxid='"+xxjbxxid+"' ";
		List<CusKwSqCkdw> list = getListHQL(hql);
		
		CusKwCkdw ckdw = new CusKwCkdw();
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		String gxsj = df.format(day);
		String sql2 = null;
		 String uid=UUID.randomUUID().toString().replaceAll("-", "");
		for (CusKwSqCkdw cusKwSqCkdw : list) {
			ckdw.setXxjbxxid(uid);
			ckdw.setXxmc(cusKwSqCkdw.getXxmc());
			ckdw.setSszgjyxzdm(cusKwSqCkdw.getSszgjyxzdm());
			ckdw.setSszgjyxzmc(cusKwSqCkdw.getSszgjyxzmc());
			ckdw.setXxbsm(cusKwSqCkdw.getXxbsm());
			ckdw.setXzxm(cusKwSqCkdw.getXzxm());
			ckdw.setBgdh(cusKwSqCkdw.getBgdh());
			ckdw.setYzbm(cusKwSqCkdw.getYzbm());
			ckdw.setGxr(mbf.getUserName());		
			ckdw.setGxsj(gxsj);
			ckdw.setDwlx("2");
			sql2="INSERT INTO zxxs_xx_jbxx (XX_JBXX_ID,XXBSM, XXMC, SSZGJYXZDM, SSZGJYXZMC, XZXM, BGDH, YZBM,  GXR, GXSJ, DWLX) " +
				"VALUES ('"+uid+"','"+cusKwSqCkdw.getXxbsm()+"','"+cusKwSqCkdw.getXxmc()+"','"+cusKwSqCkdw.getSszgjyxzdm()+"','"+cusKwSqCkdw.getSszgjyxzmc()+"','"+cusKwSqCkdw.getXzxm()+"'" +
						",'"+cusKwSqCkdw.getBgdh()+"','"+cusKwSqCkdw.getYzbm()+"'" +
								",'"+mbf.getUserName()+"','"+gxsj+"','2')";
		
		}
		
		System.out.println(sql2);
		
		
		String sql = "select * from zxxs_xx_jbxx where xxmc='"+ckdw.getXxmc()+"' and  SSZGJYXZMC ='"+ckdw.getSszgjyxzmc()+"'";
		
		List<Map<String,String>> ls = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
     
		if(ls.size()>=1){
		
			getMsgBean(false, "参考单位已经存在！");
			
		}else {
//			save(sql2);
			//save(ckdw);
			  session.createSQLQuery(sql2).executeUpdate();
			  session.flush();
			String sql1 = "update  zxxs_xxsq_jbxx set zt =1 where XX_JBXX_ID='"+xxjbxxid+"' ";
			update(sql1);
			getMsgBean(true, "审核通过!");
		}	
		
		if(session!=null){
			session.close();
		}
		if(sf!=null){
			sf.close();
		}
		return MsgBean;
	}
	@Override
	public List getZk() {
		String hql = " from CusKwZkdw";
	  	return getListHQL(hql);
	}
	

}
