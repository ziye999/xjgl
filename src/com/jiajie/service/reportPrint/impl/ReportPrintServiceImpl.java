package com.jiajie.service.reportPrint.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.service.ServiceBridge;
import com.jiajie.service.reportPrint.ReportPrintService;
import com.jiajie.service.resultsStatisticalCollection.ResultsStatisticalService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;

@Service("reportPrintServiceImpl")
@SuppressWarnings("all")
public class ReportPrintServiceImpl extends ServiceBridge implements ReportPrintService  {
	//无照片学生信息
	public List<Map<String, Object>> studentNotPhotoInfo(String schoolid,String xxdm,String nj,String bj,MBspInfo bspInfo) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT T1.GRBSM,T1.XM,T1.SFZJH,T3.BJMC,T4.MC AS XB,T5.DESCRIPTION AS MZ,T5.DIC_TYPE,");
		sql.append("T1.CSRQ AS CSRQT,T1.CSRQ");
		sql.append(" FROM ZXXS_XS_JBXX T1");
		sql.append(" LEFT JOIN ZXXS_XX_JBXX T ON T1.XX_JBXX_ID=T.XX_JBXX_ID");
		sql.append(" LEFT JOIN ZXXS_XX_NJXX T2 ON T1.XX_NJXX_ID=T2.XX_NJXX_ID");
		sql.append(" LEFT JOIN ZXXS_XX_BJXX T3 ON T1.XX_BJXX_ID=T3.XX_BJXX_ID");
		sql.append(" LEFT JOIN ZD_XB T4 ON T1.XBM=T4.DM");
		sql.append(" LEFT JOIN SYS_ENUM_VALUE T5 ON T1.MZM=T5.CODE AND DIC_TYPE='ZD_GB_MZM'");	
		sql.append(" LEFT JOIN COM_MEMS_ORGAN T9 ON T.SSZGJYXZDM=T9.REGION_CODE");
		sql.append(" LEFT JOIN COM_MEMS_ORGAN T10 ON T9.PARENT_CODE=T10.REGION_CODE");
		sql.append(" WHERE T1.XS_JBXX_ID NOT IN (SELECT DISTINCT XS_JBXX_ID FROM ZXXS_XS_PIC WHERE PATH IS NOT NULL OR PATH!=' ')");
		if("3".equals(bspInfo.getUserType())){
			//参考单位			
			sql.append(" AND T1.XX_JBXX_ID IN (").append(schoolid).append(") ");
		}else if ("2".equals(bspInfo.getUserType())) {	
			sql.append(" AND T9.REGION_CODE='"+bspInfo.getOrgan().getOrganCode()+"' ");
		}else if("1".equals(bspInfo.getUserType())){
			//组考单位
			sql.append(" AND (T9.REGION_CODE='"+bspInfo.getOrgan().getOrganCode()+"' OR ");
			sql.append(" T10.REGION_CODE='"+bspInfo.getOrgan().getOrganCode()+"' OR ");
			sql.append(" T10.PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+"') ");
		}		
		if(StringUtil.isNotNullOrEmpty(xxdm))
			sql.append(" AND T1.XX_JBXX_ID IN ('").append(xxdm.replaceAll(",", "','")).append("')");
		if(StringUtil.isNotNullOrEmpty(nj))
			sql.append(" AND T2.XJNJDM IN ('").append(nj.replaceAll(",", "','")).append("')");
		if(StringUtil.isNotNullOrEmpty(bj))
			sql.append(" AND T1.XX_BJXX_ID in ('").append(bj.replaceAll(",", "','")).append("')");
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}
	//考试不达标换证统计
	public List<Map<String, Object>> graduationStuInfo(String schoolid) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT T1.GRBSM as GRBSM,T1.XM as XM,BJ.BJMC as BJMC,T2.BYJYNY as BYJYNY,");
		sql.append(" CASE WHEN TRUNCATE(ABS(MONTH(now())-MONTH(STR_TO_DATE(T2.BYJYNY, '%Y-%m-%d')))/12,0)>=3 THEN '是' ELSE '否' END AS SFTHREEYEAR,");  
		sql.append(" CASE WHEN T4.DYFLAG='' OR T4.DYFLAG=0 THEN '否' WHEN T4.DYFLAG =1 THEN '是' END AS SFDY");
		sql.append(" FROM (");
		sql.append(" SELECT DISTINCT T1.* FROM ZXXS_XS_JBXX T1");
		sql.append(" LEFT JOIN ZXXS_XS_XSBY T2 ON T1.XS_JBXX_ID=T2.XS_JBXX_ID");
		sql.append(" LEFT JOIN cus_kw_stuscore T3 ON T1.GRBSM=T3.XJH AND T3.GRADE_M='D'");
		sql.append(" UNION");
		sql.append(" SELECT DISTINCT T1.* FROM ZXXS_XS_JBXX T1");
		sql.append(" LEFT JOIN ZXXS_XS_XSBY T2 ON T1.XS_JBXX_ID=T2.XS_JBXX_ID");
		sql.append(" LEFT JOIN cus_kw_stuscore T3 ON T1.GRBSM=T3.XJH AND T3.SCORE<60) T1");
		sql.append(" LEFT JOIN ZXXS_XS_XSBY T2 ON T1.XS_JBXX_ID=T2.XS_JBXX_ID");
		sql.append(" LEFT JOIN ZXXS_XX_BJXX BJ ON T1.XX_BJXX_ID=BJ.XX_BJXX_ID");
		sql.append(" LEFT JOIN CUS_XJ_PRINTRECORD T4 ON T1.GRBSM=T4.XJH AND T4.LX_M='1'");
		sql.append(" WHERE T1.XX_JBXX_ID IN (").append(schoolid).append(" )");
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}
	//综合素质不达标换证统计
	public List<Map<String, Object>> qualityStuInfo(String schoolid) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT T1.GRBSM as GRBSM,T1.XM as XM,T3.BJMC as BJMC,T2.BYJYNY as BYJYNY,");
		sql.append(" CASE WHEN TRUNCATE(ABS(MONTH(now())-MONTH(STR_TO_DATE(T2.BYJYNY, '%Y-%m-%d')))/12,0)>=1 THEN '是' ELSE '否' END AS SFTHREEYEAR,");  
		sql.append(" CASE WHEN T4.DYFLAG='' OR T4.DYFLAG=0 THEN '否' WHEN T4.DYFLAG =1 THEN '是' END AS SFDY");
		sql.append(" FROM (SELECT DISTINCT * FROM ZXXS_XS_JBXX) T1");
		sql.append(" LEFT JOIN ZXXS_XS_XSBY T2 ON T1.XS_JBXX_ID=T2.XS_JBXX_ID");
		sql.append(" LEFT JOIN ZXXS_XX_BJXX T3 ON T1.XX_BJXX_ID=T3.XX_BJXX_ID");
		sql.append(" LEFT JOIN CUS_XJ_PRINTRECORD T4 ON T1.GRBSM=T4.XJH AND T4.LX_M='1'");
		sql.append(" WHERE T1.XX_JBXX_ID IN (").append(schoolid).append(" )");
		return this.getListSQL(sql.toString());
	}
	//未达144个学分换证统计
	public List<Map<String, Object>> creditsStuInfo(String schoolid) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT T1.GRBSM as GRBSM,T1.XM as XM,T3.BJMC as BJMC,T2.BYJYNY as BYJYNY,");
		sql.append(" CASE WHEN TRUNCATE(ABS(MONTH(now()-MONTH(STR_TO_DATE(T2.BYJYNY, '%Y-%m-%d')))/12,0)>=3 THEN '是' ELSE '否' END AS SFTHREEYEAR,");  
		sql.append(" CASE WHEN T4.DYFLAG='' OR T4.DYFLAG=0 THEN '否' WHEN T4.DYFLAG =1 THEN '是' END AS SFDY");
		sql.append(" FROM ZXXS_XS_JBXX T1");
		sql.append(" LEFT JOIN (SELECT XJH,SUM(SCORE) AS XF FROM CUS_XJ_SCORE GROUP BY XJH");
		sql.append(" HAVING SUM(SCORE)<144 ) T5 ON T1.GRBSM=T5.XJH");
		sql.append(" LEFT JOIN ZXXS_XS_XSBY T2 ON T1.XS_JBXX_ID=T2.XS_JBXX_ID");
		sql.append(" LEFT  JOIN ZXXS_XX_BJXX T3 ON T1.XX_BJXX_ID=T3.XX_BJXX_ID");
		sql.append(" LEFT JOIN CUS_XJ_PRINTRECORD T4 ON T1.GRBSM=T4.XJH AND T4.LX_M='1'");
		sql.append(" WHERE T1.XX_JBXX_ID IN (").append(schoolid).append(" )");
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}
	//肄业申请统计
	public List<Map<String, Object>> yiyeStuInfo(String schoolid) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT T1.GRBSM,T1.XM,date_format(T2.CDATE,'%Y-%m-%d') AS CDATET,date_format(T2.CDATE,'%Y%m%d') AS CDATE,T2.REASON,T3.BJMC,");
		sql.append(" CASE WHEN T4.DYFLAG='' OR T4.DYFLAG=0 THEN '否' WHEN T4.DYFLAG =1 THEN '是' END AS SFFZ");
		sql.append(" FROM zxxs_XS_JBXX T1");
		sql.append(" LEFT JOIN CUS_XJ_BREAKSTUDY T2 ON T1.GRBSM=T2.XJH AND T2.FLAG=1");
		sql.append(" LEFT JOIN ZXXS_XX_BJXX T3 ON T1.XX_BJXX_ID=T3.XX_BJXX_ID");
		sql.append(" LEFT JOIN CUS_XJ_PRINTRECORD T4 ON T1.GRBSM=T4.XJH AND T4.LX_M='1'");
		sql.append(" WHERE T2.FLAG='1' and T1.XX_JBXX_ID IN (").append(schoolid).append(" )");
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}
	
	
}
