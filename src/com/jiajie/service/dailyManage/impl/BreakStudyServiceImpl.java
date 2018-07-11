package com.jiajie.service.dailyManage.impl;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.dailyManage.BreakStudyService;
import com.jiajie.util.StringUtil;

@Service("breakStudyService")
@SuppressWarnings("all")
public class BreakStudyServiceImpl extends ServiceBridge implements BreakStudyService{

	public PageBean getListPage(String organCode, String schools,String flag, String xj_xm_sfz) {
		StringBuilder sb = new StringBuilder();
		sb.append("select cxbs.YYID,cxbs.XM,cxbs.XJH,ifnull(zxsj.CSRQ,substr(zxsj.sfzjh,7,8)) AS CSRQ,zxsj.sfzjh SFZH,zxn.njmc NJ,zxb.bjmc BJ,")
		.append("date_format(cxbs.cdate,'%Y-%m-%d') SQRQ,cxbs.reason as YYYY,cxbs.flag SHZT,FILEPATH,")
		.append("case cxbs.flag when '1' then '已审核' when '0' then '未审核' end as ZT")
		.append(" from cus_xj_breakstudy cxbs")
		.append(" left join zxxs_xs_jbxx zxsj on zxsj.grbsm = cxbs.xjh")
		.append(" left join zxxs_xx_njxx zxn on zxsj.xx_njxx_id = zxn.xx_njxx_id")
		.append(" left join zxxs_xx_bjxx zxb on zxsj.xx_bjxx_id = zxb.xx_bjxx_id")
		.append(" left join ZXXS_XX_JBXX cmop on cmop.xx_jbxx_id = zxsj.xx_jbxx_id")
		.append(" where (cmop.SSZGJYXZDM='"+organCode+"' or cmop.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+organCode+
					"') or cmop.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+
					organCode+"')))");
		if(StringUtil.isNotNullOrEmpty(schools)){
			sb.append(" and zxsj.xx_jbxx_id in('")
			.append(schools.replaceAll(",", "','")).append("')");
		}
		if(StringUtil.isNotNullOrEmpty(xj_xm_sfz)){
			sb.append(" and (cxbs.xjh like binary('%").append(xj_xm_sfz).append("%') or cxbs.xm like binary('%")
			.append(xj_xm_sfz).append("%') or zxsj.sfzjh like binary('%").append(xj_xm_sfz).append("%')) ");
		}
		if(StringUtil.isNotNullOrEmpty(flag)){
			sb.append(" and cxbs.flag = '").append(flag).append("'");
		}
		return getSQLPageBean(sb.toString());
	}

	public com.jiajie.bean.MsgBean audit(String yyids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("update cus_xj_breakstudy set flag = '1' where yyid in ('")
			.append(yyids).append("')");
			update(sb.toString());
			this.getMsgBean(true,"成功完成审核！");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"审核失败！");
		}
		return MsgBean;
	}

}
