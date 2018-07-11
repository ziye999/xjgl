package com.jiajie.service.signup.impl;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.xml.crypto.Data;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import net.rubyeye.xmemcached.transcoders.StringTranscoder;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.bean.zzjg.CusKwZkdw;

import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.impl.FileUpServiceImpl;
import com.jiajie.service.signup.SignupService;
import com.jiajie.util.CardUtils;
import com.jiajie.util.CompressFileUits;
import com.jiajie.util.ExporKsTasksk;
import com.jiajie.util.ExportKsInfo;
import com.jiajie.util.ExportKsTask;
import com.jiajie.util.ImportUtil;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bmtime;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.XsInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Service("SignupService")
public class SignupServiceImpl extends ServiceBridge implements SignupService{

	public PageBean getList() {
		
		String sql = "select c.XM,case when c.xbm = 1 then '男' else '女' end as xbm ,c.SFZJH," +
				"(select z.XXMC from zxxs_xx_jbxx z where z.XX_JBXX_ID = c.XXDM) xxdm," +
				"(select e.xn from cus_kw_examround  e where e.LCID = c.LCID) nd," +
				"case when c.BH = null then '未考' else '已考' end as BH,c.LCID from cus_kw_examinee c";
	
		return getSQLPageBean(sql);
	}
	public Object getZkdw(String zkdw){
		String sql = "select dm from zd_fzdw where mc='"+zkdw+"'";
		List list = getListSQL(sql);
		return list;
	}
	
	public MsgBean update(XsInfo xsinfo,String bmlcid){
		WebApplicationContext wac = null;
		SessionFactory sf = null;   
		Session session = null;
		boolean bool = bmtime.checkTime();
		if(bool){
		try {
			wac = ContextLoader.getCurrentWebApplicationContext(); 
			sf = (SessionFactory)wac.getBean("sessionFactory");
			session = sf.openSession();
			boolean flag = true;
			List<Map<String,String>> lsZ = session.createSQLQuery("select c.SHFLAG from cus_kw_examround c join cus_kwbm_examround r on c.LCID=r.LCID where r.BMLCID='"+bmlcid+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if("0".equals(lsZ.get(0).get("SHFLAG"))||"1".equals(lsZ.get(0).get("SHFLAG"))){
				flag = false;
			    getMsgBean(false,"该轮次已上报或已审核，参考单位不能进行报名操作！");
			}

			
			Integer fn = Integer.parseInt(session.createSQLQuery("select count(1) From zxxs_xs_jbxx where sfzjh = '"+xsinfo.getSfzjh()+"'").uniqueResult().toString());
			if(!xsinfo.getSfzjh().equals(xsinfo.getSfzjh1()) && fn>0){
				flag =false;
				getMsgBean(false,"身份证件号已存在，请核对!");
			}
			if(xsinfo.getStuName()==null && "".equals(xsinfo.getStuName())){ 
				flag =false;
				getMsgBean(false,"姓名不能为空!");
			}
			if(xsinfo.getStuSex()==null && "".equals(xsinfo.getStuSex())){ 
				flag =false;
				getMsgBean(false,"性别不能为空!");
			}
			if(xsinfo.getSfzjh() == null && "".equals(xsinfo.getSfzjh())){ 
				flag =false;
				
				getMsgBean(false,"身份证件号不能为空!");
			}
			if(!CardUtils.isIDCard(xsinfo.getSfzjh())){
				flag =false;
				getMsgBean(false, "请检查身份证的有效性!");
				
			}	
			String xsjbxxid = session.createSQLQuery("select xs_jbxx_id from zxxs_xs_jbxx where sfzjh='" + xsinfo.getSfzjh1() + "'").uniqueResult().toString();
			
			if(flag){
				String x = session.createSQLQuery("select DM from zd_xb where mc = '"+xsinfo.getStuSex()+"'").uniqueResult().toString();//性别
				String mzm = session.createSQLQuery("select DM from  zd_mz where mc = '"+xsinfo.getMz()+"'").uniqueResult().toString();//民族
				String zzmm = session.createSQLQuery("SELECT DM FROM  zd_zzmm WHERE mc = '"+xsinfo.getZzmm()+"'").uniqueResult().toString();//政治面貌 
				String  WHCD = session.createSQLQuery("select DM from  zd_xd where mc = '"+xsinfo.getWhcd()+"'").uniqueResult().toString();//文化程度
				String zflb = session.createSQLQuery("select id from zd_zflb where zflb= '"+xsinfo.getZflb()+"'").uniqueResult().toString();//执法列别	
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dqsj = df.format(new Date());
				session.createSQLQuery("update zxxs_xs_jbxx set grbsm='G"+xsinfo.getSfzjh()+"',xm='"+xsinfo.getStuName()+"',xbm='"+x+"',sfzjh='"+xsinfo.getSfzjh()+"',mzm='"+mzm+"',ZZMM='"+zzmm+"',WHCD='"+WHCD+"',zy='"+xsinfo.getZy()+"',zw='"+xsinfo.getZw()+"',zflx='"+zflb+"',zffw='"+xsinfo.getZffw()+"',remark='"+xsinfo.getRemark()+"',fzdw='"+xsinfo.getFzdw()+"' where xs_jbxx_id='"+xsjbxxid+"'").executeUpdate();
				session.createSQLQuery("update t_qxgl_userinfo set username='"+xsinfo.getStuName()+"' where usercode='"+xsjbxxid+"'").executeUpdate();
				session.createSQLQuery("update cus_kw_examinee set xjh='G"+xsinfo.getSfzjh()+"',xm='"+xsinfo.getStuName()+"',xbm='"+x+"',sfzjh='"+xsinfo.getSfzjh()+"' where xs_jbxx_id='"+xsjbxxid+"'").executeUpdate();
				String imgpath = xsinfo.getPath();
				imgpath=imgpath.replaceAll("\\\\", "\\\\\\\\");
				Integer fx = Integer.parseInt(session.createSQLQuery("select count(1) From zxxs_xs_pic where XS_JBXX_ID = '"+xsjbxxid+"'").uniqueResult().toString());
				
				if(fx>0){
					session.createSQLQuery("update zxxs_xs_pic set path='"+imgpath+"',gxsj='"+dqsj+"' where xs_jbxx_id='"+xsjbxxid+"'").executeUpdate();
				}else{
					session.createSQLQuery("insert into   zxxs_xs_pic(XS_JBXX_ID,PATH,GXSJ,GXR,FLAG) values('"+xsjbxxid+"','"+xsinfo.getPath()+"','"+dqsj+"','0','0')").executeUpdate();
				}
				getMsgBean(true,"考生修改成功!");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			if(session!=null){
				session.close();
			}
			if(sf!=null){
				sf.close();
			}
		}
		}else{
			getMsgBean(false,"不是报名时间，不能修改考生");
		}
		return MsgBean;
	}

	public MsgBean saveBean(XsInfo xsinfo,String bmlcid,String CKDW,MBspInfo bspInfo) {
		WebApplicationContext wac = null;
		SessionFactory sf = null;   
		Session session = null;
		boolean bool = bmtime.checkTime();
		if(bool){
		try {
		
		wac = ContextLoader.getCurrentWebApplicationContext(); 
		sf = (SessionFactory)wac.getBean("sessionFactory");
		session = sf.openSession();
		boolean flag = true;
		List<Map<String,String>> ls =session.createSQLQuery("select c.SHFLAG from cus_kw_examround c join cus_kwbm_examround r on c.LCID=r.LCID where r.BMLCID='"+bmlcid+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if("0".equals(ls.get(0).get("SHFLAG"))||"1".equals(ls.get(0).get("SHFLAG"))){
			flag = false;
		    getMsgBean(false,"该轮次已上报或已审核，参考单位不能进行报名操作！");
		}
		
		
		if(xsinfo.getStuName()==null || "".equals(xsinfo.getStuName())){ 
			flag =false;
			getMsgBean(false,"姓名不能为空!");
		}
	
		if(xsinfo.getStuSex()==null || "".equals(xsinfo.getStuSex())){ 
			flag =false;
			getMsgBean(false,"性别不能为空!");
		}
		if(xsinfo.getSfzjh() == null || "".equals(xsinfo.getSfzjh())){ 
			flag =false;

			getMsgBean(false, "身份证件号不能为空!");
		}
		if(!CardUtils.isIDCard(xsinfo.getSfzjh())){
			flag =false;

			getMsgBean(false, "请检查身份证的有效性!");
			
		}
		if(xsinfo.getParticipatingUntisName() == null || "".equals(xsinfo.getParticipatingUntisName())){
			flag =false;
			getMsgBean(false, "参考单位地址不能为空!");

		}
		int fn = Integer.parseInt(session.createSQLQuery("select count(1) From zxxs_xs_jbxx where sfzjh = '"+xsinfo.getSfzjh()+"'").uniqueResult().toString());
		if(fn>0){
			flag =false;
			 List<Map<String,String>> lsZ3 = session.createSQLQuery("select xxmc from zxxs_xx_jbxx where XX_JBXX_ID=(select XX_JBXX_ID from zxxs_xs_jbxx where  sfzjh='"+xsinfo.getSfzjh()+"') ").setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			getMsgBean(false,"您上传的身份证号码与"+lsZ3.get(0).get("xxmc")+"参考单位的身份证件号重复!");
		} 
		 	String SSZGJYXZDM="";//所属法制办编码
	        String SSZGJYXZMC="";//所属法制办名称
	        String sql = "";
	        String dwid1 = null;
	        String yzbm1 = null;
	        String lcid = null;
	        
		  	if("4".equals(bspInfo.getUserType())){
				sql = "select " +
						"(select SSZGJYXZDM from zxxs_xx_jbxx a where a.xx_jbxx_id=ex.dwid) dwid2,ex.lcid, " +
						"(select (select region_edu from com_mems_organ where region_code=a.SSZGJYXZDM) from zxxs_xx_jbxx a where a.xx_jbxx_id=ex.dwid) region_edu," +
						"dwid" +
						" from  cus_kwbm_examround ex where ex.bmlcid='"+bmlcid+"'";
			}else {
				sql = "select " +
						"(select SSZGJYXZDM from zxxs_xx_jbxx a where a.xx_jbxx_id=ex.dwid) dwid2,ex.lcid, " +
						"(select (select region_edu from com_mems_organ where region_code=a.SSZGJYXZDM) from zxxs_xx_jbxx a where a.xx_jbxx_id=ex.dwid) region_edu," +
						"dwid" +
						" from  cus_kwbm_examround ex where ex.bmlcid='"+bmlcid+"'";
			}
	    	  
		  List<Map<String,String>> lsZ1 =session.createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		  
		  if(lsZ1==null || lsZ1.size()==0){
			  return getMsgBean(false,"查不到该轮次信息");
		  }else {
				SSZGJYXZDM=lsZ1.get(0).get("dwid2")+"";
				SSZGJYXZMC=lsZ1.get(0).get("region_edu")+"";
				dwid1 = lsZ1.get(0).get("dwid");
				lcid = lsZ1.get(0).get("lcid");
				if(SSZGJYXZDM.length()>6){
					yzbm1=SSZGJYXZDM.substring(0,6);
				}
		  }
		  xsinfo.setYzbm(yzbm1);
		  xsinfo.setParticipatingUntisCode(SSZGJYXZDM);
			String xxmc = session.createSQLQuery("SELECT xxmc FROM `zxxs_xx_jbxx` WHERE XX_JBXX_ID = '"+dwid1+"'").uniqueResult().toString();//参考单位
			xsinfo.setParticipatingUntisName(xxmc);
		String xx_jbxx_id =null;; 
		String zkdwm = null;
		String yzbm = null;
		List<Map<String,String>> lsZ = session.createSQLQuery("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where SSZGJYXZDM='"+xsinfo.getParticipatingUntisCode()+"' and  XXMC='"+xsinfo.getParticipatingUntisName()+"' and dwlx='2'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(lsZ==null || lsZ.size()==0){
			flag =false;
			getMsgBean(false,xsinfo.getParticipatingUntisName()+"单位名称不存在，请核对！");
		}else{
			xx_jbxx_id =lsZ.get(0).get("XX_JBXX_ID"); //参考单位id
			zkdwm = lsZ.get(0).get("SSZGJYXZDM")==null?"":lsZ.get(0).get("SSZGJYXZDM").toString();
			yzbm = lsZ.get(0).get("YZBM")==null?"":lsZ.get(0).get("YZBM").toString();
			int n = Integer.parseInt(session.createSQLQuery("select count(1) from cus_kwbm_examround where bmlcid = '"+bmlcid+"' and dwid='"+xx_jbxx_id+"'").uniqueResult().toString());
			if(n==0){
				flag =false;
				getMsgBean(false,"参考单位不属于本轮次考试!");
				
			}
		}
		if(flag){
				String xs_jbxx_id = StringUtil.getUUID();
				String x = session.createSQLQuery("select DM from zd_xb where mc = '"+xsinfo.getStuSex()+"'").uniqueResult().toString();//性别
				String mzm = session.createSQLQuery("select DM from  zd_mz where mc = '"+xsinfo.getMz()+"'").uniqueResult().toString();//民族
				String zzmm = session.createSQLQuery("SELECT DM FROM  zd_zzmm WHERE mc = '"+xsinfo.getZzmm()+"'").uniqueResult().toString();//政治面貌 
				String  WHCD = session.createSQLQuery("select DM from  zd_xd where mc = '"+xsinfo.getWhcd()+"'").uniqueResult().toString();//文化程度
				String zflb = session.createSQLQuery("select id from zd_zflb where zflb = '"+xsinfo.getZflb()+"'").uniqueResult().toString();//执法列别
				//添加考生信息
				session.createSQLQuery("insert into zxxs_xs_jbxx(xs_jbxx_id,grbsm,xm,xbm,sfzjlxm,sfzjh,xx_jbxx_id,xxsszgjyxzdm,bmdz,mzm,ZZMM,WHCD,zy,zw,zflx,zffw,fzdw,remark) "+
						"values('"+xs_jbxx_id+"','G"+xsinfo.getSfzjh()+"','"+xsinfo.getStuName()+"','"+x+"','1','"+xsinfo.getSfzjh()+
						"','"+xx_jbxx_id+"','"+xsinfo.getParticipatingUntisCode()+"','"+zkdwm+"','"+mzm+"','"+zzmm+"','"+WHCD+"','"+xsinfo.getZy()+"','"+xsinfo.getZw()+"','"+zflb+"','"+xsinfo.getZffw()+"','"+xsinfo.getFzdw()+"','"+xsinfo.getRemark()+"')").executeUpdate();
				//添加用户信息
				session.createSQLQuery("insert into t_qxgl_userinfo(usercode,username,usertype,loginid,loginpwd,flage) " +
						"values('"+xs_jbxx_id+"','"+xsinfo.getStuName()+"',3,'"+xsinfo.getSfzjh()+"','"+new Md5Hash(xsinfo.getSfzjh().substring(xsinfo.getSfzjh().length()-6)).toString()+"',1)").executeUpdate();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMM",Locale.CHINA);		
				String dateStr = format.format(new Date());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dqsj = df.format(new Date());
				NumberFormat nf = NumberFormat.getInstance();
				String kscode = yzbm+dateStr+nf.format(xsinfo.getKsbh());
				//添加考试信息
				session.createSQLQuery("insert into cus_kw_examinee(KSID,LCID,KSCODE,XJH,XM,XXDM,XBM,SFZJH,GW,XS_JBXX_ID,BMLCID) values('"+StringUtil.getUUID()+
						"','"+lcid+"','"+kscode+"','G"+xsinfo.getSfzjh()+"','"+xsinfo.getStuName()+"','"+
						xx_jbxx_id+"','"+x+"','"+xsinfo.getSfzjh()+"','"+xsinfo.getZw()+"','"+xs_jbxx_id+"','"+bmlcid+"')").executeUpdate();
				//上传照片信息
				session.createSQLQuery("insert into   zxxs_xs_pic(XS_JBXX_ID,PATH,GXSJ,GXR,FLAG) values('"+xs_jbxx_id+"','"+xsinfo.getPath()+"','"+dqsj+"','0','0')").executeUpdate();
			 int n = Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_examinee where bmlcid = '"+bmlcid+"'").uniqueResult().toString());
			 System.out.println("总人数为："+n);
			 getSession().createSQLQuery("update cus_kwbm_examround set sl = " + n + " where bmlcid = '" + bmlcid + "'").executeUpdate();
			 int n2 = Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_examinee where lcid = '"+lcid+"'").uniqueResult().toString());
			 getSession().createSQLQuery("update cus_kw_examround set sl = " + n2 + " where lcid = '" + lcid + "'").executeUpdate();
			 getMsgBean(true,"考生上传成功!");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			if(session!=null){
				session.close();
			}
			if(sf!=null){
				sf.close();
			}
		}
		}else{
			getMsgBean(false,"不是报名时间，不能增加考生!");
		}
		
	
		
		return this.MsgBean;
	}

	public PageBean getlist(String xm, String SFZJH ,String bmlcid,String zp) {
	
		String sql = "select c.XM,case when c.xbm = 1 then '男' else '女' end as xbm ," +
				"c.SFZJH,z.XXMC, e.XN,z.SSZGJYXZMC," +
				"(select mc from  zd_mz where dm = z1.MZM ) MZM ," +
				" (select mc from zd_xd where dm = z1.WHCD ) WHCD," +
				"(select mc from zd_zzmm where dm = z1.ZZMM ) ZZMM," +
				"(select zflb from zd_zflb where id = z1.zflx) zflx," +
				"z1.ZY,z1.ZW,z1.zffw,IFNULL(z1.fzdw,'') fzdw,z1.remark,"+
				"case when c.BH = null then '未考' else '已考' end as BH,c.bmlcid" +
				" from cus_kw_examinee c " +
				" join cus_kwbm_examround e on  e.bmlcid = c.bmlcid " +
				" join zxxs_xx_jbxx z on  z.XX_JBXX_ID = c.XXDM" +
				" JOIN zxxs_xs_jbxx z1 ON z1.sfzjh= c.sfzjh" +				
				" where c.bmlcid ='"+bmlcid+"' and 1=1 ";
		if(xm!=null && !"".equals(xm)){
			String xm1 = '%'+xm+'%';
			sql += " and c.xm like '"+xm1+"' ";
		}
	
		if(SFZJH!=null && !"".equals(SFZJH)){
			sql += " and c.SFZJH = '"+SFZJH+"'";
		}
		if(zp!=null && !("").equals(zp)){
		if(zp.equals("2")){
			sql +=" and c.XS_JBXX_ID in (select XS_JBXX_ID from zxxs_xs_pic where path!='')";
		}
		if(zp.equals("3")){
			sql +=" and c.XS_JBXX_ID not in (select XS_JBXX_ID from zxxs_xs_pic where path!='')";
		}
		}
		System.out.println(sql);
		return getSQLPageBean(sql);

	
	}
	
	  //查询考生详细信息
	 
	public Object getList1(String SFZJH) {
		
		String sql = "select DISTINCT z.xm,case when z.xbm = 1 then '男' else '女' end as xbm,z.sfzjh,"+
				"(select mc from  zd_mz where dm = z.MZM ) MZM ," +
				" (select mc from zd_xd where dm = z.WHCD ) WHCD," +
				"(select mc from zd_zzmm where dm = z.ZZMM ) ZZMM," +
				"(SELECT XXMC FROM `zxxs_xx_jbxx` WHERE XX_JBXX_ID=z.XX_JBXX_ID) XXMC," +
				"z.ZY,z.ZW,z.zflx,z.zffw,z.fzdw,z.remark,T1.PATH," +
				" case when c.ISTJ  IS NULL then '未提交'  WHEN c.istj=1 THEN '已提交' else '未提交' end ISTJ" +
				" from zxxs_xs_jbxx z" +
				" join  cus_kw_examinee c  on c.SFZJH = z.SFZJH " +
				" LEFT JOIN ZXXS_XS_PIC T1 ON z.XS_JBXX_ID=T1.XS_JBXX_ID " +
				" where z.sfzjh = '"+SFZJH+"'";
		List list = getListSQL(sql);
	
		Map result = new HashMap();
		if(list != null && list.size() > 0){
			result = (Map) list.get(0);
			return result;
		}

		return null;
	}
		//删除考生
		public com.jiajie.bean.MsgBean delte(String bmlcid, String sfzjh) {
			boolean bool = bmtime.checkTime();
			if(bool){
			//查询考生状态
			boolean flag = true;
			List<Map<String,String>> ls = getSession().createSQLQuery("select c.SHFLAG from cus_kw_examround c join cus_kwbm_examround r on c.LCID=r.LCID where r.BMLCID='"+bmlcid+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if("0".equals(ls.get(0).get("SHFLAG"))||"1".equals(ls.get(0).get("SHFLAG"))){
				flag = false;
			    getMsgBean(false,"该轮次已上报或已审核，参考单位不能进行报名操作！");
			}
			
			if(flag){
				String sql2 ="select ISTJ from  cus_kw_examinee where sfzjh = '"+sfzjh+"'";
				String ISTJ = null;
				List<Map<String,String>>  list  = getListSQL(sql2);
				 List<Map<String,String>> lsZ1 =getSession().createSQLQuery("select lcid from cus_kwbm_examround where bmlcid = '" + bmlcid + "'").setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
				  String lcid = lsZ1.get(0).get("lcid");
			
				ISTJ = list.get(0).get("ISTJ");
				if(ISTJ==null){
					String sql = "delete from cus_kw_examinee where  sfzjh = '"+sfzjh+"'";
					delete(sql);
					String sql1= "delete from zxxs_xs_jbxx where  sfzjh = '"+sfzjh+"'";
					delete(sql1);
					 int n = Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_examinee where bmlcid = '" + bmlcid + "'").uniqueResult().toString());			 
					 int n2 = Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_examinee where lcid = '" + lcid + "'").uniqueResult().toString());	
					 System.out.println("总人数为："+n);
					 getSession().createSQLQuery("update cus_kwbm_examround set sl = " + n + " where bmlcid = '" + bmlcid + "'").executeUpdate();
					 getSession().createSQLQuery("update cus_kw_examround set sl = " + n2 + " where lcid = '" + lcid + "'").executeUpdate();
					getMsgBean(true, MsgBean.DEL_SUCCESS);
				}else{
					 getMsgBean(false, "考生已提交不能删除");
				}
			}
			}else{
				getMsgBean(false, "不是报名时间，不能删除考生！");
			}

		return MsgBean;
	}
	  //查询考生详细信息
	public MsgBean getList(String SFZJH) {
		
		String sql = "select DISTINCT z.xm,case when z.xbm = 1 then '男' else '女' end as xbm,z.sfzjh,"+
				"(select mc from  zd_mz where dm = z.MZM ) MZM,  z.MZM as mz," +
				" (select mc from zd_xd where dm = z.WHCD ) WHCD,z.WHCD as whc," +
				"(select mc from zd_zzmm where dm = z.ZZMM ) ZZMM,  z.ZZMM as ZZM, " +
				"(SELECT XXMC FROM `zxxs_xx_jbxx` WHERE XX_JBXX_ID=z.XX_JBXX_ID) XXMC," +
				"(select zflb from zd_zflb where id = z.zflx) zflx ,z.zflx as zfl," +
				"z.ZY,z.ZW,z.zffw,z.fzdw,z.remark,T1.PATH,(select dm from zd_fzdw where mc=z.fzdw) fzdw3," +
				" case when c.ISTJ  IS NULL then '未提交'  WHEN c.istj=1 THEN '已提交' else '未提交' end ISTJ" +
				" from zxxs_xs_jbxx z" +
				" join  cus_kw_examinee c  on c.SFZJH = z.SFZJH" +
				" LEFT JOIN ZXXS_XS_PIC T1 ON z.XS_JBXX_ID=T1.XS_JBXX_ID " +
				" where z.sfzjh = '"+SFZJH+"'";
		List<Object> list = getListSQL(sql);
	if(list!=null && list.size()>0){
		getMsgBean(list.get(0));}else{getMsgBean(null );}
		System.out.println(sql);
		return this.MsgBean;
	}
	
	
	//上传考生信息


	
	   
	  
	 
	 public MsgBean exportKsInfo(File file, MBspInfo bspInfo, String bmlcid,String CKDW) {
		 	boolean bool = bmtime.checkTime();
		 	MsgBean mb = new MsgBean();
		    mb.setSuccess(true);
		 	if(bool){	
		    String path = FileUpServiceImpl.class.getResource("").toString();
		    path = path.substring(6, path.lastIndexOf("xjgl") + 4);
		    List list = new ArrayList();
		    Workbook rwb = null;
		    WritableWorkbook wwb = null;
		    //WorkbookSettings 是使应用程序可以使用各种高级工作簿属性设置，若不使用则相关属性会是默认值 
		    WorkbookSettings workbooksetting = new WorkbookSettings();
		    WritableSheet ws = null;
		    int k = 0;
		    StringBuffer sb = new StringBuffer();
		    boolean flag2 = true;
		    try {
		    	List<Map<String,String>> lsZ = getSession().createSQLQuery("select c.SHFLAG from cus_kw_examround c join cus_kwbm_examround r on c.LCID=r.LCID where r.BMLCID='"+bmlcid+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if("0".equals(lsZ.get(0).get("SHFLAG"))||"1".equals(lsZ.get(0).get("SHFLAG"))){
					mb.setSuccess(false);
					mb.setMsg("该轮次已上报或已审核，参考单位不能进行报名操作！");
				}else{
					workbooksetting.setCellValidationDisabled(true);
			    	rwb = Workbook.getWorkbook(file,workbooksetting);
			      Sheet rs = rwb.getSheet(0);
			      //xsl有几行数据
			      int clos = rs.getColumns();
			      //总共的数据个数（格子）
			      int rows = rs.getRows();
			      int total=0;
			      
			      HashMap rmap = (HashMap)ImportUtil.checkImportExcelModel(file, "examregistratio.xls");
			      MsgBean localMsgBean1;
			      if (!((Boolean)rmap.get("success")).booleanValue()) {
			        mb.setSuccess(false);
			        mb.setMsg("您导入的模板格式错误,请下载模板导入");
			        return mb;
			      }
			      if (rows == 1) {
			        mb.setSuccess(false);
			        mb.setMsg("您导入的模板是空模板,请填充好数据再进行导入");
			        return mb;
			      }


			       clos = ((Integer)rmap.get("col")).intValue();
			      List xslist = new ArrayList();
			      String key = null;
			      ExportKsInfo eki = ExportKsInfo.getInstance();
			      Session session = null;
				  SessionFactory sf = null;
				   WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
				  sf = (SessionFactory)wac.getBean("sessionFactory");
			        session = sf.openSession();
			        String SSZGJYXZDM="";//所属法制办编码
			        String SSZGJYXZMC="";//所属法制办名称
			        String yzbm="";//邮政编码
			        String sql = "";
			        String lcid = null;
			    
					sql = "select " +
						  "(select SSZGJYXZDM from zxxs_xx_jbxx a where a.xx_jbxx_id=ex.dwid) dwid,ex.lcid, " +
						  "(select (select region_edu from com_mems_organ where region_code=a.SSZGJYXZDM) from zxxs_xx_jbxx a where a.xx_jbxx_id=ex.dwid) region_edu," +
						  "dwid" +
						  " from  cus_kwbm_examround ex where ex.bmlcid='"+bmlcid+"'";
					  
				  List<Map<String,String>> lsZ1 =session.createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
				  
				  if(lsZ1==null || lsZ1.size()==0){
						 mb.setSuccess(false);
					     mb.setMsg("查不到该轮次信息");
					     return mb;
				  }else {
						SSZGJYXZDM=lsZ1.get(0).get("dwid")+"";
						SSZGJYXZMC=lsZ1.get(0).get("region_edu")+"";
						lcid = lsZ1.get(0).get("lcid");
						if(SSZGJYXZDM.length()>6){
						yzbm=SSZGJYXZDM.substring(0,6);
						}
				  }
				  int n1 = Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_examinee where bmlcid = '" + bmlcid + "'").uniqueResult().toString());//当前轮次考生数量     
			      for (int i = 1; i < rows; i++) {
			        boolean flag = true;
			        String xx_jbxx_id = null;
			        String zkdwm = null;
			       
			        String xm = rs.getCell(1, i).getContents().trim().replace(" ", ""); //姓名
			        String xb = rs.getCell(3, i).getContents().trim().replace(" ", "");//性别
			        String sfzh = rs.getCell(2, i).getContents().trim().replace(" ", "").replace("x", "X");//身份证号
			        if((sfzh==null|| sfzh.equals(""))&&(xm==null||xm.equals(""))){
			        	continue;
			        }
			        total++;//考生数量
//			        String ckdw = rs.getCell(6, i).getContents().trim().replace(" ", "");//工作单位
			        String mz = rs.getCell(4, i).getContents().trim().replace(" ", "");//民族
			        String whcd = rs.getCell(5, i).getContents().trim().replace(" ", "");//文化程度
			        String zzmm = rs.getCell(6, i).getContents().trim().replace(" ", "");//政治面貌
			        String zy = rs.getCell(7, i).getContents().trim().replace(" ", "");//专业
			        String zw = rs.getCell(8, i).getContents().trim().replace(" ", "");//职务
			        String zflb = rs.getCell(9, i).getContents().trim().replace(" ", "");//执法类别
			        String fzdw = rs.getCell(10, i).getContents().trim().replace(" ", "");//发证机关
			        String zffw = rs.getCell(11, i).getContents().trim().replace(" ", "");//执法范围
			        String remark = rs.getCell(12, i).getContents().trim().replace(" ", "");//备注
			        String uid=UUID.randomUUID().toString().replaceAll("-", "");
			        //查询参考单位的id，所属法制办编号，邮政编码
			        List<Map<String,String>> ls1 =session.createSQLQuery("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where SSZGJYXZDM='"+SSZGJYXZDM+"' and   XXMC='"+CKDW+"' and dwlx='2'").setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
					if(lsZ==null || lsZ.size()==0){
						//新增参考单位信息
						 String sql1 = "insert into zxxs_xx_jbxx(XX_JBXX_ID,xxbsm,xxmc,SSZGJYXZDM,SSZGJYXZMC,yzbm,dwlx) values ('" + uid + "','" + System.currentTimeMillis()+"" + 
			              "','" + CKDW + "','"+SSZGJYXZDM+"','"+SSZGJYXZMC+"','"+yzbm+"','" + 2 + "') ";
						  session.createSQLQuery(sql1).executeUpdate();
						  session.flush();
						  System.out.println(sql1);
					}else {
						
					}
					//工作岗位第8个
			        String gw = rs.getCell(7, i).getContents().trim().replace(" ", "");
			        if ((i == 1) && ((sfzh == null) || ("".equals(sfzh)))) {
			          mb.setSuccess(false);
			          mb.setMsg("第一行数据的学生身份证不能为空");
			          return mb;
			        }
			        if (key == null) {
			          key = bmlcid + "," + sfzh;
			          
			          int st = eki.isGoOk(key);
			          if (st == 0) {
			            mb.setSuccess(false);
			            mb.setMsg("当前较多人数导入，请稍后再试!");
			            return mb;
			          }
			          if (st == -1) {
			            mb.setSuccess(false);
			            mb.setMsg("当前导入的文件,已有人正在导入!");
			            return mb;
			          }
			        }
			        XsInfo xs = new XsInfo();
			        xs.setSfzjh(sfzh);//身份证号
			        xs.setStuName(xm);//姓名
			        xs.setParticipatingUntisCode(SSZGJYXZDM);//所属组考单位编码
			        xs.setParticipatingUntisName(CKDW);//参考单位
			        xs.setStuSex(xb);//性别
			        xs.setWorkspace(gw);//专业
			        xs.setMz(mz);//民族
			        xs.setWhcd(whcd);//////文化程度
			        xs.setZzmm(zzmm);//政治面貌
			        xs.setZy(zy);	//专业
			        xs.setZw(zw);//职务
			        xs.setZflb(zflb);//执法类别
			        xs.setZffw(zffw);//执法范围
			        xs.setFzdw(fzdw);//发证机关
			        xs.setRemark(remark);  //备注
			        xs.setKsbh(i);//几条数据
			        xs.setLcid(lcid);
			        for (int j = 0; j < xslist.size(); j++) {
						if(sfzh.equals(((XsInfo) xslist.get(j)).getSfzjh())){
							xs.setErrMsg("身份证件号重复!");
						}
						
					}
			        xslist.add(xs);
			      }
			      int statu = eki.isGoOk(key, xslist);
			      List errXs = null;
			      if (statu > 0) {
			    	  //线程池
			        ScheduledExecutorService service = 
			          Executors.newScheduledThreadPool(statu);
			        //保存数据
			        for (int i = 0; i < statu; i++) {
			          service.schedule(new ExporKsTasksk(key), 1L, 
			            TimeUnit.MILLISECONDS);
			        }
			        while (!eki.getStatuByKey(key)) {
			          System.out.print("");
			        }
			        //xecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。当所有提交任务执行完毕，线程池即被关闭。
			        service.shutdown();

			        eki.gobackUesdThreads(statu);
			        errXs = eki.getErrorData(key);
			        eki.removeTaskByKey(key);
			        if ((errXs != null) && (errXs.size() > 0))
			          flag2 = false;
			      }
			      else {
			        mb.setSuccess(false);
			        if (statu == 0)
			          mb.setMsg("当前较多人数导入，请稍后再试!");
			        else if (statu == -1) {
			          mb.setMsg("当前导入的文件,已有人正在导入!");
			        }
			        return mb;
			      }
			  
			 
			      
			      int n = Integer.parseInt(session.createSQLQuery("select count(1) from cus_kw_examinee where bmlcid = '" + bmlcid + "'").uniqueResult().toString());
			      int n2 = Integer.parseInt(session.createSQLQuery("select count(1) from cus_kw_examinee where lcid = '" + lcid + "'").uniqueResult().toString());
			      System.out.println("总人数为："+n);
			      getSession().createSQLQuery("update cus_kwbm_examround set sl = " + n + " where bmlcid = '" + bmlcid + "'").executeUpdate();
			      getSession().createSQLQuery("update cus_kw_examround set sl = " + n2 + " where lcid = '" + lcid + "'").executeUpdate();
			      String mString="";
			      if (flag2) {
			        
			        mb.setMsg("全部导入完成!一共导入" +(n-n1) + "条!<br><font>下一步请导入照片</font>");
			      } else {
			    	  //出错到处的xls文件名
			    	File f = new File("/"+path + "/export/excel/temporary/" + 
					          new Date().getTime() + ".xls");
				    File f1 = new File("/"+path + "/export/excel/temp/regis.xls");
			       
//			        workbooksetting.setCellValidationDisabled(true);
			        Workbook workbook =   Workbook.getWorkbook(f1);
			        wwb = Workbook.createWorkbook(f,workbook);
			        ws = wwb.getSheet(0);
			        mb.setSuccess(false);
			        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
			        // Jxl操作Excel设置背景、字体颜色、对齐方式、列的宽度
			        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
			          UnderlineStyle.NO_UNDERLINE, Colour.RED);
			        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			        for (int j = 0; j < errXs.size(); j++) {
			        	 //第一个是代表列数,   
			            //第二是代表行数，   
			            //第三个代表要写入的内容   
			        	//然后通过写sheet的方法addCell（）把内容写进sheet里面。   
			          Label l4 = new Label(0, j + 1, (j + 1)+"");
			          Label l5 = new Label(1, j + 1, ((XsInfo)errXs.get(j)).getStuName());//姓名
			          Label l6 = new Label(2, j + 1, ((XsInfo)errXs.get(j)).getSfzjh());//身份证
			          Label l7 = new Label(3, j + 1, ((XsInfo)errXs.get(j)).getStuSex());//性别
			          Label l8 = new Label(4, j + 1, ((XsInfo)errXs.get(j)).getMz());//民族
			          Label l9 = new Label(5, j + 1, ((XsInfo)errXs.get(j)).getWhcd());//文化程度
			          Label a = new Label(6, j + 1, ((XsInfo)errXs.get(j)).getZzmm());//政治面貌
			          Label b = new Label(7, j + 1, ((XsInfo)errXs.get(j)).getZy());//专业
			          Label c = new Label(8, j + 1,  ((XsInfo)errXs.get(j)).getZw());//职务
			          Label d = new Label(9, j + 1, ((XsInfo)errXs.get(j)).getZflb());//执法类别
			          Label e = new Label(10, j + 1, ((XsInfo)errXs.get(j)).getFzdw());//发证的单位
			          Label g = new Label(11, j + 1, ((XsInfo)errXs.get(j)).getZffw());//执法范围
			          Label h = new Label(12, j + 1, ((XsInfo)errXs.get(j)).getRemark());//备注
			          Label i = new Label(13, j + 1, ((XsInfo)errXs.get(j)).getErrMsg(), wcfFC);//错误信息
			       
			          ws.addCell(l4);
			          ws.addCell(l5);
			          ws.addCell(l6);
			          ws.addCell(l7);
			          ws.addCell(l8);
			          ws.addCell(l9);
			          ws.addCell(a);
			          ws.addCell(b);
			          ws.addCell(c);
			          ws.addCell(d);
			          ws.addCell(e);
			          ws.addCell(g);
			          ws.addCell(h);
			          ws.addCell(i);
			        }
			        mb.setSuccess(false);
			        mb.setMsg("导入完成!共导入" + (n-n1) + "条!<br>失败" + errXs.size() + "条!<br><font color='red'>确认之后返回整理好的失败记录文件<br>可继续导入</font>");
			       try {
			    	   if(wwb==null){
			    		   System.out.println("1111111111111111111");
			    	   }
			    	   wwb.write();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			       
			       
			      }
				}
		    	
		
	    	
	    } catch (Exception e) {
	     
	      e.printStackTrace();
	      mb.setSuccess(false);
	        mb.setMsg("您导入的模板格式错误,请下载模板导入");
	     
	      return mb;
	      
	    }
	    finally
	    {
	      try
	      {
	    	  if(!flag2){
	    		  rwb.close();
	    	  }
	        
	        if (!flag2)
	          wwb.close();
	      }
	      catch (WriteException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	     
	    }
		}else{
			 mb.setSuccess(false);
		     mb.setMsg("不是报名时间，不能导入考生！");
		}
	    return mb;
	  }

	  public Object getZzmm() {
			String sql = "select dm, mc from zd_zzmm ";
			List list = getListSQL(sql);
		
			return list;
		}

		public  Object getMz(String dictCode) {
			String sql = "select  dm, mc from  zd_mz";
			List list = getListSQL(sql);
			return	list;
		}
		public Object getWhcd() {
			String sql = "select  dm ,mc from zd_xd ";
			List list = getListSQL(sql);
			return list;
		}
		public Object getFzdw(){
			String sql = "select  dm ,mc from zd_fzdw ";
			List list = getListSQL(sql);
			return list;
		}
		public Object getZflb() {
			String sql = "select id dm, zflb bz from zd_zflb";
			List list = getListSQL(sql);
			return list;
		}

		//关联照片

			  public MsgBean glzp(String examround,String descDir1) {
					System.out.println(examround);
					//通过bmlcid去找参考单位编码；
				    MsgBean mb = new MsgBean();
				    WritableWorkbook wwb = null;
				    mb.setShow(true);
				    String dwbm=examround.replaceAll("'", "");
				    //String dwbm="123456";
				    String ZPCJ_ID = UUID.randomUUID().toString().replaceAll("-", "");
				    String WJMC = dwbm;
				    String CJSJ = DateFormatUtils.format(new Date(), "yyyyMMdd HH:mm:ss");
				    ArrayList xxidAry = new ArrayList();
				    try {

				    	List<Map<String,String>> lsZ2= getSession().createSQLQuery("select c.SHFLAG from cus_kw_examround c join cus_kwbm_examround r on c.LCID=r.LCID where r.BMLCID='"+examround+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
						if("0".equals(lsZ2.get(0).get("SHFLAG"))||"1".equals(lsZ2.get(0).get("SHFLAG"))){
							mb.setSuccess(false);
							mb.setMsg("该轮次已上报或已审核，参考单位不能进行报名操作！");
						}
					  String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					  Session session = null;
					  SessionFactory sf = null;
					  String lj = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo")+File.separator+dwbm+ File.separator ;
				      File df = new File(lj);
				     
				      File[] dfs = CompressFileUits.searchFile(df, descDir1);
//				      File[] dfs = df.listFiles();
				      int total=0;//总照片数
				      int countok=0;//成功数
				      int lcnum=0;
				      if (dfs != null) {
				        Set set = new HashSet();
				        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
				        sf = (SessionFactory)wac.getBean("sessionFactory");
				        session = sf.openSession();
				         lcnum=Integer.valueOf(session.createSQLQuery("select sl from CUS_KWBM_EXAMROUND where bmlcid='"+dwbm+"'").uniqueResult()+"");
				          for (int j = 0; j < dfs.length; j++)
				        	  {
				        	  //照片名字
				        		 String entryName=dfs[j].getName();
				        		 System.out.println(entryName);
				        		 //身份证号
				        		 String cardID = org.apache.commons.lang.StringUtils.split(new String(entryName.getBytes("UTF-8")), ".")[0];
				        		//.jpg后缀
				        		 String type = org.apache.commons.lang.StringUtils.split(new String(entryName.getBytes("UTF-8")), ".")[1];
					        		 if(type.equals("db")){
					        			 continue;
					        		 }
					        		total++;
					        		//考生表id，参考单位id
				        	        List lstXs = checkCardID(cardID);
				        	
				        	        if ((lstXs == null) || (lstXs.size() <= 0)) {
				        	          String ZPCJ_MXID = UUID.randomUUID().toString().replaceAll("-", "");
				        	          String WTSM = "身份证件号码" + cardID + "在系统中未找到！";
				        	          String WJLJ = "";
				        	          saveZPCJ_MX_SJPT(ZPCJ_MXID, ZPCJ_ID, WTSM, WJLJ);
				        	        } else {
				        	          String xxid = ((Map)lstXs.get(0)).get("XX_JBXX_ID").toString();
				        	          String xsid=((Map)lstXs.get(0)).get("XS_JBXX_ID").toString();
				        	          if (!xxidAry.contains(xxid)) {
				        	            xxidAry.add(xxid);
				        	          }
				        	        
				        	          countok++;
				        	          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				        	          //当前时间
				        	          String cjsj = sdf.format(new Date());
				        	          //图片地址
				        	          String path1 = dfs[j].getPath();
				        	          path1 = path1.substring(path1.indexOf(dwbm));
				        	          path1=path1.replaceAll("\\\\", "\\\\\\\\");

				        	          //查询考生照片是否存在
				        	          String sql = "select * from zxxs_xs_pic where xs_jbxx_id='" + xsid + "' ";
				        	          List list = getListSQL(sql);
//				        	          path = path.replaceAll("\\\\", "/");
				        	          
				        	          if ((list != null) && (list.size() > 0)) {
				        	            sql = " update zxxs_xs_pic set path='" + path1 + "',gxr='" + 0 + 
				        	              "',gxsj='" + cjsj + "' where xs_jbxx_id='" + xsid + "' ";
				        	           
				        	            update(sql);
				        	          } else {
				        	            sql = "insert into zxxs_xs_pic(xs_jbxx_id,path,gxr,gxsj) values ('" + xsid + "','" + path1 + 
				        	              "','" + 0 + "','" + cjsj + "') ";
				        	          
				        	            insert(sql);
				        	          }
				        	        }
				        	  }
				          }


				          List list = getSJPT(ZPCJ_ID);
				          mb.setSuccess(true);
				     
				          if ((list != null) && (list.size() > 0)) {
				            for (int jj = 0; jj < xxidAry.size(); jj++) {
				              String XX_JBXX_ID = ((String)xxidAry.get(jj)).toString();
				              saveZPCJ(ZPCJ_ID, WJMC, CJSJ, XX_JBXX_ID, "0");
				            }
				            mb.setMsg("关联照片成功,该轮次共有："+lcnum+" 人,其中关联照片成功："+countok+"人,<br>共" + (lcnum-countok) + "个照片与身份证不匹配，请检查！");
				            String path = FileUpServiceImpl.class.getResource("").toString();
						    path = path.substring(6, path.lastIndexOf("xjgl") + 4);
						    path=File.separator+path;
						    Workbook rwb = null;
						  
						    WorkbookSettings workbooksetting = new WorkbookSettings();
						    WritableSheet ws = null;
				            File f = new File("/"+path + "/export/excel/temporary/" + 
							          new Date().getTime() + ".xls");
							        wwb = Workbook.createWorkbook(
							          f, 
							          Workbook.getWorkbook(new File("/"+path + "/export/excel/temp/zp.xls")));
							        ws = wwb.getSheet(0);
							        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
							        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
							          UnderlineStyle.NO_UNDERLINE, Colour.RED);
							        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
							        for (int j = 0; j < list.size(); j++) {
							          Map m =(Map)list.get(j);
							          Label l0 = new Label(0, j + 1, (j + 1)+"");
							          Label l1 = new Label(1, j + 1, m.get("WTSM")+"");
							          ws.addCell(l0);
							          ws.addCell(l1);
							        }
							        wwb.write();
				          } else {
				            for (int jj = 0; jj < xxidAry.size(); jj++) {
				              String XX_JBXX_ID = ((String)xxidAry.get(jj)).toString();
				              String sqlXx = "select * from ZXXS_XS_ZPCJ where ZPCJ_ID='" + ZPCJ_ID + "'";
				              List lstXx = getListSQL(sqlXx);
				              if (lstXx.size() > 0) {
				                String sql = "update ZXXS_XS_ZPCJ set WJMC='" + WJMC + "',CJSJ='" + CJSJ + "',XX_JBXX_ID='" + XX_JBXX_ID + "',FLAG='0' where ZPCJ_ID='" + ZPCJ_ID + "'";
				                update(sql);
				              } else {
				                saveZPCJ(ZPCJ_ID, WJMC, CJSJ, XX_JBXX_ID, "0");
				              }
				              //更新为审核
				              StringBuffer sb = new StringBuffer();
				              sb.append(" UPDATE ZXXS_XS_ZPCJ SET FLAG='1' WHERE FLAG='0' AND ZPCJ_ID='" + ZPCJ_ID + "' ");
				              update(sb.toString());
				          }

				            if(lcnum ==countok ){
				            	 mb.setMsg("关联照片成功,该轮次共有："+lcnum+" 人,其中关联照片成功："+countok+",下一步上报，请确认考生数据");
				            }else{
					            mb.setMsg("关联照片成功,该轮次共有："+lcnum+" 人,其中关联照片成功："+countok+",请继续导入");
				            }

				          
				        }

						     
					 
				    }catch (IOException e) {
				          e.printStackTrace();
				          mb.setSuccess(false);
				          mb.setMsg(MsgBean.EXPORT_ERROR);
				        } catch (BiffException e) {

						e.printStackTrace();

					} catch (RowsExceededException e) {
							e.printStackTrace();
						} catch (WriteException e) {
							e.printStackTrace();
						}finally{
							  if(wwb!=null){
						    	  try {
									wwb.close();
								} catch (WriteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						    }
							  
						}
				  
				  
				        return mb;
				}

		     public List<Map<String, Object>> getSJPT(String ZPCJ_ID) {
		    	    String sql = "select * from ZXXS_XS_ZPCJ_MX_SJPT where ZPCJ_ID='" + ZPCJ_ID + "'";
		    	    return getListSQL(sql);
		    	  }
	        public List<Map<String, Object>> checkCardID(String cardID) {
	        	try {
	        		String sql = "select XX_JBXX_ID,XS_JBXX_ID from ZXXS_XS_JBXX where sfzjh='" + cardID + "'";
	                List list = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
	                return list;
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}
	            
	          }
	        public void saveZPCJ(String ZPCJ_ID, String WJMC, String CJSJ, String XX_JBXX_ID, String FLAG) {
	            String sql = "insert into ZXXS_XS_ZPCJ(ZPCJ_ID,WJMC,CJSJ,XX_JBXX_ID,FLAG) values('" + ZPCJ_ID + "','" + WJMC + "','" + CJSJ + "','" + XX_JBXX_ID + "','" + FLAG + "')";
	            insert(sql);
	          }
	        public void saveZPCJ_MX_SJPT(String ZPCJ_MXID, String ZPCJ_ID, String WTSM, String WJLJ) {
	            String sql = "insert into ZXXS_XS_ZPCJ_MX_SJPT(ZPCJ_MXID,ZPCJ_ID,WTSM,WJLJ)VALUES('" + ZPCJ_MXID + "','" + ZPCJ_ID + "','" + WTSM + "','" + WJLJ + "')";
	            insert(sql);
	          }
	    	
	        public List<CusKwZkdw> getFzb(String regionedu) {
		  		String hql = "from CusKwZkdw where regionedu ='"+regionedu+"'";
		  		return getListHQL(hql);
		  	}

		  	 //查询参考单位

		  	public  List<CusKwCkdw> getCk(String SSZGJYXZDM) {
		  		String hql = "from CusKwCkdw where sszgjyxzdm = '"+SSZGJYXZDM+"' ";
		  		return getListHQL(hql);
		  	}
			

}
