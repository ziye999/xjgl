package com.jiajie.service.integratedQuery.impl;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.integratedQuery.StudentQueryService;
import com.jiajie.util.bean.MBspInfo;
@Service("studentQueryService")
public class StudentQueryServiceImpl extends ServiceBridge implements StudentQueryService { 

	public PageBean graduateStudents(MBspInfo mp, String zgjyj, String bynd, String xsxm, String xjh) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.grbsm AS XJH, t.xm AS XSXM, t.BYND, case when t.xbm='1' then '男' else '女' end as XB,");
		sql.append("IFNULL(date_format(STR_TO_DATE(t.csrq,'%Y%m%d'),''),t.csrq) as CSRQ,");
		sql.append("IFNULL(date_format(STR_TO_DATE(t.byjyny,'%Y%m%d'),''),t.byjyny) as BYNY,t.BYZSH ");		
		sql.append("from zxxs_xs_xsby t where 0=0 ");
		if(bynd != null && !"".equals(bynd)){
			sql.append(" and t.bynd =  '").append(bynd).append("'");
		}
		if(xsxm != null && !"".equals(xsxm)){
			sql.append(" and t.xm like binary('%").append(xsxm).append("%')");
		}
		if(xjh != null && !"".equals(xjh)){
			sql.append(" and t.grbsm like binary('%").append(xjh).append("%')");
		}
		if("3".equals(mp.getUserType())){
			sql.append(" and t.xx_jbxx_id = '").append(mp.getRegionCode()).append("'");
		}else if("1".equals(mp.getUserType())||"2".equals(mp.getUserType())){
			if(zgjyj != null && !"".equals(zgjyj)){
				zgjyj=zgjyj.replace(",","','");
				sql.append(" and t.xx_jbxx_id in ('").append(zgjyj).append("')");
			}else{
				sql.append(" and t.xx_jbxx_id in (select t.XX_JBXX_ID from ZXXS_XX_JBXX t,V_COM_MEMS_ORGAN v where t.SSZGJYXZDM = v.REGION_CODE and (v.PARENT_CODE ='");
				sql.append(mp.getOrgan().getOrganCode())
					.append("' or v.REGION_CODE = '").append(mp.getOrgan().getOrganCode())
					.append("' or v.REGION_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='")
					.append(mp.getOrgan().getOrganCode()).append("'))").append("))");
			}
		} 
		return getSQLPageBean(sql.toString());
	}

}
