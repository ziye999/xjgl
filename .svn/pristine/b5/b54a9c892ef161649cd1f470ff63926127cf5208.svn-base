package com.jiajie.service.examResults.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examResults.ScoresQueryService;
import com.jiajie.util.StringUtil;

@Service("scoresQueryServiceImpl")
@SuppressWarnings("all")
public class ScoresQueryServiceImpl extends ServiceBridge implements ScoresQueryService  {
	private static final long serialVersionUID = 1L;

	public PageBean getStudentScores(Map<String, String> map) {
		String lcid=map.get("lcId");
		String xxid=map.get("schoolId");
		String xm_kh_xjh=map.get("xmKhXjh");						
		List<Map<String, Object>> list_km=getKskm(lcid);
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT t.KSID,t.XJH,t.EXAMCODE,t.XM,t.XB,t.XXMC,");
		if(list_km!=null && list_km.size()>0){
			for (Map<String, Object> map2 : list_km) {
				sql.append(" t.score AS ");
				sql.append(map2.get("KCH").toString());
				sql.append(",");
			}
		}
		sql.append(" t.KCH,substr(t.XJH,2) as SFZJH FROM (");
		sql.append(" SELECT T1.XJH,T1.SCORE,concat(T1.EXAMCODE,'') as EXAMCODE,T1.KMID,T2.SUBJECTNAME,T1.KSID,T1.XM,T5.MC,t2.KCH AS XB,t4.XXMC,t2.KCH");
		sql.append(" from CUS_KW_STUSCORE t1");
		sql.append(" LEFT JOIN CUS_KW_EXAMSUBJECT t2 on T2.KMID=T1.KMID");
		sql.append(" LEFT JOIN ZXXS_XX_JBXX t4 on T4.XX_JBXX_ID=T1.XXDM");
		sql.append(" LEFT JOIN ZD_XB t5 on T5.DM=T1.XBM");		
		sql.append(" WHERE t1.ksid is not null ");
		sql.append(" AND T1.LCID='");
		sql.append(lcid);
		sql.append("'");
		if(StringUtil.isNotNullOrEmpty(xm_kh_xjh)){
			sql.append(" AND (T1.XM LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%') OR T1.XJH LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%') OR T1.EXAMCODE LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%'))");
		}
		if(StringUtil.isNotNullOrEmpty(xxid)){
			sql.append(" AND T1.XXDM IN (");
			sql.append(xxid);
			sql.append(")");
		}		
		sql.append(" ) t");
		sql.append(" GROUP BY t.KSID,t.XJH,t.EXAMCODE,t.XM,t.XB,t.XXMC");
		sql.append(" ORDER BY t.XXMC,t.EXAMCODE");
		PageBean pageBean=getSQLPageBean(sql.toString());
		return pageBean;
	}
	
	public List<Map<String, Object>> getKskm(String lcid){
		String sql="SELECT distinct KCH from CUS_KW_EXAMSUBJECT WHERE lcid = '"+lcid+"'";
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}

	public List<Map<String, Object>> printStudentScores(Map<String, String> map) {
		String lcid=map.get("lcId");
		String xxid=map.get("schoolId");
		String xm_kh_xjh=map.get("xmKhXjh");		
		List<Map<String, Object>> list_km=getKskm(lcid);
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT t.KSID,t.XJH,t.EXAMCODE,t.XM,t.XB,t.XXMC,");
		if(list_km!=null && list_km.size()>0){
			for (Map<String, Object> map2 : list_km) {
				sql.append(" t.score AS ");
				sql.append(map2.get("KCH").toString());
				sql.append(",");
			}
		}
		sql.append(" t.KCH,substr(t.XJH,2) as SFZJH FROM (");
		sql.append(" SELECT T1.XJH,T1.SCORE,T1.EXAMCODE,T1.KMID,T2.SUBJECTNAME,T1.KSID,T1.XM,T5.MC AS XB,t4.XXMC,t2.KCH from CUS_KW_STUSCORE t1");
		sql.append(" LEFT JOIN CUS_KW_EXAMSUBJECT t2 on T1.KMID=T2.KMID");
		sql.append(" LEFT JOIN ZXXS_XX_JBXX t4 on T1.XXDM=T4.XX_JBXX_ID");
		sql.append(" LEFT JOIN ZD_XB t5 on T1.XBM=T5.DM");		
		sql.append(" WHERE 1=1 AND t1.ksid is not null");
		sql.append(" AND T1.LCID='");
		sql.append(lcid);
		sql.append("'");
		if(StringUtil.isNotNullOrEmpty(xm_kh_xjh)){
			sql.append(" AND (T1.XM LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%') OR T1.XJH LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%') OR T1.EXAMCODE LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%'))");
		}
		if(StringUtil.isNotNullOrEmpty(xxid)){
			sql.append(" AND T1.XXDM IN (");
			sql.append(xxid);
			sql.append(")");
		}		
		sql.append(" ) t");
		sql.append(" GROUP BY t.KSID,t.XJH,t.EXAMCODE,t.XM,t.XB,t.XXMC");
		sql.append(" ORDER BY t.XXMC,t.EXAMCODE");
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}	

}
