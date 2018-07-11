package com.jiajie.service.exambasic.impl;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExaminee;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.exambasic.ExamInfomationService;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import com.mysql.jdbc.StringUtils;

@Service("examInfomationService")
@SuppressWarnings("all")
public class ExamInfomationServiceImpl extends ServiceBridge implements ExamInfomationService{
		
	public PageBean getList(String xnxqId,String lcid,MBspInfo bspInfo){
		String xn = "";
		String xq = "";
		if(xnxqId != null && !"".equals(xnxqId)){
			String [] str = xnxqId.split(",");
			xn = str[0].toString();
			xq = str[1].toString();
		}
		MBspOrgan orgn = bspInfo.getOrgan();
		String sql = "SELECT T.DWTYPE,T.DWID,T.WJFLAG,T.LCID,T.XN,T.XQ,T.XNXQ_ID,T.EXAMNAME,T.EXAMTYPE,T.DWMC,T.CKDW,T.SL,T.YY,T.SL-T.YY as WY,T.YDL,T.SL-T.YDL as WDL,T.QK "+
				"FROM (SELECT T1.DWTYPE,T1.DWID,T1.WJFLAG,T1.LCID,T1.XN,T2.NAME AS XQ,T1.XNXQ_ID,T1.EXAMNAME,T3.MC AS EXAMTYPE,"+
				"IFNULL(IFNULL(zk.REGION_EDU,zk1.REGION_EDU),(select (select region_edu from com_mems_organ where region_code=a.SSZGJYXZDM) from zxxs_xx_jbxx a where a.xx_jbxx_id=T1.DWID)) AS DWMC,"+
				"IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = T1.dwid),'全部') CKDW,"+
				"T1.SL,T1.YY,T1.YDL,T1.QK"+
				" FROM CUS_KW_EXAMROUND T1"+
				" left join COM_MEMS_ORGAN zk on zk.region_code=t1.dwid"+
                " left join COM_MEMS_ORGAN zk1 on zk1.region_code=zk.PARENT_CODE"+	
				" left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=T1.DWID"+
				" LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XQM=T2.CODE AND T2.DIC_TYPE='ZD_XT_XQMC'"+
				" LEFT JOIN CUS_KW_EXAMTYPE T3 ON T1.EXAMTYPEM=T3.DM WHERE 1=1 ";
		sql += " and (T1.DWID='"+orgn.getOrganCode()+
				"' or zk.region_code='"+orgn.getOrganCode()+"' or zk1.region_code='"+orgn.getOrganCode()+
				"' OR zk1.PARENT_CODE = '"+orgn.getOrganCode()+"' or xx.SSZGJYXZDM='"+orgn.getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+"'))) ";
		if(xn != null && !"".equals(xn)){
			sql += " AND T1.XN='"+xn+"' ";
		}
		if(xq != null && !"".equals(xq)){
			sql += " AND T1.XQM='"+xq+"' ";
		}
		if(lcid != null && !"".equals(lcid)){
			sql += " AND T1.lcid='"+lcid+"' ";
		}
		sql += " ) T  ORDER BY T.XN,T.XQ,T.XNXQ_ID,T.LCID,T.DWID";
		return getSQLPageBean(sql);
	}
	
	public String getKs(String pc,String kd,String kc,String lcid,String ks){		
		String sqlKs = "";
		if (!StringUtils.isNullOrEmpty(pc)) {
			sqlKs = "SELECT T1.KSCODE,T1.XM,T1.SFZJH,T3.XXMC,"+
					"date_format(ap.STARTTIME,'%Y-%m-%d %H:%i') STARTTIME,T6.path AS profile_url,T1.ISKS "+
					"FROM CUS_KW_EXAMINEE T1 "+
					"LEFT JOIN ZXXS_XX_JBXX T3 ON T1.XXDM = T3.XX_JBXX_ID "+
					"LEFT JOIN ZXXS_XS_PIC T6 ON T6.xs_jbxx_id = T1.xs_jbxx_id "+
					"LEFT JOIN cus_kw_examschedule ap ON ap.kmid = T1.kmid "+					
					"WHERE T1.KMID = '"+pc+"' AND T1.KDID = '"+kd+"' AND T1.KCID = '"+kc+
					"' ORDER BY T1.KSCODE ASC";
		}else if (!StringUtils.isNullOrEmpty(lcid)) {
			String ids = "";
			String[] split = ks.split(";");
			for (int i = 0; i < split.length; i++) {
				ids = ids+"'"+split[i]+"',";
			}
			ids = ids.substring(0,ids.length()-1);
			sqlKs = "SELECT T1.KSCODE,T1.XM,T1.SFZJH,T3.XXMC,"+
					"date_format(ap.STARTTIME,'%Y-%m-%d %H:%i') STARTTIME,T6.path AS profile_url,T1.ISKS "+
					"FROM CUS_KW_EXAMINEE T1 "+
					"LEFT JOIN ZXXS_XX_JBXX T3 ON T1.XXDM = T3.XX_JBXX_ID "+
					"LEFT JOIN ZXXS_XS_PIC T6 ON T6.xs_jbxx_id = T1.xs_jbxx_id "+
					"LEFT JOIN cus_kw_examschedule ap ON ap.kmid = T1.kmid "+
					"WHERE T1.LCID = '"+lcid+"' and T1.KSCODE in ("+ids+")";
		}
		String rtnStr = "";
		if (!StringUtils.isNullOrEmpty(sqlKs)) {
			List lsKs = getListSQL(sqlKs);
			if (lsKs!=null&&lsKs.size()>0) {
				for (int i=0; i<lsKs.size(); i++) {
					Map<String, Object> mp = (Map<String, Object>) lsKs.get(i);
					String kscode = mp.get("KSCODE")==null?"":mp.get("KSCODE").toString();
					String xm = mp.get("XM")==null?"":mp.get("XM").toString();
					String sfzjh = mp.get("SFZJH")==null?"":mp.get("SFZJH").toString();
					String xxmc = mp.get("XXMC")==null?"":mp.get("XXMC").toString();
					String starttime = mp.get("STARTTIME")==null?"":mp.get("STARTTIME").toString();
					String zp = mp.get("profile_url")==null?"":mp.get("profile_url").toString();
					String isks = mp.get("ISKS")==null?"":mp.get("ISKS").toString();
					rtnStr = rtnStr+xm+";"+sfzjh+";"+xxmc+";"+starttime+";"+zp+";"+isks+",";
				}
			}	
		}	
		return rtnStr;
	}
	
	public String getKsyz(String pc,String lcid,String sfzh){		
		String updateKs = "";
		if (!StringUtils.isNullOrEmpty(pc)) {
			updateKs = "update CUS_KW_EXAMINEE set isks='1' "+
					"WHERE KMID='"+pc+"' and (SFZJH=upper('"+sfzh+"') or SFZJH=lower('"+sfzh+"'))";
		}else if (!StringUtils.isNullOrEmpty(lcid)) {
			updateKs = "update CUS_KW_EXAMINEE set isks='1' "+
					"WHERE LCID='"+lcid+"' and (SFZJH=upper('"+sfzh+"') or SFZJH=lower('"+sfzh+"'))";
		}
		String rtnStr = "";
		if (!StringUtils.isNullOrEmpty(updateKs)) {
			update(updateKs);
			Toolkit.getDefaultToolkit().beep();
		}	
		return sfzh;
	}
	
	public PageBean getList(CusKwExaminee examinee,String schools,String kmid,String kdid,String kcid,String xmkhxjh,MBspInfo bspInfo){
		String sql = "";
		String lcid = null;
		if(examinee != null){
			lcid = examinee.getLcid();
		}		
		sql = " SELECT T1.KSID,T1.LCID,T1.SFZJH,T1.KSCODE,T1.XJH,"+
				"T1.XM,T1.XXDM,T1.XBM,T9.MC AS XB,T3.XXMC,T3.XXBSM,T5.REGION_EDU AS XJJYJ," +
				"T5.REGION_CODE AS XJCODE,T6.REGION_EDU AS SJJYJ,T6.REGION_CODE AS SJCODE," +				
				"km.subjectname as KMMC,kd.pointname as KDMC,cast(concat(kc.kcbh, '考场') as char) as KCMC,T1.ZWH,"+
				" case when T1.ISKS='1' then '已验' else '未验' end ISKS,"+
				" case when T1.ISDL='1' then '已登录' else '未登录' end ISDL,"+
				" case when T1.ISTJ='1' then '已提交' when T1.ISDT='1' then '已答题' else '未答题' end ISSUBMIT"+
				" FROM cus_kw_examinee T1"+				
				" left join cus_kw_examroom kc on kc.kcid = T1.kcid"+
				" LEFT JOIN ZXXS_XX_JBXX T3 ON T3.XX_JBXX_ID=T1.XXDM"+				
				" LEFT JOIN COM_MEMS_ORGAN T5 ON T5.REGION_CODE=T3.SSZGJYXZDM"+
				" LEFT JOIN COM_MEMS_ORGAN T6 ON T6.REGION_CODE=T5.PARENT_CODE"+
				" left join cus_kw_examsubject km on km.KMID=T1.kmid"+
				" left join cus_kw_examschool kd on kd.kdid = T1.kdid"+				
				" LEFT JOIN ZD_XB T9 ON T9.DM=T1.XBM WHERE 1=1 ";
		if("1".equals(bspInfo.getUserType())||"2".equals(bspInfo.getUserType())){
			if(schools != null && !"".equals(schools)){
				sql += " AND (T1.XXDM IN ('"+schools.replaceAll(",", "','")+
						"') or T5.REGION_CODE in ('"+schools.replaceAll(",", "','")+
						"') or T6.REGION_CODE in ('"+schools.replaceAll(",", "','")+
						"') or T6.PARENT_CODE in ('"+schools.replaceAll(",", "','")+
						"'))";
			}
		}else if("3".equals(bspInfo.getUserType())){
			if(schools != null && !"".equals(schools)){
				sql += " AND T1.BH IN ('"+schools.replaceAll(",", "','")+"') ";
			}
			sql += " AND T1.XXDM='"+bspInfo.getOrgan().getOrganCode()+"' ";
		}
		if(xmkhxjh != null && !"".equals(xmkhxjh)){
			sql += " AND (T1.KSCODE LIKE binary('%"+xmkhxjh+"%') OR T1.SFZJH LIKE binary('%"+xmkhxjh+"%') OR T1.XM LIKE binary('%"+xmkhxjh+"%')) ";
		}
		if(lcid != null && !"".equals(lcid)){
			sql += " AND T1.LCID='"+lcid+"' ";
		}
		if(kmid != null && !"".equals(kmid)){
			sql += " AND T1.KMID='"+kmid+"' ";
		}		
		if(kdid != null && !"".equals(kdid)){
			sql += " and T1.kdid='"+kdid+"'";
		}
		if(kcid != null && !"".equals(kcid)){
			sql += " and T1.kcid='"+kcid+"'";
		}
		sql += " ORDER BY substr(KMMC,11,length(substr(KMMC,11))-length('批'))+0 asc,KDMC asc,KCMC+0 asc,ZWH asc";
		update("update CUS_KW_EXAMROUND a set YY=IFNULL((SELECT COUNT(*) FROM CUS_KW_EXAMINEE b WHERE b.ISKS='1' and b.LCID = a.LCID),0) where a.lcid='"+lcid+"'");
		update("update CUS_KW_EXAMROUND a set YDL=IFNULL((SELECT COUNT(*) FROM CUS_KW_EXAMINEE b WHERE b.ISDL='1' and b.LCID = a.LCID),0) where a.lcid='"+lcid+"'");
		return getSQLPageBean(sql);
	}
	
	/**
	 * 根据考生id，获取考生基本信息
	 */
	public Object getExamInfomationByKsid(String ksids){
		String sql = " SELECT T1.KSID,T1.XJH,T1.XM,T5.NAME AS SFZJLX,"+//T3.NAME AS GJDQH,T4.NAME AS GATQ,
				" T1.SFZJH,T6.NAME AS XB,T2.CSRQ,T7.REGION_NAME AS HKSZD,T2.JG,T8.NAME AS MZ,"+
				" T9.NAME AS ZZMM,T1.KSCODE,T11.XXMC,T12.NAME AS NJMC,T13.BJMC,T2.LXDH,"+//T10.NAME AS JKZK,
				" T2.JTZZ,T14.EXAMNAME,(SELECT T15.SUBJECTNAME FROM CUS_KW_EXAMSUBJECT T15"+
				" WHERE T15.KMID=T1.KMID) AS KSKM,T15.PATH"+
				" FROM CUS_KW_EXAMINEE T1 "+
				" LEFT JOIN ZXXS_XS_JBXX T2 ON T1.XJH=T2.GRBSM "+
				//" LEFT JOIN SYS_ENUM_VALUE T3 ON T2.GJDQM=T3.CODE AND T3.DIC_TYPE='ZD_GB_GJDQM' "+
				//" LEFT JOIN SYS_ENUM_VALUE T4 ON T2.GATQWM=T4.CODE AND T4.DIC_TYPE='ZD_BB_GATQWM' "+
				" LEFT JOIN SYS_ENUM_VALUE T5 ON T2.SFZJLXM=T5.CODE AND T5.DIC_TYPE='ZD_BB_SFZJLXM' "+
				" LEFT JOIN SYS_ENUM_VALUE T6 ON T1.XBM=T6.CODE AND T6.DIC_TYPE='ZD_GB_XBM' "+
				" LEFT JOIN COM_MEMS_ORGAN T7 ON T2.xxsszgjyxzdm=T7.REGION_CODE "+
				" LEFT JOIN SYS_ENUM_VALUE T8 ON T2.MZM=T8.CODE AND T8.DIC_TYPE='ZD_GB_MZM' "+
				" LEFT JOIN SYS_ENUM_VALUE T9 ON T2.ZZMM=T9.CODE AND T9.DIC_TYPE='ZD_BB_ZZMMM' "+
				//" LEFT JOIN SYS_ENUM_VALUE T10 ON T2.JKZKM=T10.CODE AND T10.DIC_TYPE='ZD_GB_JKZKM' "+
				" LEFT JOIN ZXXS_XX_JBXX T11 ON T1.XXDM=T11.XX_JBXX_ID "+
				" LEFT JOIN SYS_ENUM_VALUE T12 on T1.NJ=T12.CODE AND T12.DIC_TYPE='ZD_XT_NJDM' "+
				" LEFT JOIN ZXXS_XX_BJXX T13 ON T1.BH=T13.XX_BJXX_ID "+
				" LEFT JOIN CUS_KW_EXAMROUND T14 ON T1.LCID=T14.LCID "+
				" LEFT JOIN ZXXS_XS_PIC T15 ON T2.XS_JBXX_ID=T15.XS_JBXX_ID "+
				" WHERE T1.KSID='"+ksids+"' ";
		List list = getListSQL(sql);
		Map result = new HashMap();
		if(list != null && list.size() > 0){
			result = (Map) list.get(0);
			return result;
		}
		return null;
	}
	
	/**
	 * 根据班级id，获取班级考生信息
	 */
	public PageBean getExamInfomationByBjid(String bjid,String lcid,String xmkhxjh){
		String sql = " SELECT T1.KSID,T1.KSCODE,T1.XM,T1.SFZJH,T1.XJH as GRBSM,"+
				"T3.PATH,T4.BJMC,T5.NAME AS NJMC"+
				" FROM CUS_KW_EXAMINEE T1 " +
				" LEFT JOIN ZXXS_XS_PIC T3 ON T1.XS_JBXX_ID=T3.XS_JBXX_ID " +
				" LEFT JOIN ZXXS_XX_BJXX T4 ON T1.BH=T4.XX_BJXX_ID " +
				" LEFT JOIN SYS_ENUM_VALUE T5 ON T1.NJ=T5.CODE AND T5.DIC_TYPE='ZD_XT_NJDM' "+
				" WHERE (t1.xxdm IN ('"+bjid.replaceAll(",", "','")+
				"') or t1.xxdm in (select XX_JBXX_ID from zxxs_xx_jbxx where SSZGJYXZDM IN ('"+bjid.replaceAll(",", "','")+"'))) AND T1.LCID='"+lcid+"' ";
		if(xmkhxjh != null && !"".equals(xmkhxjh)){
			sql += " AND (T1.KSCODE LIKE binary('%"+xmkhxjh+"%') OR T1.SFZJH LIKE binary('%"+xmkhxjh+"%') OR T1.XM LIKE binary('%"+xmkhxjh+"%')) ";
		}
		return getSQLPageBean(sql);
	}
	
	/**
	 * 考生报名信息统计
	 */
	public PageBean ExamStudentCount(String type,String schools,String kslc,MBspInfo bspInfo){
		String sql = "";
		String code = bspInfo.getOrgan().getOrganCode();
		String parentCode = bspInfo.getOrgan().getParentCode();
		if("school_1".equals(type) || "jyj_1".equals(type)){
			//获取报名详细
			sql = "SELECT T1.KSID,T1.KSCODE,T1.XM,T1.SFZJH,T1.XJH as GRBSM,"+
					"T4.BJMC,T5.NAME AS NJMC,T6.XXMC,T1.XXDM,T7.NAME AS XB,"+
					"(SELECT T15.SUBJECTNAME FROM CUS_KW_EXAMSUBJECT T15 WHERE T15.KMID = T1.kmid) AS KSKM"+
					" FROM CUS_KW_EXAMINEE T1"+
					" LEFT JOIN ZXXS_XX_BJXX T4 ON T1.BH=T4.XX_BJXX_ID"+
					" LEFT JOIN SYS_ENUM_VALUE T5 ON T1.NJ=T5.CODE AND T5.DIC_TYPE='ZD_XT_NJDM'"+
					" LEFT JOIN SYS_ENUM_VALUE T7 ON T1.XBM=T7.CODE AND T7.DIC_TYPE='ZD_GB_XBM'"+
					" LEFT JOIN ZXXS_XX_JBXX T6 ON T1.XXDM=T6.XX_JBXX_ID"+
					" LEFT JOIN COM_MEMS_ORGAN CK ON T6.SSZGJYXZDM=CK.REGION_CODE"+
					" LEFT JOIN COM_MEMS_ORGAN ZK ON ZK.REGION_CODE=CK.PARENT_CODE"+
					" WHERE 1=1";
			if("1".equals(bspInfo.getUserType())||"2".equals(bspInfo.getUserType())){
				if(schools != null && !"".equals(schools)){
					sql += " AND (T1.XXDM IN ('"+schools.replaceAll(",", "','")+
							"') or T6.SSZGJYXZDM in ('"+schools.replaceAll(",", "','")+
							"') or ZK.REGION_CODE in ('"+schools.replaceAll(",", "','")+
							"') or ZK.PARENT_CODE in ('"+schools.replaceAll(",", "','")+"'))";
				}
			}else if("3".equals(bspInfo.getUserType())){
				if(schools != null && !"".equals(schools)){
					sql += " AND T1.BH IN ('"+schools.replaceAll(",", "','")+"')";
				}
				sql += " AND T1.XXDM='"+bspInfo.getOrgan().getOrganCode()+"'";
			}
			if(kslc != null && !"".equals(kslc)){
				sql += " AND T1.LCID='"+kslc+"'";
			}
			sql += " ORDER BY case when ifnull(T1.XXDM,'')='' then 0 else 1 end desc,T1.XXDM,substr(KSKM,11,length(substr(KSKM,11))-length('批'))+0 asc,T1.NJ,T1.BH,T1.KSCODE";
		}else if("jyj_2".equals(type)){
			//按参考单位统计
			sql = "SELECT T.*,COUNT(T.XXDM) AS RS FROM (SELECT T1.XXDM,T2.XXMC,T4.REGION_EDU,"+
					"(SELECT T5.SUBJECTNAME FROM CUS_KW_EXAMSUBJECT T5 WHERE T5.KMID=T1.KMID) AS KSKM"+
					" FROM CUS_KW_EXAMINEE T1"+
					" LEFT JOIN ZXXS_XX_JBXX T2 ON T1.XXDM=T2.XX_JBXX_ID"+				
					" LEFT JOIN COM_MEMS_ORGAN T4 ON T2.SSZGJYXZDM=T4.REGION_CODE"+
					" LEFT JOIN COM_MEMS_ORGAN ZK ON ZK.REGION_CODE=T4.PARENT_CODE"+
					" WHERE T1.LCID='"+kslc+
					"' AND (T2.SSZGJYXZDM='"+code+"' OR T4.PARENT_CODE='"+code+
					"' OR ZK.PARENT_CODE='"+code+"' OR T4.REGION_CODE='"+parentCode+"') ";
			if(schools != null && !"".equals(schools)){
				sql += " AND (T1.XXDM IN ('"+schools.replaceAll(",", "','")+
						"') or T1.XXDM in (SELECT SSZGJYXZDM FROM zxxs_xx_jbxx WHERE xx_jbxx_id = '"+schools.replaceAll(",", "','")+
						"') or T4.REGION_CODE in ('"+schools.replaceAll(",", "','")+
						"') or ZK.REGION_CODE in ('"+schools.replaceAll(",", "','")+
						"') or ZK.PARENT_CODE in ('"+schools.replaceAll(",", "','")+"')) ";
			}
			sql += " ) T GROUP BY T.XXDM,T.XXMC,T.REGION_EDU,T.KSKM order by substr(KSKM,11,length(substr(KSKM,11))-length('批'))+0 asc";
		}else if("school_2".equals(type)){
			//按年级统计
			sql = "SELECT T.*,COUNT(T.NJ) AS RS FROM ("+
					"SELECT T1.NJ,T2.NAME AS NJMC,T3.XXMC,"+
					"(SELECT T5.SUBJECTNAME FROM CUS_KW_EXAMSUBJECT T5 WHERE T5.KMID=T1.KMID) AS KSKM"+
					" FROM CUS_KW_EXAMINEE T1"+
					" LEFT JOIN SYS_ENUM_VALUE T2 on T1.NJ=T2.CODE AND DIC_TYPE='ZD_XT_NJDM'"+
					" LEFT JOIN ZXXS_XX_JBXX T3 ON T1.XXDM=T3.XX_JBXX_ID"+
					" WHERE T1.LCID='"+kslc+"' AND T1.XXDM='"+bspInfo.getOrgan().getOrganCode()+"'";
			if(schools != null && !"".equals(schools)){
				sql += " AND T1.NJ IN ('"+schools.replaceAll(",", "','")+"')";
			}
			sql += ") T GROUP BY T.NJ,T.NJMC,T.KSKM,T.XXMC order by substr(KSKM,11,length(substr(KSKM,11))-length('批'))+0 asc";
		}else if("school_3".equals(type)){
			//按班级统计
			sql = "SELECT T.*,COUNT(T.BJMC) AS RS FROM ("+
					"SELECT T1.NJ,T2.NAME AS NJMC,T3.BJMC,T4.XXMC,"+
					"(SELECT T5.SUBJECTNAME FROM CUS_KW_EXAMSUBJECT T5 WHERE T5.KMID=T1.KMID) AS KSKM"+
					" FROM CUS_KW_EXAMINEE T1"+
					" LEFT JOIN SYS_ENUM_VALUE T2 on T1.NJ=T2.CODE AND DIC_TYPE='ZD_XT_NJDM'"+
					" LEFT JOIN ZXXS_XX_BJXX T3 ON T1.BH=T3.XX_BJXX_ID"+
					" LEFT JOIN ZXXS_XX_JBXX T4 ON T1.XXDM=T4.XX_JBXX_ID"+
					" WHERE T1.LCID='"+kslc+"' AND T1.XXDM='"+bspInfo.getOrgan().getOrganCode()+"'";
			if(schools != null && !"".equals(schools)){
				sql += " AND T1.BH IN ('"+schools.replaceAll(",", "','")+"')";
			}
			sql += ") T GROUP BY T.NJ,T.NJMC,T.KSKM,T.BJMC,T.XXMC order by substr(KSKM,11,length(substr(KSKM,11))-length('批'))+0 asc";
		}
		return getSQLPageBean(sql);
	}
}
