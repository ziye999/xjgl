package com.jiajie.service.core.impl;
/**
 * 下拉框
 * */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.DropListService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.Tools;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import com.jiajie.util.bean.OrganTree;
@Service("dropListService")
@SuppressWarnings("all")
public class DropListServiceImpl extends ServiceBridge implements DropListService {

	public Object getDictList(String dictCode) {
		String sql = "select distinct dm as CODEID, mc as CODENAME from " + dictCode + "";
		List list = getListSQL(sql);
		return list;
	}

	//获取年度
	public Object getXn(String orgId, MBspInfo bspInfo) {
		String sql = "select distinct xnxq_id as CODEID, CONCAT(xnxq.xnmc,xq.mc) as CODENAME "+
					"from zxxs_xnxq xnxq "+
					"left join zd_xq xq on xnxq.xqmc = xq.dm "+
					"where 0=0 "+
					(orgId==null||"".equals(orgId)?"and ssdwm='"+bspInfo.getOrgan().getOrganCode()+"' ":"and ssdwm='"+orgId+"' ")+
					"ORDER BY codename desc";
		List list = getListSQL(sql);
		return list;
	}

	//获取考试类型
	public Object getExamtype(MBspInfo bspInfo) {
		String sql = " select distinct dm as CODEID, mc as CODENAME from cus_kw_examtype ";
		String orgCode = bspInfo.getOrgan().getOrganCode();
		if(!orgCode.endsWith("0000000000")){
			sql += " where dm<>3 and dm<>4 ";
		}
		List list = getListSQL(sql);
		return list;
	}
	//获取星期
	public Object getWeeks(String params, String orgId) {
		String xqxq_id = params.split(":")[1];
		//取季度起始结束时间
		String sql = "select distinct XXKSSJ, XXJSSJ from zxxs_xnxq where xnxq_id = '"+xqxq_id+"' and ssdwm='"+orgId+"'";
		List list = getListSQL(sql);
		
		List returnList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		if(list != null && list.size()>0){
			try {
				Date start = sdf.parse(((Map)list.get(0)).get("XXKSSJ").toString());
				Map sj = new HashMap();
				sj.put("XXKSSJ", ((Map)list.get(0)).get("XXKSSJ").toString());
				sj.put("XXJSSJ", ((Map)list.get(0)).get("XXJSSJ").toString());
				returnList.add(sj);
				Date end = sdf.parse(((Map)list.get(0)).get("XXJSSJ").toString());
				int num = Tools.weekInterval(start, end);
				int zcInt = 1;
				while(num > 0){
					Map hm = new HashMap();
					hm.put("CODEID", ""+zcInt);
					hm.put("CODENAME", "第"+Tools.formatInteger(zcInt)+"周");
					returnList.add(hm);
					zcInt++;
					num --;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		}
		return returnList;
	}
	
	//获取科目
	public Object getCourse(String dwid) {
		String sql="SELECT distinct NAME AS CODEID,NAME AS CODENAME FROM SYS_ENUM_VALUE WHERE DIC_TYPE='ZD_KM' order by CODEID";
		List list=getListSQL(sql);
		return list;
	}

	//获取年度
	public Object getXnxq(MBspInfo bspInfo){
		String sql = "";
		String orgCode = bspInfo.getOrgan().getOrganCode();
		if("1".equals(bspInfo.getUserType())) {
			//组考单位
			sql = "SELECT DISTINCT (T1.XNXQ_ID) AS CODEID,"+
					"CONCAT(T2.XNMC,(CASE T2.XQMC when '1' then '第1次' when '2' then '第2次' END)) AS CODENAME"+
					" FROM CUS_KW_EXAMROUND t1"+
					" INNER JOIN ZXXS_XNXQ t2 on t1.XNXQ_ID=t2.XNXQ_ID"+
					" WHERE T1.DWTYPE='1' AND T1.DWID='"+orgCode+"') ";
		}else{
			//参考单位
			sql = "(SELECT DISTINCT (T1.XNXQ_ID) AS CODEID,"+
					"CONCAT(T2.XNMC,(CASE T2.XQMC when '1' then '第1次' when '2' then '第2次' END)) AS CODENAME"+
					" FROM CUS_KW_EXAMROUND t1"+
					" INNER JOIN ZXXS_XNXQ t2 on t1.XNXQ_ID=t2.XNXQ_ID"+
					" WHERE T1.DWTYPE='2' AND T1.DWID='"+orgCode+"')"+
					" UNION"+
					" (SELECT DISTINCT T2.XNXQ_ID AS CODEID,"+
					"CONCAT(T3.XNMC,(CASE T3.XQMC when '1' then '第1次' when '2' then '第2次' END)) AS CODENAME"+
					" FROM CUS_KW_EXAMSCHOOL T1"+
					" LEFT JOIN CUS_KW_EXAMROUND T2 ON T1.LCID=T2.LCID"+
					" INNER JOIN ZXXS_XNXQ t3 on t2.XNXQ_ID=t3.XNXQ_ID"+
					" WHERE T1.XXDM='"+orgCode+"') ";
		}
		List list=getListSQL(sql);
		return list;
	}
	
	//获取考试轮次
	public Object getExamRound(String xnxqId,String userId,MBspInfo bspInfo) {
		String sql="select distinct t1.LCID AS CODEID,t1.EXAMNAME AS CODENAME "+
				"from CUS_KW_EXAMROUND t1 "+
				"left join com_mems_organ zk on zk.region_code=t1.dwid "+
				"left join com_mems_organ zk1 on zk1.region_code=zk.PARENT_CODE "+
				"left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=t1.DWID "+
				"left join com_mems_organ zkx on zkx.region_code=xx.SSZGJYXZDM "+
				"left join com_mems_organ zkx1 on zkx1.region_code=zkx.PARENT_CODE "+
				"WHERE 0=0";
		sql+=" and (t1.DWID='"+bspInfo.getOrgan().getOrganCode()+
				"' or zk.region_code='"+bspInfo.getOrgan().getOrganCode()+"' or zk1.region_code='"+bspInfo.getOrgan().getOrganCode()+
				"' or zk1.PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+"' or xx.SSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+
				"' or zkx.region_code='"+bspInfo.getOrgan().getOrganCode()+"' or zkx1.region_code='"+bspInfo.getOrgan().getOrganCode()+
				"' or zkx1.PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+"')";
		if(xnxqId!=null && !"".equals(xnxqId)){
			String xn=xnxqId.split(",")[0];
			String xq=xnxqId.split(",")[1];
			sql+=" AND t1.XN='"+xn+"'"+
				 " AND t1.XQM='"+xq+"'";
		}
		sql+=" order by t1.dwid";
		List list=getListSQL(sql);
		return list;
	}

	public Object getExamRoundByXnxq(String xnxqId,MBspInfo bspInfo) {
		MBspOrgan orgn = bspInfo.getOrgan();
		String sql="select DISTINCT T1.LCID AS CODEID,T1.EXAMNAME AS CODENAME "+
				"from CUS_KW_EXAMROUND T1 "+
				"left join com_mems_organ zk on zk.region_code=T1.dwid "+
				"left join com_mems_organ zk1 on zk1.region_code=zk.PARENT_CODE "+
				"left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=T1.DWID "+
				"left join com_mems_organ zkx on zkx.region_code=xx.SSZGJYXZDM "+
				"left join com_mems_organ zkx1 on zkx1.region_code=zkx.PARENT_CODE "+
				"WHERE 1=1 ";
		sql += "AND (T1.DWID='"+orgn.getOrganCode()+
				"' OR zk.region_code='"+orgn.getOrganCode()+"' OR zk1.region_code='"+orgn.getOrganCode()+
				"' OR zk1.PARENT_CODE = '"+orgn.getOrganCode()+"' or xx.SSZGJYXZDM='"+orgn.getOrganCode()+
				"' OR zkx.region_code='"+orgn.getOrganCode()+"' OR zkx1.region_code='"+orgn.getOrganCode()+
				"' OR zkx1.PARENT_CODE = '"+orgn.getOrganCode()+"') ";
		if(xnxqId!=null && !"".equals(xnxqId)){
			String xn=xnxqId.split(",")[0];
			String xq=xnxqId.split(",")[1];
			sql+=" AND T1.XN='"+xn+"'"+
				 " AND T1.XQM='"+xq+"'";
		}
		sql += " order by T1.dwid";
		List list=getListSQL(sql);
		return list;
	}

	public Object getGrade(String userType,String dwid) { 
		//ComUserMapping user=(ComUserMapping)getSession().get(ComUserMapping.class, userId);
		String sql="";
		if("1".equals(userType)){
			sql="SELECT distinct CODE AS CODEID, NAME AS CODENAME FROM SYS_ENUM_VALUE WHERE DIC_TYPE='ZD_XT_NJDM' AND FLAG='1'";
		}else{
			sql="SELECT distinct T2.CODE AS CODEID, T2.NAME AS CODENAME from ZXXS_XX_NJXX t1"+
				" LEFT JOIN SYS_ENUM_VALUE t2 on T1.XJNJDM=T2.CODE AND DIC_TYPE='ZD_XT_NJDM' AND FLAG='1'"+
				" WHERE t1.NJZT='1' and t1.XX_JBXX_ID='"+dwid+"'"+
				" ORDER BY CODEID";
		}
		List list=getListSQL(sql);
		return list;
	}
		
	public Object getgradeBySchool(String schId){
		String sql = " SELECT distinct T2.CODE AS CODEID,T2.NAME AS CODENAME FROM ZXXS_XX_NJXX T1"+
					 " LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XJNJDM=T2.CODE AND T2.DIC_TYPE='ZD_XT_NJDM'"+
					 " WHERE T1.NJZT='1' and T1.XX_JBXX_ID='"+schId+"'";
		return getListSQL(sql);
	}
	
	public Object getnjBySchool(String schId){
		String sql = " SELECT distinct T1.xx_njxx_id AS CODEID,T1.njmc AS CODENAME FROM ZXXS_XX_NJXX T1"+
					 " WHERE T1.NJZT='1' and T1.XX_JBXX_ID='"+schId+"'";
		return getListSQL(sql);
	}
	
	public Object getClassByGrade(String njid){
		String sql = " SELECT distinct t3.XX_BJXX_ID AS CODEID,t3.BJMC AS CODENAME from ZXXS_XX_JBXX t1"+
					 " LEFT JOIN ZXXS_XX_NJXX t2 on t1.XX_JBXX_ID=T2.XX_JBXX_ID"+
					 " LEFT JOIN ZXXS_XX_BJXX t3 on t2.XX_NJXX_ID = t3.XX_NJXX_ID"+
					 " WHERE T2.XX_NJXX_ID='"+njid+"'";
		List list=getListSQL(sql);
		return list;		
	}
	
	public Object getClassByGrade(String njid,String schoolId){
		String sql = " SELECT distinct T1.XX_BJXX_ID AS CODEID,T1.BJMC AS CODENAME from ZXXS_XX_BJXX t1"+
					 " LEFT JOIN ZXXS_XX_NJXX t2 on T1.XX_NJXX_ID=T2.XX_NJXX_ID"+
					 " WHERE t1.yxbz='1' and T1.XX_JBXX_ID='"+schoolId+"' AND T2.XJNJDM='"+njid+"'";
		List list=getListSQL(sql);
		return list;		
	}

	//获取考试时长
	public Object getTimeLength() {
		String sql="SELECT distinct TIMELENGTH AS CODEID,TIMELENGTH AS CODENAME FROM CUS_KW_EXAMSECTION";
		List list=getListSQL(sql);
		return list;
	}
	
	/**
	 * 如果登陆的是组考单位，获取当前组考单位信息，如果登陆的是参考单位，获取当前参考单位所属组考单位
	 */
	public Object getJyj(String sjjyj,MBspInfo bspInfo){
		String sql=" SELECT distinct T1.REGION_CODE AS CODEID,T1.REGION_EDU AS CODENAME FROM COM_MEMS_ORGAN T1";
		String orgCode = bspInfo.getOrgan().getOrganCode();
		/*if("1".equals(bspInfo.getUserType())){
			sql += " WHERE 0=0" +//SUBSTR(T1.REGION_CODE, 1, 4)='"+sjjyj.substring(0, 4)+"'
					" AND T1.PARENT_CODE='"+sjjyj+"' " ;
		}else if("2".equals(bspInfo.getUserType())||"3".equals(bspInfo.getUserType())){*/
			sql += " LEFT JOIN ZXXS_XX_JBXX T2 ON T2.sszgjyxzdm=T1.REGION_CODE"+
					" WHERE T2.XX_JBXX_ID='"+orgCode+"'";
		//}
		List list=getListSQL(sql);
		return list;
	}
	
	//获取登陆参考单位
	public Object getSchool(String xjjyj,MBspInfo bspInfo){
		String sql = "";
		String orgCode = bspInfo.getOrgan().getOrganCode();
		if("1".equals(bspInfo.getUserType())){
			sql = " SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME FROM ZXXS_XX_JBXX T1"+
					" WHERE (T1.SSZGJYXZDM='"+xjjyj+"' or T1.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+xjjyj+
					"') or T1.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+
					xjjyj+"'))) ";
		}else{
			sql = " SELECT distinct XX_JBXX_ID AS CODEID,XXMC AS CODENAME FROM ZXXS_XX_JBXX"+
					" WHERE XX_JBXX_ID='"+orgCode+"'";
		}
		List list=getListSQL(sql);
		return list;
	}
	
	//获取登陆参考单位所属组考单位的主管单位
	public Object getSjjyj(MBspInfo bspInfo){
		String sql=" SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME FROM COM_MEMS_ORGAN ";
		String orgCode = bspInfo.getOrgan().getOrganCode();
		if("1".equals(bspInfo.getUserType())) {
			/*if(!"".equals(orgCode) && orgCode.endsWith("0000000000")){
				//省级组考单位
				sql = sql + " WHERE SUBSTR(REGION_CODE, 1, 2)='"+orgCode.substring(0, 2)+"' " +
						" AND SUBSTR(REGION_CODE, 5, 8)='00000000' " +
						" AND SUBSTR(REGION_CODE, 3, 2)<>'00' ";
			}else if(!"".equals(orgCode) && orgCode.endsWith("00000000")){
				//市级组考单位
				sql = sql + " WHERE REGION_CODE IN ('"+orgCode+"')";
			}else if(!"".equals(orgCode) && orgCode.endsWith("000000")){*/
				sql = sql + " WHERE REGION_CODE IN (SELECT REGION_CODE FROM COM_MEMS_ORGAN " +
						" WHERE PARENT_CODE='"+orgCode+"') ";
			//}
		}else{
			//参考单位
			sql = sql + " WHERE REGION_CODE IN (SELECT T1.PARENT_CODE FROM COM_MEMS_ORGAN T1 " +
			" LEFT JOIN ZXXS_XX_JBXX T2 ON T2.sszgjyxzdm=T1.REGION_CODE " +
			" WHERE T2.XX_JBXX_ID='"+orgCode+"') ";
		}
		List list=getListSQL(sql);
		return list;
	}
	//获取考点名称
	public Object kaoDianMc(String lcid,MBspInfo bspInfo){
		String sql="SELECT distinct T1.KDID as CODEID,T2.XXMC as CODENAME from CUS_KW_EXAMSCHOOL t1"+
				" LEFT JOIN ZXXS_XX_JBXX t2 on T1.XXDM=T2.XX_JBXX_ID"+
				" where T1.LCID='"+lcid+"'";
		if("1".equals(bspInfo.getUserType())||"2".equals(bspInfo.getUserType())){
			sql = sql+" and (T2.SSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+"' or T2.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+
					"') or T2.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+
					bspInfo.getOrgan().getOrganCode()+"')))";
		}else if("3".equals(bspInfo.getUserType())){
			sql = sql+" and t2.XX_JBXX_ID='"+bspInfo.getOrgan().getOrganCode()+"'";
		}
		return getListSQL(sql);
	}
	//获取教学楼
	public Object jiaoXueLou(String kdId,String schoolid){
		String sql="";
		if(StringUtil.isNotNullOrEmpty(kdId)){
			sql="SELECT distinct LF_ID as CODEID,BUILDNAME as CODENAME from CUS_KW_BUILDING t1"+ 
				" LEFT JOIN CUS_KW_EXAMSCHOOL t2 on T1.SCHOOL_ID_QG=T2.XXDM "+
				" where T2.KDID='"+kdId+"'";
		}else{
			sql="SELECT distinct LF_ID as CODEID,BUILDNAME as CODENAME from CUS_KW_BUILDING"+
				" WHERE SCHOOL_ID_QG="+schoolid;
		}
		return getListSQL(sql);
	}
	//获取考场
	public Object examRoom(String kdId){
		String sql = " SELECT distinct KCID AS CODEID,cast(CONCAT(KCBH,'考场') as char) AS CODENAME FROM CUS_KW_EXAMROOM WHERE KDID='"+kdId+"' order by CODENAME+0 asc";
		return getListSQL(sql);
	}
	
	//根据考试轮次获取考试科目
	public Object getKskmBykslc(String lcid){
		String sql = " SELECT distinct KMID AS CODEID,SUBJECTNAME AS CODENAME FROM CUS_KW_EXAMSUBJECT WHERE LCID='"+lcid+"' order by substr(SUBJECTNAME,11,length(substr(SUBJECTNAME,11))-length('批'))+0 asc";
		return getListSQL(sql);
	}
	//获取年度数据
	public Object getXnxqListPage() {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct t1.XNMC,t1.xqmc as XQCODE,t2.name as XQMC from ZXXS_XNXQ t1 ");
		sb.append("inner join SYS_ENUM_VALUE t2 on t1.XQMC=t2.CODE and t2.DIC_TYPE='ZD_XT_XQMC' ");
		sb.append("where t1.yxbz='1' group by t1.xnmc,t1.xqmc,t2.name order by t1.xnmc desc,t1.xqmc desc");
		return getListSQL(sb.toString());
	}

	/**
	 * type的设置情况：type=0不获取参考单位数据，只选择组考单位
	 * 				  type=1单选
	 *                不设置type参考单位组考单位复选
	 * 					
	 */
	public Object getOrganTree(String organCode,String type) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct T.* from (SELECT t1.REGION_CODE,t1.REGION_EDU,PARENT_CODE,t1.IN_USE FROM COM_MEMS_ORGAN t1 ");
		if(type == null || "undefined".equals(type) || "1".equals(type)) {
			sb.append("UNION ALL ");
			sb.append("select t3.XX_JBXX_ID as REGION_CODE,t3.XXMC as REGION_EDU,t3.SSZGJYXZDM as PARENT_CODE,'1' as IN_USE from ZXXS_XX_JBXX t3 where t3.dwlx='2' ");					
		}
		sb.append(") T ");
		sb.append("WHERE IN_USE='1' ");//T.REGION_CODE like binary('").append(organCode).append("%') or PARENT_CODE LIKE binary('").append(organCode).append("%') AND 
		sb.append("ORDER BY T.PARENT_CODE");
		return getListSQL(sb.toString());
	}
	
	//修改人： 包敏     修改日期： 2018-05-17  修改原因：增加获取组考考点弹出框
	/**
	 * type的设置情况：type=0不获取参考单位数据，只选择组考单位
	 * 				  type=1单选
	 *                不设置type参考单位组考单位复选
	 * 					
	 */
	public Object getPointTree(String organCode) {
		StringBuilder sb = new StringBuilder();
		//sb.append("select distinct T.* from (SELECT t1.REGION_CODE,t1.REGION_EDU,PARENT_CODE,t1.IN_USE FROM COM_MEMS_ORGAN t1 ");
		//if(type == null || "undefined".equals(type) || "1".equals(type)) {
		//	sb.append("UNION ALL ");
		//	sb.append("select t3.XX_JBXX_ID as REGION_CODE,t3.XXMC as REGION_EDU,t3.SSZGJYXZDM as PARENT_CODE,'1' as IN_USE from ZXXS_XX_JBXX t3 where t3.dwlx='2' ");					
		//}
		//sb.append(") T ");
		//sb.append("WHERE IN_USE='1' ");//T.REGION_CODE like binary('").append(organCode).append("%') or PARENT_CODE LIKE binary('").append(organCode).append("%') AND 
		//sb.append("ORDER BY T.PARENT_CODE");
		sb.append("SELECT distinct a.REGION_CODE AS REGION_CODE,a.REGION_EDU AS REGION_EDU,a.PARENT_CODE, case when (select count(XX_JBXX_ID) from ZXXS_XX_JBXX " );
	    sb.append("where dwlx='1' and SSZGJYXZDM=a.REGION_CODE)>0 then 2 else 1 end AS CODETYPE FROM COM_MEMS_ORGAN a " ) ;
	    sb.append("where REGION_CODE in (SELECT distinct  T3.REGION_CODE   FROM cus_kw_building T1 LEFT JOIN ZXXS_XX_JBXX T2 ON T1.SCHOOL_ID_QG=T2.XX_JBXX_ID LEFT JOIN COM_MEMS_ORGAN T3 ON T3.REGION_CODE=T2.SSZGJYXZDM WHERE T2.dwlx='1') ") ;
	    sb.append("UNION ALL SELECT distinct CONCAT(T2.XX_JBXX_ID,'_',T1.LF_ID) AS REGION_CODE, " );
	    sb.append("T1.BUILDNAME AS REGION_EDU, T3.REGION_CODE as PARENT_CODE, 3 AS CODETYPE FROM cus_kw_building T1 LEFT JOIN ZXXS_XX_JBXX T2 ON T1.SCHOOL_ID_QG=T2.XX_JBXX_ID " );
	    sb.append("LEFT JOIN COM_MEMS_ORGAN T3 ON T3.REGION_CODE=T2.SSZGJYXZDM WHERE T2.dwlx='1'");
		return getListSQL(sb.toString());
	}
	
	
	public Object getGradeClassTree(String schoolCode,MBspInfo bspInfo) {
		String orgcode = "";
		if ("1".equals(bspInfo.getUserType())) {
			orgcode = bspInfo.getOrgan().getOrganCode();
		}else if ("2".equals(bspInfo.getUserType())) {
			orgcode = bspInfo.getOrgan().getOrganCode();
		}else if ("3".equals(bspInfo.getUserType())) {
			orgcode = bspInfo.getOrgan().getParentCode();
		}
		StringBuilder sb0 = new StringBuilder();
		sb0.append("select distinct REGION_CODE as CODE,REGION_EDU as NAME,PARENT_CODE as PRCODE,'0' as CODETYPE ");
		sb0.append("from com_mems_organ ");
		sb0.append("where REGION_CODE='"+orgcode+"' and PARENT_CODE not in (select REGION_CODE from com_mems_organ) ");
		List lst0 = getListSQL(sb0.toString());
		int size0 = lst0.size();
		StringBuilder sb0_1 = new StringBuilder();
		sb0_1.append("select distinct REGION_CODE as CODE,REGION_EDU as NAME,PARENT_CODE as PRCODE,'1' as CODETYPE ");
		sb0_1.append("from com_mems_organ ");
		sb0_1.append("where "+(size0>0?"PARENT_CODE='"+orgcode+"'":"REGION_CODE='"+orgcode+"'")+" and PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE not in (select REGION_CODE from com_mems_organ)) ");
		List lst0_1 = getListSQL(sb0_1.toString());
		int size1 = lst0_1.size();
		lst0.addAll(lst0_1);
		StringBuilder sb0_2 = new StringBuilder();
		sb0_2.append("select distinct REGION_CODE as CODE,REGION_EDU as NAME,PARENT_CODE as PRCODE,'2' as CODETYPE ");
		sb0_2.append("from com_mems_organ ");
		sb0_2.append("where "+(size0>0?"PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgcode+"')":(size1>0?"PARENT_CODE='"+orgcode+"'":"REGION_CODE='"+orgcode+"'"))+" and REGION_CODE not in (select PARENT_CODE from com_mems_organ) ");		
		List lst0_2 = getListSQL(sb0_2.toString());
		int size2 = lst0_2.size();
		lst0.addAll(lst0_2);
		StringBuilder sb1 = new StringBuilder();
		sb1.append("select distinct t1.xx_jbxx_id as CODE,t1.xxmc as NAME,t1.SSZGJYXZDM as PRCODE,'3' as CODETYPE ");
		sb1.append("from zxxs_xx_jbxx t1 ");
		sb1.append("where t1.dwlx='2' and (t1.xx_jbxx_id='"+orgcode+"' or t1.xx_jbxx_id='").append(schoolCode).append("' ");
		sb1.append(size2>0?"or t1.xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in ('"+schoolCode+"'))":"");
		sb1.append(size1>0?"or t1.xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+schoolCode+"'))":"");
		sb1.append(size0>0?"or t1.xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+schoolCode+"')))":"");
		sb1.append(")");
		List lst1 = getListSQL(sb1.toString());
		lst0.addAll(lst1);
		//sb.append("union all ");
		StringBuilder sb2 = new StringBuilder();
		sb2.append("select distinct XX_BJXX_ID as CODE,BJMC as NAME,XX_JBXX_ID as PRCODE,'4' as CODETYPE ");
		sb2.append("from ZXXS_XX_BJXX ");
		sb2.append("where (xx_jbxx_id='"+orgcode+"' or xx_jbxx_id='").append(schoolCode).append("' ");
		sb2.append(size2>0?"or xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in ('"+schoolCode+"'))":"");
		sb2.append(size1>0?"or xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+schoolCode+"'))":"");		
		sb2.append(size0>0?"or xx_jbxx_id IN (select xx_jbxx_id from zxxs_xx_jbxx where SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+schoolCode+"')))":"");
		sb2.append(")");
		List lst2 = getListSQL(sb2.toString());
		lst0.addAll(lst2);
		return lst0;
	}
		
	public Object getXjzt(String state) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT distinct CODE, NAME FROM SYS_ENUM_VALUE WHERE DIC_TYPE='XTGL_YDLXPZ' AND FLAG='1'");
		return getListSQL(sb.toString());
	}

	public Object getKsKm(String params) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct kmid as CODEID, subjectname as CODENAME from cus_kw_examsubject where lcid='")
		.append(params).append("' order by substr(SUBJECTNAME,11,length(substr(SUBJECTNAME,11))-length('批'))+0 asc");
		return getListSQL(sb.toString());
	}
	public Object getNjFromEnum() {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct code AS CODEID,name AS CODENAME from SYS_ENUM_VALUE where DIC_TYPE='ZD_XT_NJDM' order by order_num");
		return getListSQL(sb.toString());
	}
	
	public Object getKeCheng() {
		String sql="SELECT distinct NAME AS CODEID,NAME AS CODENAME FROM SYS_ENUM_VALUE WHERE DIC_TYPE='ZD_KM' order by CODEID";
		return getListSQL(sql);
	}
	
	public Object getJieye() {
		String sql="select distinct code AS CODEID,name AS CODENAME from SYS_ENUM_VALUE where DIC_TYPE='ZD_YWB_YWLB' and code in('42','44','46')";
		return getListSQL(sql);
	}
	
	public String getOrganNjBjTree(String type,MBspInfo bspInfo){
		List<OrganTree> list=getTreeList(null,0,bspInfo,type);
		JSONArray array=JSONArray.fromObject(list);
		return array.toString().replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true");
	}
	
	private List<OrganTree> getTreeList(String parentid,int i,MBspInfo bspInfo,String type){
		String orgcode = bspInfo.getOrgan().getOrganCode();
		String sql="";
		if("1".equals(bspInfo.getUserType()) && orgcode.endsWith("0000000000") && (parentid==null || "".equals(parentid)) && i==0 ){
			//获取省级组考单位
			sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ORDER BY REGION_CODE ";
		}else if(i==1 && orgcode.endsWith("0000000000") || i==0 && orgcode.endsWith("00000000")){
			//获取市级组考单位
			if(orgcode.endsWith("00000000") && i==0){
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ";
			}else{
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN " +
						" WHERE PARENT_CODE='"+parentid+"' UNION ALL " +
						" SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,2 AS CODETYPE FROM ZXXS_XX_JBXX T1 " +
						" WHERE T1.dwlx='2' and T1.SSZGJYXZDM='"+parentid+"' ";
			}
		}else if(orgcode.endsWith("000000") && i==0 || i==1 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==2 ){
			//获取县级组考单位
			if(orgcode.endsWith("000000") && i==0){
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ";
			}else{
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN " +
						" WHERE PARENT_CODE='"+parentid+"' UNION ALL " +
						" SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,2 AS CODETYPE FROM ZXXS_XX_JBXX T1 " +
						" WHERE T1.dwlx='2' and T1.SSZGJYXZDM='"+parentid+"' ";
			}
		}else if(("2".equals(bspInfo.getUserType()) && i==0 && (parentid==null || "".equals(parentid))) ||
				orgcode.endsWith("000000") && i==1 || i==2 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==3){
			//获取参考单位
			sql = " SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,2 AS CODETYPE FROM ZXXS_XX_JBXX T1 WHERE T1.dwlx='2' ";
			sql += " and T1.SSZGJYXZDM='"+parentid+"' ";
			if("1".equals(bspInfo.getUserType())){
				sql += " and (T1.SSZGJYXZDM='"+parentid+"' or T1.XX_JBXX_ID='"+parentid+"') ";
			}else if("2".equals(bspInfo.getUserType())){
				sql += " and (T1.XX_JBXX_ID='"+orgcode+"' or T1.SSZGJYXZDM='"+orgcode+"') ";
			}
		/*}else if(("2".equals(bspInfo.getUserType()) && i==0 && (parentid==null || "".equals(parentid))) || 
				("2".equals(bspInfo.getUserType()) && i==1) ||
				orgcode.endsWith("000000") && i==2 || i==3 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==4){
			//获取年级
			sql = " SELECT distinct CONCAT(T2.XX_JBXX_ID,'_',T1.CODE) AS CODEID,T1.NAME AS CODENAME,3 AS CODETYPE"+
					" FROM SYS_ENUM_VALUE T1"+
					" LEFT JOIN ZXXS_XX_NJXX T2 ON T1.CODE=T2.XJNJDM"+
					" WHERE T2.XX_JBXX_ID='"+parentid+"' AND T1.DIC_TYPE='ZD_XT_NJDM' ";
		}else if(parentid.contains("_") && !"5".equals(type)){
			//获取班级
			String xxdm = "";
			String njdm = "";
			if(parentid.contains("_")){
				xxdm = parentid.split("_")[0];
				njdm = parentid.split("_")[1];
			}
			sql = " SELECT distinct T1.XX_BJXX_ID AS CODEID,T1.BJMC AS CODENAME,4 AS CODETYPE"+
					" FROM ZXXS_XX_BJXX T1"+
					" LEFT JOIN ZXXS_XX_NJXX T2 ON T1.XX_NJXX_ID=T2.XX_NJXX_ID"+
					" LEFT join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=T1.xx_jbxx_id"+
					" LEFT JOIN COM_MEMS_ORGAN T4 ON xx.SSZGJYXZDM=T4.REGION_CODE"+
					" LEFT JOIN COM_MEMS_ORGAN T5 ON T4.PARENT_CODE=T5.REGION_CODE"+
					" WHERE T1.yxbz='1' and T2.XJNJDM='"+njdm+"' AND T1.XX_JBXX_ID='"+xxdm+"'";
			if("1".equals(bspInfo.getUserType())){
				sql += " AND (T4.REGION_CODE='"+orgcode+"' OR T5.REGION_CODE='"+orgcode+"' OR T5.PARENT_CODE='"+orgcode+"')"+
					" ORDER BY T1.BJMC ";
			}*/			
		}else{
			return null;
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		List<OrganTree> array=new ArrayList<OrganTree>();
		for (Map<String, Object> map : list) {
			OrganTree tree=new OrganTree();
			tree.setId(StringUtil.getString(map.get("CODEID")));
			tree.setText(StringUtil.getString(map.get("CODENAME")));
			tree.setIndex(Integer.parseInt(StringUtil.getString(map.get("CODETYPE"))));
			List<OrganTree> childList=getTreeList(StringUtil.getString(map.get("CODEID")),i+1,bspInfo,type);
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
	
	public Object bynd() {
		String sql="select distinct bynd AS CODEID,bynd AS CODENAME from zxxs_xs_xsby group by bynd";
		return getListSQL(sql);
	}

	public Object sysEnumXb(){
		String sql="SELECT distinct CODE AS CODEID,NAME AS CODENAME FROM SYS_ENUM_VALUE WHERE DIC_TYPE='ZD_GB_XBM'";
		return getListSQL(sql);
	}
	
	public Object sysEnumDj(){
		String sql="SELECT distinct CODE AS CODEID,NAME AS CODENAME FROM SYS_ENUM_VALUE WHERE DIC_TYPE='ZD_XT_NJDM' order by order_num";
		return getListSQL(sql);
	}
	
	public Object getKeChengBySchool(MBspInfo bspInfo){
		String sql="SELECT distinct NAME AS CODEID,NAME AS CODENAME FROM SYS_ENUM_VALUE WHERE DIC_TYPE='ZD_KM' order by CODEID";//"SELECT DISTINCT bjmc AS CODEID,bjmc AS CODENAME FROM zxxs_xx_bjxx where yxbz='1'";
		/*if("2".equals(bspInfo.getUserType())){
			sql += " WHERE xx_jbxx_id='"+bspInfo.getOrgan().getOrganCode()+"'";
		}*/
		return getListSQL(sql);
	}
	
	public Object getRole() {
		String sql="SELECT DISTINCT ROLECODE AS CODEID,ROLENAME AS CODENAME FROM T_QXGL_ROLEINFO where state='1'";
		return getListSQL(sql);
	}
	
	public Map<String, Object> getUserDz(String lcid,String kdid) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql="SELECT DISTINCT ifnull((SELECT DISTINCT T7.BUILDNAME as KDMC FROM CUS_KW_EXAMSCHOOL T6 "+
				"LEFT JOIN CUS_KW_BUILDING T7 ON T7.SCHOOL_ID_QG = T6.XXDM WHERE T6.LCID='"+lcid+"' and T6.kdid='"+kdid+"'),"+
				"(SELECT xxdz FROM zxxs_xx_jbxx WHERE dwlx='1' and SSZGJYXZDM in (SELECT SSZGJYXZDM FROM zxxs_xx_jbxx WHERE xx_jbxx_id=t1.dwid) limit 1)) AS DZ "+
				"FROM CUS_KW_EXAMROUND t1 WHERE t1.lcid='"+lcid+"'";
		List<Map<String, Object>> list = getListSQL(sql);
		if(list.size()>0)
			map.putAll(list.get(0));
		return map;
	}
}
