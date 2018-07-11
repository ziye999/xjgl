package com.jiajie.service.resultsStatisticalCollection.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.resultsStatisticalCollection.ResultsStatisticalService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.OrganTree;
import com.mysql.jdbc.StringUtils;

@Service("resultsStatisticalServiceImpl")
@SuppressWarnings("all")
public class ResultsStatisticalServiceImpl extends ServiceBridge implements ResultsStatisticalService {
	private static String lcid;
	public String getLungCi(String userid,String schoolid,MBspInfo bspInfo) {
		List<OrganTree> list=getLungCiList(null,userid,schoolid,bspInfo.getOrgan().getOrganCode(),bspInfo);
		JSONArray array=JSONArray.fromObject(list);
		return array.toString().replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true");
	}
	
	private List<OrganTree> getLungCiList(String parentid,String userid,String schoolid,String dwid,MBspInfo bspInfo){
		String sql="";
		List<Map<String, Object>> list1=null;
		if(parentid==null || "".equals(parentid)){
			sql="SELECT * FROM XNXQ_LC_VIEW WHERE PARENTID='' ORDER BY XN DESC";
		}else{
			sql="SELECT * FROM XNXQ_LC_VIEW WHERE PARENTID ='"+parentid+"'";
			if ("1".equals(bspInfo.getUserType())||"2".equals(bspInfo.getUserType())) {
				sql+=" AND (DWID in (SELECT SSZGJYXZDM FROM zxxs_xx_jbxx) or DWID in (select xx_jbxx_id from zxxs_xx_jbxx) or DWID='"+dwid+"')";
			}else if ("3".equals(bspInfo.getUserType())) {
				sql+=" AND DWID='"+dwid+"'";
			}			
			sql+=" ORDER BY XN DESC";
			if(StringUtil.isNotNullOrEmpty(schoolid)){
				String sql1="SELECT T1.CODEID,T1.CODENAME,T1.XN,T1.XQM,T1.PARENTID,T1.DWID "+
					"FROM XNXQ_LC_VIEW t1 "+
					"LEFT JOIN CUS_KW_POINTINFO t2 on T1.CODEID=T2.LCID "+ 
					"WHERE T2.XXDM="+schoolid+" AND T1.PARENTID='"+parentid+"'";
				list1=getSession().createSQLQuery(sql1).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			}
		}
		
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		if(list!=null && list.size()>0){
			if(list1!=null && list1.size()>0){
				list.addAll(list1);
			}
		}else{
			if(list1!=null && list1.size()>0){
				list=list1;
			}
		}
		List<OrganTree> array=new ArrayList<OrganTree>();
		for (Map<String, Object> map : list) {
			OrganTree tree=new OrganTree();
			if(parentid!=null && !"".equals(parentid)){
				tree.setChecked("false");
			}
			tree.setId(StringUtil.getString(map.get("CODEID")));
			tree.setText(StringUtil.getString(map.get("CODENAME")));
			if(StringUtil.isNotNullOrEmpty(StringUtil.getString(map.get("DWID")))){
				dwid=StringUtil.getString(map.get("DWID"));
			}
			List<OrganTree> childList=getLungCiList(StringUtil.getString(map.get("CODEID")),userid,schoolid,dwid,bspInfo);
			tree.setChildren(childList);
			if(childList.size()>0){
				tree.setLeaf("false");
			}else{
				tree.setLeaf("true");
			}
			array.add(tree);
		}
		return array;
	};

	public String getXueXiao(String userid,MBspInfo bspInfo) {
		List<OrganTree> list=getXueXiaoList(null,0,userid, bspInfo.getOrgan().getOrganCode(),bspInfo);
		JSONArray array=JSONArray.fromObject(list);
		return array.toString().replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true");
	}
	
	private List<OrganTree> getXueXiaoList(String parentid,int i,String userid,String dwid,MBspInfo bspInfo){
		String sql="";
		if(parentid==null || "".equals(parentid) && i==0 ){
			sql="SELECT * FROM XNXQ_LC_VIEW WHERE PARENTID='' ORDER BY XN DESC";
		}else if(i==1){
			sql="SELECT * FROM XNXQ_LC_VIEW WHERE PARENTID ='"+parentid+"'";
			if ("1".equals(bspInfo.getUserType())||"2".equals(bspInfo.getUserType())) {
				sql+=" AND (DWID in (SELECT SSZGJYXZDM FROM zxxs_xx_jbxx) or DWID in (select xx_jbxx_id from zxxs_xx_jbxx) or DWID='"+dwid+"')";
			}else if ("3".equals(bspInfo.getUserType())) {
				sql+=" AND DWID='"+dwid+"'";
			}
			sql+=" ORDER BY XN DESC";
		}else if(i==2){
			sql="SELECT distinct CONCAT('"+parentid+";',t3.XX_JBXX_ID) AS CODEID,t3.XXMC AS CODENAME"+
				" FROM CUS_KW_EXAMROUND t1"+
				" LEFT JOIN cus_kw_examinee t2 ON t1.lcid = T2.lcid"+
				" LEFT JOIN ZXXS_XX_JBXX t3 ON t2.xxdm = T3.XX_JBXX_ID"+
				" where t3.XX_JBXX_ID is not null and T1.LCID='"+parentid+
				"' ORDER BY case when ifnull(t3.XX_JBXX_ID,'')='' then 0 else 1 end desc,t1.LCID";
		}if(i==3){
			return null;
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		List<OrganTree> array=new ArrayList<OrganTree>();
		for (Map<String, Object> map : list) {
			OrganTree tree=new OrganTree();
			if(i==2){
				tree.setChecked("false");
			}
			tree.setId(StringUtil.getString(map.get("CODEID")));
			tree.setText(StringUtil.getString(map.get("CODENAME")));
			if(StringUtil.isNotNullOrEmpty(StringUtil.getString(map.get("DWID")))){
				dwid=StringUtil.getString(map.get("DWID"));
			}
			List<OrganTree> childList=getXueXiaoList(StringUtil.getString(map.get("CODEID")),i+1,userid,dwid,bspInfo);
			tree.setChildren(childList);
			if(childList!=null && childList.size()>0){
				tree.setLeaf("false");
			}else{
				tree.setLeaf("true");
			}
			array.add(tree);
		}
		return array;
	};
	
	public String getNianJi(String userid,MBspInfo bspInfo) {
		List<OrganTree> list=getNianJiList(null,0,userid,bspInfo.getOrgan().getOrganCode(),bspInfo);
		JSONArray array=JSONArray.fromObject(list);
		return array.toString().replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true");
	}
	private List<OrganTree> getNianJiList(String parentid,int i,String userid,String dwid,MBspInfo bspInfo){
		String sql="";
		if(parentid==null || "".equals(parentid) && i==0 ){
			sql="SELECT * FROM XNXQ_LC_VIEW WHERE PARENTID='' ORDER BY XN DESC";
		}else if(i==1){
			sql="SELECT a.* FROM (SELECT T1.LCID AS CODEID,T1.EXAMNAME AS CODENAME,T1.XN,T1.XQM,CONCAT(T1.XN,',',T1.XQM) as PARENTID,T1.DWID"+
				" from CUS_KW_EXAMROUND t1"+ 
				" where 0=0";			
			if ("1".equals(bspInfo.getUserType())||"2".equals(bspInfo.getUserType())) {
				sql+=" and (t1.DWID in (SELECT SSZGJYXZDM FROM zxxs_xx_jbxx) or t1.DWID in (select xx_jbxx_id from zxxs_xx_jbxx) or T1.DWID='"+dwid+"')";
			}else if ("3".equals(bspInfo.getUserType())) {
				sql+=" and T1.DWID='"+dwid+"'";
			}
			sql+=" UNION"+
				" SELECT T1.LCID AS CODEID,T1.EXAMNAME AS CODENAME,T1.XN,T1.XQM,CONCAT(T1.XN,',',T1.XQM) as PARENTID,T1.DWID FROM ("+
				" SELECT DISTINCT T1.* FROM CUS_KW_EXAMROUND T1"+
				" LEFT JOIN CUS_KW_POINTINFO T2 ON T1.LCID=T2.LCID"+
				" where 0=0";
			if ("1".equals(bspInfo.getUserType())||"2".equals(bspInfo.getUserType())) {
				sql+=" and (DWID in (select xx_jbxx_id from zxxs_xx_jbxx) or T2.DWID='" +dwid+"')";
			}else if ("3".equals(bspInfo.getUserType())) {
				sql+=" and T2.DWID='" +dwid+"'";
			}			
			sql+=") T1) a WHERE a.PARENTID='"+parentid+"' ORDER BY a.PARENTID";
		}else if(i==2){
			sql="SELECT DISTINCT a.* FROM ("+
				"SELECT CONCAT('"+parentid+";',T1.XJNJDM) as CODEID,T3.NAME AS CODENAME"+
				" FROM ZXXS_XX_NJXX t1"+
				" LEFT JOIN SYS_ENUM_VALUE t3 on T1.XJNJDM=T3.CODE AND T3.DIC_TYPE='ZD_XT_NJDM'"+
				" LEFT JOIN t_qxgl_usermapping t2 on T1.XX_JBXX_ID=T2.ORGAN_CODE"+
				" where T2.USER_ID='"+userid+"') a ORDER BY a.CODEID";
		}if(i==3){
			return null;
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		List<OrganTree> array=new ArrayList<OrganTree>();
		for (Map<String, Object> map : list) {
			OrganTree tree=new OrganTree();
			if(i==2){
				tree.setChecked("false");
			}
			tree.setId(StringUtil.getString(map.get("CODEID")));
			tree.setText(StringUtil.getString(map.get("CODENAME")));
			if(StringUtil.isNotNullOrEmpty(StringUtil.getString(map.get("DWID")))){
				dwid=StringUtil.getString(map.get("DWID"));
			}
			List<OrganTree> childList=getNianJiList(StringUtil.getString(map.get("CODEID")),i+1,userid,dwid,bspInfo);
			tree.setChildren(childList);
			if(childList!=null && childList.size()>0){
				tree.setLeaf("false");
			}else{
				tree.setLeaf("true");
			}
			array.add(tree);
		}
		return array;
	}

	public String getBanJi(String userid,MBspInfo bspInfo) {
		List<OrganTree> list=getBanJiList(null,0,userid,bspInfo.getOrgan().getOrganCode(),bspInfo);
		JSONArray array=JSONArray.fromObject(list);
		return array.toString().replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true");
	}
	
	private List<OrganTree> getBanJiList(String parentid,int i,String userid,String dwid,MBspInfo bspInfo){
		String sql="";
		if(parentid==null || "".equals(parentid) && i==0 ){
			sql="SELECT * FROM XNXQ_LC_VIEW WHERE PARENTID='' ORDER BY XN DESC";
		}else if(i==1){			
			sql="SELECT a.* FROM (SELECT T1.LCID AS CODEID,T1.EXAMNAME AS CODENAME,T1.XN,T1.XQM,CONCAT(T1.XN,',',T1.XQM) as PARENTID,T1.DWID"+
				" from CUS_KW_EXAMROUND t1"+ 
				" where 0=0";			
			if ("1".equals(bspInfo.getUserType())||"2".equals(bspInfo.getUserType())) {
				sql+=" and (t1.DWID in (SELECT SSZGJYXZDM FROM zxxs_xx_jbxx) or t1.DWID in (select xx_jbxx_id from zxxs_xx_jbxx) or t1.DWID='"+dwid+"')";
			}else if ("3".equals(bspInfo.getUserType())) {
				sql+=" and t1.DWID='"+dwid+"'";
			}
			sql+=" UNION"+
				" SELECT T1.LCID AS CODEID,T1.EXAMNAME AS CODENAME,T1.XN,T1.XQM,CONCAT(T1.XN,',',T1.XQM) as PARENTID,T1.DWID FROM ("+
				" SELECT DISTINCT T1.* FROM CUS_KW_EXAMROUND T1 "+
				" LEFT JOIN CUS_KW_POINTINFO T2 ON T1.LCID=T2.LCID"+
				" WHERE T2.XXDM='"+dwid+"'";
			sql+=") T1) a WHERE a.PARENTID='"+parentid+"' ORDER BY a.PARENTID";
		}else if(i==2){
			lcid=parentid;
			sql="SELECT T1.XJNJDM as CODEID,T3.NAME AS CODENAME FROM ZXXS_XX_NJXX t1"+
				" LEFT JOIN SYS_ENUM_VALUE t3 on T1.XJNJDM=T3.CODE AND T3.DIC_TYPE='ZD_XT_NJDM'"+
				" where t1.XX_JBXX_ID='"+dwid+"'";
		}else if(i==3){
			sql="SELECT CONCAT('"+lcid+";',t1.XX_BJXX_ID) AS CODEID,t1.BJMC AS CODENAME FROM ZXXS_XX_BJXX t1"+
				" LEFT JOIN ZXXS_XX_NJXX t2 on T1.XX_NJXX_ID=T2.XX_NJXX_ID"+
				" WHERE T2.XJNJDM='"+parentid+"' AND t1.XX_JBXX_ID='"+dwid+"'";
		}else if(i==4){
			return null;
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		List<OrganTree> array=new ArrayList<OrganTree>();
		for (Map<String, Object> map : list) {
			OrganTree tree=new OrganTree();
			if(i==3){
				tree.setChecked("false");
			}
			tree.setId(StringUtil.getString(map.get("CODEID")));
			tree.setText(StringUtil.getString(map.get("CODENAME")));
			if(StringUtil.isNotNullOrEmpty(StringUtil.getString(map.get("DWID")))){
				dwid=StringUtil.getString(map.get("DWID"));
			}
			List<OrganTree> childList=getBanJiList(StringUtil.getString(map.get("CODEID")),i+1,userid,dwid,bspInfo);
			tree.setChildren(childList);
			if(childList!=null && childList.size()>0){
				tree.setLeaf("false");
			}else{
				tree.setLeaf("true");
			}
			array.add(tree);
		}
		return array;
	}
		
	public List<Map<String, Object>> printDataForLC(String tjlx,String tjfw,String usertype,String schoolid,String xjnj) {
		
		StringBuffer sql=new StringBuffer();
		if("01".equals(tjlx)){
			sql.append("SELECT T1.LCID,T1.EXAMNAME,T1.COUNTRS,IFNULL(T2.COUNTYXRS, 0) AS COUNTYXRS,TRUNCATE(IFNULL(T2.COUNTYXRS, 0)/T1.SJRS*100,0) AS YXL,T1.SJRS FROM (");
			sql.append(" SELECT LCID,EXAMNAME,COUNT(DISTINCT XJH) AS COUNTRS,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW");
			if("2".equals(usertype)){
				sql.append(" WHERE XX_JBXX_ID=").append(schoolid);
			}
			sql.append(" GROUP BY LCID,EXAMNAME");
			sql.append(" ) T1 LEFT JOIN(");
			sql.append(" SELECT T1.LCID,COUNT(T1.XJH) AS COUNTYXRS FROM (");
			sql.append(" SELECT LCID,XJH,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW ");
			if("2".equals(usertype)){
				sql.append(" WHERE XX_JBXX_ID=").append(schoolid);
			}
			sql.append(" GROUP BY LCID,XJH) T1");
			sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID");
			sql.append(" WHERE T1.SCORE>T2.UPLINE");
			sql.append(" GROUP BY T1.LCID) T2 ON T1.LCID=T2.LCID");
			sql.append(" WHERE T1.LCID IN ("+tjfw+")");
		}else if ("02".equals(tjlx)){
			sql.append("SELECT T1.LCID,T1.EXAMNAME,T1.COUNTRS,IFNULL(T2.COUNTYXRS, 0) AS COUNTYXRS,TRUNCATE(IFNULL(T2.COUNTYXRS, 0)/T1.SJRS*100,0) AS HGL,T1.SJRS FROM (");
			sql.append(" SELECT LCID,EXAMNAME,COUNT(DISTINCT XJH) AS COUNTRS,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW");
			if("2".equals(usertype)){
				sql.append(" WHERE XX_JBXX_ID=").append(schoolid);
			}
			sql.append(" GROUP BY LCID,EXAMNAME");
			sql.append(" ) T1 LEFT JOIN(");
			sql.append(" SELECT T1.LCID,COUNT(T1.XJH) AS COUNTYXRS FROM (");
			sql.append(" SELECT LCID,XJH,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW ");
			if("2".equals(usertype)){
				sql.append(" WHERE XX_JBXX_ID=").append(schoolid);
			}
			sql.append(" GROUP BY LCID,XJH) T1");
			sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID");
			sql.append(" WHERE T1.SCORE<T2.DOWNLINE");
			sql.append(" GROUP BY T1.LCID) T2 ON T1.LCID=T2.LCID");
			sql.append(" WHERE T1.LCID IN ("+tjfw+")");
			
		}else if ("03".equals(tjlx)){
			sql.append("SELECT LCID,EXAMNAME,SUM(SCORE) AS SCORE,COUNT(DISTINCT XJH) AS COUNTRS, TRUNCATE(AVG(SCORE), 2) AS PJF,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW");//TRUNCATE(SUM(SCORE)/COUNT(DISTINCT XJH),1) 
			sql.append(" WHERE 1=1");
			if("2".equals(usertype)){
				sql.append(" AND XX_JBXX_ID=").append(schoolid);
			}
			sql.append(" AND LCID IN (").append(tjfw).append(")");
			sql.append(" GROUP BY LCID,EXAMNAME");
			
		}else if ("04".equals(tjlx)){
			sql.append("SELECT t.LCID,t.EXAMNAME,COUNT(DISTINCT t.XJH) AS COUNTRS,MAX(t.SCORE) AS ZGF,MIN(t.SCORE) AS ZDF,sum(t.SJRS) as SJRS FROM (");
			sql.append(" SELECT LCID,EXAMNAME,XJH,MAX(SCORE) AS SCORE,MIN(SCORE) AS TOTALSCORE,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW ");
			sql.append(" WHERE 1=1");
			if("2".equals(usertype)){
				sql.append(" AND XX_JBXX_ID=").append(schoolid);
			}
			sql.append(" AND LCID IN (").append(tjfw).append(")");
			sql.append(" GROUP BY LCID,EXAMNAME,XJH) t");
			sql.append(" GROUP BY t.LCID,t.EXAMNAME");
		}else if ("05".equals(tjlx)){
			sql.append("SELECT t.LCID,t.EXAMNAME,COUNT(t.XJH) AS COUNTRS,TRUNCATE(AVG(t.SCORE),2) AS PJF,TRUNCATE(STDDEV(t.SCORE),2) AS BZC,sum(t.SJRS) as SJRS FROM (");
			sql.append(" SELECT LCID,EXAMNAME,XJH,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW ");
			sql.append(" WHERE 1=1");
			if("2".equals(usertype)){
				sql.append(" AND XX_JBXX_ID=").append(schoolid);
			}
			sql.append(" AND LCID IN (").append(tjfw).append(")");
			sql.append(" GROUP BY LCID,EXAMNAME,XJH) t GROUP BY LCID,EXAMNAME");
			sql.append(" ORDER BY LCID");
			
		}else if ("06".equals(tjlx)){
			sql.append(" SELECT T1.*,TRUNCATE(IFNULL(T2.HGRS,0)/T1.CKRS*100,2) AS HGL FROM (");
			sql.append(" SELECT CONCAT(EXAMNAME,'-',IFNULL(NJNAME,'无')) as EXAMNAME,LCID,COUNT(XJH) AS CKRS,IFNULL(KCMC,'无') as KCMC,TOTALSCORE,TRUNCATE(AVG(SCORE),0) AS SCORE,NJNAME,XJNJDM,");
			sql.append(" TRUNCATE(STDDEV(SCORE),2) AS BZC,TRUNCATE((1-AVG(SCORE)/TOTALSCORE),2) AS NDXS ,");
			sql.append(" TRUNCATE(STDDEV(SCORE)/AVG(SCORE),2) as CYXS");
			sql.append(" FROM STUSCORE_VIEW");
			sql.append(" GROUP BY EXAMNAME,LCID,IFNULL(KCMC,'无'),TOTALSCORE,NJNAME,XJNJDM ) t1");
			sql.append(" LEFT JOIN (");
			sql.append(" SELECT T1.LCID,T1.XJNJDM,T1.KCMC,COUNT(T1.XJH) AS HGRS from STUSCORE_VIEW t1");
			sql.append(" LEFT JOIN CUS_KW_STANDARD t2 on T1.LCID=T2.LCID");
			sql.append(" where T1.SCORE<T2.DOWNLINE");
			sql.append(" GROUP BY T1.LCID,T1.XJNJDM,T1.KCMC) t2 on T1.LCID= T2.LCID AND T1.XJNJDM=T2.XJNJDM AND T1.KCMC=T2.KCMC");
			sql.append(" WHERE T1.LCID in (").append(tjfw).append(")");
			if (!StringUtils.isNullOrEmpty(xjnj)) {
				sql.append(" AND T1.XJNJDM='").append(xjnj).append("'");
			}
			sql.append(" ORDER BY T1.EXAMNAME,T1.KCMC");
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}

	public List<Map<String, Object>> printDataForXX(String tjlx, String tjfw,
			String tjkm,String xjnj) {
		String[] lcxx=tjfw.split(",");
		StringBuffer xx_lc_str=new StringBuffer();
		for (int i = 0; i < lcxx.length; i++) {
			String[] lc_xx=lcxx[i].split(";");
			if(i==0){
				xx_lc_str.append(" AND  (");
			}else{
				xx_lc_str.append(" OR ");
			}
			xx_lc_str.append(" (T1.LCID='").append(lc_xx[0]).append("' AND T1.XX_JBXX_ID='").append(lc_xx[1]).append("')");
			
			if(i+1==lcxx.length)
				xx_lc_str.append(" )");			
		}		
		
		StringBuffer sql=new StringBuffer();
		if("01".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT T1.*,IFNULL(T2.COUNTYXRS, 0) AS COUNTYXRS,TRUNCATE(IFNULL(T2.COUNTYXRS, 0)/T1.SJRS*100,0) AS YXL,T1.SJRS FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,COUNT(DISTINCT XJH) AS COUNTRS,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW");
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC");
				sql.append(" ) T1 LEFT JOIN (");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,COUNT(DISTINCT T1.XJH) AS COUNTYXRS FROM STUSCORE_VIEW  T1");
				sql.append(" LEFT  JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID AND T2.BZTYPE='2'");
				sql.append(" WHERE T1.SCORE>T2.UPLINE");
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID) T2 ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID");
				sql.append(" WHERE 1=1");
				sql.append(xx_lc_str);
				sql.append(" ORDER BY T1.EXAMNAME");
			}else{
				sql.append("SELECT T1.LCID,T1.EXAMNAME,XXMC,T1.COUNTRS,IFNULL(T2.COUNTYXRS, 0) AS COUNTYXRS,TRUNCATE(IFNULL(T2.COUNTYXRS, 0)/T1.SJRS*100,0) AS YXL,T1.SJRS FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,COUNT(DISTINCT XJH) AS COUNTRS,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW");
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC");
				sql.append(" )T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,COUNT(T1.XJH) AS COUNTYXRS FROM (");
				sql.append(" SELECT LCID,XJH,XX_JBXX_ID,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW ");
				sql.append(" GROUP BY LCID,XJH,XX_JBXX_ID) T1");
				sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID");
				sql.append(" WHERE T1.SCORE>T2.UPLINE");
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID) T2 ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID");
				sql.append(" WHERE 1=1 ");
				sql.append(xx_lc_str);
			}
		}else if("02".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT T1.*,IFNULL(T2.COUNTYXRS, 0) AS COUNTHGRS,TRUNCATE(IFNULL(T2.COUNTHGRS, 0)/T1.SJRS*100,0) AS HGL,T1.SJRS FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,COUNT(DISTINCT XJH) AS COUNTRS,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW");
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC");
				sql.append(" ) T1 LEFT JOIN (");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,COUNT(DISTINCT T1.XJH) AS COUNTHGRS FROM STUSCORE_VIEW  T1");
				sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID AND T2.BZTYPE='2'");
				sql.append(" WHERE T1.SCORE<T2.DOWNLINE");
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID) T2 ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID");
				sql.append(" WHERE 1=1");				
				sql.append(xx_lc_str);
				sql.append(" ORDER BY T1.EXAMNAME");
			}else {
				sql.append("SELECT T1.LCID,T1.EXAMNAME,XXMC,T1.COUNTRS,IFNULL(T2.COUNTHGRS, 0) AS COUNTHGRS,TRUNCATE(IFNULL(T2.COUNTHGRS, 0)/T1.SJRS*100,0) AS HGL,T1.SJRS FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,COUNT(DISTINCT XJH) AS COUNTRS,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW");
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC");
				sql.append(" )T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,COUNT(T1.XJH) AS COUNTHGRS FROM (");
				sql.append(" SELECT LCID,XJH,XX_JBXX_ID,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW ");
				sql.append(" GROUP BY LCID,XJH,XX_JBXX_ID) T1");
				sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID");
				sql.append(" WHERE T1.SCORE<T2.DOWNLINE");
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID) T2 ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID");
				sql.append(" WHERE 1=1 ");
				sql.append(xx_lc_str);				
			}
		}else if("03".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,COUNT(XJH) AS COUNTRS,TRUNCATE(AVG(SCORE), 2) AS PJF,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW t1");
				sql.append(" where 1=1");
				sql.append(xx_lc_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT LCID,EXAMNAME,XXMC,SUM(SCORE) AS SCORE,COUNT(DISTINCT XJH) AS COUNTRS,TRUNCATE(AVG(SCORE), 2) AS PJF,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW T1"); 
				sql.append(" WHERE 1=1");
				sql.append(xx_lc_str);
				sql.append("GROUP BY LCID,EXAMNAME,XXMC");
			}
		}else if("04".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,COUNT(DISTINCT XJH) AS COUNTRS,MAX(SCORE) AS ZGF,MIN(SCORE) AS ZDF,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW t1");
				sql.append(" where 1=1");
				sql.append(xx_lc_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT t.LCID,t.EXAMNAME,t.XXMC,COUNT(DISTINCT t.XJH) AS COUNTRS,MAX(t.SCORE) AS ZGF,MIN(t.SCORE) AS ZDF,sum(t.SJRS) as SJRS FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XJH,XXMC,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW t1 ");
				sql.append(" WHERE 1=1");
				sql.append(xx_lc_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XJH,XXMC) t");
				sql.append(" GROUP BY t.LCID,t.EXAMNAME,t.XXMC");
			}
		}else if("05".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,COUNT(XJH) AS COUNTRS,AVG(SCORE) AS PJF,TRUNCATE(STDDEV(SCORE),1) AS BZC,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW t1");
				sql.append(" where 1=1");
				sql.append(xx_lc_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT t.LCID,t.EXAMNAME,t.XXMC,COUNT(t.XJH) AS COUNTRS,TRUNCATE(AVG(t.SCORE),2) AS PJF,TRUNCATE(STDDEV(t.SCORE),2) AS BZC,sum(t.SJRS) as SJRS FROM(");
				sql.append(" SELECT LCID,EXAMNAME,XJH,XXMC,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1");
				sql.append(xx_lc_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XJH,XXMC) t GROUP BY t.LCID,t.EXAMNAME,t.XXMC");
				sql.append(" ORDER BY t.LCID");
			}
		}else if("06".equals(tjlx)){
			sql.append(" SELECT T1.*,TRUNCATE(IFNULL(T2.HGRS,0)/T1.CKRS*100,2) AS HGL FROM (");
			sql.append(" SELECT EXAMNAME,LCID,COUNT(XJH) AS CKRS,KCMC,TOTALSCORE,TRUNCATE(AVG(SCORE),0) AS SCORE,XXMC,NJNAME,XJNJDM,XX_JBXX_ID,");
			sql.append(" TRUNCATE(STDDEV(SCORE),2) AS BZC,TRUNCATE((1-AVG(SCORE)/TOTALSCORE),2) AS NDXS ,");
			sql.append(" TRUNCATE(STDDEV(SCORE)/AVG(SCORE),2) as CYXS");
			sql.append(" FROM STUSCORE_VIEW");
			sql.append(" GROUP BY EXAMNAME,LCID,KCMC,TOTALSCORE,XXMC,NJNAME,XJNJDM,XX_JBXX_ID) t1");
			sql.append(" LEFT JOIN (");
			sql.append(" SELECT T1.LCID,T1.XJNJDM,T1.XX_JBXX_ID,COUNT(T1.XJH) AS HGRS from STUSCORE_VIEW t1");
			sql.append(" LEFT JOIN CUS_KW_STANDARD t2 on T1.LCID=T2.LCID");
			sql.append(" where T1.SCORE<T2.DOWNLINE");
			sql.append(" GROUP BY T1.LCID,T1.XJNJDM,T1.XX_JBXX_ID) t2 on T1.LCID= T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XJNJDM=T2.XJNJDM");
			sql.append(" where 1=1 ");
			sql.append(xx_lc_str);			
			if (StringUtil.isNotNullOrEmpty(xjnj)) {
				sql.append(" AND T1.XJNJDM='").append(xjnj).append("'");
			}			
			sql.append(" ORDER BY T1.EXAMNAME,T1.XX_JBXX_ID,T1.KCMC");
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}

	public List<Map<String, Object>> printDataForNJ(String tjlx, String tjfw, String tjkm,String schoolid) {
		String[] njxx=tjfw.split(",");
		StringBuffer nj_xx_str=new StringBuffer();
		for (int i = 0; i < njxx.length; i++) {
			String[] lc_nj=njxx[i].split(";");
			if(i==0){
				nj_xx_str.append(" AND ((T1.XJNJDM ='").append(lc_nj[1]).append("' AND T1.LCID='").append(lc_nj[0]).append("')");
			}else {
				nj_xx_str.append(" OR (T1.XJNJDM ='").append(lc_nj[1]).append("' AND T1.LCID='").append(lc_nj[0]).append("')");
			}
			if(i+1==njxx.length)
				nj_xx_str.append(" ) ");
		}
		StringBuffer sql=new StringBuffer();
		if("01".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT T1.*,IFNULL(T2.COUNTYXRS, 0) AS COUNTYXRS,TRUNCATE(IFNULL(T2.COUNTYXRS, 0)/T1.COUNTRS*100,0) AS YXL FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJMC,NJNAME,KCMC,COUNT(DISTINCT XJH) AS COUNTRS FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(nj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJMC,NJNAME,KCMC");
				sql.append(" ) T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,COUNT(DISTINCT T1.XJH) AS COUNTYXRS FROM STUSCORE_VIEW  T1");
				sql.append(" LEFT  JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID AND T2.BZTYPE='2' ");
				sql.append(" WHERE T1.SCORE>T2.UPLINE AND　XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(nj_xx_str);
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID) T2 ");
				sql.append(" ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XX_NJXX_ID=T2.XX_NJXX_ID");
				sql.append(" WHERE 0=0");
				sql.append(" ORDER BY T1.EXAMNAME");
			}else{
				sql.append("SELECT T1.LCID,T1.EXAMNAME,XXMC,T1.NJMC,T1.NJNAME,T1.COUNTRS,IFNULL(T2.COUNTYXRS, 0) AS COUNTYXRS,TRUNCATE(IFNULL(T2.COUNTYXRS, 0)/T1.COUNTRS*100,0) AS YXL FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,XJNJDM,NJMC,NJNAME,COUNT(DISTINCT XJH) AS COUNTRS FROM STUSCORE_VIEW t1");
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,XJNJDM,NJMC,NJNAME");
				sql.append(" )T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,COUNT(T1.XJH) AS COUNTYXRS FROM (");
				sql.append(" SELECT LCID,XJH,XX_JBXX_ID,XX_NJXX_ID,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW ");
				sql.append(" GROUP BY LCID,XJH,XX_JBXX_ID,XX_NJXX_ID) T1");
				sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID");
				sql.append(" WHERE T1.SCORE>T2.UPLINE");
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID) T2 ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XX_NJXX_ID=T2.XX_NJXX_ID ");
				sql.append(" WHERE 1=1 ");
				sql.append(nj_xx_str);
			}
		}else if("02".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT T1.*,IFNULL(T2.COUNTHGRS, 0) AS COUNTHGRS,TRUNCATE(IFNULL(T2.COUNTHGRS, 0)/T1.COUNTRS*100,0) AS HGL,T1.SJRS FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJMC,NJNAME,KCMC,COUNT(DISTINCT XJH) AS COUNTRS,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW t1 ");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(nj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJMC,NJNAME,KCMC");
				sql.append(" ) T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,COUNT(DISTINCT T1.XJH) AS COUNTHGRS FROM STUSCORE_VIEW  T1");
				sql.append(" LEFT  JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID AND T2.BZTYPE='2' ");
				sql.append(" WHERE T1.SCORE<T2.DOWNLINE AND　XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(nj_xx_str);
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID) T2 ");
				sql.append(" ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XX_NJXX_ID=T2.XX_NJXX_ID");
				sql.append(" WHERE 0=0");
				sql.append(" ORDER BY T1.EXAMNAME");
			}else{
				sql.append("SELECT T1.LCID,T1.EXAMNAME,XXMC,T1.NJMC,T1.NJNAME,T1.COUNTRS,IFNULL(T2.COUNTHGRS, 0) AS COUNTHGRS,TRUNCATE(IFNULL(T2.COUNTHGRS, 0)/T1.COUNTRS*100,0) AS HGL,T1.SJRS FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJMC,NJNAME,XJNJDM,COUNT(DISTINCT XJH) AS COUNTRS,SUM(case when SCORE is not null then 1 else 0 end) SJRS FROM STUSCORE_VIEW");
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJMC,XJNJDM,NJNAME");
				sql.append(" )T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,COUNT(T1.XJH) AS COUNTHGRS FROM (");
				sql.append(" SELECT LCID,XJH,XX_JBXX_ID,XX_NJXX_ID,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW ");
				sql.append(" GROUP BY LCID,XJH,XX_JBXX_ID,XX_NJXX_ID) T1");
				sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID");
				sql.append(" WHERE T1.SCORE<T2.DOWNLINE");
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID) T2 ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XX_NJXX_ID=T2.XX_NJXX_ID");
				sql.append(" WHERE 1=1 ");
				sql.append(nj_xx_str);
			}
		}else if("03".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
			sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,COUNT(XJH) AS COUNTRS,TRUNCATE(AVG(SCORE), 2) AS PJF FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(nj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT LCID,EXAMNAME,XXMC,NJMC,NJNAME,SUM(SCORE) AS SCORE,COUNT(DISTINCT XJH) AS COUNTRS,TRUNCATE(AVG(SCORE), 2) AS PJF FROM STUSCORE_VIEW T1"); 
				sql.append(" WHERE 1=1");
				sql.append(nj_xx_str);
				sql.append("GROUP BY LCID,EXAMNAME,XXMC,NJMC,NJNAME");
			}
		}else if("04".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,COUNT(DISTINCT XJH) AS COUNTRS,MAX(SCORE) AS ZGF ,MIN(SCORE) AS ZDF FROM STUSCORE_VIEW T1");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(nj_xx_str);				
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT t.LCID,t.EXAMNAME,t.XXMC,t.NJMC,t.NJNAME,COUNT(DISTINCT t.XJH) AS COUNTRS,MAX(t.SCORE) AS ZGF,MIN(t.SCORE) AS ZDF FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XJH,XXMC,NJMC,NJNAME,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW t1 ");
				sql.append(" WHERE 1=1");
				sql.append(nj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XJH,XXMC,NJMC,NJNAME) t");
				sql.append(" GROUP BY t.LCID,t.EXAMNAME,t.XXMC,t.NJMC,t.NJNAME");				
			}
		}else if("05".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,COUNT(XJH) AS COUNTRS,AVG(SCORE) AS PJF,TRUNCATE(STDDEV(SCORE),1) AS BZC FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);				
				sql.append(nj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT t.LCID,t.EXAMNAME,t.XXMC,t.NJMC,t.NJNAME,COUNT(t.XJH) AS COUNTRS,TRUNCATE(AVG(t.SCORE),2) AS PJF,TRUNCATE(STDDEV(t.SCORE),2) AS BZC FROM(");
				sql.append(" SELECT LCID,EXAMNAME,XJH,XXMC,NJMC,NJNAME,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1");
				sql.append(nj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XJH,XXMC,NJMC,NJNAME) t GROUP BY t.LCID,t.EXAMNAME,t.XXMC,t.NJMC,t.NJNAME");
				sql.append(" ORDER BY t.LCID");
			}
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}

	public List<Map<String, Object>> printDataForBJ(String tjlx, String tjfw,
			String tjkm,String schoolid) {
		String[] bjxx=tjfw.split(",");
		StringBuffer bj_xx_str=new StringBuffer();
		for (int i = 0; i < bjxx.length; i++) {
			String[] lc_bj=bjxx[i].split(";");
			if(i==0){
				bj_xx_str.append(" AND ((T1.XX_BJXX_ID ='").append(lc_bj[1]).append("' AND T1.LCID='").append(lc_bj[0]).append("')");
			}else {
				bj_xx_str.append(" OR (T1.XX_BJXX_ID ='").append(lc_bj[1]).append("' AND T1.LCID='").append(lc_bj[0]).append("')");
			}
			if(i+1==bjxx.length)
				bj_xx_str.append(")");
		}
		
		StringBuffer sql=new StringBuffer();
		if("01".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT T1.*,IFNULL(T2.COUNTYXRS, 0) AS COUNTYXRS,TRUNCATE(IFNULL(T2.COUNTYXRS, 0)/T1.COUNTRS*100,0) AS YXL FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJNAME,XX_BJXX_ID,BJMC,KCMC,COUNT(DISTINCT XJH) AS COUNTRS FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(bj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJNAME,XX_BJXX_ID,BJMC,KCMC");
				sql.append(") T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,XX_BJXX_ID,COUNT(DISTINCT T1.XJH) AS COUNTYXRS FROM STUSCORE_VIEW  T1");
				sql.append(" LEFT  JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID AND T2.BZTYPE='2' ");
				sql.append(" WHERE T1.SCORE>T2.UPLINE AND　XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(bj_xx_str);
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,XX_BJXX_ID) T2 ");
				sql.append(" ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XX_NJXX_ID=T2.XX_NJXX_ID AND T1.XX_BJXX_ID=T2.XX_BJXX_ID");
				sql.append(" WHERE 0=0");
				sql.append(" ORDER BY T1.EXAMNAME");
			}else{
				sql.append("SELECT T1.LCID,T1.EXAMNAME,XXMC,T1.NJNAME,T1.BJMC,T1.COUNTRS,IFNULL(T2.COUNTYXRS, 0) AS COUNTYXRS,TRUNCATE(IFNULL(T2.COUNTYXRS, 0)/T1.COUNTRS*100,0) AS YXL FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJMC,NJNAME,XX_BJXX_ID,BJMC,COUNT(DISTINCT XJH) AS COUNTRS FROM STUSCORE_VIEW");
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJMC,NJNAME,XX_BJXX_ID,BJMC");
				sql.append(" )T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,T1.XX_BJXX_ID,COUNT(T1.XJH) AS COUNTYXRS FROM (");
				sql.append(" SELECT LCID,XJH,XX_JBXX_ID,XX_NJXX_ID,XX_BJXX_ID,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW ");
				sql.append(" GROUP BY LCID,XJH,XX_JBXX_ID,XX_NJXX_ID,XX_BJXX_ID) T1");
				sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID");
				sql.append(" WHERE T1.SCORE>T2.UPLINE");
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,T1.XX_BJXX_ID) T2 ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XX_NJXX_ID=T2.XX_NJXX_ID AND T1.XX_BJXX_ID=T2.XX_BJXX_ID");
				sql.append(" WHERE 1=1 ");
				sql.append(bj_xx_str);
			}
		}else if("02".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append("SELECT T1.*,IFNULL(T2.COUNTHGRS, 0) AS COUNTHGRS,TRUNCATE(IFNULL(T2.COUNTHGRS, 0)/T1.COUNTRS*100,0) AS HGL FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJNAME,XX_BJXX_ID,BJMC,KCMC,COUNT(DISTINCT XJH) AS COUNTRS FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(bj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_NJXX_ID,NJNAME,XX_BJXX_ID,BJMC,KCMC");
				sql.append(") T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,XX_BJXX_ID,COUNT(DISTINCT T1.XJH) AS COUNTHGRS FROM STUSCORE_VIEW  T1");
				sql.append(" LEFT  JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID AND T2.BZTYPE='2' ");
				sql.append(" WHERE T1.SCORE<T2.DOWNLINE AND　XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(bj_xx_str);
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,XX_BJXX_ID) T2 ");
				sql.append(" ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XX_NJXX_ID=T2.XX_NJXX_ID AND T1.XX_BJXX_ID=T2.XX_BJXX_ID");
				sql.append(" WHERE 0=0");
				sql.append(" ORDER BY T1.EXAMNAME");
			}else{
				sql.append("SELECT T1.LCID,T1.EXAMNAME,XXMC,T1.NJNAME,BJMC,T1.COUNTRS,IFNULL(T2.COUNTHGRS, 0) AS COUNTHGRS,TRUNCATE(IFNULL(T2.COUNTHGRS, 0)/T1.COUNTRS*100,0) AS HGL FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,NJNAME,XX_BJXX_ID,BJMC,COUNT(DISTINCT XJH) AS COUNTRS FROM STUSCORE_VIEW");
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,XX_BJXX_ID,NJNAME,BJMC");
				sql.append(" )T1 LEFT JOIN(");
				sql.append(" SELECT T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,T1.XX_BJXX_ID,COUNT(T1.XJH) AS COUNTHGRS FROM (");
				sql.append(" SELECT LCID,XJH,XX_JBXX_ID,XX_NJXX_ID,XX_BJXX_ID,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW ");
				sql.append(" GROUP BY LCID,XJH,XX_JBXX_ID,XX_NJXX_ID,XX_BJXX_ID) T1");
				sql.append(" LEFT JOIN CUS_KW_STANDARD T2 ON T1.LCID=T2.LCID");
				sql.append(" WHERE T1.SCORE<T2.DOWNLINE");
				sql.append(" GROUP BY T1.LCID,T1.XX_JBXX_ID,T1.XX_NJXX_ID,T1.XX_BJXX_ID) T2 ON T1.LCID=T2.LCID AND T1.XX_JBXX_ID=T2.XX_JBXX_ID AND T1.XX_BJXX_ID=T2.XX_BJXX_ID");
				sql.append(" WHERE 1=1 ");
				sql.append(bj_xx_str);
			}
		}else if("03".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,BJMC,COUNT(XJH) AS COUNTRS,TRUNCATE(AVG(SCORE), 2) AS PJF FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(bj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,BJMC");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT LCID,EXAMNAME,XXMC,NJMC,NJNAME,BJMC,SUM(SCORE) AS SCORE,COUNT(DISTINCT XJH) AS COUNTRS,TRUNCATE(AVG(SCORE), 2) AS PJF FROM STUSCORE_VIEW T1"); 
				sql.append(" WHERE 1=1");
				sql.append(bj_xx_str);
				sql.append("GROUP BY LCID,EXAMNAME,XXMC,NJMC,NJNAME,BJMC");
			}
		}else if("04".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,BJMC,COUNT(DISTINCT XJH) AS COUNTRS,MAX(SCORE) AS ZGF,MIN(SCORE) AS ZDF FROM STUSCORE_VIEW ");
				sql.append(" WHERE 1=1 AND XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(bj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,BJMC");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT t.LCID,t.EXAMNAME,t.XXMC,t.NJMC,t.NJNAME,t.BJMC,COUNT(DISTINCT t.XJH) AS COUNTRS,MAX(t.SCORE) AS ZGF,MIN(t.SCORE) AS ZDF FROM (");
				sql.append(" SELECT LCID,EXAMNAME,XJH,XXMC,NJMC,NJNAME,BJMC,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW t1 ");
				sql.append(" WHERE 1=1");
				sql.append(bj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XJH,XXMC,NJMC,BJMC,NJNAME) t");
				sql.append(" GROUP BY t.LCID,t.EXAMNAME,t.XXMC,t.NJMC,t.BJMC,t.NJNAME");
			}
		}else if("05".equals(tjlx)){
			if(tjkm!=null && !"".equals(tjkm)){
				sql.append(" SELECT LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,BJMC,COUNT(XJH) AS COUNTRS,AVG(SCORE) AS PJF,TRUNCATE(STDDEV(SCORE),1) AS BZC FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1 AND T1.XX_JBXX_ID=");
				sql.append(schoolid);
				sql.append(bj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XX_JBXX_ID,XXMC,KCMC,NJNAME,BJMC");
				sql.append(" ORDER BY EXAMNAME");
			}else{
				sql.append("SELECT t.LCID,t.EXAMNAME,t.XXMC,t.NJMC,t.NJNAME,t.BJMC,COUNT(t.XJH) AS COUNTRS,TRUNCATE(AVG(t.SCORE),2) AS PJF,TRUNCATE(STDDEV(t.SCORE),2) AS BZC FROM(");
				sql.append(" SELECT LCID,EXAMNAME,XJH,XXMC,NJMC,NJNAME,BJMC,SUM(SCORE) AS SCORE,SUM(TOTALSCORE) AS TOTALSCORE FROM STUSCORE_VIEW t1");
				sql.append(" WHERE 1=1");
				sql.append(bj_xx_str);
				sql.append(" GROUP BY LCID,EXAMNAME,XJH,XXMC,NJMC,NJNAME,BJMC) t GROUP BY t.LCID,t.EXAMNAME,t.XXMC,t.NJMC,t.BJMC,t.NJNAME");
				sql.append(" ORDER BY t.LCID");
			}
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}

}
