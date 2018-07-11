package com.jiajie.service.rollReport.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.rollReport.RollCardService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
@Service("rollCardService")
@SuppressWarnings("all")
public class RollCardServiceImpl extends ServiceBridge implements RollCardService{
	public PageBean getList(String orgn,String nj,String njbj,String xbm,String xmxjh,MBspInfo bspInfo){
		String sql = "SELECT distinct T1.XM,T1.GRBSM,T2.NAME AS XB,T3.NAME AS MZ,T1.SFZJH,T1.CSRQ,"+
				" T4.XXMC,T5.NAME AS NJMC,IFNULL(T6.BJMC,'公共法律知识测试') BJMC,T11.PATH,T4.XXBSM,T1.JTZZ"+
				" FROM ZXXS_XS_JBXX T1"+
				" LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XBM=T2.CODE AND T2.DIC_TYPE='ZD_GB_XBM'"+
				" LEFT JOIN SYS_ENUM_VALUE T3 ON T1.MZM=T3.CODE AND T3.DIC_TYPE='ZD_GB_MZM'"+
				" LEFT JOIN ZXXS_XX_JBXX T4 ON T1.XX_JBXX_ID=T4.XX_JBXX_ID"+
				" LEFT JOIN ZXXS_XX_NJXX T7 ON T1.XX_NJXX_ID=T7.XX_NJXX_ID"+
				" LEFT JOIN SYS_ENUM_VALUE T5 on T7.XJNJDM=T5.CODE AND T5.DIC_TYPE='ZD_XT_NJDM'"+
				" LEFT JOIN ZXXS_XX_BJXX T6 ON T1.XX_BJXX_ID=T6.XX_BJXX_ID"+
				" LEFT JOIN ZXXS_XS_PIC T11 ON T1.XS_JBXX_ID=T11.XS_JBXX_ID"+				
				" LEFT JOIN COM_MEMS_ORGAN T9 ON T4.SSZGJYXZDM=T9.REGION_CODE"+
				" LEFT JOIN COM_MEMS_ORGAN T10 ON T9.PARENT_CODE=T10.REGION_CODE WHERE 1=1 ";
		if("3".equals(bspInfo.getUserType())){
			//参考单位
			sql += " AND T1.XX_JBXX_ID='"+bspInfo.getOrgan().getOrganCode()+"' ";
		}else if ("2".equals(bspInfo.getUserType())) {	
			sql += " AND T9.REGION_CODE='"+bspInfo.getOrgan().getOrganCode()+"' ";
		}else if("1".equals(bspInfo.getUserType())){
			//组考单位
			sql += " AND (T9.REGION_CODE='"+bspInfo.getOrgan().getOrganCode()+"' OR " +
					" T10.REGION_CODE='"+bspInfo.getOrgan().getOrganCode()+"' OR " +
					" T10.PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+"') ";
		}
		if(orgn!=null && !"".equals(orgn)){
			sql += " AND T1.XX_JBXX_ID IN ('"+orgn.replaceAll(",", "','")+"') ";
		}
		if(nj!=null && !"".equals(nj)){
			sql += " AND T7.XJNJDM='"+nj+"' ";
		}
		if(njbj!=null && !"".equals(njbj)){
			sql += " AND T1.XX_BJXX_ID IN ('"+njbj.replaceAll(",", "','")+"') ";
		}
		if(xbm!=null && !"".equals(xbm)){
			sql += " AND T1.XBM='"+xbm+"' ";
		}
		if(xmxjh!=null && !"".equals(xmxjh)){
			sql += " AND (T1.GRBSM LIKE binary('%"+xmxjh+"%') OR T1.XM LIKE binary('%"+xmxjh+"%')) ";
		}
		sql += " ORDER BY T1.XX_JBXX_ID,T1.XX_NJXX_ID,T1.XX_BJXX_ID ";
		return getSQLPageBean(sql);
	}
	
	public PageBean getStudentList(String xjh){
		String sql = " SELECT distinct T1.XM,T1.GRBSM,T2.NAME AS XB,T3.NAME AS MZ,T1.SFZJH,"+
		"T1.CSRQ,T4.XXMC,T5.NAME AS NJMC,IFNULL(T6.BJMC,'公共法律知识测试') BJMC,T11.PATH,T4.XXBSM"+
		" FROM ZXXS_XS_JBXX T1"+
		" LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XBM=T2.CODE AND T2.DIC_TYPE='ZD_GB_XBM'"+
		" LEFT JOIN SYS_ENUM_VALUE T3 ON T1.MZM=T3.CODE AND T3.DIC_TYPE='ZD_GB_MZM'"+
		" LEFT JOIN ZXXS_XX_JBXX T4 ON T1.XX_JBXX_ID=T4.XX_JBXX_ID"+
		" LEFT JOIN ZXXS_XX_NJXX T7 ON T1.XX_NJXX_ID=T7.XX_NJXX_ID"+
		" LEFT JOIN SYS_ENUM_VALUE T5 on T7.XJNJDM=T5.CODE AND T5.DIC_TYPE='ZD_XT_NJDM'"+
		" LEFT JOIN ZXXS_XX_BJXX T6 ON T1.XX_BJXX_ID=T6.XX_BJXX_ID"+
		" LEFT JOIN ZXXS_XS_PIC T11 ON T1.XS_JBXX_ID=T11.XS_JBXX_ID WHERE 1=1 ";
		if(xjh!=null && !"".equals(xjh)){
			sql += " AND T1.GRBSM IN ('"+xjh.replaceAll(",", "','")+"') ";
		}
		sql += " ORDER BY T1.XX_JBXX_ID,T1.XX_NJXX_ID,T1.XX_BJXX_ID ";
		return getSQLPageBean(sql);
	}		

	public PageBean getClassList(String orgn,String nj, MBspInfo bspInfo) { 
		String sql = "select distinct t1.XX_JBXX_ID,"+
				"(select xxmc from zxxs_xx_jbxx where xx_jbxx_id=t1.xx_jbxx_id) BJMC,"+
				"(select count(xs_jbxx_id) from zxxs_xs_jbxx t3 where t3.xx_jbxx_id=t1.xx_jbxx_id) XSNUM,"+
				"(select count(distinct t4.xs_jbxx_id) from zxxs_xs_jbxx t4,zxxs_xs_pic t5 where t4.xs_jbxx_id=t5.xs_jbxx_id and t4.xx_jbxx_id=t1.xx_jbxx_id) XSPICNUM "+
				"from zxxs_xx_jbxx t1";
					
		if("3".equals(bspInfo.getUserType())){
			//参考单位
			sql += " where t1.dwlx='2'";
			sql += (StringUtil.isNotNullOrEmpty(orgn)?" and T1.XX_JBXX_ID='"+orgn+"'":"");
			sql += " AND t1.XX_JBXX_ID='"+bspInfo.getOrgan().getOrganCode()+"' ";
		}else if ("2".equals(bspInfo.getUserType())) {
			sql += " WHERE t1.dwlx='2'";
			sql += (StringUtil.isNotNullOrEmpty(orgn)?" and T1.XX_JBXX_ID='"+orgn+"'":"");
			sql += " AND T1.SSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+"' ";
		}else if("1".equals(bspInfo.getUserType())){
			//组考单位
			sql += " left join com_mems_organ t9 on t1.sszgjyxzdm=t9.region_code"+
					" left join com_mems_organ t10 on t9.parent_code=t10.region_code WHERE t1.dwlx='2'";		
			sql += (StringUtil.isNotNullOrEmpty(orgn)?
					" and (T1.XX_JBXX_ID='"+orgn+"' OR T9.REGION_CODE='"+orgn+"' OR " +
					" T10.REGION_CODE='"+orgn+"' OR T10.PARENT_CODE='"+orgn+"')":"");
			sql += " AND (T9.REGION_CODE='"+bspInfo.getOrgan().getOrganCode()+"' OR " +
					" T10.REGION_CODE='"+bspInfo.getOrgan().getOrganCode()+"' OR " +
					" T10.PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+"') ";
		}
		//(StringUtil.isNotNullOrEmpty(nj)?" and t2.XJNJDM='"+nj+"'":"")+T1.XX_NJXX_ID=t2.XX_NJXX_ID,ZXXS_XX_NJXX t2 
		sql+=" order by BJMC ";
		return getSQLPageBean(sql);
	}

	public List<Map<String, Object>> getPicList(String orgn,String bjids) {
		String bjid = "";
		for (String temp : bjids.split(",")) {
			bjid+="'"+temp+"',";
		}
		bjid=StringUtils.stripEnd(bjid, ",");
		String sql = "select distinct t1.XM,t1.GRBSM,t1.SFZJH,t1.XX_BJXX_ID,max(t2.PATH) PATH"+
				 " from ZXXS_XS_JBXX t1"+
				 " left join ZXXS_XS_PIC t2 on t2.XS_JBXX_ID=T1.XS_JBXX_ID "+
				 " where t1.XX_JBXX_ID='"+orgn+"'"+
				 (bjid==null||"".equals(bjid)||"''".equals(bjid)?"":" and t1.XX_BJXX_ID in ("+bjid+")")+
				 " group by t1.XX_BJXX_ID,t1.XM,t1.GRBSM,t1.SFZJH";
		List list = this.getListSQL(sql);
		return list;
	}

	public List<Map<String, Object>> getSchoolClassList(String orgn, String bjids) { 
		String bjid = "";
		for (String temp : bjids.split(",")) {
			bjid+="'"+temp+"',";
		}
		bjid=StringUtils.stripEnd(bjid, ",");
		String sql = "select distinct XXMC,T4.NAME AS NJMC,BJMC"+//,t2.XX_BJXX_ID
				" from ZXXS_XX_JBXX t1"+
				" left join ZXXS_XX_BJXX t2 on t2.XX_JBXX_ID=t1.XX_JBXX_ID"+
				" left join ZXXS_XX_NJXX T3 on t3.XX_NJXX_ID=t2.XX_NJXX_ID"+
				" left join SYS_ENUM_VALUE T4 on T4. CODE=T3.XJNJDM and T4.DIC_TYPE = 'ZD_XT_NJDM'"+
				" WHERE 0=0"+
				" and t1.XX_JBXX_ID in (select XX_JBXX_ID from zxxs_xs_jbxx where XX_JBXX_ID='"+orgn+"')"+
				" AND t1.XX_JBXX_ID='"+orgn+"'"+
				(bjid==null||"".equals(bjid)||"''".equals(bjid)?"":" and t2.XX_BJXX_ID in ("+bjid+")")+
				" order by XXMC";
		List list = this.getListSQL(sql);
		return list; 
	}
}
