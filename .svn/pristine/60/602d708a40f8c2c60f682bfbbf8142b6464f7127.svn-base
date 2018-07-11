package com.jiajie.service.exambasic.impl;
/**
 * 考试科目
 * */
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.exambasic.CusKwExamsubject;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.exambasic.ExamSubjectService;
import com.jiajie.util.GenerateExcel;
import com.jiajie.util.SecUtils;
import com.jiajie.util.StringUtil;
import com.jiajie.util.ZipMain;
import com.jiajie.util.ZipUtil;
import com.jiajie.util.bean.MBspInfo;

@Service("examSubjectServiceImpl")
@SuppressWarnings("all")
public class ExamSubjectServiceImpl extends ServiceBridge implements ExamSubjectService {

	public PageBean getList(Map<String, Object> map,MBspInfo mBspInfo) {
		Object obj=map.get("subject");
		String xn=null;
		String xq=null;
		String lcid=null;
		String kch=null;
		if(obj!=null){
			CusKwExamsubject subject=(CusKwExamsubject)obj;
			if(subject.getXnxqId()!=null && !"".equals(subject.getXnxqId())){
				xn=subject.getXnxqId().split(",")[0];
				xq=subject.getXnxqId().split(",")[1];
			}
			lcid=subject.getLcid();
			kch=subject.getKch();
		}
		String sql="SELECT T1.KMID,T1.XNXQ_ID,T1.XN as XN,T1.XQ,T2.MC AS XQM,T1.SUBJECTNAME,"+
				"t5.MC AS RESULTSTYLE,T1.kch AS KCH,T3.EXAMNAME,"+
				"T1.SCORE,T1.TIMELENGTH,t1.LCID,"+
				"date_format(ap.EXAMDATE,'%Y-%m-%d') as KSRQ,"+
				"date_format(ap.STARTTIME,'%H:%i') as KSSJ,"+
				"date_format(ap.ENDTIME,'%H:%i') as JSSJ "+
				"FROM CUS_KW_EXAMSUBJECT t1 "+				
				"LEFT JOIN CUS_KW_EXAMROUND t3 ON T1.LCID=T3.LCID "+
				"left join cus_kw_examschedule ap on ap.KMID=t1.KMID "+
				"left join com_mems_organ zk on zk.region_code=t3.dwid "+
				"left join com_mems_organ zk1 on zk1.region_code=zk.PARENT_CODE "+
				"left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=t3.DWID "+
				"LEFT JOIN ZD_XQ t2 ON T1.XQ=T2.DM "+
				"LEFT JOIN ZD_CJXS t5 ON T1.RESULTSTYLE = t5.DM "+
				"where 1=1 "+
				"and (t3.dwid='"+mBspInfo.getOrgan().getOrganCode()+
				"' or zk.region_code='"+mBspInfo.getOrgan().getOrganCode()+"' or zk1.region_code='"+mBspInfo.getOrgan().getOrganCode()+
				"' OR zk1.PARENT_CODE = '"+mBspInfo.getOrgan().getOrganCode()+"' or xx.SSZGJYXZDM='"+mBspInfo.getOrgan().getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+mBspInfo.getOrgan().getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+mBspInfo.getOrgan().getOrganCode()+"')))";			
		if(xn!=null && !"".equals(xn)){
			sql+="and t1.XN='"+xn+"' ";
		}
		if(xq!=null && !"".equals(xq)){
			sql+="and T1.XQ='"+xq+"' ";
		}
		if(kch!=null && !"".equals(kch)){
			sql+="and T1.KCH='"+kch+"' ";
		}
		if(lcid!=null && !"".equals(lcid)){
			sql+="and T1.LCID='"+lcid+"' ";
		}
		sql+="ORDER BY xn asc,xq asc,substr(T1.SUBJECTNAME,11,length(substr(T1.SUBJECTNAME,11))-length('批'))+0 asc";
		PageBean bean=getSQLPageBean(sql);
		return bean;
	}

	public MsgBean saveOrUpdateExamSubject(CusKwExamsubject examsubject,String xnxqValue) {		
		MsgBean msgBean=null;
		boolean isAdd=false;
		CusKwExamsubject examsub=null;
		if("".equals(examsubject.getKmid())){
			examsubject.setKmid(null);
			isAdd=true;
			examsub=examsubject;
		}else{			
			examsub=(CusKwExamsubject)get(CusKwExamsubject.class,examsubject.getKmid());
			examsub.setKch(examsubject.getKch());
			examsub.setLcid(examsubject.getLcid());
			examsub.setResultstyle(examsubject.getResultstyle());
			examsub.setScore(examsubject.getScore());
			examsub.setSubjectname(examsubject.getSubjectname());
			examsub.setTimelength(examsubject.getTimelength());
			examsub.setXnxqId(examsubject.getXnxqId());
		}
		
		if(xnxqValue!=null && !"".equals(xnxqValue) ){
			String[] arr=xnxqValue.split(",");
			examsub.setXn(arr[0]);
			examsub.setXq(arr[1]);
		}
		try {
			List<Map<String, Object>> lst = createSQLQuery("select * from CUS_KW_EXAMSUBJECT where SUBJECTNAME='"+examsub.getSubjectname()+"'").list();
			if(isAdd){				
				if (lst.size()>0) {
					msgBean=getMsgBean(false, "重复考试科目信息不能添加！");
				}else {
					saveOrUpdate(examsub);
					msgBean=getMsgBean(true, "添加成功！");				
				}			
			}else {
				if (lst.size()>0) {
					Map<String, Object> map = lst.get(0);
					if (!map.get("KMID").toString().equals(examsub.getKmid())) {
						msgBean=getMsgBean(false, "重复考试科目信息不能添加！");
						return msgBean;
					}
				}
				saveOrUpdate(examsub);
				msgBean=getMsgBean(true, "修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(isAdd){
				msgBean=getMsgBean(false, "添加失败！");
			}else{
				msgBean=getMsgBean(false, "修改失败！");
			}
		}		
		return msgBean;
	}
	
	public MsgBean copyExamSubject(CusKwExamsubject examsubject,String xnxqValue,String dwid) {
		MsgBean msgBean=null;
		try {
			String sql="SELECT distinct jb.bjmc as KC_JBXX_ID,jb.bjmc as KCMC FROM zxxs_xx_bjxx jb "+
					"left join ZXXS_XX_JBXX ck on ck.xx_jbxx_id=jb.xx_jbxx_id "+
					"left join COM_MEMS_ORGAN zk on zk.region_code=ck.SSZGJYXZDM "+
					"left join COM_MEMS_ORGAN zk1 on zk1.region_code=zk.parent_code "+
					"WHERE (jb.xx_jbxx_id='"+dwid+"' or zk.region_code='"+dwid+"' or zk.parent_code='"+dwid+"' or zk1.parent_code='"+dwid+"'))";//获取课程信息
			List<Map<String, Object>> kcList=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			if(kcList!=null && kcList.size()>0){				
				for (Map<String, Object> kc : kcList) {
					String kch=StringUtil.getString(kc.get("KC_JBXX_ID"));
					String kcmc=StringUtil.getString(kc.get("KCMC"));
					sql="SELECT COUNT(*) FROM CUS_KW_EXAMSUBJECT WHERE LCID='"+examsubject.getLcid()+"' AND KCH='"+kch+"'";
					List list=getSession().createSQLQuery(sql).list();
					if(StringUtil.getInteger(list.get(0))==0){
						CusKwExamsubject examsub=new CusKwExamsubject();
						examsub.setLcid(examsubject.getLcid());
						examsub.setResultstyle(examsubject.getResultstyle());
						examsub.setScore(examsubject.getScore());
						examsub.setTimelength(examsubject.getTimelength());
						examsub.setXnxqId(examsubject.getXnxqId());						
						if(xnxqValue!=null && !"".equals(xnxqValue) ){
							String[] arr=xnxqValue.split(",");
							examsub.setXn(arr[0]);
							examsub.setXq(arr[1]);
						}
						examsub.setKch(kch);
						examsub.setSubjectname(kcmc);
						save(examsub);
					}
				}
			}
			msgBean=getMsgBean(true, "复制成功！");
		} catch (Exception e) {
			e.printStackTrace();
			msgBean=getMsgBean(false, "复制失败！");			
		}
		return msgBean;
	}

	public MsgBean deleteExamSubject(String ids) {
		MsgBean msgBean=null;
		List ls = getSession().createSQLQuery("select * from PAPER_BASIC where KMID in ("+ids+")").list();
		if (ls.size()>0) {
			msgBean=getMsgBean(false, "已经有存在的试卷信息，不能删除！");
		}else {
			String sql="delete from CUS_KW_EXAMSUBJECT where KMID in ("+ids+")";
			msgBean=delete(sql);
		}		
		return msgBean;
	}

	public MsgBean getExamSubject(String id) {
		try {
			String sql="SELECT t1.KMID,t1.XNXQ_ID,t1.SUBJECTNAME,t1.RESULTSTYLE,"+
					"t1.KCH,t1.LCID,t1.SCORE,t1.TIMELENGTH,t1.XN,t1.XQ,CONCAT(T1.XN,'年度，',T3.MC) AS XNXQ_MC,"+
					"T2.XX_JBXX_ID AS SCHOOL "+
					"from CUS_KW_EXAMSUBJECT t1 "+
					"LEFT JOIN ZXXS_XX_NJXX t2 on T1.NJ=T2.XX_NJXX_ID "+
					"LEFT JOIN ZD_XQ T3 ON T1.XQ=T3.DM "+
					"WHERE T1.KMID='"+id+"'";
			List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			Map<String, Object> map=null;
			if(list!=null && list.size()>0){
				map=list.get(0);
			}
			CusKwExamsubject subject=new CusKwExamsubject();
			if(map!=null){
				subject.setKch(StringUtil.getString(map.get("KCH")));
				subject.setKmid(StringUtil.getString(map.get("KMID")));
				subject.setLcid(StringUtil.getString(map.get("LCID")));
				subject.setResultstyle(StringUtil.getString(map.get("RESULTSTYLE")));
				subject.setScore(Short.valueOf(StringUtil.getString(map.get("SCORE"))));
				subject.setSubjectname(StringUtil.getString(map.get("SUBJECTNAME")));
				subject.setTimelength(Short.valueOf(StringUtil.getString(map.get("TIMELENGTH"))));
				subject.setXnxqId(StringUtil.getString(map.get("XNXQ_MC")));				
			}
			this.getMsgBean(subject);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.GETDATA_ERROR);
		}
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean getExamKdDetail() {
		Session session= getSession();
		GenerateExcel ge = new GenerateExcel();
		List<Map<String,String>> list = getSession().createSQLQuery("select lf_id as kdbh,BUILDNAME as kdmc,towncity as city FROM cus_kw_building").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		ZipUtil zip = new ZipUtil();
		Random rd  = new Random(0);
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map = ge.getDetailByKd(session, list.get(i).get("kdbh"), list.get(i).get("kdmc"),list.get(i).get("city"), 50);
			if((Boolean)map.get("success")){
				String code = Math.abs(rd.nextInt())+""; 
				getSession().createSQLQuery("insert into exam_mm(kdbh,kdmc,code) values('"+list.get(i).get("kdbh")+"','"+list.get(i).get("kdmc")+"','"+code+"')").executeUpdate();
				zip.zipFileAndEncode(map.get("data").toString(), code);
			}
		} 
		System.out.println("ok");
		return null;
	}
	
	public com.jiajie.bean.MsgBean getExamCkdwDetail() {
		Session session= getSession();
		GenerateExcel ge = new GenerateExcel();
//		List<Map<String,String>> list = getSession().createSQLQuery("select ckdw,zkdw,'湖南省' as city from exam_score_detail where (select towncity from cus_kw_building where LF_ID =  kdbh) = '湖南省' group by ckdw,zkdw").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//		ZipUtil zip = new ZipUtil();
//		Random rd  = new Random(1);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println("  = = = = = "+i+"  = = = = = ");
//			Map<String,Object> map = ge.getDetailByCkdw(session, list.get(i).get("zkdw"), list.get(i).get("ckdw"),list.get(i).get("city"), 50);
//			if((Boolean)map.get("success")){
//				String code = Math.abs(rd.nextInt())+""; 
//				getSession().createSQLQuery("insert into exam_mm_ckdw(zkdw,ckdw,code) values('"+list.get(i).get("zkdw")+"','"+list.get(i).get("ckdw")+"','"+code+"')").executeUpdate();
//				zip.zipFileAndEncode(map.get("data").toString(), code);
//			}
//		} 
//		System.out.println("ok");
		
		List<String> list = getSession().createSQLQuery("select region_edu From com_mems_organ where region_code in (433100000001,433101000000)").list();
		ZipUtil zip = new ZipUtil();
		Random rd  = new Random(1);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("  = = = = = "+i+"  = = = = = ");
			Map<String,Object> map = ge.getDetailByZkdw(session, list.get(i),"222", 50);
			if((Boolean)map.get("success")){
				String code = Math.abs(rd.nextInt())+""; 
				getSession().createSQLQuery("insert into exam_mm_ckdw(zkdw,ckdw,code) values('"+list.get(i)+"','0','"+code+"')").executeUpdate();
				zip.zipFileAndEncode(map.get("data").toString(), code);
			}
		} 
		System.out.println("ok");
		return null;
	}

	public void bkcj() {
		File[] f = new File("D:/nihaopiaol/31").listFiles();
		ZipMain zm = new ZipMain();
		FileInputStream fi =null;  
		ObjectInputStream in = null;
		for (int i = 0; i < f.length; i++) {
			double sumSco = 0;
			try {
				zm.decryptUnzip(f[i].getPath(),f[i].getParent(), "hnjjrjsyb..asdfghjkl");
				fi = new FileInputStream(f[i].getPath().replace(".ca", ""));
				in = new ObjectInputStream(fi); 
				Map<String,String> pl= (Map<String,String>)in.readObject();
				if(pl!=null && pl.size()>0){  
					Iterator<String> iter =  pl.keySet().iterator();
					String paperId = pl.get("paperId");
					while(iter.hasNext()){
						String key = iter.next();
						if(!"paperId".equals(key)){
							String da = pl.get(key);
							byte[] bt = da.replace("##","").getBytes(); 
							Arrays.sort(bt);
							String nd = new String(bt);
							String sc = null;
							String sql = "select case when count(1) = 0 then 0 else concat(scores,'') end from exam_info where exam_info_id = '"+key+"' and exam_answ = '"+SecUtils.encode(nd)+"'";
							sc = getSession().createSQLQuery(sql).uniqueResult().toString()+"";
							double sco = Double.parseDouble(sc);
							sumSco = sumSco + sco;
						}
					}
					getSession().createSQLQuery("insert into cus_ks_nsocre(ksid,socre) values('"+f[i].getName().replace(".ca", "")+"',"+Math.ceil(sumSco)+")").executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	 
	
}
