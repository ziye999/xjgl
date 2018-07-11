package com.jiajie.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jiajie.service.exambasic.impl.ExamzjServiceImpl;
import com.jiajie.util.bean.KsInfo;

public class SyncData implements Runnable{
	private static final Log log = LogFactory.getLog(ExamzjServiceImpl.class);
		public void run() {  

			String xsid = null;
			String ksid = null;
			ExamKsInfo eki = ExamKsInfo.getInstance();
			eki.addThreadsNum();
			Session session = null;
			SessionFactory sf = null;
			ZipMain zm = new ZipMain();
			FileInputStream fi =null;  
			ObjectInputStream in = null;
			PreparedStatement ps = null;
			Connection conn = null;
			ResultSet rs  = null;
			String dtsj =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext(); 
			try {
				sf = (SessionFactory)wac.getBean("sessionFactory");   
				conn = SessionFactoryUtils.getDataSource(sf).getConnection();
				while(true){
					KsInfo ksinfo = eki.assignKs(); 
					if(ksinfo==null){
						break;
					}
					String roomid = ksinfo.getRoomid();
					ksid = ksinfo.getXsid();
					rs =  conn.prepareStatement("select xs_jbxx_id from cus_kw_examinee where ksid = '"+ksid+"'").executeQuery();
					while(rs.next()){
						xsid = rs.getString(1);
					}
					String kmid = ksinfo.getKmid();
					double sumSco = 0;
					String submitTime="";	
					if(!"0".equals(ksinfo.getKsStatu()) && ksinfo.getPath()!=null){
						File f_ks = new File(ksinfo.getPath());
						if(f_ks.exists()){
							try {
								zm.decryptUnzip(f_ks.getPath(),f_ks.getParent(), "hnjjrjsyb..asdfghjkl");
								fi = new FileInputStream(f_ks.getPath().replace(".ca", ""));
								in = new ObjectInputStream(fi); 
								Map<String,String> pl= (Map<String,String>)in.readObject();
								if(pl!=null && pl.size()>0){  
									Iterator<String> iter =  pl.keySet().iterator();
									String paperId = pl.get("paperId");
									while(iter.hasNext()){
										String key = iter.next();
										if(key.equals("submitTime")){
											submitTime=pl.get(key);
											continue;
										}
										if(!"paperId".equals(key) &&key.indexOf("_")==-1){
											String da = pl.get(key);
											byte[] bt = da.replace("##","").replace(key+"_", "").getBytes(); 
											Arrays.sort(bt);
											String nd = new String(bt);
											String sc = null;
											String sql = "select case when count(1) = 0 then 0 else concat(scores,'') end from exam_info where exam_info_id = '"+key+"' and exam_answ = '"+SecUtils.encode(nd)+"'";
											ps =conn.prepareStatement(sql);
											rs = ps.executeQuery();
											while(rs.next()){
												sc  = rs.getString(1);
											}
											double sco = Double.parseDouble(sc);
											sumSco = sumSco + sco;
											conn.prepareStatement("insert into cus_exam_detail_bk2(id,paper_id,exam_info_id,xs_jbxx_id,exam_bhs,score) values(replace(uuid(),'-',''),'"+paperId+"','"+key+"','"+xsid+"','"+nd+"',"+sco+")").execute();
										}else{
										}
									}
								}
							} catch (Exception e) {
								System.out.println("///");
								conn.prepareStatement("insert into da_log(logid,xsid,kmid,roomid,errinfo,file_len,file_url) values(replace(uuid(),'-',''),'"+xsid+"','"+kmid+"','"+roomid+"','"+e.getMessage()+"','"+f_ks.length()+"','"+ksinfo.getPath()+"')").execute();
							}
						}
					}
						String xsinfoSql = "select xxdm ,kmid,lcid,kscode,xjh,ksid,xm,concat(xbm,'') as xbm from cus_kw_examinee where ksid = '"+ksid+"'";
						ps = conn.prepareCall(xsinfoSql);
						rs = ps.executeQuery();
						Map<String,String> xsinfo = null;
						while(rs.next()){
							xsinfo = new HashMap<String,String>();
							xsinfo.put("xxdm", rs.getString(1));
							xsinfo.put("kmid", rs.getString(2));
							xsinfo.put("lcid", rs.getString(3));
							xsinfo.put("kscode", rs.getString(4));
							xsinfo.put("xjh", rs.getString(5));
							xsinfo.put("ksid", rs.getString(6));
							xsinfo.put("xm", rs.getString(7));
							xsinfo.put("xbm", rs.getString(8)); 
						}
						String ksjg="未合格";
						if(sumSco>=60)
							ksjg="合格";
						if(xsinfo!=null && !"0".equals(ksinfo.getKsStatu())){
							
							if(!"1".equals(ksinfo.getKsStatu())){
									int istj = 1;
									int isdl = 1; 
//									if("4".equals(ksinfo.getKsStatu())){
//										istj = 1;
//									}
									ps = conn.prepareCall("select max(xscjid) from cus_kw_stuscore_bk where ksid ='"+xsinfo.get("ksid")+"' and kmid = '"+xsinfo.get("kmid")+"'");
									rs = ps.executeQuery();
									String cjid = null;
									while(rs.next()){
										cjid = rs.getString(1);
									}
									if(cjid!=null){ 
										conn.prepareCall("update cus_kw_stuscore_bk set score="+sumSco+",WRITER='"+submitTime+"',scorejm = '"+SecUtils.encode(sumSco+"")+"' where xscjid ='"+cjid+"'").execute();
									}else{
										conn.prepareCall("insert into cus_kw_stuscore_bk(xscjid,lcid,kmid,examcode,xjh,score,WRITER,scorejm,ksid,xm,xbm,xxdm) values(replace(uuid(),'-',''),'"+xsinfo.get("lcid")+"','"+xsinfo.get("kmid")+"','"+xsinfo.get("kscode")+"','"+xsinfo.get("xjh")+"',"+sumSco+",'"+submitTime+"','"+SecUtils.encode(sumSco+"")+"','"+xsinfo.get("ksid")+"','"+xsinfo.get("xm")+"',"+xsinfo.get("xbm")+",'"+xsinfo.get("xxdm")+"')").execute();
									}
									conn.prepareCall("update cus_kw_examinee set nj='"+ksjg+"',BH='"+sumSco+"', isks = 1,isdl="+isdl+" ,istj = "+istj+",dtsj='"+dtsj+"' where ksid = '"+xsinfo.get("ksid")+"'").execute();
							}else {
								conn.prepareCall("update cus_kw_examinee set nj='"+ksjg+"',BH='"+sumSco+"', isks = 0,dtsj='"+dtsj+"' where ksid = '"+xsinfo.get("ksid")+"'").execute();
							}
							}else {
							conn.prepareCall("update cus_kw_examinee set nj='"+ksjg+"',BH='"+sumSco+"', isks = 0,dtsj='"+dtsj+"' where ksid = '"+xsinfo.get("ksid")+"'").execute();
						}
							
						
				}
			}catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage()+" = = ="+xsid);
			}finally{
				if(session!=null){
					session.close();
				}
				if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(sf!=null){
					sf.close();
				}
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fi!=null){
					try {
						fi.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			eki.addDoneThreadsNum();
	}
}