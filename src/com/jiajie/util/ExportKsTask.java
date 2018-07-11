package com.jiajie.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.util.bean.XsInfo;

public class ExportKsTask implements Runnable{
	private String key;
	
	public ExportKsTask(String key){
		this.key = key;
	}
	
	public void run() {
		ExportKsInfo eki = ExportKsInfo.getInstance();
		WebApplicationContext wac = null;
		SessionFactory sf = null;   
		Session session = null;
		StringBuilder sb = new StringBuilder();
		try {
			wac = ContextLoader.getCurrentWebApplicationContext(); 
			sf = (SessionFactory)wac.getBean("sessionFactory");
			session = sf.openSession();
			String lcid = key.split(",")[0];
			while(true){
				XsInfo xsinfo = eki.assignInfoByKey(key);
				if(xsinfo==null){
					break;
				}
				System.out.println(xsinfo.getSfzjh());
				boolean flag = true;
				sb.delete(0,sb.length());
				int xbm =1;
				if(xsinfo.getStuName()==null || "".equals(xsinfo.getStuName())){ 
					flag =false;
					sb.append("姓名不能为空!");
				}
				if(xsinfo.getStuSex()==null || "".equals(xsinfo.getStuSex())){ 
					flag =false;
					sb.append("性别不能为空!");
				}else{
					if("男".equals(xsinfo.getStuSex()))xbm = 1;
					else if("女".equals(xsinfo.getStuSex()))xbm=2;
					else{
						flag =false;
						sb.append("性别填写有误!");
					}
				}
				if(xsinfo.getSfzjh() == null || "".equals(xsinfo.getSfzjh())){ 
					flag =false;
					sb.append("身份证件号不能为空!");
				}
//				if(!CardUtils.isIDCard(xsinfo.getSfzjh())){
//					flag =false;
//					sb.append("请检查身份证的有效性!");
//				}
				if(xsinfo.getParticipatingUntisName() == null || "".equals(xsinfo.getParticipatingUntisName())){ 
					flag =false;
					sb.append("参考单位地址不能为空!");
				}
				int fn = Integer.parseInt(session.createSQLQuery("select count(1) From zxxs_xs_jbxx where sfzjh = '"+xsinfo.getSfzjh()+"'").uniqueResult().toString());
				if(fn>0){
					flag =false;
					sb.append("身份证件号重复!");
				} 
				String xx_jbxx_id =null;; 
				String zkdwm = null;
				String yzbm = null;
				List<Map<String,String>> lsZ = session.createSQLQuery("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where SSZGJYXZDM='"+xsinfo.getParticipatingUntisCode()+"' and  XXMC='"+xsinfo.getParticipatingUntisName()+"' and dwlx='2'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if(lsZ==null || lsZ.size()==0){
					flag =false;
					sb.append(xsinfo.getParticipatingUntisName()+"单位名称不存在，请核对！");
				}else{
					xx_jbxx_id =lsZ.get(0).get("XX_JBXX_ID"); 
					zkdwm = lsZ.get(0).get("SSZGJYXZDM")==null?"":lsZ.get(0).get("SSZGJYXZDM").toString();
					yzbm = lsZ.get(0).get("YZBM")==null?"":lsZ.get(0).get("YZBM").toString();
					int n = Integer.parseInt(session.createSQLQuery("select count(1) from cus_kw_examround where lcid = '"+lcid+"' and locate('"+zkdwm+"',getRegionCode(dwid))>0").uniqueResult().toString());
					if(n==0){
						flag = false;
						sb.append("参考单位不属于本轮次考试!");
					}
				}
				if(flag){
					String xs_jbxx_id = StringUtil.getUUID();
					System.out.println(".............."+xsinfo.getSfzjh());
					String x = session.createSQLQuery("select DM from zd_xb where mc = '"+xsinfo.getStuSex()+"'").uniqueResult().toString();//性别
					session.createSQLQuery("insert into zxxs_xs_jbxx(xs_jbxx_id,grbsm,xm,xbm,sfzjlxm,sfzjh,xx_jbxx_id,xxsszgjyxzdm,bmdz) "+
							"values('"+xs_jbxx_id+"','G"+xsinfo.getSfzjh()+"','"+xsinfo.getStuName()+"','"+x+"','1','"+xsinfo.getSfzjh()+
							"','"+xx_jbxx_id+"','"+zkdwm+"','"+zkdwm+"')").executeUpdate();
					session.createSQLQuery("insert into t_qxgl_userinfo(usercode,username,usertype,loginid,loginpwd,flage) values('"+xs_jbxx_id+"','"+xsinfo.getStuName()+"',3,'"+xsinfo.getSfzjh()+"','"+new Md5Hash(xsinfo.getSfzjh().substring(xsinfo.getSfzjh().length()-6)).toString()+"',1)").executeUpdate();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMM",Locale.CHINA);		
					String dateStr = format.format(new Date());
					NumberFormat nf = NumberFormat.getInstance();
					String kscode = yzbm+dateStr+nf.format(xsinfo.getKsbh());
					session.createSQLQuery("insert into cus_kw_examinee(KSID,LCID,KSCODE,XJH,XM,XXDM,XBM,SFZJH,GW) values('"+StringUtil.getUUID()+
							"','"+lcid+"','"+kscode+"','G"+xsinfo.getSfzjh()+"','"+xsinfo.getStuName()+"','"+
							xx_jbxx_id+"','"+x+"','"+xsinfo.getSfzjh()+"','"+xsinfo.getWorkspace()+"')").executeUpdate();
				} else{
					xsinfo.setErrMsg(sb.toString());
					eki.putErrorData(key,xsinfo);
				}
				
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
		eki.doneThreadsByKey(key);
	}
}
