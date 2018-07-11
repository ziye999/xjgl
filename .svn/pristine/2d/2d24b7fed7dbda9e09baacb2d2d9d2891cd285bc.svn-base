package com.jiajie.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.hnjj.bean.StudentInfo;

import com.jiajie.util.FileIoUtils;
import com.jiajie.util.ZipMain;

public class DownloadExamFilter implements Filter { 
	private static final Log log = LogFactory.getLog(DownloadExamFilter.class); 

	public void destroy() { 
		log.debug("DownloadExamFilter destroy compleate.........");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException,IOException{ 
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response; 
//		Session session = null;
//		ObjectOutputStream out = null;
//		ObjectOutputStream out2 = null;
//		FileOutputStream fos = null;
//		FileOutputStream fos2 = null;
//		try {
//			String uri = req.getRequestURI();
//			String roominfo = uri.replace(".cf","").replace("/","").replace("xjglexam","");
//			String roomid = roominfo.split("_")[0];
//			String d = roominfo.split("_")[1];
//			String de = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//			if(!de.equals(d)){
//				Calendar cal = Calendar.getInstance();
//				if(cal.get(Calendar.HOUR_OF_DAY)<17){
//					return;
//				}
//			}
//			log.error("1111111111111111111111111111111111");
//			ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
//			SessionFactory sessionFactory = (SessionFactory)ac1.getBean("sessionFactory");
//			session = sessionFactory.openSession();
//			List<Map<String,String>> list = session.createSQLQuery("select er.kcid,er.lcid from cus_kw_examroom er ,cus_kw_examround r where r.lcid = er.LCID and roomid = '"+roomid+"' order by r.startdate desc limit 1").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//			//序列化到物理内存
//			if(list==null || list.size() ==0){
//				return;
//			}else{
//				String lcid = list.get(0).get("lcid");
//				String kcid = list.get(0).get("kcid");  
//				if(lcid!=null){
//					StringBuilder sql = new StringBuilder();
//					List<Map<String,String>> kms = (List<Map<String,String>>)session.createSQLQuery("select kmid,starttime ,endtime from cus_kw_examschedule where lcid = '"+lcid+"' and date_format(examdate,'%Y-%m-%d') = '"+d+"' order by starttime ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//					List<Map<String,String>> xsidinfo = new ArrayList<Map<String,String>>();
//					Map<Long,String> pcid = new TreeMap<Long, String>();
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					for (int i = 0; i < kms.size(); i++) {
//						pcid.put(sdf.parse(kms.get(i).get("starttime")).getTime(), kms.get(i).get("kmid"));
//						sql.delete(0,sql.length());
//						sql.append("select '").append(kms.get(i).get("starttime")).append("' as starttime ,'").append(kms.get(i).get("endtime")).append("' as endtime, '").append(kms.get(i).get("kmid")).append("' as kmid ,usercode as xsid,loginid as sfzh,'123456' as pwd,username from t_qxgl_userinfo where concat('G',loginid) in (select xh from cus_kw_roomstudent t where kcid = '");
//						sql.append(kcid).append("' and kmid = '").append(kms.get(i).get("kmid")).append("')");
//						xsidinfo.addAll((List<Map<String,String>>)session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
//					}
//					List<StudentInfo> xsids = new ArrayList<StudentInfo>();
//					//创建初始化kcid文件夹
//					String path = init(roominfo);
//					File obj = new File(path+File.separator+roominfo+File.separator+"students.cs"); 
//					System.out.println(obj);
//					boolean flag = false;
//					for (int i = 0; i < xsidinfo.size(); i++) {
//						StudentInfo s = new StudentInfo();
//						s.setPwd(xsidinfo.get(i).get("sfzh").substring(xsidinfo.get(i).get("sfzh").length()-6));
//						s.setXsid(xsidinfo.get(i).get("xsid"));
//						s.setSfzh(xsidinfo.get(i).get("sfzh")); 
//						s.setUsername(xsidinfo.get(i).get("username"));
//						s.setBegindate(xsidinfo.get(i).get("starttime"));
//						s.setEnddate(xsidinfo.get(i).get("endtime"));
//						s.setPcid(xsidinfo.get(i).get("kmid"));
//						xsids.add(s);
//						File f = new File(path+File.separator+xsidinfo.get(i).get("xsid")+".cf");
//						File f2 = new File(path+File.separator+xsidinfo.get(i).get("xsid")+".tx");
//						if(f.exists()){
//							flag = true;
//							FileIoUtils.moveFile(path+File.separator+xsidinfo.get(i).get("xsid")+".cf",path+File.separator+roominfo);
//						}
//						if(f2.exists()){ 
//							FileIoUtils.moveFile(path+File.separator+xsidinfo.get(i).get("xsid")+".tx",path+File.separator+roominfo);
//						}
//					}
//					if(obj.exists()){
//						obj.delete();
//						obj.createNewFile();
//					}else{
//						obj.createNewFile();
//					}
//					fos = new FileOutputStream(path+File.separator+roominfo+File.separator+"students.cs");
//					out = new ObjectOutputStream(fos);
//					out.writeObject(xsids);
//					fos2 = new FileOutputStream(path+File.separator+roominfo+File.separator+"pcid.cs");
//					out2 = new ObjectOutputStream(fos2);
//					out2.writeObject(pcid);
//					log.error("2222222222222222222222222222222");
//					if(true){
//						File f = new File(path.replace("WEB-INF/exam", "")+"exam"+File.separator+roominfo+".cf");
//						if(f.exists()){
//							f.delete();
//						}
//						ZipMain.encryptZip(path+File.separator+roominfo, path.replace("WEB-INF/exam", "")+"exam"+File.separator+roominfo+".cf", "hnjjrjsyb..asdfghjkl");
//					}
//					chain.doFilter(req, res);
//				}
//			}
//			log.error("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			if(fos!=null){
//				fos.close();
//			}
//			if(fos2!=null){
//				fos2.close();
//			}
//			if(out!=null){
//				out.close();
//			}
//			if(out2!=null){
//				out2.close();
//			}
//			if(session!=null){
//				session.close();
//			}
//		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("DownloadExamFilter init compleate.........");
	}

//	private String init(String kcid) throws IOException{
//		String path = DownloadExamFilter.class.getResource("").toString().replace("classes/com/jiajie/filter/", "").replace("file:/", "")+"exam";
//		File fkcid = new File(path+File.separator+kcid);
//		if(!fkcid.exists()){
//			fkcid.mkdirs(); 
//		}
//		return path;
//	} 
	 
	 
	 
}
