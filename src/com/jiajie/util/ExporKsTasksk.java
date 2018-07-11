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

	public class  ExporKsTasksk  implements Runnable{
		private String key;
		
		public ExporKsTasksk(String key){
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
				String bmlcid = key.split(",")[0];
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
						sb.append("姓名不能为空! ");
					}
					if(xsinfo.getErrMsg()==null || "".equals(xsinfo.getErrMsg())){ 
//						flag =true;
					}else{
						flag =false;
						sb.append(xsinfo.getErrMsg());
					}
					
					if(xsinfo.getZzmm()==null || "".equals(xsinfo.getZzmm())){ 
						flag =false;
						sb.append("政治面貌不能为空! ");
					}else{
						int a =	Integer.parseInt(session.createSQLQuery("select count(1) From zd_zzmm where mc = '"+xsinfo.getZzmm()+"'").uniqueResult().toString());
						if(a<1){
							flag =false;
							sb.append("政治面貌:'"+xsinfo.getZzmm()+"'在字典项中不存在! ");
						} 
					}
					if(xsinfo.getMz()==null || "".equals(xsinfo.getMz())){ 
						flag =false;
						sb.append("民族不能为空! ");
					}else{
						int a =	Integer.parseInt(session.createSQLQuery("select count(1) From zd_mz where mc = '"+xsinfo.getMz()+"'").uniqueResult().toString());
						if(a<1){
							flag =false;
							sb.append("民族:'"+xsinfo.getMz()+"'在字典项中不存在! ");
						} 
					}
					if(xsinfo.getWhcd()==null || "".equals(xsinfo.getWhcd())){ 
						flag =false;
						sb.append("文化程度不能为空! ");
					}else{
						int a =	Integer.parseInt(session.createSQLQuery("select count(1) From zd_xd where mc = '"+xsinfo.getWhcd()+"'").uniqueResult().toString());
						if(a<1){
							flag =false;
							sb.append("文化程度:'"+xsinfo.getWhcd()+"'在字典项中不存在! ");
						} 
					}
					if(xsinfo.getZy()==null || "".equals(xsinfo.getZy())){ 
						flag =false;
						sb.append("专业不能为空! ");
					}
					if(xsinfo.getZw()==null || "".equals(xsinfo.getZw())){ 
						flag =false;
						sb.append("职务不能为空! ");
					}
					if(xsinfo.getZffw()==null || "".equals(xsinfo.getZffw())){ 
						flag =false;
						sb.append("执法范围不能为空! ");
					}
					if(xsinfo.getZflb()==null || "".equals(xsinfo.getZflb())){ 
						flag =false;
						sb.append("执法类别不能为空! ");
					}else{
						int a =	Integer.parseInt(session.createSQLQuery("select count(1) From zd_zflb where zflb = '"+xsinfo.getZflb()+"'").uniqueResult().toString());
						if(a<1){
							flag =false;
							sb.append("执法类别:'"+xsinfo.getZflb()+"'在字典项中不存在! ");
						} 
					}
					if(xsinfo.getStuSex()==null || "".equals(xsinfo.getStuSex())){ 
						flag =false;
						sb.append("性别不能为空! ");
					}else{
						if("男".equals(xsinfo.getStuSex()))xbm = 1;
						else if("女".equals(xsinfo.getStuSex()))xbm=2;
						else{
							flag =false;
							sb.append("性别填写有误! ");
						}
					}
					if(xsinfo.getSfzjh() == null || "".equals(xsinfo.getSfzjh())){ 
						flag =false;
						sb.append("身份证件号不能为空! ");
					}else{
						if(!CardUtils.isIDCard(xsinfo.getSfzjh())){			
							flag =false;
							sb.append("请检查身份证的有效性!");
						}
					}
					
					if(xsinfo.getParticipatingUntisName() == null || "".equals(xsinfo.getParticipatingUntisName())){ 
						flag =false;
						sb.append("参考单位地址不能为空! ");
					}
					int fn = Integer.parseInt(session.createSQLQuery("select count(1) From zxxs_xs_jbxx where sfzjh = '"+xsinfo.getSfzjh()+"'").uniqueResult().toString());
					if(fn>0){
						flag =false;
						List<Map<String,String>> lsZ3 = session.createSQLQuery("select xxmc from zxxs_xx_jbxx where XX_JBXX_ID=(select XX_JBXX_ID from zxxs_xs_jbxx where  sfzjh='"+xsinfo.getSfzjh()+"') ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
						sb.append("您上传的身份证号码与"+lsZ3.get(0).get("xxmc")+"参考单位的身份证件号重复!");
					}
					String xx_jbxx_id =null;; 
					String zkdwm = null;
					String yzbm = null;
					if(xsinfo.getFzdw()==null&&xsinfo.getFzdw().equals("")){
						flag =false;
						sb.append("发证单位不能为空! ");
					}else{
						int a =	Integer.parseInt(session.createSQLQuery("select count(1) From zfry_fzdw where fzdw = '"+xsinfo.getFzdw()+"'").uniqueResult().toString());
						if(a<1){
							flag =false;
							sb.append("发证单位填写有误! ");
						} 
					}
					List<Map<String,String>> lsZ = session.createSQLQuery("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where SSZGJYXZDM='"+xsinfo.getParticipatingUntisCode()+"' and  XXMC='"+xsinfo.getParticipatingUntisName()+"' and dwlx='2'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
					if(lsZ==null || lsZ.size()==0){
						flag =false;
						sb.append(xsinfo.getParticipatingUntisName()+"单位名称不存在，请核对！");
					}else{
						xx_jbxx_id =lsZ.get(0).get("XX_JBXX_ID"); 
						zkdwm = lsZ.get(0).get("SSZGJYXZDM")==null?"":lsZ.get(0).get("SSZGJYXZDM").toString();
						yzbm = lsZ.get(0).get("YZBM")==null?"":lsZ.get(0).get("YZBM").toString();
						int n = Integer.parseInt(session.createSQLQuery("select count(1) from cus_kwbm_examround where bmlcid = '"+bmlcid+"' and dwid='"+xx_jbxx_id+"'").uniqueResult().toString());
						if(n==0){
							flag = false;
							sb.append("参考单位不属于本轮次考试! ");
						}
					}
					if(flag){
						String xs_jbxx_id = StringUtil.getUUID();
						System.out.println(".............."+xsinfo.getSfzjh());
						String x = session.createSQLQuery("select DM from zd_xb where mc = '"+xsinfo.getStuSex()+"'").uniqueResult().toString();//性别
						String mzm = session.createSQLQuery("select DM from  zd_mz where mc = '"+xsinfo.getMz()+"'").uniqueResult().toString();//民族
						String zzmm = session.createSQLQuery("SELECT DM FROM  zd_zzmm WHERE mc = '"+xsinfo.getZzmm()+"'").uniqueResult().toString();//政治面貌 
						String  WHCD = session.createSQLQuery("select DM from  zd_xd where mc = '"+xsinfo.getWhcd()+"'").uniqueResult().toString();//文化程度
						String zflb = session.createSQLQuery("select id from zd_zflb where zflb  = '"+xsinfo.getZflb()+"'").uniqueResult().toString();//执法列别
						//添加考生信息
						session.createSQLQuery("insert into zxxs_xs_jbxx(xs_jbxx_id,grbsm,xm,xbm,sfzjlxm,sfzjh,xx_jbxx_id,xxsszgjyxzdm,bmdz,mzm,ZZMM,WHCD,zy,zw,zflx,zffw,fzdw,remark) "+
								"values('"+xs_jbxx_id+"','G"+xsinfo.getSfzjh()+"','"+xsinfo.getStuName()+"','"+x+"','1','"+xsinfo.getSfzjh()+
								"','"+xx_jbxx_id+"','"+zkdwm+"','"+zkdwm+"','"+mzm+"','"+zzmm+"','"+WHCD+"','"+xsinfo.getZy()+"','"+xsinfo.getZw()+"','"+zflb+"','"+xsinfo.getZffw()+"','"+xsinfo.getFzdw()+"','"+xsinfo.getRemark()+"')").executeUpdate();
						//添加用户信息
						session.createSQLQuery("insert into t_qxgl_userinfo(usercode,username,usertype,loginid,loginpwd,flage) " +
								"values('"+xs_jbxx_id+"','"+xsinfo.getStuName()+"',3,'"+xsinfo.getSfzjh()+"','"+new Md5Hash(xsinfo.getSfzjh().substring(xsinfo.getSfzjh().length()-6)).toString()+"',1)").executeUpdate();
						SimpleDateFormat format = new SimpleDateFormat("yyyyMM",Locale.CHINA);		
						String dateStr = format.format(new Date());
						NumberFormat nf = NumberFormat.getInstance();
						String kscode = yzbm+dateStr+nf.format(xsinfo.getKsbh());
						//添加考试信息
						session.createSQLQuery("insert into cus_kw_examinee(KSID,LCID,KSCODE,XJH,XM,XXDM,XBM,SFZJH,GW,XS_JBXX_ID,BMLCID) values('"+StringUtil.getUUID()+
								"','"+xsinfo.getLcid()+"','"+kscode+"','G"+xsinfo.getSfzjh()+"','"+xsinfo.getStuName()+"','"+
								xx_jbxx_id+"','"+x+"','"+xsinfo.getSfzjh()+"','"+xsinfo.getZw()+"','"+xs_jbxx_id+"','"+bmlcid+"')").executeUpdate();
				
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
